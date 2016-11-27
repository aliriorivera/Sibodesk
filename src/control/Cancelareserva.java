
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;
import DAO.BoletaDAO;
import DAO.ClienteDAO;
import DAO.RegistroventaDAO;
import DAO.PresentacionDAO;
import DAO.exceptions.NonexistentEntityException;
import DAO.RemuneracionDAO;
import entidad.Remuneracion;
import entidad.Presentacion;
import DAO.LocacionDAO;
import DAO.EventoDAO;
import entidad.Evento;
import entidad.Boleta;
import entidad.Cliente;
import entidad.Locacion;
import entidad.Registroventa;
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
public class Cancelareserva {

    private BoletaDAO boletaDAO;
    private RegistroventaDAO registroventaDAO;
    private PresentacionDAO presentacionDAO;
    private RemuneracionDAO remuneracionDAO;
    private Remuneracion remuneracion;
    private Presentacion presentacion;
    private LocacionDAO locacionDAO;
    private Locacion locacion;
    private EventoDAO eventoDAO;
    private Evento evento;
    private List<Registroventa> listaidregventa;
    private List<Boleta> listaidboleta;
    public List<String> lista = new ArrayList<String>();
    private int h=0,m=0;
    private String mes, anio, dia;

    public void Cancelareserva(){
       // clienteDAO = new ClienteDAO();
    }

    public List getdocumento(String documento){
        ClienteDAO clienteDAO = new ClienteDAO();
       registroventaDAO = new RegistroventaDAO();
       Cliente cliente = clienteDAO.findClienteByDocumento(documento);
       if (cliente == null)
            listaidregventa = registroventaDAO.getRegistroByClienteDesk(documento);
       else
           listaidregventa = registroventaDAO.getRegistroByClienteWeb(documento);
       int c1 = listaidregventa.size();

        //System.out.print("hhh"+c1);
       if(c1 != 0){

           for(int i=0; i<c1; i++){
               int c2 = listaidregventa.get(i).getId();
               boletaDAO = new BoletaDAO();
               listaidboleta = boletaDAO.getidboleta(c2);

                   int c3 = listaidboleta.size();
                   //System.out.print(i +"asdasd"+c3);

               if(c3 != 0){


                   for(int j=0; j<c3;j++){
                       String c4 = listaidboleta.get(j).getEstado();
                       if(c4.equals("Reservada")){
                           Date fecha = listaidboleta.get(j).getFechaEntrega();
                           int locationf = listaidboleta.get(j).getFkLocacion().getId();
                           int idboleta = listaidboleta.get(j).getId();
                           String nombre = getevent(locationf);
                           String nombreloc = getlocation(locationf);
                           double abono = listaidboleta.get(j).getAbono();

                           lista.add(Integer.toString(idboleta));
                           lista.add(fecha.toString());
                           lista.add(nombre);
                           lista.add(nombreloc);
                           lista.add(setlisttotal(locationf));//precio
                           //m++;//es el numero de
                           lista.add(Double.toString(abono));
                           //m++;
                       }
                   }
                   //lista.add(Integer.toString(m));


               }
               else{
                   JOptionPane.showMessageDialog((Component)null,
				"El numero de identificacion no tiene ninguna boleta en algun estado", "Ninguna Reserva",JOptionPane.WARNING_MESSAGE);

               }

           }
           return lista;
       }
       return null;
    }


    //se encarag de traer el nombre del evento
    public String getevent(int location){

        locacionDAO = new LocacionDAO();
        locacion = new Locacion();

        locacion = locacionDAO.getLocacionByboleta(location);
        int locid = locacion.getFkPresentacion().getId();

        presentacionDAO = new PresentacionDAO();
        presentacion = new Presentacion();

        presentacion = presentacionDAO.getpresentacionBylocacion(locid);
        int presen = presentacion.getFkEvento().getId();

        eventoDAO = new EventoDAO();
        evento = new Evento();

        evento = eventoDAO.geteventBypresentation(presen);
        String nombrevento = evento.getNombre();

        return nombrevento;
    }

    //se encarga de traer el nombre de la locacion
    public String getlocation(int location){
        locacionDAO = new LocacionDAO();
        locacion = new Locacion();

        locacion = locacionDAO.getLocacionByboleta(location);
        String nameloc = locacion.getNombre();
        return nameloc;
    }

    //se encarga de establecer el valor de la lista en el total de la boleta
    public String setlisttotal(int location){
        locacionDAO = new LocacionDAO();
        locacion = new Locacion();

        locacion = locacionDAO.getLocacionByboleta(location);
        double precio = locacion.getPrecio();
        return (Double.toString(precio));
    }

    public String getregventa(int id){
        boletaDAO = new BoletaDAO();
        int j = boletaDAO.getidBoleta(id);
        registroventaDAO = new RegistroventaDAO();
        String r = registroventaDAO.getcantidadBoleta(j);
        return r;
    }


    public void destroyboletaregventa(int id) throws NonexistentEntityException{
        System.out.print(" id: "+id);
        boletaDAO = new BoletaDAO();
        int registro = boletaDAO.getidBoleta(id);
        System.out.print(" fkreg: "+registro);
        try{
            //boletaDAO.destroy(id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog((Component)null,
				"No se pudo cancelar la reserva, intente mas tarde!!", "error",JOptionPane.ERROR_MESSAGE);
        }
        registroventaDAO = new RegistroventaDAO();
        try{
            //registroventaDAO.destroy(registro);
        }catch(Exception ex){
             JOptionPane.showMessageDialog((Component)null,
				"No se pudo cancelar la reserva, intente mas tarde!!", "error",JOptionPane.ERROR_MESSAGE);
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
