package com.lovewuchin.game.twozerogame.setting;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.*;
import android.view.MenuItem;
import android.widget.Toast;
import com.lovewuchin.game.twozerogame.Common;
import com.lovewuchin.game.twozerogame.R;

/**
 * Created by Zhou on 2014/5/1.
 */
public class SettingPreference extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    public SharedPreferences prefs;
    private ListPreference mMode;
    private CheckBoxPreference mVariety;
    private CheckBoxPreference mCostom;
    private EditTextPreference mEdit;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.setting_preference);

        prefs = getSharedPreferences(Common.KEY_PREFERENCE , MODE_WORLD_READABLE);

        mMode = (ListPreference) findPreference(Common.KEY_MODE);
        mVariety = (CheckBoxPreference) findPreference(Common.KEY_VARIETY);
        mCostom  = (CheckBoxPreference) findPreference(Common.KEY_COSTOMIZE);
        mEdit = (EditTextPreference) findPreference(Common.KEY_COSTOMIZE_EDIT);

        mMode.setOnPreferenceChangeListener(this);
        mVariety.setOnPreferenceChangeListener(this);
        mCostom.setOnPreferenceChangeListener(this);
        mEdit.setOnPreferenceChangeListener(this);

        int mode = prefs.getInt(Common.KEY_MODE , 0);
        mMode.setValueIndex(mode);
        String[] modeSummaries = getResources().getStringArray(R.array.setting_mode_entries);
        mMode.setSummary(modeSummaries[mode]);

        boolean checked = prefs.getBoolean(Common.KEY_VARIETY,false);
        mVariety.setChecked(checked);

        boolean cos = prefs.getBoolean(Common.KEY_COSTOMIZE, false);
        mCostom.setChecked(cos);
        if(cos) {
            mMode.setEnabled(false);
        } else {
            mMode.setEnabled(true);
        }

        String edit = prefs.getString(Common.KEY_COSTOMIZE_EDIT , "");
        mEdit.setSummary(getString(R.string.pref_costomize_editview_summary)+"\n当前玩法："+edit);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if(key.equals(Common.KEY_MODE)) {
            int mode = Integer.valueOf((String) newValue);
            String[] varietySummaries = getResources().getStringArray(R.array.setting_mode_entries);
            mMode.setSummary(varietySummaries[mode]);
            prefs.edit().putInt(Common.KEY_MODE , mode).commit();

            Toast.makeText(this, R.string.msg, 1000).show();

            return true;
        } else if(key.equals(Common.KEY_VARIETY)) {
            boolean checked = (Boolean) newValue;
            prefs.edit().putBoolean(Common.KEY_VARIETY , checked).commit();

            return true;
        }else if(key.equals(Common.KEY_COSTOMIZE)) {
            boolean cos = (Boolean) newValue;
            prefs.edit().putBoolean(Common.KEY_COSTOMIZE, cos).commit();
            if(cos) {
                mMode.setEnabled(false);
            } else {
                mMode.setEnabled(true);
            }

            return true;
        }else if(key.equals(Common.KEY_COSTOMIZE_EDIT)) {
            String edit = (String) newValue;
            prefs.edit().putString(Common.KEY_COSTOMIZE_EDIT, edit).commit();
            mEdit.setSummary(getString(R.string.pref_costomize_editview_summary)+"\n"+edit);

            Toast.makeText(this, R.string.msg ,1000).show();

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
