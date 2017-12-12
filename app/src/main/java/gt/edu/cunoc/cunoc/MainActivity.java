package gt.edu.cunoc.cunoc;


import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.TableLayout;


import gt.edu.cunoc.cunoc.Adapters.FragmentsAdapters;
import gt.edu.cunoc.cunoc.Fragments.NotasFragment;
import gt.edu.cunoc.cunoc.Fragments.NoticiasFragment;
import gt.edu.cunoc.cunoc.Interfaces.SeleccionCarrera;


public class MainActivity extends AppCompatActivity implements SeleccionCarrera{

    private FragmentsAdapters mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private  Bundle datosRecividos;
    TableLayout tabla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new FragmentsAdapters(getSupportFragmentManager(),"1","1");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (getIntent().getExtras() != null){
            datosRecividos = getIntent().getExtras();
            Log.i("AlumnoRecivido " , datosRecividos.getString("id_alumno0"));
            Log.i("CarreraRecivido " , datosRecividos.getString("id_carrera0"));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            try{
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void idCarrera(String id_alumno, String id_carrera) {
    }


    public class FragmentsAdaptersV2 extends FragmentPagerAdapter {

        String id_usuario, id_carrera;

        public FragmentsAdaptersV2(FragmentManager fm) {
            super(fm);
        }

        public FragmentsAdaptersV2(FragmentManager fm, String id_usuario, String id_carrera) {
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
                    return new NotasFragment();
                case 2:
                    return new NotasFragment();
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

}
