package id.kelompok3.bookspace.activity.landing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.pinjam.PinjamActivity;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.PenggunaAPIHelper;
import id.kelompok3.bookspace.database.PinjamAPIHelper;
import id.kelompok3.bookspace.model.PenggunaHandler;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {
    private Button daftar;
    private EditText no_telpon, nama, alamat, username, email, password;
    private RadioGroup jenis_kelamin;
    private RadioButton jk;
    private SeekBar minat_baca;
    private CheckBox check_term;
    private String strSeekbar = "0";
    private int valueSeekbar = 0;
    private ConnectivityManager connectivityManager;
    private RetroHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        apiHelper = new RetroHelper();

        daftar = (Button)findViewById(R.id.btn_daftar);
        no_telpon = (EditText)findViewById(R.id.notelpon);
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
            daftar.setEnabled(false);
        }

        check_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check_term.isChecked()){
                    daftar.setAlpha(.5f);
                    daftar.setEnabled(false);
                } else {
                    daftar.setAlpha(1);
                    daftar.setEnabled(true);
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

                if (no_telpon.length() == 0 && nama.length() == 0 && alamat.length() == 0 && jenis_kelamin.getCheckedRadioButtonId() == -1
                        && username.length() == 0 && email.length() == 0 && password.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Lengkapi form pendaftaran!", Toast.LENGTH_SHORT).show();
                } else if (nama.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (alamat.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (jenis_kelamin.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(DaftarActivity.this, "Jenis kelamin tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (no_telpon.length() < 12 || no_telpon.length() > 12) {
                    Toast.makeText(DaftarActivity.this, "No Telpon harus 12 digit!", Toast.LENGTH_SHORT).show();
                }  else if (username.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (email.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!getEmail.matches(checkEmail)) {
                    Toast.makeText(DaftarActivity.this, "Email tidak valid!", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(DaftarActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(DaftarActivity.this, "Password minimal 8 karakter!", Toast.LENGTH_SHORT).show();
                } else if (strSeekbar.toString().equals("0")) {
                    Toast.makeText(DaftarActivity.this, "Minat Baca tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    dialogAlert();
                }
            }
        });
    }

    private void dialogAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DaftarActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Data");
        dialogAlertBuilder
                .setMessage("Nama           : " +nama.getText().toString()+ "\n" +
                            "Alamat         : " +alamat.getText().toString()+ "\n" +
                            "Jenis Kelamin  : " +jk.getText().toString()+ "\n" +
                            "No Telpon      : " +no_telpon.getText().toString()+ "\n" +
                            "Email          : " +email.getText().toString()+ "\n" +
                            "Username       : " +username.getText().toString()+ "\n" +
                            "Minat Membaca  : "+strSeekbar.toString()+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DBHelper dbHelper = new DBHelper(getApplicationContext());
//                        PenggunaHandler penggunaHandler = new PenggunaHandler();
//                        penggunaHandler.setNama_lengkap(nama.getText().toString());
//                        penggunaHandler.setAlamat(alamat.getText().toString());
//                        penggunaHandler.setJenis_kelamin(jk.getText().toString());
//                        penggunaHandler.setNo_telpon(no_telpon.getText().toString());
//                        penggunaHandler.setEmail(email.getText().toString());
//                        penggunaHandler.setUsername(username.getText().toString());
//                        penggunaHandler.setPassword(password.getText().toString());
//                        penggunaHandler.setMinat_membaca(strSeekbar.toString());
//
//                        boolean tambahPengguna = dbHelper.tambahPengguna(penggunaHandler);
//
//                        if (tambahPengguna) {
//                            Toast.makeText(DaftarActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(DaftarActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
//                        }
//                        dbHelper.close();
                        createData();
                        Intent gotoLogin = new Intent(DaftarActivity.this, LoginActivity.class);
                        startActivity(gotoLogin);
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

    public void createData(){
        PenggunaAPIHelper penggunaCreateData = RetroHelper.connectRetrofit().create(PenggunaAPIHelper.class);
        Call<PenggunaHandler> addPengguna = penggunaCreateData.penggunaInsertData(nama.getText().toString(), alamat.getText().toString(), jk.getText().toString(), no_telpon.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), strSeekbar.toString());

        addPengguna.enqueue(new Callback<PenggunaHandler>() {
            @Override
            public void onResponse(Call<PenggunaHandler> call, Response<PenggunaHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();
                if (statusAPI == true) {
                    Toast.makeText(DaftarActivity.this, ""+ message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(DaftarActivity.this, "" + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PenggunaHandler> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, "Gagal menghubungkan ke server : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}