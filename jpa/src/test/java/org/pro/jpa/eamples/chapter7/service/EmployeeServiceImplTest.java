package org.pro.jpa.eamples.chapter7.service;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;
import org.pro.jpa.eamples.chapter7.entity.Department;
import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/7/2.
 */
public class EmployeeServiceImplTest extends BaseServiceTest {
    private EmployeeService employeeService;
    @Before
    public void initialize() {
        employeeService = new EmployeeServiceImpl(em);
    }

    @Test
    public void findEmployeeByName() throws Exception {
        String empName = "Roberts";
        Employee employee = employeeService.findEmployeeByName(empName);
        System.out.println(employee);
    }

    @Test
    public void findEmployeeByPrimaryKey() throws Exception {
        Employee employee = employeeService.findEmployeeByPrimaryKey(3);
        System.out.println(employee);
    }

    @Test
    public void findSalaryForNameAndDepartment() throws Exception {
        String deptName = "CA13";
        String empName = "Sarah";
        long salary = employeeService.findSalaryForNameAndDepartment(deptName, empName);
        System.out.println(salary);
    }

    @Test
    public void findAllEmployees() throws Exception {
        Collection<Employee> employees = employeeService.findAllEmployees();
        System.out.println(employees);
    }

    @Test
    public void findEmployeesAboveSal() {
        Department dept = new Department();
        dept.setId(2);
        long minSal = 5300;
        Collection<Employee> employees = employeeService.findEmployeesAboveSal(dept, minSal);
        System.out.println(employees);
    }

    @Test
    public void findEmployeesHiredDuringPeriod() {

    }

    @Test
    public void findHighestPaidByDepartment() {

    }
}