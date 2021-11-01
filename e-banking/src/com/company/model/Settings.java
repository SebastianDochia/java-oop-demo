package com.company.model;

public class Settings {
    private String userName;
    private String account;
    private String autoConfirm;
    private String defaultFile;

    private final static Settings instance = new Settings();

    public static Settings getSettings() {
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String isAutoConfirm() {
        return autoConfirm;
    }

    public void setAutoConfirm(String autoConfirm) {
        this.autoConfirm = autoConfirm;
    }

    public String getDefaultFile() {
        return defaultFile;
    }

    public void setDefaultFile(String defaultFile) {
        this.defaultFile = defaultFile;
    }

    private Settings() {}
}
