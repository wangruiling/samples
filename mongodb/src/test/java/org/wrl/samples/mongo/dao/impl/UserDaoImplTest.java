package org.wrl.samples.mongo.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wrl.samples.mongo.dao.IUserDao;
import org.wrl.samples.mongo.mentity.NameEntity;
import org.wrl.samples.mongo.mentity.UserEntity;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-04 15:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/*.xml")
public class UserDaoImplTest {
    @Resource(name = "userDao")
    private IUserDao userDao;

    @Test
    public void testFindList() throws Exception {

    }

    @Test
    public void testFindListByAge() throws Exception {

    }

    @Test
    public void testFindOne() throws Exception {

    }

    @Test
    public void testFindOneByUsername() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        userEntity.setBirth(new Date());
        userEntity.setPassword("123");
        userEntity.setRegionName("region");
        userEntity.setWorks(12);
        userEntity.setSpecial(new String[]{"look", "swing"});
        NameEntity name = new NameEntity();
        name.setNickname("nick");
        name.setUsername("user");
        userEntity.setName(name);
        userDao.insert(userEntity);
        System.out.println(userEntity);
    }

    @Test
    public void testUpdate() throws Exception {

    }
}