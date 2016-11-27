/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidad.Evento;
import entidad.Registroventa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidad.Escenario;
import entidad.Categoria;
import entidad.Organizador;
import entidad.Presentacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Frank
 */
public class EventoDAO {

    public EventoDAO() {
        emf = Persistence.createEntityManagerFactory("SIBODeskPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evento evento) {
        if (evento.getPresentacionList() == null) {
            evento.setPresentacionList(new ArrayList<Presentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escenario fkEscenario = evento.getFkEscenario();
            if (fkEscenario != null) {
                fkEscenario = em.getReference(fkEscenario.getClass(), fkEscenario.getId());
                evento.setFkEscenario(fkEscenario);
            }
            Categoria fkCategoria = evento.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria = em.getReference(fkCategoria.getClass(), fkCategoria.getId());
                evento.setFkCategoria(fkCategoria);
            }
            Organizador fkOrganizador = evento.getFkOrganizador();
            if (fkOrganizador != null) {
                fkOrganizador = em.getReference(fkOrganizador.getClass(), fkOrganizador.getNit());
                evento.setFkOrganizador(fkOrganizador);
            }
            List<Presentacion> attachedPresentacionList = new ArrayList<Presentacion>();
            for (Presentacion presentacionListPresentacionToAttach : evento.getPresentacionList()) {
                presentacionListPresentacionToAttach = em.getReference(presentacionListPresentacionToAttach.getClass(), presentacionListPresentacionToAttach.getId());
                attachedPresentacionList.add(presentacionListPresentacionToAttach);
            }
            evento.setPresentacionList(attachedPresentacionList);
            em.persist(evento);
            if (fkEscenario != null) {
                fkEscenario.getEventoList().add(evento);
                fkEscenario = em.merge(fkEscenario);
            }
            if (fkCategoria != null) {
                fkCategoria.getEventoList().add(evento);
                fkCategoria = em.merge(fkCategoria);
            }
            if (fkOrganizador != null) {
                fkOrganizador.getEventoList().add(evento);
                fkOrganizador = em.merge(fkOrganizador);
            }
            for (Presentacion presentacionListPresentacion : evento.getPresentacionList()) {
                Evento oldFkEventoOfPresentacionListPresentacion = presentacionListPresentacion.getFkEvento();
                presentacionListPresentacion.setFkEvento(evento);
                presentacionListPresentacion = em.merge(presentacionListPresentacion);
                if (oldFkEventoOfPresentacionListPresentacion != null) {
                    oldFkEventoOfPresentacionListPresentacion.getPresentacionList().remove(presentacionListPresentacion);
                    oldFkEventoOfPresentacionListPresentacion = em.merge(oldFkEventoOfPresentacionListPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evento evento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento persistentEvento = em.find(Evento.class, evento.getId());
            Escenario fkEscenarioOld = persistentEvento.getFkEscenario();
            Escenario fkEscenarioNew = evento.getFkEscenario();
            Categoria fkCategoriaOld = persistentEvento.getFkCategoria();
            Categoria fkCategoriaNew = evento.getFkCategoria();
            Organizador fkOrganizadorOld = persistentEvento.getFkOrganizador();
            Organizador fkOrganizadorNew = evento.getFkOrganizador();
            List<Presentacion> presentacionListOld = persistentEvento.getPresentacionList();
            List<Presentacion> presentacionListNew = evento.getPresentacionList();
            List<String> illegalOrphanMessages = null;
            for (Presentacion presentacionListOldPresentacion : presentacionListOld) {
                if (!presentacionListNew.contains(presentacionListOldPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presentacion " + presentacionListOldPresentacion + " since its fkEvento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkEscenarioNew != null) {
                fkEscenarioNew = em.getReference(fkEscenarioNew.getClass(), fkEscenarioNew.getId());
                evento.setFkEscenario(fkEscenarioNew);
            }
            if (fkCategoriaNew != null) {
                fkCategoriaNew = em.getReference(fkCategoriaNew.getClass(), fkCategoriaNew.getId());
                evento.setFkCategoria(fkCategoriaNew);
            }
            if (fkOrganizadorNew != null) {
                fkOrganizadorNew = em.getReference(fkOrganizadorNew.getClass(), fkOrganizadorNew.getNit());
                evento.setFkOrganizador(fkOrganizadorNew);
            }
            List<Presentacion> attachedPresentacionListNew = new ArrayList<Presentacion>();
            for (Presentacion presentacionListNewPresentacionToAttach : presentacionListNew) {
                presentacionListNewPresentacionToAttach = em.getReference(presentacionListNewPresentacionToAttach.getClass(), presentacionListNewPresentacionToAttach.getId());
                attachedPresentacionListNew.add(presentacionListNewPresentacionToAttach);
            }
            presentacionListNew = attachedPresentacionListNew;
            evento.setPresentacionList(presentacionListNew);
            evento = em.merge(evento);
            if (fkEscenarioOld != null && !fkEscenarioOld.equals(fkEscenarioNew)) {
                fkEscenarioOld.getEventoList().remove(evento);
                fkEscenarioOld = em.merge(fkEscenarioOld);
            }
            if (fkEscenarioNew != null && !fkEscenarioNew.equals(fkEscenarioOld)) {
                fkEscenarioNew.getEventoList().add(evento);
                fkEscenarioNew = em.merge(fkEscenarioNew);
            }
            if (fkCategoriaOld != null && !fkCategoriaOld.equals(fkCategoriaNew)) {
                fkCategoriaOld.getEventoList().remove(evento);
                fkCategoriaOld = em.merge(fkCategoriaOld);
            }
            if (fkCategoriaNew != null && !fkCategoriaNew.equals(fkCategoriaOld)) {
                fkCategoriaNew.getEventoList().add(evento);
                fkCategoriaNew = em.merge(fkCategoriaNew);
            }
            if (fkOrganizadorOld != null && !fkOrganizadorOld.equals(fkOrganizadorNew)) {
                fkOrganizadorOld.getEventoList().remove(evento);
                fkOrganizadorOld = em.merge(fkOrganizadorOld);
            }
            if (fkOrganizadorNew != null && !fkOrganizadorNew.equals(fkOrganizadorOld)) {
                fkOrganizadorNew.getEventoList().add(evento);
                fkOrganizadorNew = em.merge(fkOrganizadorNew);
            }
            for (Presentacion presentacionListNewPresentacion : presentacionListNew) {
                if (!presentacionListOld.contains(presentacionListNewPresentacion)) {
                    Evento oldFkEventoOfPresentacionListNewPresentacion = presentacionListNewPresentacion.getFkEvento();
                    presentacionListNewPresentacion.setFkEvento(evento);
                    presentacionListNewPresentacion = em.merge(presentacionListNewPresentacion);
                    if (oldFkEventoOfPresentacionListNewPresentacion != null && !oldFkEventoOfPresentacionListNewPresentacion.equals(evento)) {
                        oldFkEventoOfPresentacionListNewPresentacion.getPresentacionList().remove(presentacionListNewPresentacion);
                        oldFkEventoOfPresentacionListNewPresentacion = em.merge(oldFkEventoOfPresentacionListNewPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evento.getId();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Presentacion> presentacionListOrphanCheck = evento.getPresentacionList();
            for (Presentacion presentacionListOrphanCheckPresentacion : presentacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evento (" + evento + ") cannot be destroyed since the Presentacion " + presentacionListOrphanCheckPresentacion + " in its presentacionList field has a non-nullable fkEvento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Escenario fkEscenario = evento.getFkEscenario();
            if (fkEscenario != null) {
                fkEscenario.getEventoList().remove(evento);
                fkEscenario = em.merge(fkEscenario);
            }
            Categoria fkCategoria = evento.getFkCategoria();
            if (fkCategoria != null) {
                fkCategoria.getEventoList().remove(evento);
                fkCategoria = em.merge(fkCategoria);
            }
            Organizador fkOrganizador = evento.getFkOrganizador();
            if (fkOrganizador != null) {
                fkOrganizador.getEventoList().remove(evento);
                fkOrganizador = em.merge(fkOrganizador);
            }
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Evento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Evento as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Evento> findEventoEntitiesByEscenarioActivos(String escenario) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select e from Evento e where e.fkEscenario.nombre = ?1 and e.estado = 'Activo'").setParameter(1, escenario);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Evento> findEventoEntitiesByCategoria(String categoria) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select e from Evento e where e.fkCategoria.nombre = ?1 and e.estado = 'Activo'").setParameter(1, categoria);
            return q.getResultList();
        } catch (Exception e){
            return null;
        }finally {
            em.close();
        }
    }

    public List<Evento> findEventoEntitiesByFinalizado() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select e from Evento e where e.estado = 'Finalizado' or e.estado = 'Remunerado'");
            return q.getResultList();
        } catch (Exception e){
            return null;
        }finally {
            em.close();
        }
    }

    public Evento geteventBypresentation(int event) {
        EntityManager em = getEntityManager();
        try {
            return (Evento)em.createQuery("select o from Evento o where o.id=?1").setParameter(1, event).getSingleResult();
        }catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public Evento findEventoByNombreCiudad(String nombre, String ciudad) {
        EntityManager em = getEntityManager();
        try {
            return (Evento) em.createQuery("select e from Evento  e where e.nombre = ?1 and e.fkEscenario.ciudad = ?2").setParameter(1, nombre).setParameter(2, ciudad).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Evento> geteventBystatus(String status) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Evento o where o.estado =?1").setParameter(1, status);
            return q.getResultList();
        }catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public Evento findEventoByRegistro(Registroventa registro) {
        EntityManager em = getEntityManager();
        try {
            String nombreEvento = registro.getEvento();
            Integer idRegistro = registro.getId();
            Query q = em.createQuery("select e from Evento e where e.nombre = ?1").setParameter(1, nombreEvento);
            Evento evento = (Evento)q.getSingleResult();
            Query q2 = em.createQuery("select r from Registroventa r where r.id = ?1").setParameter(1, idRegistro);
            registro = (Registroventa)q2.getSingleResult();
            evento.setPresentacionList(new ArrayList());
            evento.getPresentacionList().add(registro.getBoletaList().get(0).getFkLocacion().getFkPresentacion());
            evento.getPresentacionList().get(0).setLocacionList(new ArrayList());
            evento.getPresentacionList().get(0).getLocacionList().add(registro.getBoletaList().get(0).getFkLocacion());
            return evento;
        }/*catch (Exception ex) {
            return null;
        } */finally {
            em.close();
        }
    }
}
