package it.eg.sloth.framework.utility.mail.asynchronousMail;

import it.eg.sloth.framework.FrameComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * Singleton per la gestione della configurazione applicativa
 *
 * @author Enrico Grillini
 */
@Slf4j
public class AsynchronousMailSingleton extends FrameComponent {
//
//    private boolean sending;
//    private Set<MailToSend> mailSet;
//
//    private Map<Timestamp, MailStatistics> trend;
//
//    private static volatile AsynchronousMailSingleton instance = null;
//
//    public enum TipoInvio {
//        FORCE_SYNCRONOUS, TRY_SYNCRONOUS, ASYNCRONOUS
//    }
//
//    private AsynchronousMailSingleton() {
//        mailSet = new HashSet();
//        trend = new LinkedHashMap();
//    }
//
//    public static AsynchronousMailSingleton getInstance() {
//        if (instance == null) {
//            synchronized (AsynchronousMailSingleton.class) {
//                if (instance == null) {
//                    instance = new AsynchronousMailSingleton();
//                }
//            }
//        }
//
//        return instance;
//    }
//
//    public synchronized static AsynchronousMailSingleton initInstance(String rootPath, String fileName) {
//        if (instance == null) {
//            instance = new AsynchronousMailSingleton();
//        }
//        return instance;
//    }
//
//    /**
//     * Invia la masil
//     *
//     * @param email
//     * @throws Exception
//     */
//    private void sendMail(HtmlEmail email) throws Exception {
//        try {
//            log.info("HtmlEmail: SmtpPort " + email.getSmtpPort() + " - SslSmtpPort " + email.getSslSmtpPort());
//
//            email.sendMimeMessage();
//            addSuccess();
//
//        } catch (Exception e) {
//            log.info("HtmlEmail: " + email);
//
//            addFail();
//            throw e;
//        }
//    }
//
//    private synchronized void add(HtmlEmail email) {
//        mailSet.add(new MailToSend(email));
//    }
//
//    /**
//     * Ritorna il numero di mail in coda da inviare
//     *
//     * @return
//     */
//    public int size() {
//        return mailSet.size();
//    }
//
//    /**
//     * Gestione avanzata dell'invio di mail
//     * - trySynchronous = true: prova l'invio sincrono della mail
//     * - trySynchronous = false: gestisce l'invio asincrono della mail
//     * - retry = true: in caso di fallimaento dell'invio sincrono passa alla modalità asincrona
//     * - retry = false: in caso di fallimaento dell'invio sincrono propaga l'errore
//     *
//     * @param email
//     * @param tipoInvio
//     * @throws Exception
//     */
//    public void send(HtmlEmail email, TipoInvio tipoInvio) throws Exception {
//        // Costruisco il mime message
//        email.buildMimeMessage();
//
//        if (tipoInvio == TipoInvio.FORCE_SYNCRONOUS || tipoInvio == TipoInvio.TRY_SYNCRONOUS) {
//            try {
//                // Invio la mail
//                sendMail(email);
//
//            } catch (Exception e) {
//                log.warn("Errore invio mail in modalità sincrona. Mail aggiunta alla coda.", e);
//                if (tipoInvio == TipoInvio.TRY_SYNCRONOUS) {
//                    add(email);
//                } else {
//                    throw e;
//                }
//            }
//
//        } else {
//            add(email);
//        }
//    }
//
//    /**
//     * Invia la mail passata in modalità sincrona
//     *
//     * @param email
//     * @throws Exception
//     * @deprecated
//     */
//    public void sendSyncronous(HtmlEmail email) throws Exception {
//        // Costruisco il mime message
//        email.buildMimeMessage();
//
//        // Invio la mail
//        sendMail(email);
//    }
//
//    private synchronized void addSuccess() {
//
//        Timestamp ora = BaseFunction.trunc(TimeStampUtil.sysdate(), "dd/MM/yyyy HH");
//        MailStatistics mailStatistics = new MailStatistics(1, 0);
//
//        if (trend.containsKey(ora)) {
//            mailStatistics = trend.get(ora);
//            mailStatistics.setSuccess(mailStatistics.getSuccess() + 1);
//        }
//
//        trend.put(ora, mailStatistics);
//    }
//
//    private synchronized void addFail() {
//        Timestamp ora = BaseFunction.trunc(TimeStampUtil.sysdate(), "dd/MM/yyyy HH");
//        MailStatistics mailStatistics = new MailStatistics(0, 1);
//
//        if (trend.containsKey(ora)) {
//            mailStatistics = trend.get(ora);
//            mailStatistics.setFail(mailStatistics.getFail() + 1);
//        }
//
//        trend.put(ora, mailStatistics);
//
//
//    }
//
//    /**
//     * Invia le mail in coda
//     */
//    public void sendQueue() {
//        List<MailToSend> list = new ArrayList();
//        // Gestisco il semaforo di invio per evitare che le mail siano inviate più
//        // volte
//        if (!sending) {
//            synchronized (AsynchronousMailSingleton.class) {
//                if (!sending) {
//                    sending = true;
//                    list = new ArrayList<MailToSend>(mailSet);
//                } else {
//                    return;
//                }
//            }
//        } else {
//            return;
//        }
//
//        // Invio
//        for (MailToSend mailToSend : list) {
//            try {
//                // Invio la mail
//                mailToSend.setLastTry(TimeStampUtil.sysdate());
//                sendMail(mailToSend.getHtmlEmail());
//
//                // In caso di successo la rimuovo dalla coda
//                mailSet.remove(mailToSend);
//
//            } catch (Exception e) {
//                log.warn("Errore invio mail da coda.", e);
//                mailToSend.setSendError(e);
//            }
//        }
//
//        // Sblocco il semaforo
//        sending = false;
//    }
//
//    public int getSend() {
//        int result = 0;
//        for (MailStatistics mailStatistics : trend.values()) {
//            result += mailStatistics.getSuccess();
//        }
//
//        return result;
//    }
//
//    public int getSendToday() {
//        int result = 0;
//        for (Map.Entry<Timestamp, MailStatistics> entry : trend.entrySet()) {
//            if (BaseFunction.trunc(entry.getKey()).equals(TimeStampUtil.truncSysdate())) {
//                result += entry.getValue().getSuccess();
//            }
//        }
//
//        return result;
//    }
//
//    public int getFail() {
//        int result = 0;
//        for (MailStatistics mailStatistics : trend.values()) {
//            result += mailStatistics.getFail();
//        }
//
//        return result;
//    }
//
//    public int getFailToday() {
//        int result = 0;
//        for (Map.Entry<Timestamp, MailStatistics> entry : trend.entrySet()) {
//            if (BaseFunction.trunc(entry.getKey()).equals(TimeStampUtil.truncSysdate())) {
//                result += entry.getValue().getFail();
//            }
//        }
//
//        return result;
//    }
//
//    public DataTable<?> getStatistics() {
//        DataTable<?> table = new Table();
//
//        for (Map.Entry<Timestamp, MailStatistics> entry : trend.entrySet()) {
//            DataRow row = entry.getValue().getRow();
//            row.setTimestamp("ora", entry.getKey());
//
//            table.add().copyFromDataSource(row);
//        }
//
//        return table;
//    }
//
//    public DataTable<?> getDataTable() {
//        Table table = new Table();
//
//        for (MailToSend mail : mailSet) {
//            Row row = table.add();
//
//            row.setTimestamp("SubmitTime", mail.getSubmitDate());
//            row.setString("From", BaseFunction.isNull(mail.getHtmlEmail().getFromAddress()) ? "" : mail.getHtmlEmail().getFromAddress().toString());
//            row.setString("To", BaseFunction.isNull(mail.getHtmlEmail().getToAddresses()) ? "" : mail.getHtmlEmail().getToAddresses().toString());
//            row.setString("Subject", mail.getHtmlEmail().getSubject());
//            row.setTimestamp("LastTry", mail.getLastTry());
//            row.setString("SendError", mail.getSendError() == null ? "" : mail.getSendError().toString());
//        }
//
//        return table;
//    }

}
