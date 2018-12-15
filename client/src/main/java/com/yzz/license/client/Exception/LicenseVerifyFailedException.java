package com.yzz.license.client.Exception;

/**
 * describe:
 * E-mail:yzzstyle@163.com  date:2018/12/15
 *
 * @Since 0.0.1
 */
public class LicenseVerifyFailedException extends Throwable{

    public LicenseVerifyFailedException(){
        new LicenseVerifyFailedException("the license verify failed ！");
    }

    public LicenseVerifyFailedException(String msg){
        super(msg);
    }
}
