package com.yzz.license.client.valid;

import com.yzz.license.client.Exception.LicenseExpiredException;
import com.yzz.license.client.Exception.LicenseInstallFailedException;
import com.yzz.license.client.Exception.LicenseVerifyFailedException;
import com.yzz.license.client.bean.ClientValidConfigBean;
import com.yzz.license.client.reader.PropertyClientReader;
import de.schlichtherle.license.*;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * describe:客户端验证License Api
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since 0.0.1
 */
public class ClientValidLicense {

    private static final String Def_CONF_PATH = "client-valid.properties";

    private static volatile ClientValidLicense clientValidLicense;

    private ClientValidLicense(String onlyKey) {
        //防止反射
        if (null != clientValidLicense){
            throw new IllegalStateException("ClientValidLicense has be created !");
        }
       new ClientValidLicense(Def_CONF_PATH,onlyKey);
    }

    private ClientValidLicense(String confPath, String onlyKey) {
        //防止反射
        if (null != clientValidLicense){
            throw new IllegalStateException("ClientValidLicense has be created !");
        }
        PropertyClientReader reader = new PropertyClientReader();
        ClientValidConfigBean clientValidConfigBean = reader.read(confPath);
        clientValidConfigBean.setOnlykey(onlyKey);
        //初始licenseManger
        LicenseParam param = initLicenseParams(clientValidConfigBean);
        LicenseManagerHolder.initManager(param);

    }

    public static ClientValidLicense getInstance(String onlyKey){
        return getInstance(Def_CONF_PATH,onlyKey);
    }

    /**
     * 需要加上volatile关键字，防止ClientValidLicense 实例化的时候重排序，导致其他获取的对象为null
     * double check 单利
     * @param confPath
     * @param onlyKey
     * @return
     */
    public static ClientValidLicense getInstance(String confPath, String onlyKey){
        if (null != clientValidLicense){
            return clientValidLicense;
        }
        synchronized (ClientValidLicense.class){
            clientValidLicense = new ClientValidLicense(confPath, onlyKey);
        }
        return clientValidLicense;
    }

    /**
     * 初始化参数
     * @param clientValidConfigBean
     * @return
     */
    private LicenseParam initLicenseParams(ClientValidConfigBean clientValidConfigBean) {
        Class<ClientValidLicense> clazz = ClientValidLicense.class;
        Preferences pre = Preferences.userNodeForPackage(clazz);
        CipherParam cipherParam = new DefaultCipherParam(clientValidConfigBean.getKeyStorePwd());
        KeyStoreParam pubStoreParam = new DefaultKeyStoreParam(
                clazz, clientValidConfigBean.getPubPath(),clientValidConfigBean.getPubAlias(), clientValidConfigBean.getKeyStorePwd(), null);

        LicenseParam licenseParam = new DefaultLicenseParam(
                clientValidConfigBean.getOnlykey(), pre, pubStoreParam, cipherParam);
        return licenseParam;
    }

    /**
     * 安装license
     * @param licDir
     * @return
     */
    public void install(String licDir) throws LicenseInstallFailedException, LicenseVerifyFailedException {
        try {
            LicenseManager licenseManager = LicenseManagerHolder.getLicenseManager();
            File file = new File(licDir);
            licenseManager.install(file);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof LicenseContentException){
                throw new LicenseInstallFailedException();
            }else {
                throw new LicenseVerifyFailedException();
            }
        }
    }

    /**
     * 验证license
     * @return
     */
    public void verify() throws LicenseExpiredException, LicenseVerifyFailedException {
        try {
            LicenseManagerHolder.getLicenseManager().verify();
        } catch (LicenseContentException ex) {
            throw new LicenseExpiredException();
        } catch (Exception e) {
           throw new LicenseVerifyFailedException();
        }

    }

    public static void main(String[] args) {
        try {
            ClientValidLicense clientValidLicense = ClientValidLicense.getInstance("GADQB");
            clientValidLicense.install("D:\\key\\lic\\license.lic");
            clientValidLicense.verify();
        } catch (LicenseInstallFailedException e) {
            e.printStackTrace();
        } catch (LicenseVerifyFailedException e) {
            e.printStackTrace();
        } catch (LicenseExpiredException e) {
            e.printStackTrace();
        }

    }
}
