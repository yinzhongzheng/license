package com.yzz.license.client.reader;


import com.yzz.license.client.bean.ClientValidConfigBean;

import java.util.Properties;

/**
 * describe: 客户端 读取配置文件 .properties
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since 0.0.1
 */
public class PropertyClientReader extends AbstractPropertyReader<ClientValidConfigBean> {

    /**
     * 将Properties ==> bean
     *
     * @param prop
     */
    @Override
    protected void resolveProperties(Properties prop) {
        t = new ClientValidConfigBean();
        t.setPubAlias(prop.getProperty("public.alias"));
        t.setKeyStorePwd(prop.getProperty("key.store.pwd"));
        t.setPubPath(prop.getProperty("public.store.path"));
    }

}
