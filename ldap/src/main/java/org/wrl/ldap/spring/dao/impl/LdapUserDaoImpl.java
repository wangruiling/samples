package org.wrl.ldap.spring.dao.impl;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.*;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Repository;
import org.wrl.ldap.spring.HomeShowLdapUtil;
import org.wrl.ldap.spring.dao.ILdapUserDao;
import org.wrl.ldap.spring.domain.LdapUser;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-17 15:31
 */
@Repository("ldapUserDao")
public class LdapUserDaoImpl implements ILdapUserDao {
    private final static Logger logger = LoggerFactory.getLogger(LdapUserDaoImpl.class);

    private LdapTemplate ldapTemplate;
    private static String userCode_filter_Start = "(&(objectClass=person)(sAMAccountName=*)(pager=";
    private static String userName_filter_Start = "(&(objectClass=person)(sAMAccountName=";
    private static String filterEnd = "))";

    @Resource(name = "ldapTemplate")
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<LdapUser> findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "person");
        return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    public LdapUser findUserByUID(String uid) {
        List<LdapUser> list = ldapTemplate.search("ou=users", "(&(objectclass=person)(uid=" + uid + "))",getContextMapper());
        if (null == list || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public LdapUser load(String userCode) {
        Name dn = buildDn(userCode);
        return (LdapUser) ldapTemplate.lookup(dn, new LdapUserContextMapper());
    }

    public void create(LdapUser ldapUser) {
        DirContextOperations ctx = new DirContextAdapter(buildDn(ldapUser));
        mapToContext(ldapUser, ctx, true);
        ldapTemplate.bind(ctx);
    }

    public void update(LdapUser ldapUser) {
        DirContextOperations ctx = ldapTemplate.lookupContext(buildDn(ldapUser));
        mapToContext(ldapUser, ctx, false);
        ldapTemplate.modifyAttributes(ctx);
    }

    public void updatePassword(String uid, String password) {
        LdapUser ldapUser = load(uid);
        ldapUser.setUserPassword(password.getBytes());

        DirContextOperations ctx = ldapTemplate.lookupContext(buildDn(ldapUser));
        mapToContext(ldapUser, ctx, true);
        ldapTemplate.modifyAttributes(ctx);
    }

    public void delete(LdapUser ldapUser) {
        ldapTemplate.unbind(buildDn(ldapUser));
    }

    private Name buildDn(LdapUser ldapUser) {
        return buildDn(ldapUser.getUid());
    }

    private Name buildDn(String userCode) {
        return buildDn(LdapUser.DEFAULT_COMPANY, userCode);
    }

    private Name buildDn(String company, String userCode) {
        DistinguishedName dn = new DistinguishedName();
        dn.append("ou", company);
        dn.append("pager", userCode);
        return dn;
    }

    private void mapToContext(LdapUser ldapUser, DirContextOperations ctx, boolean password) {
        ctx.setAttributeValues("objectclass", new String[] { "top", "person", "inetOrgPerson",
                "organizationalPerson" });
        ctx.setAttributeValue("uid", ldapUser.getUid());
        ctx.setAttributeValue("cn", ldapUser.getFullName());
        ctx.setAttributeValue("sn", ldapUser.getLastName());
        ctx.setAttributeValue("mail", ldapUser.getMail());
        ctx.setAttributeValue("department", ldapUser.getDepartment());
        if (password)
            ctx.setAttributeValue("userPassword", ldapUser.getUserPassword());
    }

    private ContextMapper getContextMapper() {
        return new LdapUserContextMapper();
    }

    private static class LdapUserContextMapper implements ContextMapper {
        public Object mapFromContext(Object ctx) {
            DirContextAdapter context = (DirContextAdapter) ctx;

            LdapUser ldapUser = new LdapUser();
            ldapUser.setUid(context.getStringAttribute("uid"));
            ldapUser.setFullName(context.getStringAttribute("cn"));
            ldapUser.setLastName(context.getStringAttribute("sn"));
            ldapUser.setMail(context.getStringAttribute("mail"));
            ldapUser.setUserPassword((byte[]) context.getObjectAttribute("userPassword"));
            ldapUser.setDepartment(context.getStringAttribute("department"));
            ldapUser.setUserCode(context.getStringAttribute("pager"));
            ldapUser.setUserName(context.getStringAttribute("sAMAccountName"));
            ldapUser.setRoleName(context.getStringAttribute("title"));
            ldapUser.setMobile(context.getStringAttribute("mobile"));
            ldapUser.setDnName(context.getStringAttribute("distinguishedName"));
            return ldapUser;
        }
    }

    public List<LdapUser> findUsers(String userCode, String userName) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        if(userCode!=null){
            andFilter.and(new LikeFilter("pager", "*"+userCode+"*"));
        }
        if(userName!=null){
            andFilter.and(new LikeFilter("sAMAccountName", "*"+userName+"*"));
        }
        List<LdapUser> list = ldapTemplate.search("ou=北京链家",andFilter.encode(),getContextMapper());
        return list;
    }

    @Override
    public LdapUser authenticateLdapUser(String userCode, String passWord) {
        LdapUser ldapUser = loadByUserCode(userCode);
        if (ldapUser != null){
            authenticatePassword(ldapUser, passWord);
        }
        return ldapUser;
    }

    /**
     *
     * @param ldapUser
     * @param passWord
     * @return
     */
    private boolean authenticatePassword(LdapUser ldapUser, String passWord){
        try {
            ldapTemplate.getContextSource().getContext(ldapUser.getDnName(), passWord);
            ldapUser.setAuthorizeRank(1);
            return Boolean.TRUE;
        } catch (Exception e) {
            ldapUser.setAuthorizeRank(2);
            return Boolean.FALSE;
        }
    }

    public LdapUser loadByUserCode(String userCode) {
        LdapUser ldapUser = null;
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        andFilter.and(new EqualsFilter("sAMAccountName", "*"));
        if(userCode!=null){
            andFilter.and(new LikeFilter("pager", userCode));
        }
        String base = "ou=" + LdapUser.DEFAULT_COMPANY;
        List<LdapUser> list = ldapTemplate.search(base,andFilter.encode(),getContextMapper());
        if (list != null && list.size() > 0){
            ldapUser = list.get(0);
        }
        return ldapUser;
    }

    /**
     * 验证用户系统号与密码是否匹配
     * @param userCode 系统号或邮箱前缀
     * @param password 密码
     * @return true 匹配；false 不匹配
     */
    public boolean authenticate(String userCode, String password){
        logger.info("authenticate start,userCode:{}", userCode);
        boolean result = Boolean.FALSE;

        if (StringUtils.length(userCode) == 8 && StringUtils.isNumeric(userCode)){
            String filter = userCode_filter_Start + userCode + filterEnd;
            result = doAuthenticate(filter, password);
        } else {
            String filter = userName_filter_Start + userCode + filterEnd;
            result = doAuthenticate(filter, password);
        }

        logger.info("authenticate end,and result is {}", result);
        return result;
    }

    private boolean doAuthenticate(String filter, String password) {
        boolean result = Boolean.FALSE;
        Set<String> companyList = HomeShowLdapUtil.getMobileToWebQueueName();
        for (String companyBase:companyList){
            try {
                logger.info(companyBase);
                result = ldapTemplate.authenticate(companyBase, filter, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result)
                break;
        }
        return result;
    }

    public List<LdapUser> getTe(){
        final SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        final String base = "ou=北京链家";
        final String filter = "(&(objectClass=*)(pager=" + 20003101 + "))";
        SearchExecutor executor = new SearchExecutor() {
            @Override
            public NamingEnumeration executeSearch(DirContext ctx) throws NamingException {
                return ctx.search(base, filter, constraints);
            }
        };
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(new PersonAttributesMapper());

        ldapTemplate.search(executor, handler);
        return handler.getList();
    }

    private class PersonAttributesMapper implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            LdapUser ldapUser = new LdapUser();
            Attribute a = attributes.get("cn");
            if (a != null) ldapUser.setUserName((String) a.get());
            return ldapUser;
        }
    }
}
