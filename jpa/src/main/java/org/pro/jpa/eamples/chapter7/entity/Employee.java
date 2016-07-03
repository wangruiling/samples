package org.pro.jpa.eamples.chapter7.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

@Entity
@NamedQuery(name="findSalaryForNameAndDepartment",
        query="SELECT e.salary " +
                "FROM Employee e " +
                "WHERE e.department.name = :deptName AND " +
                "      e.name = :empName")
@NamedQueries({
        @NamedQuery(name="Employee.findAll",
                query="SELECT e FROM Employee e"),
        @NamedQuery(name="Employee.findByPrimaryKey",
                query="SELECT e FROM Employee e WHERE e.id = :id"),
        @NamedQuery(name="Employee.findByName",
                query="SELECT e FROM Employee e WHERE e.name = :name"),
        @NamedQuery(name="findEmployeesAboveSal",
                query="SELECT e " +
                        "FROM Employee e " +
                        "WHERE e.department = :dept AND " +
                        "      e.salary > :sal"),
        @NamedQuery(name="findHighestPaidByDepartment",
                query="SELECT e " +
                        "FROM Employee e " +
                        "WHERE e.department = :dept AND " +
                        "      e.salary = (SELECT MAX(e2.salary) " +
                        "                  FROM Employee e2 " +
                        "                  WHERE e2.department = :dept)")
})
public class Employee {
    @Id
    private int id;
    private String name;
    private long salary;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @ManyToOne
    private Employee manager;
    
    @OneToMany(mappedBy="manager")
    private Collection<Employee> directs;

    @ManyToOne
    private Department department;
    
    @ManyToMany 
    private Collection<Project> projects;

    public Employee() {
        projects = new ArrayList<>();
        directs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public long getSalary() {
        return salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Collection<Employee> getDirects() {
        return directs;
    }
    
    public Employee getManager() {
        return manager;
    }

    public Collection<Project> getProjects() {
        return projects;
    }
    
    public String toString() {
        return "Employee " + getId() + 
               ": name: " + getName() +
               ", salary: " + getSalary() +
               ", dept: " + ((getDepartment() == null) ? null : getDepartment().getName());
    }
}
