package gt.edu.cunoc.cunoc.Adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import gt.edu.cunoc.cunoc.Fragments.AsignacionesFragment;
import gt.edu.cunoc.cunoc.Fragments.NotasFragment;
import gt.edu.cunoc.cunoc.Fragments.NoticiasV2Fragment;

/**
 * Created by yelc on 11/12/17.
 */

public class FragmentsAdapters extends FragmentPagerAdapter {

    Bundle datosNotas = null;
    Bundle datosCursos = null;

    public FragmentsAdapters(FragmentManager fm) {
        super(fm);
    }

    public FragmentsAdapters(FragmentManager fm, Bundle datosNotas, Bundle datosCursos) {
        super(fm);
        this.datosNotas = datosNotas;
        this.datosCursos = datosCursos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NotasFragment notasFragment =  new NotasFragment();
                notasFragment.setArguments(datosNotas);
                return notasFragment;
            case 1:
                return new NoticiasV2Fragment();
            case 2:
                AsignacionesFragment asignacionesFragment = new AsignacionesFragment();
                asignacionesFragment.setArguments(datosCursos);
                return asignacionesFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Notas";
            case 1:
                return "Noticias";
            case 2:
                return "Asignaciones";
        }
        return null;
    }

}
