package work.technie.popularmovies.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import java.util.Arrays;
import java.util.Locale;

import work.technie.popularmovies.R;

/**
 * Created by anupam on 14/12/16.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    protected static void setListPreferenceRegion(ListPreference lp) {

        Locale[] locales = Locale.getAvailableLocales();

        Country entry[] = new Country[locales.length];

        int i = 0;
        for (Locale locale : locales) {
            String name = locale.getDisplayCountry().trim();
            String code = locale.getCountry();
            if (name.length() > 0) {
                boolean flag = true;
                for (int j = 0; j < i; j++) {
                    if (name.equals(entry[j].name)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    entry[i++] = new Country(name, code);
                }
            }
        }

        Arrays.sort(entry, 0, i);
        CharSequence countryName[] = new CharSequence[i];
        CharSequence countryCode[] = new CharSequence[i];

        for (int j = 0; j < i; j++) {
            countryName[j] = entry[j].name;
            countryCode[j] = entry[j].code;
        }

        lp.setEntries(countryName);
        lp.setEntryValues(countryCode);
    }

    protected static void setListPreferenceLanguage(ListPreference lp) {

        Locale[] locales = Locale.getAvailableLocales();

        Language entry[] = new Language[locales.length];

        int i = 0;
        for (Locale locale : locales) {
            String name = locale.getDisplayLanguage().trim();
            String code = locale.getLanguage();
            if (name.length() > 0) {
                boolean flag = true;
                for (int j = 0; j < i; j++) {
                    if (name.equals(entry[j].name)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    entry[i++] = new Language(name, code);
                }
            }
        }

        Arrays.sort(entry, 0, i);
        CharSequence countryName[] = new CharSequence[i];
        CharSequence countryCode[] = new CharSequence[i];

        for (int j = 0; j < i; j++) {
            countryName[j] = entry[j].name;
            countryCode[j] = entry[j].code;
        }

        lp.setEntries(countryName);
        lp.setEntryValues(countryCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String REGION = "region";
        String LANGUAGE = "language";

        final ListPreference listPreferenceRegion = (ListPreference) findPreference(REGION);
        final ListPreference listPreferenceLanguage = (ListPreference) findPreference(LANGUAGE);

        setListPreferenceRegion(listPreferenceRegion);
        setListPreferenceLanguage(listPreferenceLanguage);

        listPreferenceRegion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                setListPreferenceRegion(listPreferenceRegion);
                return false;
            }
        });
        int prefIndex = listPreferenceRegion.findIndexOfValue(sharedPreferences.getString(REGION, ""));
        if (prefIndex >= 0) {
            listPreferenceRegion.setSummary(listPreferenceRegion.getEntries()[prefIndex]);
        }

        listPreferenceLanguage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                setListPreferenceLanguage(listPreferenceLanguage);
                return false;
            }
        });
        prefIndex = listPreferenceLanguage.findIndexOfValue(sharedPreferences.getString(LANGUAGE, ""));
        if (prefIndex >= 0) {
            listPreferenceLanguage.setSummary(listPreferenceLanguage.getEntries()[prefIndex]);
        }


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    static class Country implements Comparable<Country> {
        CharSequence name;
        CharSequence code;

        Country(CharSequence name, CharSequence code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public int compareTo(@NonNull Country another) {
            return this.name.toString().compareTo(another.name.toString());
        }
    }

    static class Language implements Comparable<Language> {
        CharSequence name;
        CharSequence code;

        Language(CharSequence name, CharSequence code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public int compareTo(@NonNull Language another) {
            return this.name.toString().compareTo(another.name.toString());
        }
    }
}