package org.wrl.spring.tx.dao;


import org.wrl.spring.tx.model.AddressModel;

public interface IAddressDao {
    
    public void save(AddressModel address);

    public int countAll();
}
