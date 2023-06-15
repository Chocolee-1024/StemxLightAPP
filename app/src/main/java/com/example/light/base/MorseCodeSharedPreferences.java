package com.example.light.base;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;
import com.example.light.R;

public class MorseCodeSharedPreferences {
    private SharedPreferences sPref;

    public MorseCodeSharedPreferences(Context context){
        sPref = context.getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
    }
    public int getSpeed() {
        return sPref.getInt("SPEED",0);
    }
    public void setSpeed(int speed) {
        sPref.edit().putInt("SPEED", speed).apply();
    }
}
