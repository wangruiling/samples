package org.wrl.ldap.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-25 10:10
 */
public class HomeShowLdapUtil {
    private static Set<String> companyList = new HashSet<String>();

    /**
     * LDAP运营用户验证，使用的各分公司Base
     * 使用此方法进行加载配置文件
     * @return
     */
    public static Set<String> getMobileToWebQueueName(){
        if (companyList.size() < 1){
            Properties importConfig = new Properties();
            try {
                InputStream fis = HomeShowLdapUtil.class.getClassLoader().getResourceAsStream("resource/company_ldap_base.properties");
                importConfig.load(fis);
                if(importConfig != null && importConfig.size() != 0){
                    Collection<Object> collection = importConfig.values();
                    for (Object object:collection){
                        if (object != null){
                            companyList.add(object.toString());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("加载LDAP配置文件异常:" + e);
            }
        }

        return companyList;
    }
}
