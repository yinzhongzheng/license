package com.yzz.license.client.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * describe:
 * 抽象AbstractPropertyReader
 * 两个实现类
 * E-mail:yzzstyle@163.com  date:2018/12/5
 *
 * @Since 0.0.1
 */
public abstract class AbstractPropertyReader<T extends Object> {
    //config 映射 bean
    protected T t;

    public T read(String path) {
        InputStream in = null;
        Properties prop = new Properties();
        try {
            String parent = getClass().getClassLoader().getResource("").getPath();
            in = new FileInputStream(parent + path);
            prop.load(in);
            resolveProperties(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * properties --> bean
     * @param prop
     */
    protected abstract void resolveProperties(Properties prop);

    public T getT() {
        return t;
    }
}
