package com.example.rusbellgutierrez.SRT;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rusbell Gutierrez on 14/03/2017.
 */

public class Fragment_Perfil extends Fragment{

    public static final String TAG="Perfil";

    public Fragment_Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }
}
