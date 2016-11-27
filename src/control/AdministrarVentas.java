/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.BoletaDAO;
import DAO.CategoriaDAO;
import DAO.ClienteDAO;
import DAO.EventoDAO;
import DAO.LocacionDAO;
import DAO.PresentacionDAO;
import DAO.RegistroventaDAO;
import DAO.UsuarioDAO;
import DAO.exceptions.NonexistentEntityException;
import entidad.Boleta;
import entidad.Categoria;
import entidad.Cliente;
import entidad.Evento;
import entidad.Locacion;
import entidad.Presentacion;
import entidad.Registroventa;
import entidad.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.PVentaBoleta;

/**
 *
 * @author Frank
 */
public class AdministrarVentas {

    private UsuarioDAO usuarioDAO;
    private CategoriaDAO categoriaDAO;
    private EventoDAO eventoDAO;
    private PresentacionDAO presentacionDAO;
    private LocacionDAO locacionDAO;
    private RegistroventaDAO registroVentaDAO;
    private BoletaDAO boletaDAO;
    private double porcentajeReserva;
    private ClienteDAO clienteDAO;

    public AdministrarVentas(){
        usuarioDAO = new UsuarioDAO();
        categoriaDAO = new CategoriaDAO();
        eventoDAO = new EventoDAO();
        presentacionDAO = new PresentacionDAO();
        locacionDAO = new LocacionDAO();
        registroVentaDAO = new RegistroventaDAO();
        boletaDAO = new BoletaDAO();
        clienteDAO = new ClienteDAO();
        porcentajeReserva = 0.3;
    }

    public Usuario obtenerUsuario(String usuario) {
        return usuarioDAO.readByUsername(usuario);
    }

    public List<Categoria> getCategorias() {
        return categoriaDAO.findCategoriaEntities();
    }

    public Categoria getCategoriaByName(String string) {
        return categoriaDAO.findCategoriaByName(string);
    }

    public List<Categoria> getCategoriaByType(Categoria cat) {
        return categoriaDAO.findCategoriaEntitiesByType(cat);
    }

    public List<Evento> getEventosByCategoria(String categoria) {
        return eventoDAO.findEventoEntitiesByCategoria(categoria);
    }

    public List<Presentacion> getPresentacionesByEvento(Evento evento) {
        return presentacionDAO.findPresentacionEntitiesByEvento(evento.getId());
    }

    public List<Locacion> getLocacionesByPresentacion(Presentacion presentacion) {
        return locacionDAO.findLocacionEntitiesByPresentacion(presentacion.getId());
    }

