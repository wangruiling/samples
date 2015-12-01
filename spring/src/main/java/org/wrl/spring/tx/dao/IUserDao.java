package org.wrl.spring.tx.dao;


import org.wrl.spring.tx.model.UserModel;

public interface IUserDao {
    
    public void save(UserModel user);
    
    public int countAll();
    
}
