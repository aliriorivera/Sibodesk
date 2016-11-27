/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Frank
 */
@Entity
@Table(name = "historicoevento", catalog = "sibodb", schema = "", uniqueConstraints = {@UniqueConstraint(columnNames = {"evento"})})
@NamedQueries({@NamedQuery(name = "Historicoevento.findAll", query = "SELECT h FROM Historicoevento h"), @NamedQuery(name = "Historicoevento.findById", query = "SELECT h FROM Historicoevento h WHERE h.id = :id"), @NamedQuery(name = "Historicoevento.findByFecha", query = "SELECT h FROM Historicoevento h WHERE h.fecha = :fecha"), @NamedQuery(name = "Historicoevento.findByEvento", query = "SELECT h FROM Historicoevento h WHERE h.evento = :evento"), @NamedQuery(name = "Historicoevento.findByEstado", query = "SELECT h FROM Historicoevento h WHERE h.estado = :estado"), @NamedQuery(name = "Historicoevento.findByBoletasVendidas", query = "SELECT h FROM Historicoevento h WHERE h.boletasVendidas = :boletasVendidas"), @NamedQuery(name = "Historicoevento.findByTotalBoletas", query = "SELECT h FROM Historicoevento h WHERE h.totalBoletas = :totalBoletas")})
public class Historicoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "evento", nullable = false, length = 45)
    private String evento;
    @Basic(optional = false)
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;
    @Basic(optional = false)
    @Column(name = "boletasVendidas", nullable = false)
    private int boletasVendidas;
    @Basic(optional = false)
    @Column(name = "totalBoletas", nullable = false)
    private int totalBoletas;

    public Historicoevento() {
    }

    public Historicoevento(Integer id) {
        this.id = id;
    }

    public Historicoevento(Integer id, Date fecha, String evento, String estado, int boletasVendidas, int totalBoletas) {
        this.id = id;
        this.fecha = fecha;
        this.evento = evento;
        this.estado = estado;
        this.boletasVendidas = boletasVendidas;
        this.totalBoletas = totalBoletas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getBoletasVendidas() {
        return boletasVendidas;
    }

    public void setBoletasVendidas(int boletasVendidas) {
        this.boletasVendidas = boletasVendidas;
    }

    public int getTotalBoletas() {
        return totalBoletas;
    }

    public void setTotalBoletas(int totalBoletas) {
        this.totalBoletas = totalBoletas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historicoevento)) {
            return false;
        }
        Historicoevento other = (Historicoevento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Historicoevento[id=" + id + "]";
    }

}
