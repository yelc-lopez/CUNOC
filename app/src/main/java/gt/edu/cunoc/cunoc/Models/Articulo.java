package gt.edu.cunoc.cunoc.Models;

/**
 * Created by yelc on 30/11/17.
 */

public class Articulo {
    String titulo, contenido, imagen, rutaImagen;

    public Articulo(String titulo, String contenido, String imagen, String rutaImagen) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.imagen = imagen;
        this.rutaImagen = rutaImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
