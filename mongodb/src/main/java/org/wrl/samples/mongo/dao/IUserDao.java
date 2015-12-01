package org.wrl.samples.mongo.dao;

import org.wrl.samples.mongo.mentity.UserEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-01 15:13
 */
public interface IUserDao {
    void _test();

    void createCollection();

    List<UserEntity> findList(int skip, int limit);

    List<UserEntity> findListByAge(int age);

    UserEntity findOne(String id);

    UserEntity findOneByUsername(String username);

    void insert(UserEntity entity);

    void update(UserEntity entity);
}
