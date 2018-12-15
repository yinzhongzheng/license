package com.yzz.license.client.Exception;

/**
 * describe:
 * E-mail:yzzstyle@163.com  date:2018/12/15
 *
 * @Since 0.0.1
 */
public class LicenseExpiredException extends Throwable {

    public LicenseExpiredException(){
        new LicenseExpiredException("the license is expired !");
    }

    public LicenseExpiredException(String msg){
        super(msg);
    }
}
