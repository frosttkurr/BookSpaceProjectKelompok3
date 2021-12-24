package id.kelompok3.bookspace.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandler {
    public void login(Integer user_id, String token, Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("key_id", user_id);
        editor.putString("keyToken", token);
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    public void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public boolean isLogin(Context context){
        return context.getSharedPreferences("pref_name", 0).getBoolean("isLogin", false);
    }
}
