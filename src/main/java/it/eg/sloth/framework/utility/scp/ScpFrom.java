package it.eg.sloth.framework.utility.scp;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class ScpFrom {

    public static final void scpFrom(OutputStream outputStream, String host, String user, String password, String remoteFile) throws IOException, JSchException {
        File file = null;
        try {
            file = File.createTempFile("localtemp", "scp");
            file.deleteOnExit();

            scpFrom(file.getAbsolutePath(), host, user, password, remoteFile);
            try (InputStream inputStream = new FileInputStream(file)) {
                IOUtils.copy(inputStream, outputStream);
            }

        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    /**
     * Trasferisce un file dall'host remoto
     *
     * @param localFile
     * @param host
     * @param user
     * @param password
     * @param remoteFile
     * @throws IOException
     * @throws JSchException
     */
    public static final void scpFrom(String localFile, String host, String user, String password, String remoteFile) throws IOException, JSchException {

        FileOutputStream fos = null;
        try {
            String prefix = null;
            if (new File(localFile).isDirectory()) {
                prefix = localFile + File.separator;
            }

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);

            // username and password will be given via UserInfo interface.
            UserInfo ui = new MyUser(password);
            session.setUserInfo(ui);
            session.connect();

            // exec 'scp -f rfile' remotely
            String command = "scp -f " + remoteFile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ')
                        break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0; ; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // read a content of lfile
                fos = new FileOutputStream(prefix == null ? localFile : prefix + file);
                int foo;
                while (true) {
                    if (buf.length < filesize)
                        foo = buf.length;
                    else
                        foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L)
                        break;
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    throw new RuntimeException("Errore");
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            session.disconnect();

        } catch (Exception e) {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ee) {
            }
        }
    }

    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0)
            return b;
        if (b == -1)
            return b;

        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                throw new RuntimeException(sb.toString());
            }
            if (b == 2) { // fatal error
                throw new RuntimeException(sb.toString());
            }
        }
        return b;
    }

}