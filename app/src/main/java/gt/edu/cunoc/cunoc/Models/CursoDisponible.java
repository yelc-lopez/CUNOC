package gt.edu.cunoc.cunoc.Models;

/**
 * Created by yelc on 13/12/17.
 */

public class CursoDisponible {
    String id_curso, codigo_curso, nombre_curso;

    public CursoDisponible(String id_curso,String codigo_curso, String nombre_curso) {
        this.id_curso = id_curso;
        this.codigo_curso = codigo_curso;
        this.nombre_curso = nombre_curso;
    }

    public String getId_curso() {
        return id_curso;
    }

    public void setId_curso(String id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre_curso() {
        return nombre_curso;
    }

    public void setNombre_curso(String nombre_curso) {
        this.nombre_curso = nombre_curso;
    }

    public String getCodigo_curso() {
        return codigo_curso;
    }

    public void setCodigo_curso(String codigo_curso) {
        this.codigo_curso = codigo_curso;
    }
}
