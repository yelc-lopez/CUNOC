package gt.edu.cunoc.cunoc.Models;

import java.util.ArrayList;

/**
 * Created by INTECAP on 30/11/2017.
 */

public class Nota {
    String codigo, curso, zona, nfinal, total, perfil;

    public Nota(String codigo, String curso, String zona, String nfinal, String total, String perfil) {
        this.codigo = codigo;
        this.curso = curso;
        this.zona = zona;
        this.nfinal = nfinal;
        this.total = total;
        this.perfil = perfil;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNfinal() {
        return nfinal;
    }

    public void setNfinal(String nfinal) {
        this.nfinal = nfinal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public ArrayList<String> datos(){
        ArrayList<String> data = new ArrayList<>();
        data.add(codigo);
        data.add(curso);
        data.add(zona);
        data.add(nfinal);
        data.add(total);
        data.add(perfil);
        return data;
    }
}
