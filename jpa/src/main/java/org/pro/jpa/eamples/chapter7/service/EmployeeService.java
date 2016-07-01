package org.pro.jpa.eamples.chapter7.service;

import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;

/**
 * 2- 命名查询示例
 */
public interface EmployeeService {
    public Collection<Employee> findAllEmployees();
    public Employee findEmployeeByName(String name);
    public Employee findEmployeeByPrimaryKey(int id);
    public long findSalaryForNameAndDepartment(String deptName, String empName);
}
