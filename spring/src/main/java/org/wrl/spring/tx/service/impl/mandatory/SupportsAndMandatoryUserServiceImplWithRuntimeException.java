package org.wrl.spring.tx.service.impl.mandatory;

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


public class SupportsAndMandatoryUserServiceImplWithRuntimeException implements IUserService {
    
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
                    TransactionDefinition.PROPAGATION_SUPPORTS, 
                    TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userDao.save(user);//无事务环境允许，将发生立即提交
                user.getAddress().setUserId(user.getId());
                addressService.save(user.getAddress());//由于该方法必须允许在事务环境，因此将抛出异常IllegalTransactionStateException
                
            }
        });
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
