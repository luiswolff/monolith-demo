package de.wolff.monolith.order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderBean{

    @PersistenceContext
    private EntityManager em;

    public void save(Order order){
        em.persist(order);
        for (OrderLine line : order.getOrderLines()){
            em.persist(line);
        }
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(){
        return em.createQuery("from Order o", Order.class)
                .getResultList();
    }


    public void delete(Long id) {
        Order order = em.find(Order.class, id);
        em.remove(order);
    }
}
