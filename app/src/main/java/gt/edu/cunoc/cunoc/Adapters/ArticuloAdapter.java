package gt.edu.cunoc.cunoc.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

import gt.edu.cunoc.cunoc.Models.Articulo;
import gt.edu.cunoc.cunoc.R;

/**
 * Created by yelc on 30/11/17.
 */

public class ArticuloAdapter extends ArrayAdapter<Articulo> {

    TextView titulo,contenido;
    ImageView imagen;

    public ArticuloAdapter(@NonNull Context context, List<Articulo> articulos) {
        super(context, R.layout.template_articulos, articulos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.template_articulos,null);


        titulo = view.findViewById(R.id.tituloArticulo);
        contenido = view.findViewById(R.id.contenidoArticulo);
        imagen = view.findViewById(R.id.imagenArticulo);

        titulo.setText(getItem(position).getTitulo());
        contenido.setText(getItem(position).getContenido());
        Glide.with(getContext()).load(getItem(position).getImagen()).into(imagen);
        return view;
    }
}
