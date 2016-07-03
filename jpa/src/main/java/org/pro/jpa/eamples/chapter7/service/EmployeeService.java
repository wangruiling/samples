package org.pro.jpa.eamples.chapter7.service;

import org.pro.jpa.eamples.chapter7.entity.Department;
import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 2- 命名查询示例
 */
public interface EmployeeService {
    Collection<Employee> findAllEmployees();
    Employee findEmployeeByName(String name);
    Employee findEmployeeByPrimaryKey(int id);
    long findSalaryForNameAndDepartment(String deptName, String empName);

    Collection<Employee> findEmployeesAboveSal(Department dept, long minSal);
    Collection<Employee> findEmployeesHiredDuringPeriod(Date start, Date end);
    Employee findHighestPaidByDepartment(Department dept);

    List findProjectEmployees(String projectName);
    List findProjectEmployeesWithConstructor(String projectName);
}
