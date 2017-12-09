package gt.edu.cunoc.cunoc.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import gt.edu.cunoc.cunoc.MainActivity;
import gt.edu.cunoc.cunoc.Models.Nota;
import gt.edu.cunoc.cunoc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotasFragment extends Fragment {

    TableLayout tabladatos;
    private String alumno = "", carrera = "";

    public NotasFragment() {
        // Required empty public constructor
    }

    public NotasFragment(String alumno, String carrera) {
        this.alumno = alumno;
        this.carrera = carrera;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_notas, container, false);
        // reference UI
        tabladatos = view.findViewById(R.id.tablaDatos);

        if (!alumno.isEmpty() && !carrera.isEmpty()){
            new getNotas().execute();
        }

        return view;
    }

    public class getNotas extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                Toast.makeText(getContext(),"CONECTESE A UNA RED WIFI O ACTIVE LOS DATOS",Toast.LENGTH_LONG).show();
                cancel(true);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            String resultString = "";
            String url_= "http://192.168.1.7/WebServiceAndroid/getNotas.php";
            URL url = null;
            try {
                url = new URL(url_);
                // ejecutar url con parametros
                resultString = downloadUrl(url,alumno,carrera);
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
                JSONArray jsonArray = new JSONArray(respuesta);
                Log.d("notas ",jsonArray.toString());
                Log.d("tamaño",String.valueOf(jsonArray.length()));
                if (jsonArray.length()>0){

                    Bundle datosAlumno = new Bundle();
                    // llenar datos
                    ArrayList<Nota> notas = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        notas.add(new Nota(jsonArray.getJSONObject(i).getString("codigo"),
                                jsonArray.getJSONObject(i).getString("nombreCurso"),
                                jsonArray.getJSONObject(i).getString("zona"),
                                jsonArray.getJSONObject(i).getString("final"),
                                jsonArray.getJSONObject(i).getString("total"),
                                jsonArray.getJSONObject(i).getString("perfil")));
                    }
                    // titulos de columnas para la tabla
                    String[] titles = getResources().getStringArray(R.array.titles);
                    ArrayList<String> titulos = new ArrayList<>();
                    for (int i = 0; i < titles.length; i++) {
                        titulos.add(titles[i]);
                    }
                    llenarfila(titulos, Color.RED);
                    notasCursos(notas);

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
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
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
               /*  if (stream != null) {
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




    public void notasCursos(ArrayList<Nota> notas){
        for (int i = 0; i < notas.size(); i++) {
            ArrayList<String> datosCurso = notas.get(i).datos();
            llenarfila(datosCurso,getResources().getColor(R.color.colorPrimary));
        }
    }

    public void llenarfila(ArrayList<String> datosFila, int color){
        TableRow fila = new TableRow(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);
        TextView txt;

        for (int i = 0; i < datosFila.size(); i++) {
            txt = new TextView(getContext());
            txt.setText(datosFila.get(i));
            txt.setLayoutParams(params);
            txt.setTextColor(color);
            fila.addView(txt);
        }

        tabladatos.addView(fila);
    }
}
