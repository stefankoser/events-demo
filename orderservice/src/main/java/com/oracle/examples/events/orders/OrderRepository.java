package com.oracle.examples.events.orders;

import com.oracle.examples.events.orders.model.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
//import javax.persistence.criteria.Order;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class OrderRepository {

    @PersistenceContext
    @RequestScoped
    private static EntityManager entityManager;

    @Inject
    public OrderRepository(OrderProvider orderProvider) {
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        configOverrides.put("hibernate.connection.url", orderProvider.getDbUrl());
        configOverrides.put("hibernate.connection.username", orderProvider.getDbUser());
        configOverrides.put("hibernate.connection.password", orderProvider.getDbPassword());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OrderPU", configOverrides);
        entityManager = emf.createEntityManager();
    }

    public Set<ConstraintViolation<Order>> validate(Order order) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        return constraintViolations;
    }

    public Order save(Order order) {
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        return order;
    }

    public Order get(String id) {
        Order order = entityManager.find(Order.class, id);
        return order;
    }

    public List<Order> findAll() {
        return entityManager.createQuery("from Order").getResultList();
    }

    public List<Order> findAll(int offset, int max) {
        Query query = entityManager.createQuery("from Order");
        query.setFirstResult(offset);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public long count() {
        Query queryTotal = entityManager.createQuery("Select count(u.id) from Order u");
        long countResult = (long)queryTotal.getSingleResult();
        return countResult;
    }

    public void deleteById(String id) {
        // Retrieve the movie with this ID
        Order order = get(id);
        if (order != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(order);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
