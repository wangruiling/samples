package org.pro.jpa.eamples.chapter7.service;

import org.pro.jpa.eamples.chapter7.entity.Department;
import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

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

    public Collection<Employee> findEmployeesAboveSal(Department dept, long minSal) {
        return (Collection<Employee>) em.createNamedQuery("findEmployeesAboveSal")
                .setParameter("dept", dept)
                .setParameter("sal", minSal)
                .getResultList();
    }

    public Collection<Employee> findEmployeesHiredDuringPeriod(Date start, Date end) {
        return (Collection<Employee>)
                em.createQuery("SELECT e " +
                        "FROM Employee e " +
                        "WHERE e.startDate BETWEEN :start AND :end")
                        .setParameter("start", start, TemporalType.DATE)
                        .setParameter("end", end, TemporalType.DATE)
                        .getResultList();
    }

    public Employee findHighestPaidByDepartment(Department dept) {
        try {
            return (Employee) em.createNamedQuery("findHighestPaidByDepartment")
                    .setParameter("dept", dept)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
