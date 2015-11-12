package org.wrl.spring.jdbc.dao;


import org.wrl.spring.jdbc.model.UserModel;

public interface IUserDao {
    
    public void save(UserModel model);

    public int countAll();
}
