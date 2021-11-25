package id.syakurr.bookspace;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailPinjamActivity extends AppCompatActivity {
    private String strTgl_kembali, status;
    private EditText judul, nik, nama, alamat, tgl_pinjam, tgl_kembali;
    private Button btnKembali, btnEdit, btnHapus;
    private Integer id = 0;
    private ArrayList<PinjamHandler> pinjamHandler = new ArrayList<PinjamHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);

        judul = (EditText)findViewById(R.id.detail_judul);
        nik = (EditText)findViewById(R.id.detail_nik);
        nama = (EditText)findViewById(R.id.detail_nama);
        alamat = (EditText)findViewById(R.id.detail_alamat);
        tgl_pinjam = (EditText)findViewById(R.id.detail_tanggal_pinjam);
        tgl_kembali = (EditText)findViewById(R.id.detail_tanggal_kembali);
        btnKembali = (Button)findViewById(R.id.btn_kembali);
        btnEdit = (Button)findViewById(R.id.btn_edit);
        btnHapus = (Button)findViewById(R.id.btn_hapus);

        judul.setEnabled(false);
        nik.setEnabled(false);
        nama.setEnabled(false);
        alamat.setEnabled(false);
        tgl_pinjam.setEnabled(false);

        Intent getData = getIntent();
        id = getData.getIntExtra("id", 0);

        if (id > 0) {
            final DBHelper dh = new DBHelper(getApplicationContext());
            Cursor cursor = dh.detailPinjam(id);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    judul.setText((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
                    nik.setText((cursor.getString(cursor.getColumnIndexOrThrow("nik"))));
                    nama.setText((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
                    alamat.setText((cursor.getString(cursor.getColumnIndexOrThrow("alamat"))));
                    tgl_pinjam.setText((cursor.getString(cursor.getColumnIndexOrThrow("tgl_pinjam"))));
                    tgl_kembali.setText((cursor.getString(cursor.getColumnIndexOrThrow("tgl_kembali"))));
                    cursor.moveToNext();
                }
                dh.close();
            }
        }

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
                            .setMessage("Judul : " +judul.getText().toString()+ "\n" +
                                    "NIK : " +nik.getText().toString()+ "\n" +
                                    "Nama : " +nama.getText().toString()+ "\n" +
                                    "Alamat : " +alamat.getText().toString()+ "\n" +
                                    "Tgl Pinjam : " +tgl_pinjam.getText().toString()+ "\n" +
                                    "Tgl Kembali : " +strTgl_kembali+ "\n")
                            .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                                    PinjamHandler pinjamHandler = new PinjamHandler();
                                    pinjamHandler.setTgl_kembali(strTgl_kembali.toString());

                                    boolean suntingPinjam = dbHelper.suntingPinjam(pinjamHandler, id);

                                    if (suntingPinjam) {
                                        Toast.makeText(DetailPinjamActivity.this, "Sunting Peminjaman Berhasil", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DetailPinjamActivity.this, "Sunting Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                    dbHelper.close();
                                    Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
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
                                } else {
                                    Toast.makeText(DetailPinjamActivity.this, "Hapus Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
                                Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
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
                                } else {
                                    Toast.makeText(DetailPinjamActivity.this, "Kembali Peminjaman Gagal", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
                                Intent goListPinjam = new Intent(DetailPinjamActivity.this,ListPinjamActivity.class);
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
        });
    }
}