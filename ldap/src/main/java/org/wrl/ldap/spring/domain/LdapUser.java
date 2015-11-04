package org.wrl.ldap.spring.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangruiling
 * @Date: 2013-05-17 15:28
 */
public class LdapUser implements Serializable {
    public static final String DEFAULT_COMPANY = "北京链家";
//    public static final String DEFAULT_COMPANY = "users";
    private String uid;
    private String fullName; //中文全称(cn)
    private String lastName; //姓(sn)
    private String mail; //邮箱(mail)
    private String userCode; //用户编号(pager)
    private String userName; //用户邮箱前缀(sAMAccountName)
    private byte[] userPassword; //密码
    private String roleName; //岗位(title)
    private String mobile; //手机号(mobile)
    private String department; //所在部门(department)
    private String dnName; //用户的DN(distinguishedName)
    private int authorizeRank; //用户验证级别(0:帐号存在，1:帐号密码匹配，2:帐号密码不匹配)

    public int getAuthorizeRank() {
        return authorizeRank;
    }

    public void setAuthorizeRank(int authorizeRank) {
        this.authorizeRank = authorizeRank;
    }

    public String getDnName() {
        return dnName;
    }

    public void setDnName(String dnName) {
        this.dnName = dnName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setUserPassword(byte[] userPassword) {
        this.userPassword = userPassword;
    }

    public byte[] getUserPassword() {
        return userPassword;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
