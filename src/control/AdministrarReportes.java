/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.BoletaDAO;
import DAO.EventoDAO;
import DAO.HistoricoeventoDAO;
import DAO.LocacionDAO;
import DAO.PresentacionDAO;
import DAO.RegistroventaDAO;
import DAO.RemuneracionDAO;
import DAO.ReporteventasDAO;
import entidad.Boleta;
import entidad.Evento;
import entidad.Historicoevento;
import entidad.Locacion;
import entidad.Presentacion;
import entidad.Registroventa;
import entidad.Remuneracion;
import entidad.Reporteventas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Frank
 */
public class AdministrarReportes {

    private EventoDAO eventoDAO;
    private ReporteventasDAO reportesDAO;
    private RegistroventaDAO registroDAO;
    private BoletaDAO boletaDAO;
    private PresentacionDAO presentacionDAO;
    private LocacionDAO locacionDAO;
    private RemuneracionDAO remuneracionDAO;
    private HistoricoeventoDAO historicoEventoDAO;

    public AdministrarReportes(){
        eventoDAO = new EventoDAO();
        reportesDAO = new ReporteventasDAO();
        registroDAO = new RegistroventaDAO();
        boletaDAO = new BoletaDAO();
        presentacionDAO = new PresentacionDAO();
        locacionDAO = new LocacionDAO();
        remuneracionDAO = new RemuneracionDAO();
        historicoEventoDAO = new HistoricoeventoDAO();
    }

    public List<Evento> obtenerEventosPorEstado(String estado) {
        return eventoDAO.geteventBystatus(estado);
    }

    public List<Reporteventas> obtenerReportes() {
        return reportesDAO.getReportesVentas();
    }

    public Reporteventas generarReporteVentasPorEvento(Evento evento) {
        double totalVendido = 0;
        int vendidas = 0, reservadas = 0, totalBoletas = 0;
        Reporteventas reporte = new Reporteventas();
        List<Registroventa> registros = registroDAO.getRegistroByEvento(evento.getNombre());
        List<Boleta> boletas;
        List<Presentacion> presentaciones = presentacionDAO.findPresentacionEntitiesByEvento(evento.getId());
        List<Locacion> locaciones;
        for (int i=0; i<presentaciones.size(); i++){
            locaciones = locacionDAO.findLocacionEntitiesByPresentacion(presentaciones.get(i).getId());
            for (int j=0; j<locaciones.size(); j++){
                totalBoletas = totalBoletas + locaciones.get(j).getCupo();
            }
        }
        if (registros != null){
            for (int i=0; i<registros.size(); i++){
                totalVendido = totalVendido + registros.get(i).getTotalVendido();
                boletas = boletaDAO.findBoletaEntitiesByRegistro(registros.get(i).getId());
                for (int j=0; j<boletas.size(); j++){
                    if (boletas.get(j).getEstado().equals("Comprada") || boletas.get(j).getEstado().equals("Entregada"))
                        vendidas ++;
                    else
                        reservadas ++;
                }
            }
            reporte.setFechaReporte(new Date());
            reporte.setEvento(evento.getNombre());
            reporte.setBoletasVendidas(vendidas);
            reporte.setBoletasReservadas(reservadas);
            reporte.setBoletasnovendidas(totalBoletas - (vendidas + reservadas));
            reporte.setTotalVendido(totalVendido);
            reporte.setTipodereporte("Evt");
            reportesDAO.create(reporte);
            return reporte;
        }else{
            reporte.setFechaReporte(new Date());
            reporte.setBoletasReservadas(reservadas);
            reporte.setEvento(evento.getNombre());
            reporte.setBoletasVendidas(vendidas);
            reporte.setBoletasnovendidas(totalBoletas);
            reporte.setTipodereporte("Evt");
            reporte.setTotalVendido(totalVendido);
            reportesDAO.create(reporte);
            return reporte;
        }

    }

    public List<Registroventa> obtenerRegistrosPorEvento(String evento) {
        return registroDAO.getRegistroByEvento(evento);
    }

    public Locacion obtenerLoccionPorRegistro(Registroventa registro) {
        return locacionDAO.findLocacionByRegistro(registro.getId());
    }

    public List<Boleta> obtenerBoletasPorRegistro(Registroventa registro) {
        return boletaDAO.findBoletaEntitiesByRegistro(registro.getId());
    }

    public List<Reporteventas> obtenerReportesPorTipo(String tipo) {
        return reportesDAO.getReportesVentasByTipo(tipo);
    }

    public Reporteventas generarReporteVentasPorFecha(Date fecha) {
        List<Registroventa> registros = registroDAO.getRegistroByFecha(fecha);

        System.out.println(registros);

        Reporteventas reporte = new Reporteventas();
        List<Boleta> boletas;
        double totalVendido = 0;
        int vendidas = 0, reservadas = 0;
        if (!registros.isEmpty()){
            for (int i=0; i<registros.size(); i++){
                totalVendido = totalVendido + registros.get(i).getTotalVendido();
                boletas = boletaDAO.findBoletaEntitiesByRegistro(registros.get(i).getId());
                for (int j=0; j<boletas.size(); j++){
                    if (boletas.get(j).getEstado().equals("Comprada") || boletas.get(j).getEstado().equals("Entregada"))
                        vendidas ++;
                    else
                        reservadas ++;
                }
            }
            reporte.setFechaReporte(new Date());
            reporte.setFechaSolicitud(fecha);
            reporte.setBoletasVendidas(vendidas);
            reporte.setBoletasReservadas(reservadas);
            reporte.setTotalVendido(totalVendido);
            reporte.setTipodereporte("Fec");
            reportesDAO.create(reporte);
            return reporte;
        }else{
            return null;
        }
        
    }

    public List<Registroventa> obtenerRegistrosPorFecha(Date fechaSolicitud) {
        return registroDAO.getRegistroByFecha(fechaSolicitud);
    }

    public List<Remuneracion> obtenerRemuneracionPorEvento(Evento evento) {
        return remuneracionDAO.findRemuneracionByEvento(evento.getNombre());
    }

    public int obtenerTotalClientes(Evento evento) {
        return registroDAO.getRegistroventaCount(evento.getNombre());
    }

    public List<Historicoevento> obtenerHistoricos() {
        return historicoEventoDAO.findHistoricoeventoEntities();
    }

}
