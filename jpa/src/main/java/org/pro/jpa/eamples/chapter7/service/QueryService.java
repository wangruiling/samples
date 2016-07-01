package org.pro.jpa.eamples.chapter7.service;

import org.pro.jpa.eamples.chapter7.entity.Employee;

import java.util.Collection;

/**
 * 1- 动态查询示例
 */
public interface QueryService {
    Collection<Employee> findAllEmployees();
    long queryEmpSalary(String deptName, String empName);
    long queryEmpSalaryUsingParams(String deptName, String empName);
}
