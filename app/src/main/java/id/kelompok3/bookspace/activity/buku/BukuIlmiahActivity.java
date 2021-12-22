package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.database.BukuIlmiahAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.adapter.buku.BukuIlmiahAdapter;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuIlmiahActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuIlmiahAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_ilmiah);

//        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_ilmiah);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanBukuIlmiah();
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
//        bukuIlmiahAdapter = new BukuIlmiahAdapter(bukuHandler, BukuIlmiahActivity.this, recyclerView);
//        recyclerView.setAdapter(bukuIlmiahAdapter);
        retrieveData();
    }

    public void retrieveData(){
        BukuIlmiahAPIHelper bukuIlmiahRequestData = RetroHelper.connectRetrofit().create(BukuIlmiahAPIHelper.class);
        Call<List<BukuHandler>> getBukuIlmiah = bukuIlmiahRequestData.bukuIlmiahRetrieveData();

        getBukuIlmiah.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuIlmiahAdapter = new BukuIlmiahAdapter(bukuHandler, BukuIlmiahActivity.this, recyclerView);
                recyclerView.setAdapter(bukuIlmiahAdapter);
                bukuIlmiahAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuIlmiahActivity.this, "Gagal mengambil data buku : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}