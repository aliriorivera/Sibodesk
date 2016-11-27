/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import DAO.UsuarioDAO;
import entidad.Usuario;

/**
 *
 * @author AnBoCa
 */
public class ValidarIngreso {
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    public ValidarIngreso(){
        usuarioDAO=new UsuarioDAO();
    }
    public boolean validarIngreso(String username,String password){
        if (username.length()>0 && password.length()>0){
            usuario=usuarioDAO.readByUsername(username);
            if (usuario!=null){
                if (usuario.getContrasena().equals(password) && usuario.getActivo()==true)
                    return true;
            }
        }
        return false;
    }
    public String getRol(){
        return usuario.getFkRol().getRol();
    }
}
