/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.BoletaDAO;
import DAO.CategoriaDAO;
import DAO.EscenarioDAO;
import DAO.EventoDAO;
import DAO.HistoricoeventoDAO;
import DAO.LocacionDAO;
import DAO.OrganizadorDAO;
import DAO.PresentacionDAO;
import DAO.RegistroventaDAO;
import DAO.RemuneracionDAO;
import DAO.exceptions.*;
import entidad.Boleta;
import entidad.Categoria;
import entidad.Escenario;
import entidad.Evento;
import entidad.Historicoevento;
import entidad.Locacion;
import entidad.Organizador;
import entidad.Presentacion;
import entidad.Registroventa;
import entidad.Remuneracion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frank
 */
public class AdministrarEvento {

    private EventoDAO eventoDAO;
    private PresentacionDAO presentacionDAO;
    private LocacionDAO locacionDAO;
    private CategoriaDAO categoriaDAO;
    private EscenarioDAO escenarioDAO;
    private OrganizadorDAO organizadorDAO;
    private RegistroventaDAO registroVentaDAO;
    private RemuneracionDAO remuneracionDAO;
    private BoletaDAO boletaDAO;
    private HistoricoeventoDAO historicoEventoDAO;

    public AdministrarEvento(){
        eventoDAO = new EventoDAO();
        presentacionDAO = new PresentacionDAO();
        locacionDAO = new LocacionDAO();
        categoriaDAO = new CategoriaDAO();
        escenarioDAO = new EscenarioDAO();
        organizadorDAO = new OrganizadorDAO();
        registroVentaDAO = new RegistroventaDAO();
        remuneracionDAO = new RemuneracionDAO();
        boletaDAO = new BoletaDAO();
        historicoEventoDAO = new HistoricoeventoDAO();
    }

    public List<Evento> getEventosByCategoria(String categoria) {
        return eventoDAO.findEventoEntitiesByCategoria(categoria);
    }

    public List<Presentacion> getPresentacionesByEvento(Evento eventoEdicion) {
        return presentacionDAO.findPresentacionEntitiesByEvento(eventoEdicion.getId());
    }

    public List<Locacion> getLocacionesByPresentacion(Presentacion presentacion) {
        return locacionDAO.findLocacionEntitiesByPresentacion(presentacion.getId());
    }

