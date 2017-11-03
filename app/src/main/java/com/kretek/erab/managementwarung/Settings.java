package com.kretek.erab.managementwarung;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by erab on 01/11/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class Settings extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        getActivity().setTitle("Pengaturan");
    }

}
