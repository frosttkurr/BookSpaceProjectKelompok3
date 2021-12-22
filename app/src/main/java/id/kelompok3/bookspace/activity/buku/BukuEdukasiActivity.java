package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.adapter.buku.BukuEdukasiAdapter;
import id.kelompok3.bookspace.database.BukuEdukasiAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuEdukasiActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuEdukasiAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_edukasi);

//        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_edukasi);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanBukuEdukasi();
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            while (!cursor.isAfterLast()) {
//                BukuHandler bukuHandlerList = new BukuHandler();
//                bukuHandlerList.setJudul((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
//                bukuHandlerList.setKategori((cursor.getString(cursor.getColumnIndexOrThrow("kategori"))));
//                bukuHandler.add(bukuHandlerList);
//                cursor.moveToNext();
//            }
//            dh.close();
//        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        bukuEdukasiAdapter = new BukuFiksiAdapter(bukuHandler, BukuEdukasiActivity.this, recyclerView);
//        recyclerView.setAdapter(bukuEdukasiAdapter);
        retrieveData();
    }

    public void retrieveData(){
        BukuEdukasiAPIHelper bukuEdukasiRequestData = RetroHelper.connectRetrofit().create(BukuEdukasiAPIHelper.class);
        Call<List<BukuHandler>> getBukuEdukasi = bukuEdukasiRequestData.bukuEdukasiRetrieveData();

        getBukuEdukasi.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuEdukasiAdapter = new BukuEdukasiAdapter(bukuHandler, BukuEdukasiActivity.this, recyclerView);
                recyclerView.setAdapter(bukuEdukasiAdapter);
                bukuEdukasiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuEdukasiActivity.this, "Gagal mengambil data buku : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}