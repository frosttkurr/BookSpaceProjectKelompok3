package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.pinjam.DetailPinjamActivity;
import id.kelompok3.bookspace.activity.pinjam.ListPinjamActivity;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.DetailBukuAPIHelper;
import id.kelompok3.bookspace.database.DetailPinjamAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBukuActivity extends AppCompatActivity {
    private String judul_buku, kategori_buku;
    private EditText judul, kategori;
    private Button btnSuntingBuku, btnHapusBuku;
    private Integer id;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        judul = (EditText)findViewById(R.id.value_detail_judul);
        kategori = (EditText)findViewById(R.id.value_detail_kategori);
        btnSuntingBuku = (Button)findViewById(R.id.btn_edit_buku);
        btnHapusBuku = (Button)findViewById(R.id.btn_hapus_buku);

        Intent getData = getIntent();
        id = getData.getIntExtra("id", 0);

        if (id > 0) {
//            final DBHelper dh = new DBHelper(getApplicationContext());
//            Cursor cursor = dh.detailBuku(id);
//            cursor.moveToFirst();
//            if (cursor.getCount() > 0) {
//                while (!cursor.isAfterLast()) {
//                    judul.setText((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
//                    kategori.setText((cursor.getString(cursor.getColumnIndexOrThrow("kategori"))));
//                    cursor.moveToNext();
//                }
//                dh.close();
//            }
            retrieveData();
        }

        btnSuntingBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul_buku = judul.getText().toString();
                kategori_buku = kategori.getText().toString();
                suntingAlert();
            }
        });

        btnHapusBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusAlert();
            }
        });
    }

    private void suntingAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DetailBukuActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Suntingan");
        dialogAlertBuilder
                .setMessage("Judul      : " +judul_buku+ "\n" +
                        "Kategori : " +kategori_buku+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DBHelper dbHelper = new DBHelper(getApplicationContext());
//                        BukuHandler bukuHandler = new BukuHandler();
//                        bukuHandler.setJudul(judul_buku.toUpperCase());
//                        bukuHandler.setKategori(kategori_buku.toUpperCase());
//
//                        boolean suntingBuku = dbHelper.suntingBuku(bukuHandler, id);
//
//                        if (suntingBuku) {
//                            Toast.makeText(DetailBukuActivity.this, "Sunting Buku Berhasil", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(DetailBukuActivity.this, "Sunting Buku Gagal", Toast.LENGTH_SHORT).show();
//                        }
//                        dbHelper.close();
//
//                        judul.getText().clear();
//                        kategori.getText().clear();

                        updateData();

//                        Intent goListBuku = new Intent(DetailBukuActivity .this, LobbyActivity.class);
//                        startActivity(goListBuku);
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

    private void hapusAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(DetailBukuActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi");
        dialogAlertBuilder
                .setMessage("Yakin menghapus buku?")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DBHelper dbHelper = new DBHelper(getApplicationContext());
//
//                        boolean hapusBuku = dbHelper.hapusBuku(id);
//
//                        if (hapusBuku) {
//                            Toast.makeText(DetailBukuActivity.this, "Hapus buku Berhasil", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(DetailBukuActivity.this, "Hapus buku Gagal", Toast.LENGTH_SHORT).show();
//                        }
//                        dbHelper.close();
////                        Intent goListBuku = new Intent(DetailBukuActivity .this, LobbyActivity.class);
////                        startActivity(goListBuku);
                        deleteData();
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

    public void retrieveData(){
        DetailBukuAPIHelper detailBukuRequestData = RetroHelper.connectRetrofit().create(DetailBukuAPIHelper.class);
        Call<List<BukuHandler>> getDetailBuku = detailBukuRequestData.detailBukuRetrieveData(id);

        getDetailBuku.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();

                judul.setText(bukuHandler.get(0).getJudul());
                kategori.setText(bukuHandler.get(0).getKategori());
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(DetailBukuActivity.this, "Gagal mengambil data buku : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(){
        DetailBukuAPIHelper detailBukuRequestData = RetroHelper.connectRetrofit().create(DetailBukuAPIHelper.class);
        Call<BukuHandler> getDetailBuku = detailBukuRequestData.detailBukuUpdateData(id, judul_buku, kategori_buku);

        getDetailBuku.enqueue(new Callback<BukuHandler>() {
            @Override
            public void onResponse(Call<BukuHandler> call, Response<BukuHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();

                if (statusAPI) {
                    Toast.makeText(DetailBukuActivity.this, ""+ message, Toast.LENGTH_LONG).show();
//                        Intent goListBuku = new Intent(DetailBukuActivity .this, LobbyActivity.class);
//                        startActivity(goListBuku);
                } else {
                    Toast.makeText(DetailBukuActivity.this, "Gagal mengupdate data buku", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BukuHandler> call, Throwable t) {
                Toast.makeText(DetailBukuActivity.this, "Gagal mengupdate data buku : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteData(){
        DetailBukuAPIHelper detailBukuRequestData = RetroHelper.connectRetrofit().create(DetailBukuAPIHelper.class);
        Call<BukuHandler> getDetailBuku = detailBukuRequestData.detailBukuDeleteData(id);

        getDetailBuku.enqueue(new Callback<BukuHandler>() {
            @Override
            public void onResponse(Call<BukuHandler> call, Response<BukuHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();

                if (statusAPI) {
                    Toast.makeText(DetailBukuActivity.this, ""+ message, Toast.LENGTH_LONG).show();
//                        Intent goListBuku = new Intent(DetailBukuActivity .this, LobbyActivity.class);
//                        startActivity(goListBuku);
                } else {
                    Toast.makeText(DetailBukuActivity.this, "Gagal menghapus data buku", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BukuHandler> call, Throwable t) {
                Toast.makeText(DetailBukuActivity.this, "Gagal menghapus data buku : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}