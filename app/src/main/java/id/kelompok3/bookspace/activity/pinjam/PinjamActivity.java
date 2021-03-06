package id.kelompok3.bookspace.activity.pinjam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.PinjamAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.CalendarHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinjamActivity extends AppCompatActivity {
    private String judul_buku, notelp_peminjam, nama_peminjam, jk_peminjam, alamat_peminjam, strTgl_pinjam, syarat_pinjam, strTgl_kembali, minat_baca = "0", status = "DIPINJAM";
    private EditText judul, no_telp, nama, alamat, tgl_pinjam, tgl_kembali;
    private Button btnPinjam;
    private RadioGroup jenis_kelamin;
    private RadioButton jk;
    private SeekBar minat;
    private CheckBox syarat;
    private int valueSeekbar = 0;
    private List<PinjamHandler> pinjamHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);

        judul = (EditText)findViewById(R.id.input_judul);
        no_telp = (EditText)findViewById(R.id.input_notelp);
        nama = (EditText)findViewById(R.id.input_nama);
        alamat = (EditText)findViewById(R.id.input_alamat);
        tgl_pinjam = (EditText)findViewById(R.id.tanggal_pinjam);
        tgl_kembali = (EditText)findViewById(R.id.tanggal_kembali);
        jenis_kelamin = (RadioGroup)findViewById(R.id.jk);
        minat = (SeekBar)findViewById(R.id.skala_minatbaca);
        syarat = (CheckBox)findViewById(R.id.syarat_pinjam);
        btnPinjam = (Button)findViewById(R.id.btn_pinjam);

        CalendarHandler calendarHandler = new CalendarHandler(PinjamActivity.this);

        minat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueSeekbar = progress;
                minat_baca = Integer.toString(valueSeekbar) + "%";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (!syarat.isChecked()){
            btnPinjam.setAlpha(.5f);
            btnPinjam.setEnabled(false);
        }

        syarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!syarat.isChecked()){
                    btnPinjam.setAlpha(.5f);
                    btnPinjam.setEnabled(false);
                } else {
                    btnPinjam.setAlpha(1);
                    btnPinjam.setEnabled(true);
                }
            }
        });

        tgl_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar getCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PinjamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar setCalendar = Calendar.getInstance();
                        setCalendar.set(Calendar.YEAR, year);
                        setCalendar.set(Calendar.MONTH, month);
                        setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                        tgl_pinjam.setText(calendarHandler.convertTanggalIndo(setCurrentDate));
                        strTgl_pinjam = dateFormat.format(setCalendar.getTime()).toString();
                    }
                },getCalendar.get(Calendar.YEAR), getCalendar.get(Calendar.MONTH), getCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tgl_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar getCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PinjamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar setCalendar = Calendar.getInstance();
                        setCalendar.set(Calendar.YEAR, year);
                        setCalendar.set(Calendar.MONTH, month);
                        setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                        tgl_kembali.setText(calendarHandler.convertTanggalIndo(setCurrentDate));
                        strTgl_kembali = dateFormat.format(setCalendar.getTime()).toString();
                    }
                },getCalendar.get(Calendar.YEAR), getCalendar.get(Calendar.MONTH), getCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_jk = jenis_kelamin.getCheckedRadioButtonId();
                jk = (RadioButton) findViewById(selected_jk);

                judul_buku = judul.getText().toString();
                notelp_peminjam = no_telp.getText().toString();
                nama_peminjam = nama.getText().toString();
                jk_peminjam = jk.getText().toString();
                alamat_peminjam = alamat.getText().toString();

                if(syarat.isChecked()){
                    syarat_pinjam = "Setuju";
                } else {
                    syarat_pinjam = "Tidak Setuju";
                }

                if (judul.length() == 0 && no_telp.length() == 0 && nama.length() == 0 &&  alamat.length() == 0 && tgl_pinjam.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Lengkapi form pendaftaran!", Toast.LENGTH_SHORT).show();
                } else if (judul.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Judul tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (nama.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }  else if (alamat.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (no_telp.length() < 12 || no_telp.length() > 12) {
                    Toast.makeText(PinjamActivity.this, "No Telpon harus 12 digit!", Toast.LENGTH_SHORT).show();
                } else if (tgl_pinjam.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Tanggal Pinjam tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (tgl_kembali.length() == 0) {
                    Toast.makeText(PinjamActivity.this, "Tanggal Kembali tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (minat_baca.toString().equals("0")) {
                    Toast.makeText(PinjamActivity.this, "Minat Baca tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    dialogAlert();
                }
            }
        });
    }

    private void dialogAlert(){
        CalendarHandler calendarHandler = new CalendarHandler(PinjamActivity.this);
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(PinjamActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Pinjaman");
        dialogAlertBuilder
                .setMessage("Judul                      : " +judul_buku+ "\n" +
                        "Nama                     : " +nama_peminjam+ "\n" +
                        "Jenis Kelamin      : " +jk_peminjam+ "\n" +
                        "Alamat                   : " +alamat_peminjam+ "\n" +
                        "No Telpon             : " +notelp_peminjam+ "\n" +
                        "Tgl Pinjam            : " +tgl_pinjam.getText().toString()+ "\n" +
                        "Tgl Kembali          : " +tgl_kembali.getText().toString()+ "\n" +
                        "Minat Membaca : "+minat_baca.toString()+ "\n" +
                        "Syarat Pinjam      : "+syarat_pinjam.toString()+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        PinjamHandler pinjamHandler = new PinjamHandler();
                        pinjamHandler.setJudul(judul_buku);
                        pinjamHandler.setNama(nama_peminjam);
                        pinjamHandler.setJenis_kelamin(jk_peminjam);
                        pinjamHandler.setAlamat(alamat_peminjam);
                        pinjamHandler.setNo_telp(notelp_peminjam);
                        pinjamHandler.setTgl_pinjam(strTgl_pinjam);
                        pinjamHandler.setTgl_kembali(strTgl_kembali);
                        pinjamHandler.setStatus(status.toString());

                        boolean tambahPinjam = dbHelper.tambahPinjam(pinjamHandler);

                        if (tambahPinjam) {
                            Toast.makeText(PinjamActivity.this, "Tambah Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PinjamActivity.this, "Tambah Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                        }
                        dbHelper.close();

                        createData();

                        Intent goListPinjam = new Intent(PinjamActivity.this,ListPinjamActivity.class);
                        startActivity(goListPinjam);
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
        PinjamAPIHelper pinjamCreateData = RetroHelper.connectRetrofit().create(PinjamAPIHelper.class);
        Call<PinjamHandler> addPinjam = pinjamCreateData.pinjamInsertData(nama_peminjam, judul_buku, alamat_peminjam, notelp_peminjam, strTgl_pinjam, strTgl_kembali, status);

        addPinjam.enqueue(new Callback<PinjamHandler>() {
            @Override
            public void onResponse(Call<PinjamHandler> call, Response<PinjamHandler> response) {
                Boolean statusAPI = response.body().isStatusAPI();
                String message = response.body().getMessage();
                if (statusAPI) {
                    Toast.makeText(PinjamActivity.this, ""+ message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PinjamActivity.this, "Gagal menambah data pinjaman", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<PinjamHandler> call, Throwable t) {
                Toast.makeText(PinjamActivity.this, "Gagal menghubungkan ke server : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}