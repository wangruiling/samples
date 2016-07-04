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

}