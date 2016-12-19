package com.scala.cordova.plugin.daydreamer;

import android.app.Application;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by Cesar Oyarzun on 12/19/16.
 */

public class ExpDayDreamSettingsActivity extends PreferenceActivity {

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            FragmentTransaction tx = getFragmentManager().beginTransaction();
            tx.replace(android.R.id.content, new SamplePreferenceFragment());
            tx.commit();
        }

    public static class SamplePreferenceFragment extends PreferenceFragment {
            EditTextPreference editTextPreference;
            SharedPreferences sharedPref;

            @Override
            public void onCreate(Bundle bundle) {
                super.onCreate(bundle);

                Application app = getActivity().getApplication();
                String package_name = app.getPackageName();
                Resources resources = app.getResources();
                int prefs = resources.getIdentifier("prefs", "xml", package_name);

                addPreferencesFromResource(prefs);
                sharedPref = getActivity().getSharedPreferences(ExpDayDreamService.EXP_DAYDREAM_PREF,Context.MODE_PRIVATE);
                editTextPreference = (EditTextPreference) findPreference("player_textfield");
                String expPlayerUrl = sharedPref.getString(ExpDayDreamService.EXP_PLAYER_URL, ExpDayDreamService.DEFAULT_URL_PLAYER);
                editTextPreference.setSummary(expPlayerUrl);
                editTextPreference.setText(expPlayerUrl);
                editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        String newUrl = o.toString();
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(ExpDayDreamService.EXP_PLAYER_URL, newUrl);
                        editor.commit();
                        editTextPreference.setSummary(newUrl);
                        return false;
                    }
                });
            }

        }
    }

