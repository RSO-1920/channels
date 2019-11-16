package si.fri.rso.services.utils;

import si.fri.rso.services.models.interfaces.MainEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class DbUtils {
    @Inject
    private EntityManager em;

    public MainEntity createNewEntity(MainEntity entity) {
        try {
            beginTx();
            em.persist(entity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
        return  entity;
    }

    public  void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    public void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    public void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
