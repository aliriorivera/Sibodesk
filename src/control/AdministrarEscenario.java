/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.EscenarioDAO;
import entidad.Escenario;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class AdministrarEscenario {

    
    public void registrarEscenario( String nombre, String direccion, String mapa, String telefono, String ciudad )throws Exception{
        EscenarioDAO lEscenarioDAO = new EscenarioDAO();
        Escenario es = new  Escenario();
        es.setNombre(nombre);
        es.setDireccion(direccion);
        es.setMapa(mapa);
        es.setTelefono(telefono);
        es.setCiudad(ciudad);
        lEscenarioDAO.create(es);

    }

    public List Buscar (String nombre){
        EscenarioDAO l = new EscenarioDAO();
        Escenario es = new Escenario();
        es = l.findEscenarioByName(nombre);
        if (es!=null){
            List<String> lista = new ArrayList<String>();
            lista.add(es.getId().toString());

            lista.add(es.getNombre());
            lista.add(es.getCiudad());
            lista.add(es.getDireccion());
            lista.add(es.getTelefono());
            lista.add(es.getMapa());
            return lista;
        }
        else
            return null;

    }

    public void modificarEscenario( int id, String nombre, String direccion, String mapa, String telefono, String ciudad )throws Exception
    {
        EscenarioDAO l = new EscenarioDAO();
        Escenario es = new Escenario();
        es = l.findEscenario(id);
        es.setCiudad(ciudad);
        es.setDireccion(direccion);
        es.setMapa(mapa);
        es.setNombre(nombre);
        es.setTelefono(telefono);
        l.edit(es);    
    }

    public int eliminarEscenario(int id )throws Exception
    {
        try{
            EscenarioDAO l = new EscenarioDAO();
            l.destroy(id);
            return 0;
        }catch(Exception ex){
            JOptionPane.showMessageDialog((Component)null,
				"el escenario no puede ser eliminado porque hay eventos programados en el mismo", "eventos programados",JOptionPane.ERROR_MESSAGE);
            return 1;
        }

    }

}
