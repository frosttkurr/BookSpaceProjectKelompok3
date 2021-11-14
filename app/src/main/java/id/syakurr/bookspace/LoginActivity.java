package id.syakurr.bookspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText username, password;
    private TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.btn_login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        daftar = (TextView)findViewById(R.id.link_daftar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Lengkapi Username atau Password!", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().equals("syakurrhmn") && password.getText().toString().equals("qwerty123")) {
                    Intent gotoLoby = new Intent(LoginActivity.this, LobbyActivity.class);
                    gotoLoby.putExtra("username", username.getText().toString());
                    gotoLoby.putExtra("nik", "5772053313200102");
                    gotoLoby.putExtra("nama", "Muhammad Syakurrahman");
                    gotoLoby.putExtra("alamat", "Jimbaran");
                    gotoLoby.putExtra("jeniskelamin", "Laki-Laki");
                    gotoLoby.putExtra("email", "inialamatvalid@gmail.com");
                    startActivity(gotoLoby);
                } else {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
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
}