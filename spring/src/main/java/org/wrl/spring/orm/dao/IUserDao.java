package org.wrl.spring.orm.dao;

import org.wrl.spring.orm.UserModel;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-16 14:12
 */
public interface IUserDao {

    void save(UserModel model);

    BigInteger countAll();

    void delete(UserModel model);
}
