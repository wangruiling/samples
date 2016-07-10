package org.pro.jpa.eamples.chapter9;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;
import org.pro.jpa.eamples.chapter8.entity.Department;
import org.pro.jpa.eamples.chapter8.entity.Employee;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/7/6.
 */
public class SearchServiceTest extends BaseServiceTest{
    SearchService searchService;

    @Before
    public void init() {
        searchService = new SearchService(em);
    }

    @Test
    public void findEmployeeName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Employee> emp = cq.from(Employee.class);
        cq.select(emp.get("name")).distinct(true);

        TypedQuery<String> tq = em.createQuery(cq);
        System.out.println(tq.getResultList());
    }

    @Test
    public void findEmployee() throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = cb.createQuery(Department.class);
        Root<Department> dept = criteriaQuery.from(Department.class);
        Root<Employee> emp = criteriaQuery.from(Employee.class);

        criteriaQuery.select(dept).distinct(true)
                .where(cb.equal(dept, emp.get("department")));

        List<Predicate> criteria = new ArrayList<>();
        ParameterExpression<String> p = cb.parameter(String.class, "empName");
        criteria.add(cb.equal(emp.get("name"), p));

        criteriaQuery.where(criteria.get(0));

        TypedQuery<Department> q = em.createQuery(criteriaQuery);
        q.setParameter("empName", "Sue");

        List<Department> result = q.getResultList();
        System.out.println(result);
    }

    @Test
    public void findAllEmployees() throws Exception {

    }

    @Test
    public void findEmployees() throws Exception {
        String name = "Sue";
        List<Employee> employees = searchService.findEmployees(name, null, "Release1", null);
        System.out.println(employees);
    }

}