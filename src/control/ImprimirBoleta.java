/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import entidad.Boleta;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author AnBoCa
 */
public class ImprimirBoleta implements Printable{

    private Boleta boleta;

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d=(Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        //lo que se imprime
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(0, 0, 200, 700);
        graphics.setColor(Color.WHITE);
        graphics.drawString(boleta.getFkLocacion().getNombre(), 10, 10);
        graphics.drawString("$"+boleta.getFkLocacion().getPrecio(), 10, 25);
        graphics.drawString(boleta.getFkLocacion().getFkPresentacion().getFkEvento().getNombre(), 10, 40);
        //darle formato a la fecha
        DateFormat formato=new SimpleDateFormat("E, d MMM yyyy");
        graphics.drawString(formato.format(boleta.getFkLocacion().getFkPresentacion().getFecha()), 10, 55);
        graphics.drawString(boleta.getFkLocacion().getFkPresentacion().getFkEvento().getFkEscenario().getNombre(), 10, 70);
        graphics.drawString(boleta.getFkLocacion().getFkPresentacion().getFkEvento().getFkEscenario().getCiudad(), 10, 85);
        graphics.drawString("Responsable: "+boleta.getFkLocacion().getFkPresentacion().getFkEvento().getFkOrganizador().getNombre(), 10, 100);
        graphics.drawString("Nit: "+boleta.getFkLocacion().getFkPresentacion().getFkEvento().getFkOrganizador().getNit(), 10, 115);
        return PAGE_EXISTS;
    }

    /**
     * @return the boleta
     */
    public Boleta getBoleta() {
        return boleta;
    }

    /**
     * @param boleta the boleta to set
     */
    public void setBoleta(Boleta boleta) {
        this.boleta = boleta;
    }

}
