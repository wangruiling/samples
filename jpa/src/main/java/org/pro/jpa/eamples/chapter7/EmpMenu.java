package org.pro.jpa.eamples.chapter7;

public class EmpMenu {
    private String employeeName;
    private String departmentName;

    public EmpMenu(String employeeName, String departmentName) {
        this.employeeName = employeeName;
        this.departmentName = departmentName;
    }

    public String getEmployeeName() { 
        return employeeName;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
}

