package com.project.service.util;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ParseRsql {

    public static List findAll(CriteriaQuery<?> query, EntityManager entityManager) {
        return entityManager.createQuery(query).getResultList();
    }



}
