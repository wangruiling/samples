package org.wrl.spring.tx.service;


import org.wrl.spring.tx.model.UserModel;

public interface IUserService {
    
    public void save(UserModel user);
    
    public int countAll();
}
