package control;
import entidad.Usuario;
import DAO.UsuarioDAO;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class RegistrarVendedor
{
    public RegistrarVendedor()
    {
    }

    private void validarDatos( Usuario pUsuario )throws Exception
    {
        Exception  lException;
        if( pUsuario != null )
        {
            if( pUsuario.getNombres() == null || pUsuario.getNombres().equals("") )
            {
                lException = new Exception("Falta valores para los nombres");
                throw lException;
            }
        }
        else
        {
            lException = new Exception("Objeto Invalido");
            throw lException;
        }

    }

    public Usuario registrarVendedor( Usuario pUsuario )throws Exception
    {
        validarDatos(pUsuario);
        UsuarioDAO lUsuarioDAO = new UsuarioDAO();
        lUsuarioDAO.create(pUsuario);

        return pUsuario;
    }

    public List obtenerVendedores( String pCampoFiltro, String pValorFiltro )throws Exception
    {
           List lRegistros = null;
           UsuarioDAO lUsuarioDAO = new UsuarioDAO();
           Exception lException = null;

           try
           {
                if( pCampoFiltro != null && !pCampoFiltro.equals("") &&
                    pValorFiltro != null && !pValorFiltro.equals(""))
                {
                        if( pCampoFiltro.equals("Cedula") )
                        {
                            lRegistros = lUsuarioDAO.readByCedula(pValorFiltro);
                        }
                        else if( pCampoFiltro.equals("Nombre") )
                        {
                            lRegistros = lUsuarioDAO.readByName(pValorFiltro);
                        }
                        else
                        {
                            lRegistros = null;
                        }
                }
                else
                {
                   lRegistros = null;
                }
           }
           catch( Exception pException )
           {
               lException = new Exception("No se encontraron registros para la busqueda" );
               throw lException;
           }

           return lRegistros;
    }

    public Usuario modificarVendedor( Usuario pUsuario )throws Exception
    {
        validarDatos(pUsuario);
        UsuarioDAO lUsuarioDAO = new UsuarioDAO();
        lUsuarioDAO.edit(pUsuario);

        return pUsuario;
    }

    public Usuario eliminarVendedor( Usuario pUsuario )throws Exception
    {
        UsuarioDAO lUsuarioDAO = new UsuarioDAO();
        lUsuarioDAO.destroy(pUsuario.getId());

        return pUsuario;
    }

    public List<Usuario> obtenerTodos(String tipo) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findUsuarioEntitiesByTipo(tipo);
    }

    public Usuario obtenerVendedorPorCedula(String cedula) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.obtenerPorCedula(cedula);
    }

}
