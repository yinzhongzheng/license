package com.yzz.license.client.valid;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * describe:LicenseManager 的holder
 * E-mail:yzzstyle@163.com  date:2018/12/5
 * 封装，禁止外部访问
 * @Since 0.0.1
 */
 public class LicenseManagerHolder {

    private static volatile LicenseManager licenseManager;

    /**
     * 防止反射破坏
     */
    private LicenseManagerHolder(){
        if(null != licenseManager){
            throw new IllegalStateException("LicenseManager's instance had be created ! ");
        }
    }

    /**
     * 初始化LicenseManager
     * @param licenseParam:初始化必须提供的参数
     * @return
     */
    static LicenseManager initManager(LicenseParam licenseParam){
        licenseManager = new LicenseManager(licenseParam);
        return licenseManager;
    }

     static LicenseManager getLicenseManager() throws IllegalAccessException {
        if (null == licenseManager){
            throw new IllegalAccessException("init method must be call before this method");
        }
        return licenseManager;
    }
}
