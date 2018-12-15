package com.yzz.license.server.manager;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * describe:LicenseManager 的holder
 * E-mail:yzzstyle@163.com  date:2018/12/5
 * @Since 0.0.1
 */
public class LicenseManagerHolder {

    private LicenseManagerHolder(){}

    private static LicenseManager licenseManager;

    /**
     * 初始化LicenseManager
     * @param licenseParam:初始化必须提供的参数
     * @return
     */
    public static LicenseManager initManager(LicenseParam licenseParam){
        licenseManager = new LicenseManager(licenseParam);
        return licenseManager;
    }

    public static LicenseManager getLicenseManager() throws IllegalAccessException {
        if (null == licenseManager){
            throw new IllegalAccessException("init method must be call before this method");
        }
        return licenseManager;
    }
}
