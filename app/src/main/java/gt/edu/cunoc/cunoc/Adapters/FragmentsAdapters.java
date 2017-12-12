package gt.edu.cunoc.cunoc.Adapters;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import gt.edu.cunoc.cunoc.Fragments.AlumnoFragment;
import gt.edu.cunoc.cunoc.Fragments.ExtraFragment;
import gt.edu.cunoc.cunoc.Fragments.NotasFragment;
import gt.edu.cunoc.cunoc.Fragments.NoticiasFragment;
import gt.edu.cunoc.cunoc.R;

/**
 * Created by yelc on 11/12/17.
 */

public class FragmentsAdapters extends FragmentPagerAdapter {

    String id_usuario, id_carrera;

    public FragmentsAdapters(FragmentManager fm) {
        super(fm);
    }

    public FragmentsAdapters(FragmentManager fm, String id_usuario, String id_carrera) {
        super(fm);
        this.id_usuario = id_usuario;
        this.id_carrera = id_carrera;
        Log.i("valor aaa ", id_usuario);
        Log.i("valor ccc ", id_carrera);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NotasFragment notasFragment = new NotasFragment();
                if (id_usuario!=null && id_carrera!=null){
                Bundle bundle = new Bundle();
                bundle.putString("id_usuario",id_usuario);
                bundle.putString("id_carrera",id_carrera);
                notasFragment.setArguments(bundle);
                }
                return notasFragment;
            case 1:
                return new NoticiasFragment();
            case 2:
                return new AlumnoFragment();
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
                return "Extras";
        }
        return null;
    }

}
