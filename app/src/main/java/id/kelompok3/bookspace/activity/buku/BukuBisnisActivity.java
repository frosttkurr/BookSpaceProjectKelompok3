package id.kelompok3.bookspace.activity.buku;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuBisnisAdapter;
import id.kelompok3.bookspace.database.BukuBisnisAPIHelper;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuBisnisActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuBisnisAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_bisnis);

//        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_bisnis);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanBukuBisnis();
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
//        bukuBisnisAdapter = new BukuBisnisAdapter(bukuHandler, BukuBisnisActivity.this, recyclerView);
//        recyclerView.setAdapter(bukuBisnisAdapter);
        retrieveData();
    }

    public void retrieveData(){
        BukuBisnisAPIHelper bukuBisnisRequestData = RetroHelper.connectRetrofit().create(BukuBisnisAPIHelper.class);
        Call<List<BukuHandler>> getBukuSejarah = bukuBisnisRequestData.bukuBisnisRetrieveData();

        getBukuSejarah.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuBisnisAdapter = new BukuBisnisAdapter(bukuHandler, BukuBisnisActivity.this, recyclerView);
                recyclerView.setAdapter(bukuBisnisAdapter);
                bukuBisnisAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuBisnisActivity.this, "Gagal mengambil data buku : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}