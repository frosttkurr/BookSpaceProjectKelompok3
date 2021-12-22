package id.kelompok3.bookspace.activity.buku;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuNovelAdapter;
import id.kelompok3.bookspace.database.BukuNovelAPIHelper;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuNovelActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuNovelAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_novel);

//        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_novel);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanBukuNovel();
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
//        bukuNovelAdapter = new BukuNovelAdapter(bukuHandler, BukuNovelActivity.this, recyclerView);
//        recyclerView.setAdapter(bukuNovelAdapter);
        retrieveData();
    }

    public void retrieveData(){
        BukuNovelAPIHelper bukuNovelRequestData = RetroHelper.connectRetrofit().create(BukuNovelAPIHelper.class);
        Call<List<BukuHandler>> getBukuNovel = bukuNovelRequestData.bukuNovelRetrieveData();

        getBukuNovel.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuNovelAdapter = new BukuNovelAdapter(bukuHandler, BukuNovelActivity.this, recyclerView);
                recyclerView.setAdapter(bukuNovelAdapter);
                bukuNovelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuNovelActivity.this, "Gagal mengambil data pinjaman : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}