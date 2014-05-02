package com.lovewuchin.game.twozerogame.setting;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import com.lovewuchin.game.twozerogame.Common;
import com.lovewuchin.game.twozerogame.R;

/**
 * Created by Zhou on 2014/5/1.
 */
public class SettingPreference extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    public SharedPreferences prefs;
    private ListPreference mMode;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.setting_preference);

        prefs = getSharedPreferences(Common.KEY_PREFERENCE , MODE_WORLD_READABLE);

        mMode = (ListPreference) findPreference(Common.KEY_MODE);

        mMode.setOnPreferenceChangeListener(this);

        int mode = prefs.getInt(Common.KEY_MODE , 0);
        mMode.setValueIndex(mode);
        String[] modeSummaries = getResources().getStringArray(R.array.setting_mode_entries);
        mMode.setSummary(modeSummaries[mode]);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if(key.equals(Common.KEY_MODE)) {
            int mode = Integer.valueOf((String) newValue);
            String[] varietySummaries = getResources().getStringArray(R.array.setting_mode_entries);
            mMode.setSummary(varietySummaries[mode]);
            prefs.edit().putInt(Common.KEY_MODE , mode).commit();

            return true;
        }
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
