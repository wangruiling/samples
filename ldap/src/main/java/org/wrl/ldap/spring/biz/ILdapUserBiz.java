package org.wrl.ldap.spring.biz;


import java.util.List;

import org.wrl.ldap.spring.domain.LdapUser;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-17 15:31
 */
public interface ILdapUserBiz {
    public List<LdapUser> findUsers(String userCode, String userName);

    /**
     * 检查系统号、密码是否匹配<br></br>
     * 可用于登录验证
     * @param userCode 系统号
     * @param passWord 密码
     * @return AD中的用户信息
     */
    public LdapUser authenticateLdapUser(String userCode, String passWord);

    /**
     * 验证用户系统号与密码是否匹配
     * @param userCode 系统号
     * @param password 密码
     * @return true 匹配；false 不匹配
     */
    public boolean authenticate(String userCode, String password);
}
