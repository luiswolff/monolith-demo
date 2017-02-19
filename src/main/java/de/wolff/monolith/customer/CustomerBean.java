package de.wolff.monolith.customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerBean {

    @PersistenceContext
    private EntityManager em;

    public Customer findOne(Long id){
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll(){
        return em.createQuery("from Customer c", Customer.class)
                .getResultList();
    }

    public void save(Customer Customer) {
        if (Customer.getId() <= 0)
            em.persist(Customer);
        else
            em.merge(Customer);
    }

    public List<Customer> findByName(String name){
        return em.createQuery("from Customer c where c.name = :name", Customer.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void delete(Long id) {
        Customer customer = em.find(Customer.class, id);
        em.remove(customer);
    }

}
