package org.pro.jpa.eamples.chapter8.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangrl on 2016/7/4.
 */
public class ComposeService {
    //@PersistenceContext(unitName="LocalPersistenceUnit")
    //private EntityManager em;
    @PersistenceUnit(unitName="LocalPersistenceUnit")
    private EntityManagerFactory emf;

    public ComposeService(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void executeAndPrintQuery(String queryString, PrintStream out){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery(queryString);
            printQueryResult(queryString, query.getResultList(), out);
        } finally {
            em.close();
        }
    }

    private void printQueryResult(String queryString, List result, PrintStream out) {
        out.println("<tr><td><b>EJB QL: </b>" + queryString + "</td></tr>");
        if (result.isEmpty()) {
            out.println("No results Found");
        } else {
            for (Object o : result) {
                out.println(resultAsString(o));
            }
        }
        out.println("");
    }


    private String resultAsString(Object o) {
        if (o instanceof Object[]) {
            return Arrays.asList((Object[])o).toString();
        } else {
            return String.valueOf(o);
        }
    }
}
