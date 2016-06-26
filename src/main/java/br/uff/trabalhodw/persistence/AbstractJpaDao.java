package br.uff.trabalhodw.persistence;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public abstract class AbstractJpaDao<T, PK extends Serializable> {

    private Class type;
    @PersistenceContext
    private EntityManager em;

    public AbstractJpaDao(final Class<T> type) {
        this.type = type;
    }

    public void persist(final T object) {
        em.persist(object);
    }

    public void merge(final T object){
        em.merge(object);
    }

    public void remove(final T object){
        em.remove(object);
    }

    public void refresh(final T object) {
        em.refresh(object);
    }

    public List<T> getAll() {
        String queryString = "from " + type.getName();

        Query q = em.createQuery(queryString);

        return q.getResultList();
    }

 
    public List<T> getAllOrdered(String fieldToOrder) {
        String queryString = "from " + type.getName() + " order by " + fieldToOrder;

        Query q = em.createQuery(queryString);

        return q.getResultList();
    }

    public T getById(final PK id)  {
        return (T) em.find(type, id);
    }

    //Ex: from CLASS where nome = "Mário"
   
    public List<T> getByAttribute(String attribute, Object value) {
        String queryString = "from " + type.getName() + " where ";

        //se for uma String, adiciona o upper
        if (value instanceof String) {
            queryString += "upper(" + attribute + ") = upper(?)";
        } else {
            queryString += attribute + " = ? ";
        }

        Query q = em.createQuery(queryString);
        q.setParameter(1, value);

        return q.getResultList();
    }

    public List<T> getByAttributeOrdered(String attribute, Object value, String fieldToOrder) {
        String queryString = "from " + type.getName() + " where ";

        //se for uma String, adiciona o upper
        if (value instanceof String) {
            queryString += "upper(" + attribute + ") = upper(?)";
        } else {
            queryString += attribute + " = ? ";
        }

        queryString += " order by " + fieldToOrder;

        Query q = em.createQuery(queryString);
        q.setParameter(1, value);

        return q.getResultList();
    }

    //Ex: from CLASS where nome like "%Mário%"
    public List<T> getLikeByAttribute(String attribute, String value) {
        String queryString = "from " + type.getName() + " where upper(" + attribute + ") like upper(%?%) ";

        Query q = em.createQuery(queryString);
        q.setParameter(1, value);

        return q.getResultList();
    }

    public List<T> getLikeByAttributeOrdered(String attribute, String value, String fieldToOrder){
        String queryString = "from " + type.getName() + " where upper(" + attribute + ") like upper(%?%) order by " + fieldToOrder;

        Query q = em.createQuery(queryString);
        q.setParameter(1, value);

        return q.getResultList();
    }

    public T getReference(final PK id) {
        return (T) em.getReference(type, id);
    }

    public List getByQueryAndParameters(String queryString, Object... values) {
        Query q = em.createQuery(queryString);

        for (int i = 0; i < values.length ; i++) {
            q.setParameter(i + 1, values[i]);
        }

        return q.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
