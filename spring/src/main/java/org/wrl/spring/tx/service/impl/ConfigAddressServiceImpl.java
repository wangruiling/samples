package org.wrl.spring.tx.service.impl;

import org.wrl.spring.tx.dao.IAddressDao;
import org.wrl.spring.tx.model.AddressModel;
import org.wrl.spring.tx.service.IAddressService;

public class ConfigAddressServiceImpl implements IAddressService {
    
    private IAddressDao addressDao;
    

    public void setAddressDao(IAddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    @Override
    public void save(final AddressModel address) {
        addressDao.save(address);
    }

    @Override
    public int countAll() {
        return addressDao.countAll();
    }


}
