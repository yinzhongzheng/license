# License
## key tool 生成私钥和公钥库
 
 ####生成密钥库和密钥
 
    keytool -genkey -alias privatekey -keysize 1024 -keystore D:\privateKey.store -validity 365
 
 ####导出密钥库内密钥的证书
    keytool -export -alias privatekey -keystore D:\privateKey.store -file D:\certfile.cer
 
 ####创建公钥库
    keytool -import -alias pulbiccert -file D:\certfile.cer -keystore D:\publicCerts.store
 
 ##解释
     -alias 表示密钥的别名
     
     -keystore 表示当前密钥属于哪个密钥库 
     
     -validity 表示有效期（单位：天）
## Server
```java
package com.yzz.license.server.license;

import com.yzz.license.server.bean.LicenseConfigBean;
import com.yzz.license.server.manager.LicenseManagerHolder;
import com.yzz.license.server.reader.AbstractPropertyReader;
import com.yzz.license.server.reader.PropertyServerReader;
import de.schlichtherle.license.*;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

/**
 * describe:
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since ${project_version}
 */
public class LicenseMaker {

    /**
     * X500Principal是一个证书文件的固有格式
     */
    private final static X500Principal DEFAULTHOLDERANDISSUER =
            new X500Principal("CN=y,OU=y,O=y,L=y,ST=y,C=y");

    private LicenseParam initLicenseParam(LicenseConfigBean licenseConfigBean) {
        Class<LicenseMaker> clazz = LicenseMaker.class;
        Preferences pre = Preferences.userNodeForPackage(clazz);
        //设置对证书内容加密的对称密码
        CipherParam cipherParam = new DefaultCipherParam(licenseConfigBean.getKeyStorePwd());
        /**
         * clazz 从哪个类Class.getResource()获得密钥库
         * priPath 从哪个类Class.getResource()获得密钥库
         * priAlias 密钥库的别名
         * keystorePwd 密钥库存储密码
         * privateKeyPwd 密钥库密码
         */
        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
                clazz, licenseConfigBean.getPriPath(), licenseConfigBean.getPriAlias(), licenseConfigBean.getKeyStorePwd(), licenseConfigBean.getPrivateKeyPwd());
        //返回生成证书时需要的参数
        LicenseParam licenseParam = new DefaultLicenseParam(
                licenseConfigBean.getSubject(), pre, privateStoreParam, cipherParam);
        return licenseParam;
    }

    public LicenseContent buildLicenseContent(LicenseConfigBean licenseConfigBean) throws ParseException {
        LicenseContent content = new LicenseContent();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        content.setSubject(licenseConfigBean.getSubject());
        content.setConsumerAmount(licenseConfigBean.getConsumerAmount());
        content.setConsumerType(licenseConfigBean.getConsumerType());
        content.setHolder(DEFAULTHOLDERANDISSUER);
        content.setIssuer(DEFAULTHOLDERANDISSUER);
        content.setIssued(format.parse(licenseConfigBean.getIssued()));
        content.setNotBefore(format.parse(licenseConfigBean.getNotBefore()));
        content.setNotAfter(format.parse(licenseConfigBean.getNotAfter()));
        content.setInfo(licenseConfigBean.getInfo());
        content.setExtra(new Object());
        return content;
    }

    public void create(String confPath) {
        try {
            //将配置文件加载进内存
            AbstractPropertyReader<LicenseConfigBean> propertyReader = new PropertyServerReader();
            LicenseConfigBean licenseConfigBean = propertyReader.read(confPath);
            LicenseManager licenseManager = LicenseManagerHolder.initManager(initLicenseParam(licenseConfigBean));
            LicenseContent content = buildLicenseContent(licenseConfigBean);
            licenseManager.store(content, new File(licenseConfigBean.getLicPath()));
            System.out.println("证书发布成功");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LicenseMaker maker = new LicenseMaker();
        maker.create("license-make-config.properties");
    }
}

```
## Client
```java
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

```
