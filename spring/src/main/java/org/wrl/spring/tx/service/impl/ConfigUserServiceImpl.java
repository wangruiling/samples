package org.wrl.spring.tx.service.impl;

import org.wrl.spring.tx.dao.IUserDao;
import org.wrl.spring.tx.model.UserModel;
import org.wrl.spring.tx.service.IAddressService;
import org.wrl.spring.tx.service.IUserService;

public class ConfigUserServiceImpl implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Override
    public void save(final UserModel user) {
        userDao.save(user);
        user.getAddress().setUserId(user.getId());
        addressService.save(user.getAddress());
        
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
