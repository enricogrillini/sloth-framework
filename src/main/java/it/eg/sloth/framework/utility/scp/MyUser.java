package it.eg.sloth.framework.utility.scp;

import com.jcraft.jsch.UserInfo;

public class MyUser implements UserInfo {
    private String password;

    public MyUser(String password) {
        this.password = password;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassphrase(String arg0) {
        return false;
    }

    @Override
    public boolean promptPassword(String arg0) {
        return true;
    }

    @Override
    public boolean promptYesNo(String arg0) {
        return true;
    }

    @Override
    public void showMessage(String arg0) {
        // NOP
    }

}