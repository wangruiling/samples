package org.pro.jpa.eamples.chapter8.service;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;

import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/7/4.
 */
public class ComposeServiceTest extends BaseServiceTest{
    ComposeService service;
    PrintStream out = System.out;

    @Before
    public void initialize() {
        service = new ComposeService(emf);
    }

    @Test
    public void executeAndPrintQuery() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery2() throws Exception {
        service.executeAndPrintQuery("SELECT d FROM Department d", out);
    }

    @Test
    public void executeAndPrintQuery3() throws Exception {
        service.executeAndPrintQuery("SELECT OBJECT(d) FROM Department d", out);
    }

    @Test
    public void executeAndPrintQuery4() throws Exception {
        service.executeAndPrintQuery("SELECT e.name FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery5() throws Exception {
        service.executeAndPrintQuery("SELECT e.department FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery6() throws Exception {
        service.executeAndPrintQuery("SELECT DISTINCT e.department FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery7() throws Exception {
        service.executeAndPrintQuery("SELECT d.employees FROM Department d", out);
    }

    @Test
    public void executeAndPrintQuery8() throws Exception {
        service.executeAndPrintQuery("SELECT e.name, e.salary FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery9() throws Exception {
        service.executeAndPrintQuery("SELECT NEW org.pro.jpa.eamples.chapter8.entity.EmployeeDetails(e.name, e.salary, e.department.name) FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery10() throws Exception {
        service.executeAndPrintQuery("SELECT p FROM Project p WHERE p.employees IS NOT EMPTY", out);
    }

    @Test
    public void executeAndPrintQuery101() throws Exception {
        service.executeAndPrintQuery("SELECT p FROM Project p WHERE TYPE(p) = DesignProject OR TYPE(p) = QualityProject", out);
    }

    @Test
    public void executeAndPrintQuery11() throws Exception {
        service.executeAndPrintQuery("SELECT p FROM Employee e JOIN e.phones p", out);
    }

    @Test
    public void executeAndPrintQuery12() throws Exception {
        service.executeAndPrintQuery("SELECT DISTINCT p FROM Employee e, IN(e.phones) p", out);
    }

    @Test
    public void executeAndPrintQuery13() throws Exception {
        service.executeAndPrintQuery("SELECT d FROM Employee e JOIN e.department d", out);
    }

    @Test
    public void executeAndPrintQuery132() throws Exception {
        service.executeAndPrintQuery("SELECT e.department FROM Employee e", out);
    }

    @Test
    public void executeAndPrintQuery133() throws Exception {
        service.executeAndPrintQuery("SELECT DISTINCT d FROM Department d, Employee e " +
                "WHERE d = e.department", out);
    }

    @Test
    public void executeAndPrintQuery15() throws Exception {
        service.executeAndPrintQuery("SELECT DISTINCT e.department FROM Project p JOIN p.employees e " +
                "WHERE p.name = 'Release1' AND e.address.state = 'CA'", out);
    }

    @Test
    public void executeAndPrintQuery152() throws Exception {
        service.executeAndPrintQuery("SELECT DISTINCT d FROM Project p JOIN p.employees e JOIN e.department d JOIN e.address a " +
                "WHERE p.name = 'Release1' AND a.state = 'CA'", out);
    }

    @Test
    public void executeAndPrintQuery16() throws Exception {
        //Department实体与作为部门经理的Employee之间没有关联
        //使用特殊的集合表达式之一，“IS NOT EMPTY”，来检查直接对员工报告的集合不是空的。根据定义，任何具有一个非空的directs集合的员工是一位经理。
        service.executeAndPrintQuery("SELECT d, m FROM Department d, Employee m " +
                "WHERE d = m.department AND m.directs IS NOT EMPTY", out);
    }

    @Test
    public void executeAndPrintQuery17() throws Exception {
        //多个联接，下列查询返回属于员工(属于一个部门)的不同项目的集合
        service.executeAndPrintQuery("SELECT DISTINCT p FROM Department d JOIN d.employees e JOIN e.projects p", out);
    }

    @Test
    public void executeAndPrintQuery18() throws Exception {
        //映射联接，下列查询枚举所有员工的电话号码
        service.executeAndPrintQuery("SELECT e.name, p FROM Employee e JOIN e.phones p", out);
    }

    @Test
    public void executeAndPrintQuery182() throws Exception {
        //映射联接，下列查询枚举所有员工的电话号码
        service.executeAndPrintQuery("SELECT e.name, VALUE(p) FROM Employee e JOIN e.phones p", out);
    }

    @Test
    public void executeAndPrintQuery183() throws Exception {
        //映射联接，下列查询枚举所有员工的电话号码
        service.executeAndPrintQuery("SELECT e.name, KEY(p), VALUE(p) FROM Employee e JOIN e.phones p WHERE KEY(p) IN('Work', 'Cell')", out);
    }

    @Test
    public void executeAndPrintQuery19() throws Exception {
        service.executeAndPrintQuery("SELECT e, d FROM Employee e LEFT JOIN e.department d", out);
    }

    @Test
    public void executeAndPrintQuery20() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e JOIN FETCH e.address", out);
    }

    @Test
    public void executeAndPrintQuery21() throws Exception {
        service.executeAndPrintQuery("SELECT e, a FROM Employee e JOIN e.address a", out);
    }

    @Test
    public void executeAndPrintQuery22() throws Exception {
        service.executeAndPrintQuery("SELECT d FROM Department d LEFT JOIN FETCH d.employees", out);
    }

    @Test
    public void executeAndPrintQuery23() throws Exception {
        service.executeAndPrintQuery("SELECT d, e FROM Department d LEFT JOIN d.employees e", out);
    }


}