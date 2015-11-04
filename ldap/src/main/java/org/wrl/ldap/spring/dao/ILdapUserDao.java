package org.wrl.ldap.spring.dao;


import java.util.List;

import org.wrl.ldap.spring.domain.LdapUser;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-17 15:30
 */
public interface ILdapUserDao {
    public List<LdapUser> findUsers(String userCode, String userName);

    LdapUser authenticateLdapUser(String userCode, String passWord);

    /**
     * 验证用户系统号与密码是否匹配
     * @param userCode 系统号或邮箱前缀
     * @param password 密码
     * @return true 匹配；false 不匹配
     */
    public boolean authenticate(String userCode, String password);
}
