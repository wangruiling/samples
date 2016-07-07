package org.pro.jpa.eamples.chapter9;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;
import org.pro.jpa.eamples.chapter8.entity.Employee;

import java.io.PrintStream;
import java.util.List;

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
    public void findEmployee() throws Exception {

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