package gt.edu.cunoc.cunoc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONArray;
import org.json.JSONException;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText user, pin;
    Button loguearse;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // UI references
        user = findViewById(R.id.edit_text_user);
        pin = findViewById(R.id.edit_text_pin);
        loguearse = findViewById(R.id.btn_loguearse);
        lottieAnimationView = findViewById(R.id.animacionProgress);

        loguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearse.setClickable(false);
                new IniciarSesionWS().execute();
            }
        });
        transicionEntrada();
    }



    public class IniciarSesionWS extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                Toast.makeText(getApplicationContext(),"ERROR EN CONEXION",Toast.LENGTH_LONG).show();
                cancel(true);
                loguearse.setClickable(true);
            } else if (user.getText().toString().isEmpty()){
                user.setError("Usuario Obligatorio");
                cancel(true);
                loguearse.setClickable(true);
            } else if (pin.getText().toString().isEmpty()){
                pin.setError("pin no puede ser nulo");
                cancel(true);
                loguearse.setClickable(true);
            }else{
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setMinAndMaxFrame(0,100);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
           String resultString = "";
            //String url_= "http://192.168.1.7/WebServiceAndroid/getDatos.php";
            String url_ = "http://testcunoc.000webhostapp.com//getDatos.php";
                URL url = null;
                try {
                    url = new URL(url_);
                    // ejecutar url con parametros
                    resultString = downloadUrl(url,/*user.getText().toString(),pin.getText().toString()*/"269014-10997","789123");
                    return resultString;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return resultString;
        }

        @Override
        protected void onPostExecute(String respuesta) {
            try {

                Log.i("respuesta ", respuesta);
                JSONArray jsonArray = new JSONArray(respuesta);
                Log.d("job ",jsonArray.toString());
                Log.d("tamaño",String.valueOf(jsonArray.length()));
                if (jsonArray.length()>0){

                    Bundle datosAlumno = new Bundle();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("valor", jsonArray.getJSONObject(i).getString("id_alumno"));
                        datosAlumno.putString("id_alumno"+i,jsonArray.getJSONObject(i).getString("id_alumno"));
                        datosAlumno.putString("id_carrera"+i,jsonArray.getJSONObject(i).getString("id_carrera"));
                    }
                    nextActivity(MainActivity.class,datosAlumno);

                }else{
                    Toast.makeText(getApplicationContext(),"Revise Los Datos",Toast.LENGTH_LONG).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }

            } catch (JSONException e) {
                Log.d("error " , e.getMessage());
                lottieAnimationView.setVisibility(View.INVISIBLE);
            }
            loguearse.setClickable(true);
        }

        /**
         *  Obtener datos de un webService
         */
        private String downloadUrl(URL url,String usuario, String pin) throws IOException {
            String parametros = "user="+ usuario +"&password=" + pin;
            Log.i("usuario ",usuario);
            Log.i("pin ",pin);
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = "";
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Lenght",""+Integer.toString(parametros.getBytes().length));
                connection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(parametros);
                dataOutputStream.close();

                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                stream = connection.getInputStream();
                if (stream != null) {
                    result = readStream(stream, 500);
                }

               /* Scanner inStream = new Scanner(stream);
                while(inStream.hasNextLine()){
                    result += (inStream.nextLine());
                }*/

            }catch (Exception e){e.printStackTrace();}
            finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result.toString();
        }

        /**
         * Converts the contents of an InputStream to a String.
         */
        private String readStream(InputStream stream, int maxReadSize)
                throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] rawBuffer = new char[maxReadSize];
            int readSize;
            StringBuffer buffer = new StringBuffer();
            while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
                if (readSize > maxReadSize) {
                    readSize = maxReadSize;
                }
                buffer.append(rawBuffer, 0, readSize);
                maxReadSize -= readSize;
            }
            return buffer.toString();
        }

    }

    public void transicionEntrada(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Slide(Gravity.LEFT).setInterpolator(new DecelerateInterpolator()).setDuration(1000));
        }
    }

    public void nextActivity(Class activity, Bundle datos){
        Intent intent = new Intent(getApplicationContext(),activity);
        intent.putExtras(datos);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setExitTransition(new Slide(Gravity.RIGHT).setInterpolator(new DecelerateInterpolator()).setDuration(1000));
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }
    }

}


