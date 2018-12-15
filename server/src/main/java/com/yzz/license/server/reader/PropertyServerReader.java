package com.yzz.license.server.reader;


import com.yzz.license.server.bean.LicenseConfigBean;

import java.util.Properties;

/**
 * describe:读取配置文件 .properties
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since 0.0.1
 */
public class PropertyServerReader extends AbstractPropertyReader<LicenseConfigBean>{


    /**
     * 将Properties ==> bean
     *
     * @param prop
     */
    protected void resolveProperties(Properties prop) {
        t = new LicenseConfigBean();
        t.setPriAlias(prop.getProperty("private.key.alias"));
        t.setPrivateKeyPwd(prop.getProperty("private.key.pwd"));
        t.setKeyStorePwd(prop.getProperty("key.store.pwd"));
        t.setSubject(prop.getProperty("subject"));
        t.setPriPath(prop.getProperty("priPath"));
        t.setLicPath(prop.getProperty("licPath"));
        t.setIssued(prop.getProperty("issuedTime"));
        t.setNotBefore(prop.getProperty("notBefore"));
        t.setNotAfter(prop.getProperty("notAfter"));
        t.setConsumerType(prop.getProperty("consumerType"));
        t.setConsumerAmount(Integer.valueOf(prop.getProperty("consumerAmount")));
        t.setInfo(prop.getProperty("info"));
    }
}
