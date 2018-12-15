package com.yzz.license.server.bean;

/**
 * describe:
 * E-mail:yzzstyle@163.com  date:2018/12/5
 * 映射 license-make-config.properties
 * @Since 0.0.1
 */
public class LicenseConfigBean {

    private String licPath;
    private String issued;
    private String notBefore;
    private String notAfter;
    private String consumerType;
    private int consumerAmount;
    private String info;
    /**
     * 私钥的别名
     */
    private String priAlias;
    /**
     * 该密码生成密钥对的密码
     */
    private String privateKeyPwd;
    /**
     * 使用keytool生成密钥对时设置的密钥库的访问密码
     */
    private String keyStorePwd;
    private String subject;
    private String priPath;

    public String getLicPath() {
        return licPath;
    }

    public void setLicPath(String licPath) {
        this.licPath = licPath;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(String notBefore) {
        this.notBefore = notBefore;
    }

    public String getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(String notAfter) {
        this.notAfter = notAfter;
    }

    public String getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(String consumerType) {
        this.consumerType = consumerType;
    }

    public int getConsumerAmount() {
        return consumerAmount;
    }

    public void setConsumerAmount(int consumerAmount) {
        this.consumerAmount = consumerAmount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPriAlias() {
        return priAlias;
    }

    public void setPriAlias(String priAlias) {
        this.priAlias = priAlias;
    }

    public String getPrivateKeyPwd() {
        return privateKeyPwd;
    }

    public void setPrivateKeyPwd(String privateKeyPwd) {
        this.privateKeyPwd = privateKeyPwd;
    }

    public String getKeyStorePwd() {
        return keyStorePwd;
    }

    public void setKeyStorePwd(String keyStorePwd) {
        this.keyStorePwd = keyStorePwd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPriPath() {
        return priPath;
    }

    public void setPriPath(String priPath) {
        this.priPath = priPath;
    }
}
