package org.pro.jpa.eamples.chapter8.service;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;

import java.io.PrintStream;

/**
 * Created by wangrl on 2016/7/4.
 */
public class ComposeServiceTest2 extends BaseServiceTest{
    ComposeService service;
    PrintStream out = System.out;

    @Before
    public void initialize() {
        service = new ComposeService(emf);
    }

    @Test
    public void whereClause1() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e " +
                "WHERE e.salary BETWEEN 40000 AND 45000", out);
    }

    @Test
    public void whereClause2() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e WHERE e.salary >= 40000 AND e.salary <= 45000", out);
    }

    @Test
    public void whereClause3() throws Exception {
        service.executeAndPrintQuery("SELECT d FROM Department d WHERE d.name LIKE '__Eng%'", out);
    }

    @Test
    public void whereClause4() throws Exception {
        service.executeAndPrintQuery("SELECT d FROM Department d " +
                "WHERE d.name LIKE 'QA\\_%' ESCAPE '\\'", out);
    }

    @Test
    public void whereClause5() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e " +
                "WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2)", out);
    }

    @Test
    public void whereClause6() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e WHERE EXISTS (SELECT p " +
                "FROM Phone p WHERE p.employee = e AND p.type = 'Cell')", out);
    }

    @Test
    public void whereClause7() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e " +
                "WHERE EXISTS (SELECT p FROM e.phones p WHERE p.type = 'Cell')", out);
    }

    @Test
    public void whereClause8() throws Exception {
        service.executeAndPrintQuery("SELECT e FROM Employee e WHERE e.address.state IN ('NY', 'CA')", out);
    }

}