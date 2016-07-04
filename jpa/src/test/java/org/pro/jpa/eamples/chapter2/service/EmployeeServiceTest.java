package org.pro.jpa.eamples.chapter2.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pro.jpa.eamples.chapter2.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/6/17.
 */
public class EmployeeServiceTest {
    private static EmployeeService employeeService;
    private static EntityManager em;

    @BeforeClass
    public static void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocalPersistenceUnit");
        em = emf.createEntityManager();
        employeeService = new EmployeeService(em);
    }

    @AfterClass
    public static void down() {
        // close the EM and EMF when done
        em.close();
    }

    @Test
    public void createEmployee() throws Exception {
        //  create and persist an employee
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Employee emp = employeeService.createEmployee(158, "John Doe", 45000);
        transaction.commit();
        System.out.println("Persisted " + emp);
    }

    @Test
    public void removeEmployee() throws Exception {
        // remove an employee
        em.getTransaction().begin();
        employeeService.removeEmployee(158);
        em.getTransaction().commit();
        System.out.println("Removed Employee 158");
    }

    @Test
    public void raiseEmployeeSalary() throws Exception {
        // update the employee
        em.getTransaction().begin();
        Employee emp = employeeService.raiseEmployeeSalary(158, 1000);
        em.getTransaction().commit();
        System.out.println("Updated " + emp);
    }

    @Test
    public void findEmployee() throws Exception {
        // find a specific employee
        Employee emp = employeeService.findEmployee(158);
        System.out.println("Found " + emp);
    }

    @Test
    public void findAllEmployees() throws Exception {
        // find all employees
        Collection<Employee> emps = employeeService.findAllEmployees();
        for (Employee e : emps)
            System.out.println("Found Employee: " + e);
    }

}