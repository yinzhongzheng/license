package com.yzz.license.client.Exception;

/**
 * describe:
 * E-mail:yzzstyle@163.com  date:2018/12/15
 *
 * @Since 0.0.1
 */
public class LicenseInstallFailedException extends Throwable{


    public LicenseInstallFailedException(){
        new LicenseInstallFailedException("the license install failed!");
    }

    public LicenseInstallFailedException(String msg){
        super(msg);
    }
}
