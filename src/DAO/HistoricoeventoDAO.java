/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Historicoevento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Frank
 */
public class HistoricoeventoDAO {

    public HistoricoeventoDAO() {
        emf = Persistence.createEntityManagerFactory("SIBODeskPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historicoevento historicoevento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historicoevento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historicoevento historicoevento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historicoevento = em.merge(historicoevento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historicoevento.getId();
                if (findHistoricoevento(id) == null) {
                    throw new NonexistentEntityException("The historicoevento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historicoevento historicoevento;
            try {
                historicoevento = em.getReference(Historicoevento.class, id);
                historicoevento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historicoevento with id " + id + " no longer exists.", enfe);
            }
            em.remove(historicoevento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historicoevento> findHistoricoeventoEntities() {
        return findHistoricoeventoEntities(true, -1, -1);
    }

    public List<Historicoevento> findHistoricoeventoEntities(int maxResults, int firstResult) {
        return findHistoricoeventoEntities(false, maxResults, firstResult);
    }

    private List<Historicoevento> findHistoricoeventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Historicoevento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Historicoevento findHistoricoevento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historicoevento.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoricoeventoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Historicoevento as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
