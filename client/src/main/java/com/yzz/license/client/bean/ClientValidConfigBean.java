package com.yzz.license.client.bean;

/**
 * describe: 客户端验证配置bean
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since 0.0.1
 */
public class ClientValidConfigBean {
    /**
     * 公钥别名
     */
    private String pubAlias;
    /**
     * 该密码是在使用keytool生成密钥对时设置的密钥库的访问密码
     */
    private String keyStorePwd;
    /**
     * 系统的统一识别码
     */
    private String onlykey;
    /**
     * 公钥库路径
     */
    private String pubPath;

    public String getPubAlias() {
        return pubAlias;
    }

    public void setPubAlias(String pubAlias) {
        this.pubAlias = pubAlias;
    }

    public String getKeyStorePwd() {
        return keyStorePwd;
    }

    public void setKeyStorePwd(String keyStorePwd) {
        this.keyStorePwd = keyStorePwd;
    }

    public String getOnlykey() {
        return onlykey;
    }

    public void setOnlykey(String onlykey) {
        this.onlykey = onlykey;
    }

    public String getPubPath() {
        return pubPath;
    }

    public void setPubPath(String pubPath) {
        this.pubPath = pubPath;
    }
}
