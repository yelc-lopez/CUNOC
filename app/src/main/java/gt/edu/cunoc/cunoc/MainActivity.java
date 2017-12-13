package gt.edu.cunoc.cunoc;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;


import gt.edu.cunoc.cunoc.Adapters.FragmentsAdapters;
import gt.edu.cunoc.cunoc.Fragments.NotasFragment;
import gt.edu.cunoc.cunoc.Interfaces.NotasInteractionListener;


public class MainActivity extends AppCompatActivity implements NotasInteractionListener{

    private FragmentsAdapters mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Bundle datosRecividos;
    private NotasFragment notasFragment;
    private int items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getIntent().getExtras() != null){
            datosRecividos = getIntent().getExtras();
            Log.i("bundlerecivido",getIntent().getExtras().toString());

            mSectionsPagerAdapter = new FragmentsAdapters(getSupportFragmentManager(),
                    datosRecividos.getString("id_alumno0"),
                    datosRecividos.getString("id_carrera0"));
            // Set up the ViewPager with the sections adapter.

            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        transicionEntrada();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // agregar los menus a la actividad
        for (int i = 0; i < datosRecividos.size(); i++) {
            if (datosRecividos.getString("id_alumno"+i)!=null){
                Log.i("carrera Recivida ", datosRecividos.getString("id_carrera"+i));
                menu.add(0, i, 0, datosRecividos.getString("nombre_carrera"+i));
                items++;
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        for (int i = 0; i < items; i++) {
            if (id==i){
                Toast.makeText(getApplicationContext(),datosRecividos.getString("Actualizando"+i),Toast.LENGTH_SHORT).show();
                // Crea el nuevo fragmento y la transacción.
                notasFragment.peticionNotasWS(datosRecividos.getString("id_alumno"+i),datosRecividos.getString("id_carrera"+i));
            }
        }

        if (id == R.id.inf_user) {
            startActivity(new Intent(this,UserActivity.class)
                    .putExtra("urlImage", datosRecividos.getString("urlImagen"))
                    .putExtra("name", datosRecividos.getString("nombre"))
                    .putExtra("lastname", datosRecividos.getString("apellido")));
            return true;
        }

        if (id == R.id.close_session){
            startActivity(new Intent(this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(NotasFragment notasFragment) {
        if (notasFragment!=null) {
            this.notasFragment = notasFragment;
        }
    }

    public void transicionEntrada(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Slide(Gravity.LEFT).setInterpolator(new DecelerateInterpolator()).setDuration(1000));
        }
    }

}
