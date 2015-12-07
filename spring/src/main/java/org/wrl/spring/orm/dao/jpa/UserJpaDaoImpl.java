package org.wrl.spring.orm.dao.jpa;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wrl.spring.orm.UserModel;
import org.wrl.spring.orm.dao.IUserDao;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-16 14:14
 */
@Transactional(propagation = Propagation.REQUIRED)
public class UserJpaDaoImpl implements IUserDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(UserModel model) {
        entityManager.persist(model);
    }

    @Override
    public BigInteger countAll() {
        Query query = entityManager.createNativeQuery("SELECT count(*) FROM test");
        return (BigInteger) query.getSingleResult();
    }

    @Override
    public void delete(UserModel model) {
        entityManager.remove(model);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
