package de.wolff.monolith.catalog;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CatalogBean {

    @PersistenceContext
    private EntityManager em;

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("from Item i", Item.class)
                .getResultList();
    }

    public void save(Item item) {
        if (item.getId() <= 0)
            em.persist(item);
        else
            em.merge(item);
    }

    public List<Item> findByName(String name){
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Item> findByNameContaining(String name){
        return em.createQuery("select i from Item i where i.name LIKE :name", Item.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public void delete(Long id) {
        Item item = em.find(Item.class, id);
        em.remove(item);
    }

}
