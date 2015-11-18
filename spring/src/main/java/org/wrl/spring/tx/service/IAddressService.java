package org.wrl.spring.tx.service;


import org.wrl.spring.tx.model.AddressModel;

public interface IAddressService {
    
    public void save(AddressModel address);
    
    public int countAll();
}
