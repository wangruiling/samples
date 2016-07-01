package org.pro.jpa.eamples.chapter7.service;

import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class EmployeeServiceImpl implements EmployeeService {
//    @PersistenceContext(unitName="NamedQueries")
    EntityManager em;

    public EmployeeServiceImpl(EntityManager em) {
        this.em = em;
    }

    public Employee findEmployeeByName(String name) {
        try {
            return (Employee) em.createNamedQuery("Employee.findByName")
                                .setParameter("name", name)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
   }

    public Employee findEmployeeByPrimaryKey(int id) {
        try {
            return (Employee) em.createNamedQuery("Employee.findByPrimaryKey")
                                .setParameter("id", id)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public long findSalaryForNameAndDepartment(String deptName, String empName) {
        try {
            return (Long) em.createNamedQuery("findSalaryForNameAndDepartment")
                            .setParameter("deptName", deptName)
                            .setParameter("empName", empName)
                            .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
    
    public Collection<Employee> findAllEmployees() {
        return (Collection<Employee>) em.createNamedQuery("Employee.findAll")
                                        .getResultList();
    }
}
