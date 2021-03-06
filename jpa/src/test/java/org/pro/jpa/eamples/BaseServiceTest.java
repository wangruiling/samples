package org.pro.jpa.eamples;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.pro.jpa.eamples.chapter2.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by wangrl on 2016/7/1.
 */
public class BaseServiceTest {
    protected static EntityManager em;
    protected static EntityManagerFactory emf;

    @BeforeClass
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("LocalPersistenceUnit");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void down() {
        // close the EM and EMF when done
        em.close();
    }
}
