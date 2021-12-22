package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuSejarahAdapter;
import id.kelompok3.bookspace.database.BukuSejarahAPIHelper;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuSejarahActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuSejarahAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_sejarah);

//        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_sejarah);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanBukuSejarah();
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
//        bukuSejarahAdapter = new BukuSejarahAdapter(bukuHandler, BukuSejarahActivity.this, recyclerView);
//        recyclerView.setAdapter(bukuSejarahAdapter);
        retrieveData();
    }

    public void retrieveData(){
        BukuSejarahAPIHelper bukuSejarahRequestData = RetroHelper.connectRetrofit().create(BukuSejarahAPIHelper.class);
        Call<List<BukuHandler>> getBukuSejarah = bukuSejarahRequestData.bukuSejarahRetrieveData();

        getBukuSejarah.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuSejarahAdapter = new BukuSejarahAdapter(bukuHandler, BukuSejarahActivity.this, recyclerView);
                recyclerView.setAdapter(bukuSejarahAdapter);
                bukuSejarahAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuSejarahActivity.this, "Gagal mengambil data buku : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}