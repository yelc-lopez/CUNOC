package gt.edu.cunoc.cunoc.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.util.List;
import java.util.Scanner;

import gt.edu.cunoc.cunoc.Fragments.AsignacionesFragment;
import gt.edu.cunoc.cunoc.Models.CursoDisponible;
import gt.edu.cunoc.cunoc.R;

/**
 * Created by yelc on 13/12/17.
 */

public class CursosDisponiblesAdapter extends ArrayAdapter<CursoDisponible> {

    TextView id, codigo, nombre;
    RadioButton button;

    public CursosDisponiblesAdapter(@NonNull Context context, List<CursoDisponible> cursos) {
        super(context,R.layout.template_cursos_disponibles,cursos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.template_cursos_disponibles,null);
            id = view.findViewById(R.id.idCurso);
            codigo = view.findViewById(R.id.codigoCurso);
            nombre = view.findViewById(R.id.nombreCurso);
            button = view.findViewById(R.id.radioButton);


            id.setText(getItem(position).getId_curso());
            codigo.setText(getItem(position).getCodigo_curso());
            nombre.setText(getItem(position).getNombre_curso());

        final String idActual = getItem(position).getId_curso();

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(R.string.dialogoTitulo)
                                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(getContext(), "idR -- " + idActual, Toast.LENGTH_SHORT).show();
                                        try{
                                            new asignarCurso(idActual).execute();
                                            button.setSelected(false);
                                        }catch (Exception e){
                                            Log.i("error Asignacion ",e.getMessage());
                                        }

                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                }
            });


        return view;
    }

    private class asignarCurso extends AsyncTask<Void, Void, String> {


        String resultString = "";
        String url_ = "http://testcunoc.000webhostapp.com/setCursosAsignacion.php";
        String idfinal;

        public asignarCurso(String id){
            this.idfinal=id;

            //Toast.makeText(getContext(), "idRec -- " + idfinal, Toast.LENGTH_SHORT).show();
        }

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
                resultString = downloadUrl(url,AsignacionesFragment.usuario,idfinal);
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
                Log.i("respuestaAsignacion ", respuesta);
                if (respuesta.equals("Registrado")){
                    Toast.makeText(getContext(),"Registro Guardado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Registro No Guardado Intentelo Nuevamente",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        /**
         *  Obtener datos de un webService
         */
        private String downloadUrl(URL url,String usuario, String curso) throws IOException {
            String parametros = "user="+ usuario +"&course=" + curso;
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

}
