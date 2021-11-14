package id.syakurr.bookspace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DaftarActivity extends AppCompatActivity {
    private Button daftar;
    private EditText nik, nama, alamat, username, email, password;
    private RadioGroup jenis_kelamin;
    private RadioButton jk;
    private SeekBar minat_baca;
    private CheckBox check_term;
    private String strSeekbar;
    private int valueSeekbar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        daftar = (Button)findViewById(R.id.btn_daftar);
        nik = (EditText)findViewById(R.id.nik);
        nama = (EditText)findViewById(R.id.nama);
        alamat = (EditText)findViewById(R.id.alamat);
        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        jenis_kelamin = (RadioGroup)findViewById(R.id.jenis_kelamin);
        minat_baca = (SeekBar)findViewById(R.id.seekbar);
        check_term = (CheckBox)findViewById(R.id.check_term);

        minat_baca.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueSeekbar = progress;
                strSeekbar = Integer.toString(valueSeekbar) + "%";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (!check_term.isChecked()){
            daftar.setAlpha(.5f);
            daftar.setClickable(false);
        }

        check_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check_term.isChecked()){
                    daftar.setAlpha(.5f);
                    daftar.setClickable(false);
                } else {
                    daftar.setAlpha(1);
                    daftar.setClickable(true);
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String checkEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                int selected_jk = jenis_kelamin.getCheckedRadioButtonId();
                jk = (RadioButton) findViewById(selected_jk);

                if (nik.length() == 0 && nama.length() == 0 && alamat.length() == 0 && jenis_kelamin.getCheckedRadioButtonId() == -1
                        && username.length() == 0 && email.length() == 0 && password.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Lengkapi form pendaftaran!", Toast.LENGTH_SHORT).show();
                } else if (nik.length() < 16 || nik.length() > 16) {
                    Toast.makeText(DaftarActivity.this, "NIK harus 16 digit!", Toast.LENGTH_SHORT).show();
                } else if (nama.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (alamat.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (jenis_kelamin.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(DaftarActivity.this, "Jenis kelamin tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (username.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (email.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!getEmail.matches(checkEmail)) {
                    Toast.makeText(DaftarActivity.this, "Email tidak valid!", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(DaftarActivity.this, "Password minimal 8 karakter!", Toast.LENGTH_SHORT).show();
                } else if (nik.length() == 16 && nama.length() > 0 && alamat.length() > 0 && jenis_kelamin.getCheckedRadioButtonId() != -1
                        && username.length() > 0 && email.length() > 0 && password.length() > 0) {
                    dialogAlert();
                }
            }
        });
    }

    private void dialogAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DaftarActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Data");
        dialogAlertBuilder
                .setMessage("NIK : " +nik.getText().toString()+ "\n" +
                            "Nama : " +nama.getText().toString()+ "\n" +
                            "Alamat : " +alamat.getText().toString()+ "\n" +
                            "Jenis Kelamin : " +jk.getText().toString()+ "\n" +
                            "Email : " +email.getText().toString()+ "\n" +
                            "Username : " +username.getText().toString()+ "\n" +
                            "Minat Membaca : "+strSeekbar.toString()+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DaftarActivity.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                        Intent gotoLoby = new Intent(DaftarActivity.this, LobbyActivity.class);
                        gotoLoby.putExtra("nik", nik.getText().toString());
                        gotoLoby.putExtra("nama", nama.getText().toString() );
                        gotoLoby.putExtra("alamat", alamat.getText().toString());
                        gotoLoby.putExtra("jeniskelamin", jk.getText().toString());
                        gotoLoby.putExtra("email", email.getText().toString());
                        gotoLoby.putExtra("username", username.getText().toString());
                        gotoLoby.putExtra("minatbaca", strSeekbar.toString());
                        startActivity(gotoLoby);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = dialogAlertBuilder.create();

        dialog.show();
    }
}