    public void registrarVenta(Registroventa registro, Locacion locacion, Date fecha) {
        registroVentaDAO.create(registro);
        Boleta boleta = new Boleta();
        int cantidad = Integer.parseInt(registro.getCantidadBoletas());
        Calendar auxFecha = Calendar.getInstance();
        auxFecha.setTime(fecha);
        auxFecha.set(Calendar.DAY_OF_MONTH, -31);
        Date fechaEntrega = auxFecha.getTime();

        /*
        int boletasVendidas = locacion.getVendidas();
        locacion.setVendidas(boletasVendidas + Integer.parseInt(registro.getCantidadBoletas()));
        try {
            locacionDAO.edit(locacion);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AdministrarVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdministrarVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarVentas.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        double valorBoleta = locacion.getPrecio();
        System.out.println(fechaEntrega);
        for (int i=0; i<cantidad; i++){
            boleta = new Boleta();
            boleta.setAbono(valorBoleta);
            boleta.setEstado("Comprada");
            boleta.setFkRegistroventa(registro);
            boleta.setFkLocacion(locacion);
            boleta.setFechaEntrega(fechaEntrega);
            boletaDAO.create(boleta);
        }

    }

    public int verificarCupo(Locacion auxLocacion) {
        Locacion locacion = locacionDAO.findLocacion(auxLocacion.getId());
        if (locacion != null){
            return locacion.getCupo() - locacion.getVendidas();
        }else
            return 0;
    }

    public List<Registroventa> cargarRegistros(String documento) {
        Cliente cliente = clienteDAO.findClienteByDocumento(documento);
        if (cliente != null)
            return registroVentaDAO.getRegistroByClienteWeb(documento);
        else
            return registroVentaDAO.getRegistroByClienteDesk(documento);
    }

    public boolean verificarAbono(String auxAbono, String auxTotal) {
        double abono = Double.valueOf(auxAbono).doubleValue();
        double totalapagar = Double.valueOf(auxTotal).doubleValue();
        double abonoMinimo = totalapagar * porcentajeReserva;
        if (abono >= abonoMinimo)
            return true;
        else
            return false;
    }

    public void registrarReserva(Registroventa registro, Locacion auxLocacion, Date fecha, String ab) {
        registroVentaDAO.create(registro);
        double abono = Double.valueOf(ab).doubleValue();
        double precioBoleta = auxLocacion.getPrecio();
        Calendar auxFecha = Calendar.getInstance();
        auxFecha.setTime(fecha);
        auxFecha.set(Calendar.DAY_OF_MONTH, -31);
        Date fechaEntrega = auxFecha.getTime();
        Boleta boleta;
        int cantidad = Integer.parseInt(registro.getCantidadBoletas());
        for (int i=0; i<cantidad; i++){
            boleta = new Boleta();
            if (abono != 0){
                if (abono >= precioBoleta){
                    boleta.setAbono(precioBoleta);
                    boleta.setEstado("Comprada");
                    abono = abono - precioBoleta;
                }else{
                    boleta.setAbono(abono);
                    boleta.setEstado("Reservada");
                    abono = 0;
                }
            }else{
                boleta.setAbono(0.0);
                boleta.setEstado("Reservada");
            }
            boleta.setFechaEntrega(fechaEntrega);
            boleta.setFkLocacion(auxLocacion);
            boleta.setFkRegistroventa(registro);
            boletaDAO.create(boleta);
        }
    }

    public double getPorcentajeReserva() {
        return porcentajeReserva;
    }

    /*public List<Boleta> getBoletasByRegistro(Registroventa reg) {
        return boletaDAO.findBoletaEntitiesByRegistro(reg.getId());
    }*/

    public Locacion getLocacionByBoleta(Boleta boleta) {
        return locacionDAO.getLocacionByboleta(boleta.getId());
    }

    public void cargarReserva(PVentaBoleta venta, Registroventa registro) {
        Evento evento = eventoDAO.findEventoByRegistro(registro);
        venta.setEventoReserva(evento);
        List<Boleta> boletas = boletaDAO.findBoletaEntitiesByRegistro(registro.getId());
        venta.setNumBoletas(boletas.size());
        Locacion locacion = getLocacionByRegistro (registro);
        double saldo,total,abonado = 0;
        total = locacion.getPrecio() * boletas.size();
        for (int i=0; i<boletas.size(); i++){
            if(boletas.get(i).getAbono() != null)
                abonado += boletas.get(i).getAbono();
        }
        saldo = total - abonado;
        venta.setSaldoReserva(saldo);
    }

    public Locacion getLocacionByRegistro(Registroventa registro) {
        return locacionDAO.findLocacionByRegistro(registro.getId());
    }

    public List<Registroventa> verificarReserva(List<Registroventa> registros) {
        int reservas; 
        List<Boleta> boletas;
        for (int i=0; i<registros.size(); i++){
            reservas = 0;
            boletas = new ArrayList();
            boletas = boletaDAO.findBoletaEntitiesByRegistro(registros.get(i).getId());
            for (int j=0; j<boletas.size(); j++){
                if (boletas.get(j).getEstado().equals("Reservada"))
                    reservas ++;
            }
            if (reservas == 0){
                registros.remove(i);
                i--;
            }
        }
        return registros;
    }

    public void actualizarReserva(Registroventa registroReserva, String auxAbono) {
        double saldo,abono = Double.valueOf(auxAbono).doubleValue();
        List<Boleta> boletas = boletaDAO.findBoletaEntitiesByRegistro(registroReserva.getId());
        Locacion locacion = getLocacionByRegistro(registroReserva);
        double valorBoleta = locacion.getPrecio();
        for (int i=0; i<boletas.size(); i++){
            if (boletas.get(i).getEstado().equals("Reservada")){
                saldo = valorBoleta - boletas.get(i).getAbono();
                if (abono != 0){
                    if (abono >= saldo){
                        boletas.get(i).setAbono(valorBoleta);
                        boletas.get(i).setEstado("Comprada");
                        abono = abono - saldo;
                    }else{
                        boletas.get(i).setAbono(valorBoleta - saldo + abono);
                        abono = 0;
                    }
                    try {
                        boletaDAO.edit(boletas.get(i));
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(AdministrarVentas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AdministrarVentas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }


}
