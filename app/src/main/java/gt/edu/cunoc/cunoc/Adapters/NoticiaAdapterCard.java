package gt.edu.cunoc.cunoc.Adapters;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import gt.edu.cunoc.cunoc.DetallesNoticiaActivity;
import gt.edu.cunoc.cunoc.Models.Articulo;
import gt.edu.cunoc.cunoc.Models.Dato;
import gt.edu.cunoc.cunoc.R;

/**
 * Created by yelc on 8/09/17.
 */

public class NoticiaAdapterCard extends RecyclerView.Adapter<NoticiaAdapterCard.tarjetaViewHoldier> {

    private ArrayList<Articulo> tarjetas;
    private int resource;
    private Activity origenActivity;

    public NoticiaAdapterCard(ArrayList<Articulo> tarjetas, int resource, Activity origenActivity) {
        this.tarjetas = tarjetas;
        this.resource = resource;
        this.origenActivity = origenActivity;
    }

    @Override
    public tarjetaViewHoldier onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new tarjetaViewHoldier(view);
    }

    @Override
    public void onBindViewHolder(tarjetaViewHoldier holder, int position) {
        Articulo tarjeta = tarjetas.get(position);
        holder.titulo.setText(tarjeta.getTitulo());
        String rutaImagen = tarjeta.getImagen();
        Glide.with(origenActivity).load(rutaImagen).into(holder.imagen);
        holder.detalle.setText(tarjeta.getContenido());
        holder.urlImagen.setText(tarjeta.getRutaImagen());
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    public class tarjetaViewHoldier extends RecyclerView.ViewHolder{

        ImageView imagen;
        TextView titulo,detalle,urlImagen;
        Bundle datos = new Bundle();

        public tarjetaViewHoldier(final View itemView) {
            super(itemView);

            imagen = (ImageView) itemView.findViewById(R.id.imagenTarjeta);
            titulo = (TextView) itemView.findViewById(R.id.tituloTarjeta);
            detalle = (TextView) itemView.findViewById(R.id.detalle_Tarjeta);
            urlImagen = itemView.findViewById(R.id.direccionURlImagen);

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();

                    imagen.buildDrawingCache();
                    Bitmap bmap = imagen.getDrawingCache();

                    Dato dato = new Dato(bmap,titulo.getText().toString(),detalle.getText().toString(),"");
                    nextActivity(DetallesNoticiaActivity.class, imagen, dato);
                }
            });

            titulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dato dato = new Dato(null,titulo.getText().toString(),detalle.getText().toString(),urlImagen.getText().toString());
                    nextActivity(DetallesNoticiaActivity.class, imagen, dato);
                }
            });

        }
    }


    public void nextActivity(Class activity, View view, Dato dato){
        try{
            Intent intent = new Intent(origenActivity,activity);

            if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Explode explode = new Explode();
                explode.setDuration(1000);
                explode.setInterpolator(new DecelerateInterpolator());
                intent.putExtra("bitMap",dato);
                origenActivity.getWindow().setExitTransition(explode);
                origenActivity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(origenActivity,view,origenActivity.getResources().getString(R.string.transitionName)).toBundle());
            }else{
                intent.putExtra("bitMap",dato);
                origenActivity.startActivity(intent);
            }
        }catch (Exception e){
            Log.i("error intent",e.getMessage());
        }

    }

    /*
    public void animacionCircular(View view){
        int centerx = 0;
        int centery = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(),view.getHeight());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(view,centerx,centery,startRadius,endRadius);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            view.setVisibility(View.VISIBLE);
            animator.start();
        }
    }

    @Override
    public void onViewAttachedToWindow(tarjetaViewHoldier holder) {
        super.onViewAttachedToWindow(holder);
        animacionCircular(holder.itemView);
    }*/
}
