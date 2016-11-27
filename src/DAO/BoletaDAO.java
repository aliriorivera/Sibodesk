/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Boleta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Locacion;
import entidad.Registroventa;

/**
 *
 * @author Frank
 */
public class BoletaDAO {

    public BoletaDAO() {
        emf = Persistence.createEntityManagerFactory("SIBODeskPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boleta boleta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacion fkLocacion = boleta.getFkLocacion();
            if (fkLocacion != null) {
                fkLocacion = em.getReference(fkLocacion.getClass(), fkLocacion.getId());
                boleta.setFkLocacion(fkLocacion);
            }
            Registroventa fkRegistroventa = boleta.getFkRegistroventa();
            if (fkRegistroventa != null) {
                fkRegistroventa = em.getReference(fkRegistroventa.getClass(), fkRegistroventa.getId());
                boleta.setFkRegistroventa(fkRegistroventa);
            }
            em.persist(boleta);
            if (fkLocacion != null) {
                fkLocacion.getBoletaList().add(boleta);
                fkLocacion = em.merge(fkLocacion);
            }
            if (fkRegistroventa != null) {
                fkRegistroventa.getBoletaList().add(boleta);
                fkRegistroventa = em.merge(fkRegistroventa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boleta boleta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boleta persistentBoleta = em.find(Boleta.class, boleta.getId());
            Locacion fkLocacionOld = persistentBoleta.getFkLocacion();
            Locacion fkLocacionNew = boleta.getFkLocacion();
            Registroventa fkRegistroventaOld = persistentBoleta.getFkRegistroventa();
            Registroventa fkRegistroventaNew = boleta.getFkRegistroventa();
            if (fkLocacionNew != null) {
                fkLocacionNew = em.getReference(fkLocacionNew.getClass(), fkLocacionNew.getId());
                boleta.setFkLocacion(fkLocacionNew);
            }
            if (fkRegistroventaNew != null) {
                fkRegistroventaNew = em.getReference(fkRegistroventaNew.getClass(), fkRegistroventaNew.getId());
                boleta.setFkRegistroventa(fkRegistroventaNew);
            }
            boleta = em.merge(boleta);
            if (fkLocacionOld != null && !fkLocacionOld.equals(fkLocacionNew)) {
                fkLocacionOld.getBoletaList().remove(boleta);
                fkLocacionOld = em.merge(fkLocacionOld);
            }
            if (fkLocacionNew != null && !fkLocacionNew.equals(fkLocacionOld)) {
                fkLocacionNew.getBoletaList().add(boleta);
                fkLocacionNew = em.merge(fkLocacionNew);
            }
            if (fkRegistroventaOld != null && !fkRegistroventaOld.equals(fkRegistroventaNew)) {
                fkRegistroventaOld.getBoletaList().remove(boleta);
                fkRegistroventaOld = em.merge(fkRegistroventaOld);
            }
            if (fkRegistroventaNew != null && !fkRegistroventaNew.equals(fkRegistroventaOld)) {
                fkRegistroventaNew.getBoletaList().add(boleta);
                fkRegistroventaNew = em.merge(fkRegistroventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boleta.getId();
                if (findBoleta(id) == null) {
                    throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.");
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
            Boleta boleta;
            try {
                boleta = em.getReference(Boleta.class, id);
                boleta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.", enfe);
            }
            Locacion fkLocacion = boleta.getFkLocacion();
            if (fkLocacion != null) {
                fkLocacion.getBoletaList().remove(boleta);
                fkLocacion = em.merge(fkLocacion);
            }
            Registroventa fkRegistroventa = boleta.getFkRegistroventa();
            if (fkRegistroventa != null) {
                fkRegistroventa.getBoletaList().remove(boleta);
                fkRegistroventa = em.merge(fkRegistroventa);
            }
            em.remove(boleta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boleta> findBoletaEntities() {
        return findBoletaEntities(true, -1, -1);
    }

    public List<Boleta> findBoletaEntities(int maxResults, int firstResult) {
        return findBoletaEntities(false, maxResults, firstResult);
    }

    private List<Boleta> findBoletaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Boleta as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boleta findBoleta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boleta.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Boleta as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Boleta> getidboleta(int regventa){

        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Boleta o WHERE o.fkRegistroventa.id=?1").setParameter(1, regventa).getResultList();
        }catch (Exception ex) {
            return null;
        }
        finally {
            em.close();
        }

    }

    public List<Boleta> findBoletaEntitiesByLocacion(Integer locacion) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Boleta b WHERE b.fkLocacion.id = ?1").setParameter(1, locacion).getResultList();
        }catch (Exception ex) {
            return null;
        }
        finally {
            em.close();
        }
    }


    public int getidBoletaCount(int regventa) {
        EntityManager em = getEntityManager();
        try {
            return ((Integer) em.createQuery("select o.fkLocacion.id from Boleta o WHERE o.fkRegistroventa.id=?1").setParameter(1, regventa).getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Boleta> getBoletaByClienteWeb(String cliente){

        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Boleta o, Registroventa r WHERE (r.id=o.fkRegistroventa.id AND o.estado='Comprada' AND r.fkCliente.documento=?1)").setParameter(1, cliente).getResultList();
        }
        finally {
            em.close();
        }

    }

    public List<Boleta> getBoletaByCliente(String cliente){

        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Boleta o, Registroventa r WHERE (r.id=o.fkRegistroventa.id AND o.estado='Comprada' AND r.documentoCliente=?1)").setParameter(1, cliente).getResultList();
        }
        finally {
            em.close();
        }

    }

    public List<Boleta> findBoletaEntitiesByRegistro(Integer registro) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Boleta b WHERE b.fkRegistroventa.id = ?1").setParameter(1, registro).getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }  

    


    public Boleta getboleta(int regventa){

        EntityManager em = getEntityManager();
        try {
            return (Boleta) em.createQuery("SELECT o FROM Boleta o WHERE o.fkRegistroventa.id=?1").setParameter(1, regventa).getSingleResult();
        }catch (Exception ex) {
            return null;
        }
        finally {
            em.close();
        }

    }


    public int getidBoleta(int regventa) {
        EntityManager em = getEntityManager();
        try {
            return ((Integer) em.createQuery("select o.fkRegistroventa.id from Boleta o WHERE o.id=?1").setParameter(1, regventa).getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
