package org.wrl.spring.tx.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wrl.spring.tx.dao.IUserDao;
import org.wrl.spring.tx.model.UserModel;
import org.wrl.spring.tx.service.IAddressService;
import org.wrl.spring.tx.service.IUserService;


public class AnnotationUserServiceImpl implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    @Override
    public void save(final UserModel user) {
        userDao.save(user);
        user.getAddress().setUserId(user.getId());
        addressService.save(user.getAddress());
        
    }

    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
