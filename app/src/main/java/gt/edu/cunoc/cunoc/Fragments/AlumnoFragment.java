package gt.edu.cunoc.cunoc.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gt.edu.cunoc.cunoc.DetallesNoticiaActivity;
import gt.edu.cunoc.cunoc.R;
import gt.edu.cunoc.cunoc.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlumnoFragment extends Fragment {


    Button button1, button2;

    public AlumnoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alumno, container, false);
        button1 = view.findViewById(R.id.boton1);
        button2 = view.findViewById(R.id.boton2);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UserActivity.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DetallesNoticiaActivity.class));
            }
        });

        return view;
    }

}
