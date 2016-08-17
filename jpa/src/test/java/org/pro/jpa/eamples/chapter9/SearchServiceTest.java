package org.pro.jpa.eamples.chapter9;

import org.junit.Before;
import org.junit.Test;
import org.pro.jpa.eamples.BaseServiceTest;
import org.pro.jpa.eamples.chapter8.entity.Department;
import org.pro.jpa.eamples.chapter8.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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
    public void findEmployeeName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Employee> emp = cq.from(Employee.class);
        cq.select(emp.get("name")).distinct(true);

        TypedQuery<String> tq = em.createQuery(cq);
        System.out.println(tq.getResultList());
    }

    @Test
    public void findEmployee() throws Exception {
        //CriteriaBuilder接口：用来构建CritiaQuery的构建器对象
        CriteriaBuilder cb = em.getCriteriaBuilder();

        //CriteriaQuery接口：代表一个specific的顶层查询对象，它包含着查询的各个部分，比如：select 、from、where、group by、order by等
        CriteriaQuery<Department> criteriaQuery = cb.createQuery(Department.class);

        //Root接口：代表Criteria查询的根对象，Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似
        //1：Root实例是类型化的，且定义了查询的FROM子句中能够出现的类型。
        //2：查询根实例能通过传入一个实体类型给 AbstractQuery.from方法获得。
        //3：Criteria查询，可以有多个查询根。
        //4：AbstractQuery是CriteriaQuery 接口的父类，它提供得到查询根的方法
        Root<Department> dept = criteriaQuery.from(Department.class);
        Root<Employee> emp = criteriaQuery.from(Employee.class);

        criteriaQuery.select(dept).distinct(true)
                .where(cb.equal(dept, emp.get("department")));

        //Predicate：一个简单或复杂的谓词类型，其实就相当于条件或者是条件组合
        //构建简单的Predicate示例：
        //Predicate p1=cb.like(root.get(“name”).as(String.class), “%”+uqm.getName()+“%”);
        //Predicate p2=cb.equal(root.get("uuid").as(Integer.class), uqm.getUuid());
        //Predicate p3=cb.gt(root.get("age").as(Integer.class), uqm.getAge());
        //构建组合的Predicate示例：Predicate p = cb.and(p3,cb.or(p1,p2));
        List<Predicate> criteria = new ArrayList<>();
        ParameterExpression<String> p = cb.parameter(String.class, "empName");
        criteria.add(cb.equal(emp.get("name"), p));

        criteriaQuery.where(criteria.get(0));

        TypedQuery<Department> q = em.createQuery(criteriaQuery);
        q.setParameter("empName", "Sue");

        List<Department> result = q.getResultList();
        System.out.println(result);
    }


    private void getPredicate1(Employee employee) throws Exception {

        Specification<Employee> spec = new Specification<Employee>() {

            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(employee.getName()!=null && employee.getName().trim().length()>0){
                    list.add(cb.like(root.get("name").as(String.class), "%" + employee.getName()+"%"));
                }
                if(employee.getId() > 0){
                    list.add(cb.equal(root.get("uuid").as(Integer.class), employee.getId()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
    }

    private void getPredicate2(Employee employee) {
        Specification<Employee> spec = new Specification<Employee>() {
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate p1 = cb.like(root.get("name").as(String.class), "%" + employee.getName()+"%");
                Predicate p2 = cb.equal(root.get("uuid").as(Integer.class), employee.getId());
                Predicate p3 = cb.gt(root.get("age").as(Integer.class), employee.getSalary());
                //把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的
                query.where(cb.and(p3,cb.or(p1,p2)));
                //添加排序的功能
                query.orderBy(cb.desc(root.get("uuid").as(Integer.class)));

                return query.getRestriction();
            }
        };
    }

    @Test
    public void findEmployees() throws Exception {
        String name = "Sue";
        List<Employee> employees = searchService.findEmployees(name, null, "Release1", null);
        System.out.println(employees);
    }

}