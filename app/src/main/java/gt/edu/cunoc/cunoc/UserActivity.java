package gt.edu.cunoc.cunoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {

    //private String urlImagen = "http://192.168.1.7/WebServiceAndroid/";
    private String urlImagen = "https://testcunoc.000webhostapp.com/";
    private CircleImageView f_Perfil;
    private TextView name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // UI References
        f_Perfil = findViewById(R.id.circleImagViewPerfil);
        name = findViewById(R.id.textViewName);



        if (getIntent().getExtras() != null){
            urlImagen += getIntent().getExtras().getString("urlImage");
            Glide.with(this).load(urlImagen).into(f_Perfil);
            name.setText(getIntent().getExtras().getString("name")
                + " " + getIntent().getExtras().getString("lastname"));
        }

    }
}
