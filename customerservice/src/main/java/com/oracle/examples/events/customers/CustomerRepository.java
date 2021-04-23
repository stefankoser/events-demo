package com.oracle.examples.events.customers;

import com.oracle.examples.events.customers.model.Customer;

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
public class CustomerRepository {

    @PersistenceContext
    @RequestScoped
    private static EntityManager entityManager;

    @Inject
    public CustomerRepository(CustomerProvider customerProvider) {
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        configOverrides.put("hibernate.connection.url", customerProvider.getDbUrl());
        configOverrides.put("hibernate.connection.username", customerProvider.getDbUser());
        configOverrides.put("hibernate.connection.password", customerProvider.getDbPassword());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CustomerPU", configOverrides);
        entityManager = emf.createEntityManager();
    }

    public Set<ConstraintViolation<Customer>> validate(Customer customer) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
        return constraintViolations;
    }

    public Customer save(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    public Customer get(String id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer;
    }

    public List<Customer> findAll() {
        return entityManager.createQuery("from Customer").getResultList();
    }

    public List<Customer> findAll(int offset, int max) {
        Query query = entityManager.createQuery("from Customer");
        query.setFirstResult(offset);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public long count() {
        Query queryTotal = entityManager.createQuery("Select count(u.id) from Customer c");
        long countResult = (long)queryTotal.getSingleResult();
        return countResult;
    }

    public void deleteById(String id) {
        // Retrieve the movie with this ID
        Customer customer = get(id);
        if (customer != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(customer);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
