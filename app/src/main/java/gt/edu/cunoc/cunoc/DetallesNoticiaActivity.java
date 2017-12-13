package gt.edu.cunoc.cunoc;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import gt.edu.cunoc.cunoc.Models.Dato;

public class DetallesNoticiaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewContenido,textViewTitulo;
    private Toolbar toolbar;
    private Dato dato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_noticia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        instanciasObj();
        transicionEntrada();
        //changeProperties();
        //buttonUp();

    }


    // instaciar objetos
    public void instanciasObj(){
        imageView = findViewById(R.id.app_bar_image);
        textViewContenido = findViewById(R.id.textViewContenido);
        textViewTitulo = findViewById(R.id.textViewTitulo);
        toolbar = findViewById(R.id.toolbar);

        // objeto que se recibe atravez del intent
        if (getIntent().getExtras() != null){
            dato = getIntent().getParcelableExtra("bitMap");
            updateDatos();
        }

    }

    // modificarDatos de acuerdo a los parametros recibidos
    public void updateDatos(){
        imageView.setImageBitmap(dato.getImagen());
        textViewContenido.setText(dato.getDetalle());
        textViewTitulo.setText(dato.getTitulo());
    }


    // public void cambiar propiedades de personalizacion
    public void changeProperties(){
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
    }

    // transicion para fragment
    public void transicionEntrada(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Slide fade = new Slide(Gravity.BOTTOM);
            fade.setDuration(500);
            fade.setInterpolator(new DecelerateInterpolator());
            getWindow().setEnterTransition(fade);
            getWindow().setAllowEnterTransitionOverlap(false);
        }
    }

    // Enable the Up button
    public void buttonUp(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
