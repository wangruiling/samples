package org.wrl.spring.tx.service.impl.supports;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.wrl.spring.tx.dao.IAddressDao;
import org.wrl.spring.tx.model.AddressModel;
import org.wrl.spring.tx.service.IAddressService;
import org.wrl.spring.tx.util.TransactionTemplateUtils;

public class SupportsAndSupportsAddressServiceImplWithRuntimeException implements IAddressService {
    
    private IAddressDao addressDao;
    
    private PlatformTransactionManager txManager;

    public void setAddressDao(IAddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
    
    @Override
    public void save(final AddressModel address) {
        TransactionTemplate transactionTemplate = 
            TransactionTemplateUtils.getTransactionTemplate(
                    txManager, 
                    TransactionDefinition.PROPAGATION_SUPPORTS, 
                    TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                addressDao.save(address);//无事务环境运行，将发生立即提交
            }
        });
        //抛出异常
        throw new RuntimeException();
    }

    @Override
    public int countAll() {
        return addressDao.countAll();
    }


}
