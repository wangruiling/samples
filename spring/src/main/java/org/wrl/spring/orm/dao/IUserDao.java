package org.wrl.spring.orm.dao;

import org.wrl.spring.orm.UserModel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-16 14:12
 */
public interface IUserDao {

    void save(UserModel model);

    int countAll();

    void delete(UserModel model);
}
