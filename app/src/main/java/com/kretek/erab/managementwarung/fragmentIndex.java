package com.kretek.erab.managementwarung;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class fragmentIndex extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_index,container,false);

        TextView bijak = vg.findViewById(R.id.kataKataBijak);

        String[] bija = getResources().getStringArray(R.array.kataBijak);
        int rand = new Random().nextInt(bija.length);
        String randomKata = bija[rand];
        bijak.setText(randomKata);
        return vg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Halaman Depan");
    }


}
