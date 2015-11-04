package org.wrl.ldap.spring.biz.impl;

import javax.annotation.Resource;
import java.util.List;

import org.springframework.stereotype.Service;
import org.wrl.ldap.spring.biz.ILdapUserBiz;
import org.wrl.ldap.spring.dao.ILdapUserDao;
import org.wrl.ldap.spring.domain.LdapUser;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-17 15:32
 */
@Service("ldapUserBiz")
public class LdapUserBizImpl implements ILdapUserBiz {
    private ILdapUserDao ldapUserDao;

    @Resource(name = "ldapUserDao")
    public void setLdapUserDao(ILdapUserDao ldapUserDao) {
        this.ldapUserDao = ldapUserDao;
    }

    @Override
    public List<LdapUser> findUsers(String userCode, String userName) {
        return ldapUserDao.findUsers(userCode, userName);
    }

    /**
     * 检查系统号、密码是否匹配<br></br>
     * 可用于登录验证
     * @param userCode 系统号
     * @param passWord 密码
     * @return AD中的用户信息
     */
    @Override
    public LdapUser authenticateLdapUser(String userCode, String passWord) {
        return ldapUserDao.authenticateLdapUser(userCode, passWord);
    }

    /**
     * 验证用户系统号与密码是否匹配
     *
     * @param userCode 系统号或邮箱前缀
     * @param password 密码
     * @return true 匹配；false 不匹配
     */
    @Override
    public boolean authenticate(String userCode, String password) {
        return ldapUserDao.authenticate(userCode, password);
    }
}
