package gt.edu.cunoc.cunoc.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by yelc on 8/09/17.
 */

public class modelTarjeta {

    public modelTarjeta(Drawable rutaImagen, String tituloTarjeta, String detallesTarjeta) {
        this.rutaImagen = rutaImagen;
        this.tituloTarjeta = tituloTarjeta;
        this.detallesTarjeta = detallesTarjeta;
    }

    public Drawable getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(Drawable rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getTituloTarjeta() {
        return tituloTarjeta;
    }

    public void setTituloTarjeta(String tituloTarjeta) {
        this.tituloTarjeta = tituloTarjeta;
    }



    public String getDetallesTarjeta() {
        return detallesTarjeta;
    }

    public void setDetallesTarjeta(String detallesTarjeta) {
        this.detallesTarjeta = detallesTarjeta;
    }


    private Drawable rutaImagen;
    private String tituloTarjeta;
    private String detallesTarjeta;


}
