package gt.edu.cunoc.cunoc.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import gt.edu.cunoc.cunoc.Adapters.ArticuloAdapter;
import gt.edu.cunoc.cunoc.Models.Articulo;
import gt.edu.cunoc.cunoc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {


    ListView listaArticulos;
    LottieAnimationView lottieAnimationView;

    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     /*   View view =  inflater.inflate(R.layout.fragment_noticias, container, false);
        listaArticulos = view.findViewById(R.id.listaArticulos);
        lottieAnimationView = view.findViewById(R.id.animacionDescarga);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.setClickable(false);
                new CunocPage().execute();
            }
        });

        try {
            new CunocPage().execute();

        }catch (Exception e){
            Log.i("Error en pagina ", e.getMessage());
        }*/

        return null;
    }


    public class CunocPage extends AsyncTask<Void, Void, Void> {

        String paginaWeb="http://cunoc.edu.gt";
        ArrayList<Articulo> Articulos = new ArrayList<>();
        String tituloArticulo, infoArticulo, rutaImagen;    //variables temporales

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(paginaWeb).get();

                // Obtener Articulos
                Elements articulos = doc.select("div#articulos");
                Elements titulos = articulos.select("h2");
                Elements articulo;
                for (int i = 0; i <titulos.size()-1 ; i++) {
                  //  Log.i("Titulo = ", titulos.get(i).text());
                    tituloArticulo = titulos.get(i).text();
                    if (i==0){
                        articulo = articulos.select("div.leading-0");

                    }else{
                        articulo = articulos.select("div.item.column-"+i);
                    }

                    Elements contenido = articulo.select("p span");
                    infoArticulo="";
                    for (int j = 0; j < contenido.size()-2; j++) {
                        //Log.i("Contenido ", contenido.get(j).text());
                        infoArticulo += contenido.get(j).text() + "\n";

                       // Log.i("info ",infoArticulo);
                    }

                    Elements png = articulo.select("img[src$=.jpg]");
                   // Log.i("Imagen = ",paginaWeb+png.attr("src"));
                    rutaImagen = paginaWeb+png.attr("src");


                    // agregar a la lista de articulos
                    Articulos.add(new Articulo(
                            tituloArticulo,
                            infoArticulo,
                            rutaImagen,
                            rutaImagen
                    ));
                }

                /** obtener varias fotos de un articulo
                 Elements pngs = doc.select("img[src$=.jpg]");
                 Log.i("tamaño pngs ", String.valueOf(pngs.size()));
                 for (int i = 0; i < pngs.size(); i++) {
                 Log.i("atributo " , pngs.get(i).attr("src").toString());
                 }*/

            }catch (Exception e){e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!Articulos.isEmpty()){
                lottieAnimationView.setVisibility(View.INVISIBLE);
                ArticuloAdapter adapter = new ArticuloAdapter(getContext(),Articulos);
                listaArticulos.setAdapter(adapter);
            }else{
                lottieAnimationView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"No Se Puede Descargar El contenido",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
