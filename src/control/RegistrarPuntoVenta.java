/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;
import DAO.PuntodeventaDAO;
import DAO.exceptions.NonexistentEntityException;
import entidad.Puntodeventa;
import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author alirio
 */
public class RegistrarPuntoVenta {
    private PuntodeventaDAO ptoventa;
    public Puntodeventa ptoventaentity,ptoventaentity2;

    public RegistrarPuntoVenta(){
        ptoventa = new PuntodeventaDAO();
    }

    public void crearptoventa(String nombre, String ciudad, String direccion, String telefono, String horario) {

        if(nombre.length()>0 && ciudad.length()>0 && telefono.length()>0 && direccion.length()>0 && horario.length()>0){
            try{
                ptoventaentity = new Puntodeventa();
                ptoventaentity.setNombre(nombre);
                ptoventaentity.setCiudad(ciudad);
                ptoventaentity.setDireccion(direccion);
                ptoventaentity.setTelefono(telefono);
                ptoventaentity.setHorario(horario);
                ptoventa.create(ptoventaentity);
                JOptionPane.showMessageDialog((Component)null,
				"Punto de venta Creado Satidfactoriamente", "Registro",JOptionPane.INFORMATION_MESSAGE);

            }catch(Exception ex){
                JOptionPane.showMessageDialog((Component)null,
				"hubo un error intente de nuevo!!", "Registro",JOptionPane.WARNING_MESSAGE);

            }
      }

    }


     public Puntodeventa getpuntodeventa(String consulta){
        ptoventaentity = ptoventa.getPuntodeventa(consulta);

        if (ptoventaentity!=null){
           return ptoventaentity;
        }
        else{
            return null;
        }
    }


     public void editptoventa(int id, String nombre, String ciudad, String direccion, String telefono, String horario) throws Exception {

         ptoventa = new PuntodeventaDAO();
         ptoventaentity2 = new Puntodeventa();
         ptoventaentity2 = ptoventa.getPunto(id);
         ptoventaentity2.setNombre(nombre);
         ptoventaentity2.setCiudad(ciudad);
         ptoventaentity2.setDireccion(direccion);
         ptoventaentity2.setTelefono(telefono);
         ptoventaentity2.setHorario(horario);
         ptoventa.edit(ptoventaentity2);
         JOptionPane.showMessageDialog((Component)null,
				"Punto de venta editado Satisfactoriamente", "Edición",JOptionPane.INFORMATION_MESSAGE);
     }



      public int deleteptoventa(int id) throws NonexistentEntityException{
          try{
            ptoventa.destroy(id);
            return 0;
          }catch(Exception ex){
              JOptionPane.showMessageDialog((Component)null,
                                        "el punto de venta no se puede borrar ya que existen aun vendedores asociados a este punto", "Error",JOptionPane.ERROR_MESSAGE);
              return 1;
          }
     }

      
     public List getPuntosDeVenta()
     {
        return ptoventa.findPuntodeventaEntities();
     }

}
