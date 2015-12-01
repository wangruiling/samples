package org.wrl.spring.tx.service.impl.notsupported;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.wrl.spring.tx.dao.IUserDao;
import org.wrl.spring.tx.model.UserModel;
import org.wrl.spring.tx.service.IAddressService;
import org.wrl.spring.tx.service.IUserService;
import org.wrl.spring.tx.util.TransactionTemplateUtils;


public class RequiredAndNotSupportedUserServiceImplWithRuntimeException implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    private PlatformTransactionManager txManager;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Override
    public void save(final UserModel user) {
        TransactionTemplate transactionTemplate = 
            TransactionTemplateUtils.getTransactionTemplate(
                    txManager, 
                    TransactionDefinition.PROPAGATION_REQUIRED, 
                    TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userDao.save(user);
                user.getAddress().setUserId(user.getId());
                addressService.save(user.getAddress());//将暂停当前事务并在无事务环境运行
                //由于 addressService.save抛出RuntimeException，因此此事务需要回滚
                
            }
        });
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
