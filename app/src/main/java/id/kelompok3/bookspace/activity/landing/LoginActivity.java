package id.kelompok3.bookspace.activity.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.MainActivity;
import id.kelompok3.bookspace.activity.home.LobbyActivity;
import id.kelompok3.bookspace.activity.home.ProfileActivity;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.PenggunaAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.PenggunaHandler;
import id.kelompok3.bookspace.model.SessionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText user, pass;
    private TextView daftar;
    private List<PenggunaHandler> penggunaHandler = new ArrayList<>();
    private String username, password;
    private boolean detectUsername, detectUsernamePassword;
    private Integer id_pengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionHandler session = new SessionHandler();

        if (session.isLogin(LoginActivity.this)){
            Intent goToLobby = new Intent(LoginActivity.this, LobbyActivity.class);
            LoginActivity.this.startActivity(goToLobby);
        }

        login = (Button)findViewById(R.id.btn_login);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        daftar = (TextView)findViewById(R.id.link_daftar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DBHelper db = new DBHelper(getApplicationContext());
                username = user.getText().toString();
                password = pass.getText().toString();

                if (user.length() == 0 || pass.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Lengkapi Username atau Password!", Toast.LENGTH_LONG).show();
                } else {
                    checkUsernamePassword();
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegist = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(gotoRegist);
            }
        });
    }

    public void checkUsernamePassword(){
        PenggunaAPIHelper penggunaRequestData = RetroHelper.connectRetrofit().create(PenggunaAPIHelper.class);
        Call<PenggunaHandler> getPengguna = penggunaRequestData.checkUsernamePassword(username, password);

        getPengguna.enqueue(new Callback<PenggunaHandler>() {
            @Override
            public void onResponse(Call<PenggunaHandler> call, Response<PenggunaHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();
                List<PenggunaHandler> dataPenggunaList = response.body().getPenggunaHandler();
                SessionHandler session = new SessionHandler();

                if(dataPenggunaList.size() > 0) {
                    session.login(dataPenggunaList.get(0).getId(), dataPenggunaList.get(0).getToken(), LoginActivity.this);
                    Intent gotoLoby = new Intent(LoginActivity.this, LobbyActivity.class);
                    startActivity(gotoLoby);
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password Salah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PenggunaHandler> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal menghubungkan ke server : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}