package id.kelompok3.bookspace.activity.pinjam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.DetailPinjamAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.PinjamHandler;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPinjamActivity extends AppCompatActivity {
    private String strTgl_kembali, status;
    private EditText judul, no_telp, nama, alamat, tgl_pinjam, tgl_kembali;
    private Button btnKembali, btnEdit, btnHapus;
    private Integer id;
    private List<PinjamHandler> pinjamHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);

        judul = (EditText)findViewById(R.id.detail_judul);
        no_telp = (EditText)findViewById(R.id.detail_notelp);
        nama = (EditText)findViewById(R.id.detail_nama);
        alamat = (EditText)findViewById(R.id.detail_alamat);
        tgl_pinjam = (EditText)findViewById(R.id.detail_tanggal_pinjam);
        tgl_kembali = (EditText)findViewById(R.id.detail_tanggal_kembali);
        btnKembali = (Button)findViewById(R.id.btn_kembali);
        btnEdit = (Button)findViewById(R.id.btn_edit);
        btnHapus = (Button)findViewById(R.id.btn_hapus);

        judul.setEnabled(false);
        no_telp.setEnabled(false);
        nama.setEnabled(false);
        alamat.setEnabled(false);
        tgl_pinjam.setEnabled(false);

        Intent getData = getIntent();
        id = getData.getIntExtra("id", 0);

        if (id > 0) {
//            final DBHelper dh = new DBHelper(getApplicationContext());
//            Cursor cursor = dh.detailPinjam(id);
//            cursor.moveToFirst();
//            if (cursor.getCount() > 0) {
//                while (!cursor.isAfterLast()) {
//                    judul.setText((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
//                    nama.setText((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
//                    alamat.setText((cursor.getString(cursor.getColumnIndexOrThrow("alamat"))));
//                    no_telp.setText((cursor.getString(cursor.getColumnIndexOrThrow("no_telpon"))));
//                    tgl_pinjam.setText((cursor.getString(cursor.getColumnIndexOrThrow("tgl_pinjam"))));
//                    tgl_kembali.setText((cursor.getString(cursor.getColumnIndexOrThrow("tgl_kembali"))));
//                    cursor.moveToNext();
//                }
//                dh.close();
//            }
            retrieveData();
        }

        tgl_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar getCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DetailPinjamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar setCalendar = Calendar.getInstance();
                        setCalendar.set(Calendar.YEAR, year);
                        setCalendar.set(Calendar.MONTH, month);
                        setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                        tgl_kembali.setText(setCurrentDate);
                    }
                },getCalendar.get(Calendar.YEAR), getCalendar.get(Calendar.MONTH), getCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strTgl_kembali = tgl_kembali.getText().toString();

                if (tgl_kembali.length() == 0) {
                    Toast.makeText(DetailPinjamActivity.this, "Tanggal Kembali tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DetailPinjamActivity.this);
                    dialogAlertBuilder.setTitle("Konfirmasi Suntingan");
                    dialogAlertBuilder
                            .setMessage("Judul      : " +judul.getText().toString()+ "\n" +
                                    "Nama           : " +nama.getText().toString()+ "\n" +
                                    "Alamat :       " +alamat.getText().toString()+ "\n" +
                                    "No Telpon      : " +no_telp.getText().toString()+ "\n" +
                                    "Tgl Pinjam     : " +tgl_pinjam.getText().toString()+ "\n" +
                                    "Tgl Kembali :   " +strTgl_kembali+ "\n")
                            .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                                    PinjamHandler pinjamHandler = new PinjamHandler();
                                    pinjamHandler.setTgl_kembali(strTgl_kembali.toString());

                                    boolean suntingPinjam = dbHelper.suntingPinjam(pinjamHandler, id);

                                    if (suntingPinjam) {
                                        Toast.makeText(DetailPinjamActivity.this, "Sunting Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                                        Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
                                        startActivity(goListPinjam);
                                    } else {
                                        Toast.makeText(DetailPinjamActivity.this, "Sunting Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                    dbHelper.close();
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
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DetailPinjamActivity.this);
                dialogAlertBuilder.setTitle("Konfirmasi");
                dialogAlertBuilder
                        .setMessage("Yakin menghapus data?")
                        .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHelper dbHelper = new DBHelper(getApplicationContext());

                                boolean hapusPinjam = dbHelper.hapusPinjam(id);

                                if (hapusPinjam) {
                                    Toast.makeText(DetailPinjamActivity.this, "Hapus Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
                                    startActivity(goListPinjam);
                                } else {
                                    Toast.makeText(DetailPinjamActivity.this, "Hapus Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
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
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DetailPinjamActivity.this);
                dialogAlertBuilder.setTitle("Konfirmasi");
                dialogAlertBuilder
                        .setMessage("Konfirmasi Pengembalian?")
                        .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                status = "DIKEMBALIKAN";
                                DBHelper dbHelper = new DBHelper(getApplicationContext());
                                PinjamHandler pinjamHandler = new PinjamHandler();
                                pinjamHandler.setStatus(status.toString());

                                boolean kembaliPinjam = dbHelper.kembaliPinjam(pinjamHandler,id);

                                if (kembaliPinjam) {
                                    Toast.makeText(DetailPinjamActivity.this, "Kembali Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
                                    startActivity(goListPinjam);
                                } else {
                                    Toast.makeText(DetailPinjamActivity.this, "Kembali Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
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
        });
    }

    public void retrieveData(){
        DetailPinjamAPIHelper detailPinjamRequestData = RetroHelper.connectRetrofit().create(DetailPinjamAPIHelper.class);
        Call<List<PinjamHandler>> getDetailPinjam = detailPinjamRequestData.detailPinjamRetrieveData();

        getDetailPinjam.enqueue(new Callback<List<PinjamHandler>>() {
            @Override
            public void onResponse(Call<List<PinjamHandler>> call, Response<List<PinjamHandler>> response) {
                pinjamHandler = response.body();
            }

            @Override
            public void onFailure(Call<List<PinjamHandler>> call, Throwable t) {
                Toast.makeText(DetailPinjamActivity.this, "Gagal mengambil data detail pinjaman : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}