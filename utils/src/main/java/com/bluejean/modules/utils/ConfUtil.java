package com.bluejean.modules.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.List;

/**
 * @author: wangrl
 * @Date: 2016-03-28 10:12
 */
public class ConfUtil {
    public static void main(String[] args) {
        //properties();
        xml();
    }

    /**
     * 访问Properties格式的配置文件
     * 使用PropertiesConfiguration类可以加载并访问 properties格式的配置文件，并提供对数值型、数组型和List列表型配置信息的支持
     */
    public static void properties() {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("config.properties"));
            // access configuration properties
            float speed = config.getFloat("speed");
            List names = config.getList("names");
            boolean correct = config.getBoolean("correct");

            System.out.println("speed:" + speed);
            System.out.println("names:" + names);
            System.out.println("correct:" + correct);
        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        }
    }

    /**
     * 读取XML文件中内容
     */
    public static void xml() {
        Configurations configs = new Configurations();
        try {
            XMLConfiguration config = configs.xml("config.xml");
            // access configuration properties
            String stage = config.getString("processing[@stage]");
            List<String> paths = config.getList(String.class, "processing.paths.path");
            String secondPath = config.getString("processing.paths.path(1)");

            System.out.println("stage:" + stage);
            System.out.println("paths:" + paths);
            System.out.println("secondPath:" + secondPath);
        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        }
    }
}
