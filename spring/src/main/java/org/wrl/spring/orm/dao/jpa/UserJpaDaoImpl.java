package org.wrl.spring.orm.dao.jpa;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wrl.spring.orm.UserModel;
import org.wrl.spring.orm.dao.IUserDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-16 14:14
 */
@Transactional(propagation = Propagation.REQUIRED)
public class UserJpaDaoImpl implements IUserDao {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager entityManager;

    @Override
    public void save(UserModel model) {
        entityManager.persist(model);
    }

    @Override
    public int countAll() {
        Query query = entityManager.createNativeQuery("");
        query.getSingleResult();
        return 0;
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
