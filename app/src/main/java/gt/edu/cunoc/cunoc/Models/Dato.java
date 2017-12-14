package gt.edu.cunoc.cunoc.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yelc on 11/09/17.
 * clase utilizada para pasar un objeto entre intents
 *
 */

public class Dato implements Parcelable {

    private Bitmap imagen;
    private String titulo, detalle, rutaImagen;


    public Dato(Bitmap imagen, String titulo, String detalle, String rutaImagen) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.detalle = detalle;
        this.rutaImagen = rutaImagen;
    }

    protected Dato(Parcel in) {
        imagen = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        titulo = in.readString();
        detalle = in.readString();
        rutaImagen = in.readString();
    }

    public static final Creator<Dato> CREATOR = new Creator<Dato>() {
        @Override
        public Dato createFromParcel(Parcel in) {
            return new Dato(in);
        }

        @Override
        public Dato[] newArray(int size) {
            return new Dato[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(imagen);
        dest.writeString(titulo);
        dest.writeString(detalle);
        dest.writeString(rutaImagen);
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