    public void actualizarEvento(Evento evento) {
        int i,j,k;
        boolean existe;
        List<Presentacion> presentaciones = new ArrayList();
        //evento.getPresentacionList();
        List<Locacion> locaciones = new ArrayList();
        //evento.getPresentacionList().get(0).getLocacionList();
        List<Presentacion> auxPresentacion = new ArrayList();
        List<Locacion> auxLocacion = new ArrayList();
        List<List<Locacion>> listaLocacionesPorPresentacion = new ArrayList();

        for (i=0; i<evento.getPresentacionList().size(); i++){
            if (evento.getPresentacionList().get(i).getId() != null)
                listaLocacionesPorPresentacion.add(evento.getPresentacionList().get(i).getLocacionList());
            presentaciones.add(evento.getPresentacionList().get(i));
        }

        locaciones = listaLocacionesPorPresentacion.get(0);

        for (i=0; i<locaciones.size(); i++){
            if (locaciones.get(i).getId() != null){
                for (j=1; j<presentaciones.size(); j++){
                    listaLocacionesPorPresentacion.get(j).get(i).setNombre(locaciones.get(i).getNombre());
                    listaLocacionesPorPresentacion.get(j).get(i).setPrecio(locaciones.get(i).getPrecio());
                    listaLocacionesPorPresentacion.get(j).get(i).setCupo(locaciones.get(i).getCupo());
                }
            }else{
                for (j=1; j<listaLocacionesPorPresentacion.size(); j++){
                    listaLocacionesPorPresentacion.get(j).add(locaciones.get(i));
                }
            }
        }

        //Se eliminan las presentaciones que fueron borradas del evento
        for (i=0; i<evento.getPresentacionList().size(); i++){
            for (j=0; j<evento.getPresentacionList().get(i).getLocacionList().size(); j++){
                if (evento.getPresentacionList().get(i).getLocacionList().get(j).getId() == null)
                    evento.getPresentacionList().get(i).getLocacionList().remove(j);
            }
            if(evento.getPresentacionList().get(i).getId() == null)
                evento.getPresentacionList().remove(i);
        }

        System.out.println(listaLocacionesPorPresentacion);
        System.out.println(presentaciones);

        try {
            eventoDAO.edit(evento);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }



        auxPresentacion = presentacionDAO.findPresentacionEntitiesByEvento(evento.getId());

        //Se eliminan las presentaciones que fueron borradas del evento
        for (i=0; i<auxPresentacion.size(); i++){
            j=0;
            existe = false;
            while (j<presentaciones.size() && !existe){
                if (auxPresentacion.get(i).getId().intValue() == presentaciones.get(j).getId().intValue())
                    existe = true;
                j++;
            }
            if (!existe)
                try {
                    presentacionDAO.destroy(auxPresentacion.get(i).getId());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        //Se crean las presentaciones que hayan sido añadidas
        for (i=0; i<presentaciones.size(); i++){
            if (presentaciones.get(i).getId() == null){
                presentaciones.get(i).setFkEvento(evento);
                presentacionDAO.create(presentaciones.get(i));
            }
        }
        auxPresentacion = presentacionDAO.findPresentacionEntitiesByEvento(evento.getId());
        //Se eliminan las locaciones que fueron borradas de cada presentacion
        for (i=0; i<auxPresentacion.size(); i++){
            auxLocacion = locacionDAO.findLocacionEntitiesByPresentacion(auxPresentacion.get(i).getId());
            if (!auxLocacion.isEmpty()){
                for (j=0; j<auxLocacion.size(); j++){
                    k=0;
                    existe = false;
                    while (k<locaciones.size() && !existe){
                        if (auxLocacion.get(j).getId().intValue() == locaciones.get(k).getId().intValue())
                            existe = true;
                        k++;
                    }
                    if (!existe)
                        try {
                            locacionDAO.destroy(auxLocacion.get(j).getId());
                        } catch (IllegalOrphanException ex) {
                            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            }


        }
        /** Se editan y se añaden las locaciones existentes a las nuevas presentaciones
         * Se añaden las nuevas locaciones
         */
        Locacion aux;
        auxLocacion = locacionDAO.findLocacionEntities();
        for (i=0; i<auxPresentacion.size(); i++){
            auxLocacion = locacionDAO.findLocacionEntitiesByPresentacion(auxPresentacion.get(i).getId());

        System.out.println("i= "+i);
        System.out.println("presentacionID "+auxPresentacion.get(i).getId());
        System.out.println("presentaciones "+auxPresentacion.get(i));
        System.out.println("locaciones "+auxLocacion);


            if (!auxLocacion.isEmpty()){
                for (j=0; j<locaciones.size(); j++){
                    if (locaciones.get(j).getId() != null){
                        try {
                            locacionDAO.edit(locaciones.get(j));
                        } catch (IllegalOrphanException ex) {
                            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        locaciones.get(j).setFkPresentacion(auxPresentacion.get(i));
                        locacionDAO.create(locaciones.get(j));
                    }
                }
            }else{
                for (j=0; j<locaciones.size(); j++){
                    if (locaciones.get(j).getId() != null){
                        aux = new Locacion();
                        aux.setNombre(locaciones.get(j).getNombre());
                        aux.setPrecio(locaciones.get(j).getPrecio());
                        aux.setCupo(locaciones.get(j).getCupo());
                        aux.setVendidas(0);
                        aux.setFkPresentacion(auxPresentacion.get(i));
                        locacionDAO.create(aux);
                    }else{
                        locaciones.get(j).setFkPresentacion(auxPresentacion.get(i));
                        locacionDAO.create(locaciones.get(j));
                    }
                }
            }
        }
    }

    public List<Categoria> getCategorias(){
        return categoriaDAO.findCategoriaEntities();
    }

    public Categoria getCategoriaByName(String nombre){
        return categoriaDAO.findCategoriaByName(nombre);
    }

    public List<Escenario> getEscenarios(){
        return escenarioDAO.findEscenarioEntities();
    }

    public List<Categoria> getCategoriaByType(Categoria cat) {
        return categoriaDAO.findCategoriaEntitiesByType(cat);
    }

    public boolean comprobarDisponibilidadEscenario(Date fecha, Object esc) {
        boolean flag = true;
        Calendar fechaParam = Calendar.getInstance(),fechaPresentacion = Calendar.getInstance();
        fechaParam.setTime(fecha);
        String escenario = (String)esc;
        List<Evento> eventos = eventoDAO.findEventoEntitiesByEscenarioActivos(escenario);
        List<Presentacion> presentaciones;
        if(!eventos.isEmpty()){
            int i=0;
            while(i<eventos.size() && flag){
                int j=0;
                presentaciones = presentacionDAO.findPresentacionEntitiesByEvento(eventos.get(i).getId());
                while (j<presentaciones.size() && flag){
                    // pendiente mejorar la forma de comparar las fechas
                    fechaPresentacion.setTime(presentaciones.get(j).getFecha());
                    if(fechaParam.get(Calendar.DAY_OF_MONTH) == fechaPresentacion.get(Calendar.DAY_OF_MONTH)
                            && fechaParam.get(Calendar.YEAR) == fechaPresentacion.get(Calendar.YEAR)
                            && fechaParam.get(Calendar.MONTH) == fechaPresentacion.get(Calendar.MONTH))
                        flag = false;
                    j++;
                }
                i++;
            }
        }
        return flag;
    }

    public List<Organizador> getOrganizadores() {
        return organizadorDAO.findOrganizadorEntities();
    }

    public Escenario getEscenarioByName(String nombre) {
        return escenarioDAO.findEscenarioByName(nombre);
    }

    public Organizador getOrganizadorByName(String nombre) {
        return organizadorDAO.findOrganizadorByName(nombre);
    }

    public boolean registrar(Evento evento) {
        boolean registro = false;
        List<Presentacion> presentaciones = evento.getPresentacionList();
        List<Locacion> locaciones = evento.getPresentacionList().get(0).getLocacionList();
        Evento auxEvento = eventoDAO.findEventoByNombreCiudad(evento.getNombre(),evento.getFkEscenario().getCiudad());
        if (auxEvento == null){
            evento.setPresentacionList(null);
            eventoDAO.create(evento);
            for (int i=0; i<presentaciones.size(); i++){
                presentaciones.get(i).setLocacionList(null);
                presentaciones.get(i).setFkEvento(evento);
                presentacionDAO.create(presentaciones.get(i));
                for (int j=0; j<locaciones.size(); j++){
                    locaciones.get(j).setFkPresentacion(presentaciones.get(i));
                    locacionDAO.create(locaciones.get(j));
                    locaciones.get(j).setFkPresentacion(null);
                    locaciones.get(j).setId(null);
                }
            }
            registro = true;
        }
        return registro;
    }

    public boolean cancelarEvento(Evento eventoCancelacion) {
        int i;
        boolean flag = true;
        eventoCancelacion.setEstado("Cancelado");
        try {
            eventoDAO.edit(eventoCancelacion);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Registroventa> registroVenta = registroVentaDAO.getRegistroByEvento(eventoCancelacion.getNombre());
        List<Remuneracion> remuneraciones = new ArrayList();
        Remuneracion remuneracion = new Remuneracion();

        if (!registroVenta.isEmpty()){
            for (i=0; i<registroVenta.size(); i++){
                remuneracion.setDocumento(registroVenta.get(i).getDocumentoCliente());
                remuneracion.setEvento(registroVenta.get(i).getEvento());
                remuneracion.setFecha(new Date());
                remuneracion.setValor(registroVenta.get(i).getTotalVendido());
                remuneraciones.add(remuneracion);
            }
            for (i=0; i<remuneraciones.size(); i++){
                remuneracionDAO.create(remuneraciones.get(i));
            }
        }else
            flag = false;

        return flag;
    }

    public List<Evento> obtenerEventosParaEliminacion() {
        return eventoDAO.findEventoEntitiesByFinalizado();
    }

    public void eliminarLocacion(Locacion locacion) {
        try {
            locacionDAO.destroy(locacion.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Boleta> getBoletasByLocacion(Locacion locacion) {
        return boletaDAO.findBoletaEntitiesByLocacion(locacion.getId());
    }

    public void eliminarBoleta(Boleta boleta) {
        try {
            boletaDAO.destroy(boleta.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarPresentacion(Presentacion presentacion) {
        try {
            presentacionDAO.destroy(presentacion.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarEvento(Evento eventoEliminacion) {
        try {
            eventoDAO.destroy(eventoEliminacion.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarHistorico(Historicoevento historico) {
        historicoEventoDAO.create(historico);
    }

    public boolean verificarPresentacion(Presentacion presentacion) {
        boolean flag = true;
        List<Locacion> locaciones = locacionDAO.findLocacionEntitiesByPresentacion(presentacion.getId());
        for (int i=0; i<locaciones.size(); i++){
            if (locaciones.get(i).getVendidas() > 0)
                flag = false;
        }
        return flag;
    }

}
