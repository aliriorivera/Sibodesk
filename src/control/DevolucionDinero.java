/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.EventoDAO;
import DAO.exceptions.NonexistentEntityException;
import entidad.Evento;
import DAO.PresentacionDAO;
import entidad.Presentacion;
import DAO.RegistroventaDAO;
import entidad.Registroventa;
import DAO.BoletaDAO;
import DAO.RemuneracionDAO;
import entidad.Remuneracion;
import entidad.Boleta;
import DAO.LocacionDAO;
import entidad.Locacion;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author alirio
 */
public class DevolucionDinero {
    public List<Evento> listcancelevents;
    public List<Presentacion> listpresentacion;
    private EventoDAO eventoDAO;
    private Evento evento;
    private PresentacionDAO presentacionDAO;
    private Presentacion presentacion;
    private RegistroventaDAO registroventadao;
    private Registroventa registroventa;
    private BoletaDAO boletaDAO;
    private Boleta boleta;
    private LocacionDAO locacionDAO;
    private Locacion locacion;
    private RemuneracionDAO remuneracionDAO;
    private Remuneracion remuneracion;
    public List listcancel = new ArrayList<String>();
    public List id = new ArrayList<String>();
    public List<Presentacion> ide;
    public List listpresentation = new ArrayList<String>();
    public List<Registroventa> listreg;
    public List listregistro = new ArrayList<String>();
    public List<Boleta> listboleta;
    public List<Locacion> listlocacion;
    public List listloc = new ArrayList<String>();
    private String mes, anio, dia;



/*Con este metodo obtengo los eventos canelados con sus respectivas id*/
public List<String> getcancelevents(String status){
    eventoDAO = new EventoDAO();
    listcancelevents = eventoDAO.geteventBystatus(status);
    int g = listcancelevents.size();
    if(g != 0){
        int size =  listcancelevents.size();
        for (int i=0;i<size ;i++){
            listcancel.add(listcancelevents.get(i).getId().toString());
            listcancel.add(listcancelevents.get(i).getNombre());
        }
        return listcancel;
    }else{
        JOptionPane.showMessageDialog((Component)null,
				"No existe ningun evento cancelado (No devolver ningun dinero!!!)", "Ninguna Cancelacion",JOptionPane.WARNING_MESSAGE);
    }

    return null;

}


/*Con este metodo obtengo las presentaciones de los eventos cancelados*/
public List getpresentation(int event){
        presentacionDAO = new PresentacionDAO();
        listpresentacion = presentacionDAO.getPresentacionByEvento(event);
        int h = listpresentacion.size();
        if(h!=0){
            for(int i=0; i<h; i++){
                listpresentation.add(listpresentacion.get(i).getFecha().toString());
            }
        }
        else{
//            JOptionPane.showMessageDialog((Component)null,
//                                    "No existe ninguna presentacion con el evento relacionado", "Ninguna Presentacion",JOptionPane.WARNING_MESSAGE);
        }


        return listpresentation;
}


/*Se obtinen las id de las presentaciones*/
public List getpresentationid(int event){
        presentacionDAO = new PresentacionDAO();
        ide = presentacionDAO.getPresentacionByEvento(event);
        int h = ide.size();
        if(h!=0){
            for(int i=0; i<h; i++){
                id.add(ide.get(i).getId().toString());
            }
        }
        else{
//            JOptionPane.showMessageDialog((Component)null,
//                                    "No existe ninguna presentacion con el evento relacionado", "Ninguna Presentacion",JOptionPane.WARNING_MESSAGE);
        }


        return id;
}


public List getregistro(String document, String event, int presentation){

    listregistro.clear();
    int prueba=0;
    registroventadao = new RegistroventaDAO();
    registroventa = new Registroventa();
    listreg = registroventadao.getRegistroByidenandname(document, event);
    int g = listreg.size();
    if(g!=0){

        for(int i=0; i<g; i++){
            int idreg = listreg.get(i).getId();
            //System.out.print("hhh" + idreg);
            boletaDAO = new BoletaDAO();
            int k = boletaDAO.getidBoletaCount(idreg);
            //System.out.print("k" + k);
            locacionDAO = new LocacionDAO();
            int t = locacionDAO.getidLocacionCount(k);
            //System.out.print("t" + t);
            //System.out.print("pre" + presentation);

            if(t == presentation){
                listregistro.add(listreg.get(i).getId().toString());
                listregistro.add(listreg.get(i).getFecha().toString());
                listregistro.add(listreg.get(i).getCantidadBoletas().toString());
                prueba=1;
            }
            else{
                   /* JOptionPane.showMessageDialog((Component)null,
                                    "eso pñasoososo", "Ninguna Boleta",JOptionPane.WARNING_MESSAGE);*/
            }

        }
        if(prueba==0){
            JOptionPane.showMessageDialog((Component)null,
                                    "El numero de cedula no tiene una compra de boleta para esa presentacion ", "Ninguna Boleta",JOptionPane.WARNING_MESSAGE);
        }

    }

    else{
    JOptionPane.showMessageDialog((Component)null,
                                    "El numero de cedula no tiene una compra de boleta para ese evento", "Ninguna Boleta",JOptionPane.WARNING_MESSAGE);
    }

    return listregistro;

}


public List getlocaciones(int id){
    boletaDAO = new BoletaDAO();
    boleta = new Boleta();
    boleta = boletaDAO.getboleta(id);
    int h = boletaDAO.getidBoletaCount(id);
    locacionDAO = new LocacionDAO();
    locacion = new Locacion();
    locacion = locacionDAO.getLocacionByboleta(h);
    listloc.add(locacion.getNombre().toString());
    listloc.add(Double.toString(locacion.getPrecio()));
    listloc.add(Double.toString(boleta.getAbono()));
    return listloc;
}

public void destroyboletaregventa(int id) throws NonexistentEntityException{
    try{
        registroventadao = new RegistroventaDAO();
        boletaDAO = new BoletaDAO();
        boleta = new Boleta();
        boleta =  boletaDAO.getboleta(id);
        //eliminamos la boleta
        //boletaDAO.destroy(boleta.getId());
        //eliminamos el registro de venta
        //registroventadao.destroy(id);
    }catch(Exception ex){
        JOptionPane.showMessageDialog((Component)null,
                                    "Hubo un error durante el registro de la devolucion", "error",JOptionPane.ERROR_MESSAGE);
    }
}


    public void setremuneracion(String document, String event, Double value){
         Date fec = Fecha();
            if(fec != null){
                remuneracionDAO = new RemuneracionDAO();
                remuneracion = new Remuneracion();
                remuneracion.setDocumento(document);
                remuneracion.setEvento(event);
                remuneracion.setFecha(fec);
                remuneracion.setValor(value);
                remuneracionDAO.create(remuneracion);
            }
            else{
            JOptionPane.showMessageDialog((Component)null,
				"registro no creado", "error",JOptionPane.ERROR_MESSAGE);
            }
    }



    private Date Fecha(){
        Calendar calendar = Calendar.getInstance();
        anio = Integer.toString(calendar.get(Calendar.YEAR));
        mes = Integer.toString(calendar.get(Calendar.MONTH));
        dia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String fec = "" + anio + "-" + mes + "-" + dia;
        boolean parsing = true;
            Date fecha = null;
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fecha = formatoFecha.parse(fec);
            } catch (ParseException ex) {
                parsing=false;
            }
            if(parsing){
                return fecha;
            }
            else {return null;}
    }

}

