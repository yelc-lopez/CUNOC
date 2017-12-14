package gt.edu.cunoc.cunoc.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import gt.edu.cunoc.cunoc.Adapters.CursosDisponiblesAdapter;
import gt.edu.cunoc.cunoc.DetallesNoticiaActivity;
import gt.edu.cunoc.cunoc.Models.CursoDisponible;
import gt.edu.cunoc.cunoc.Models.Nota;
import gt.edu.cunoc.cunoc.R;
import gt.edu.cunoc.cunoc.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsignacionesFragment extends Fragment {


    private ListView listaCursosDisponibles;
    public static String usuario="";
    public static String carreraActual="";
    private String[] carreras, idcarreras;
    ArrayList<CursoDisponible> cursos;
    private Button button;
    private TableLayout tablaCarreras;
    private int contadorCarreras = 0;

    public AsignacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            Log.i("bundleCursosFragment ", getArguments().toString());
            usuario = getArguments().getString("usuario");
            idcarreras = new String[getArguments().size()];
            carreras = new String[getArguments().size()];
            for (int i = 0; i < getArguments().size(); i++) {
                if (getArguments().getString("id_carrera"+i)!=null){
                    idcarreras[i] = getArguments().getString("id_carrera"+i);
                    carreras[i] = getArguments().getString("nombre_carrera"+i);
                    contadorCarreras++;
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_asignaciones, container, false);

        listaCursosDisponibles = view.findViewById(R.id.listaCursosDisponibles);
        tablaCarreras = view.findViewById(R.id.tablaCarreras);

        listaCursosDisponibles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //EditText e = (EditText) view.findViewById(R.id.codigoCurso);
                Toast.makeText(getContext(),position,Toast.LENGTH_SHORT);
            }
        });

        if (getArguments()!=null){
            ArrayList<String> Nombrescarreras = new ArrayList<>();
            for (int i = 0; i < contadorCarreras; i++) {
                    Nombrescarreras.add(carreras[i]);
            }
            llenarfila(Nombrescarreras, Color.RED);
        }

        return view;
    }


    private class getCursosDispoblesWS extends AsyncTask<Void, Void, String>{

        String resultString = "";
        String url_ = "http://testcunoc.000webhostapp.com//getCursosDisponibles.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(url_);
                // ejecutar url con parametros
                resultString = downloadUrl(url,usuario,carreraActual);
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

            try{
                Log.i("respuestaDisponibles ",respuesta);
                JSONArray jsonArray = new JSONArray(respuesta);
                Log.i("totolDispobles", String.valueOf(jsonArray.length()));

                if (jsonArray.length()>0){

                    // llenar datos
                    cursos = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        cursos.add(new CursoDisponible(jsonArray.getJSONObject(i).getString("id_curso"),
                                jsonArray.getJSONObject(i).getString("codigo"),
                                jsonArray.getJSONObject(i).getString("nombreCurso")));
                    }
                    if (!cursos.isEmpty()) {
                        CursosDisponiblesAdapter adapter = null;
                            adapter = new CursosDisponiblesAdapter(getContext(), cursos);
                        if (listaCursosDisponibles != null){
                            listaCursosDisponibles.setAdapter(adapter);
                        }
                    }

                }else{
                    Toast.makeText(getContext(),"NO SE HA PODIDO DESCARGAR LOS DATOS",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Log.d("error " , e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        /**
         *  Obtener datos de un webService
         */
        private String downloadUrl(URL url,String usuario, String carrera) throws IOException {
            String parametros = "user="+ usuario +"&carrera=" + carrera;
            Log.i("usuario ",usuario);
            Log.i("carrera ",carrera);
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
                Log.i("codrespuesta", String.valueOf(responseCode));
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                stream = connection.getInputStream();
                /* if (stream != null) {
                    result = readStream(stream, 500);
                }*/

                Scanner inStream = new Scanner(stream);
                while(inStream.hasNextLine()){
                    result += (inStream.nextLine());
                }

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

    }


    public void llenarfila(ArrayList<String> datosFila, int color){
        TableRow fila = new TableRow(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,15,5,15);

        for (int i = 0; i < datosFila.size(); i++) {

            final Button btn = new Button(getContext());
            btn.setText(datosFila.get(i));
            btn.setLayoutParams(params);
            btn.setTextColor(color);
            btn.setBackgroundResource(R.color.colorFondoCard);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < getArguments().size(); i++) {
                        if (getArguments().getString("id_carrera"+i)!=null){
                            if (btn.getText().toString().equals(getArguments().getString("nombre_carrera"+i))){
                                //Toast.makeText(getContext(),"Clave " +getArguments().getString("id_carrera"+i),Toast.LENGTH_SHORT).show();
                                AsignacionesFragment.carreraActual = getArguments().getString("id_carrera"+i);
                                try{
                                    new getCursosDispoblesWS().execute();

                                }catch (Exception e){
                                    Log.i("errorAsignaciones",e.getMessage());
                                }
                            }
                        }
                    }

                }
            });

            fila.addView(btn);
        }

        tablaCarreras.addView(fila);
    }

}
