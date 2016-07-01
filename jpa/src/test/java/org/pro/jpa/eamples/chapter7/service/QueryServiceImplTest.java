package org.pro.jpa.eamples.chapter7.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;
import org.pro.jpa.eamples.chapter7.entity.Employee;
import org.xml.sax.SAXException;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/7/1.
 */
public class QueryServiceImplTest extends BaseServiceTest{
    private QueryService queryService;

    @Before
    public void initialize() {
        queryService = new QueryServiceImpl(em);
    }

    @Test
    public void queryEmpSalary() throws Exception {
        String deptName = "CA13";
        String empName = "Sarah";
        long salary = queryService.queryEmpSalary(deptName, empName);
        System.out.println("Found Salary: " + salary);
    }

    @Test
    public void queryEmpSalaryUsingParams() throws Exception {
        String deptName = "CA13";
        String empName = "Sarah";
        long salary = queryService.queryEmpSalaryUsingParams(deptName, empName);
        System.out.println("Found Salary: " + salary);
    }

    @Test
    public void findAllEmployees() throws Exception {
        Collection<Employee> employees = queryService.findAllEmployees();
        System.out.println(employees);
    }

}