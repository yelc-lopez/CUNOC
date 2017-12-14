package gt.edu.cunoc.cunoc.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import gt.edu.cunoc.cunoc.Adapters.ArticuloAdapter;
import gt.edu.cunoc.cunoc.Adapters.NoticiaAdapterCard;
import gt.edu.cunoc.cunoc.Models.Articulo;
import gt.edu.cunoc.cunoc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasV2Fragment extends Fragment {

    private RecyclerView listaRecycler;
    private LottieAnimationView lottieAnimationView;

    public NoticiasV2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias_v2, container, false);

        // UIReferences
        listaRecycler = view.findViewById(R.id.listaRecycler);
        lottieAnimationView = view.findViewById(R.id.animacionDownloader);

                try {
                    new CunocPage().execute();
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Error Noticias - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


        return view;

    }


    public class CunocPage extends AsyncTask<Void, Void, Void> {

        private static final String PAGINAWEB = "http://cunoc.edu.gt";
        private ArrayList<Articulo> Articulos = new ArrayList<>();
        private String tituloArticulo, infoArticulo, rutaImagen;    //variables temporales

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(PAGINAWEB).get();

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
                    rutaImagen = PAGINAWEB+png.attr("src");


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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listaRecycler.setLayoutManager(linearLayoutManager);
                NoticiaAdapterCard noticias = new NoticiaAdapterCard(Articulos,R.layout.card_noticias,getActivity());
                listaRecycler.setAdapter(noticias);
            }else{
                lottieAnimationView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"No Se Puede Descargar El contenido",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
