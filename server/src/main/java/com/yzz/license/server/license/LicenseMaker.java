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

