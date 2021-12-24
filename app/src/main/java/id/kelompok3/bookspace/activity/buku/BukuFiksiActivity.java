package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.adapter.buku.BukuFiksiAdapter;
import id.kelompok3.bookspace.database.BukuFiksiAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuFiksiActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuFiksiAdapter;
    private List<BukuHandler> bukuHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_fiksi);

        recyclerView = (RecyclerView)findViewById(R.id.list_fiksi);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        retrieveData();
    }

    public void retrieveData(){
        BukuFiksiAPIHelper bukuFiksiRequestData = RetroHelper.connectRetrofit().create(BukuFiksiAPIHelper.class);
        Call<List<BukuHandler>> getBukuFiksi = bukuFiksiRequestData.bukuFiksiRetrieveData();

        getBukuFiksi.enqueue(new Callback<List<BukuHandler>>() {
            @Override
            public void onResponse(Call<List<BukuHandler>> call, Response<List<BukuHandler>> response) {
                bukuHandler = response.body();
                bukuFiksiAdapter = new BukuFiksiAdapter(bukuHandler, BukuFiksiActivity.this, recyclerView);
                recyclerView.setAdapter(bukuFiksiAdapter);
                bukuFiksiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BukuHandler>> call, Throwable t) {
                Toast.makeText(BukuFiksiActivity.this, "Anda offline : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                final DBHelper dh = new DBHelper(getApplicationContext());
                Cursor cursor = dh.tampilkanBukuFiksi();
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    while (!cursor.isAfterLast()) {
                        BukuHandler bukuHandlerList = new BukuHandler();
                        bukuHandlerList.setJudul((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
                        bukuHandlerList.setKategori((cursor.getString(cursor.getColumnIndexOrThrow("kategori"))));
                        bukuHandler.add(bukuHandlerList);
                        cursor.moveToNext();
                    }
                    dh.close();
                }
                bukuFiksiAdapter = new BukuFiksiAdapter(bukuHandler, BukuFiksiActivity.this, recyclerView);
                recyclerView.setAdapter(bukuFiksiAdapter);
            }
        });
    }
}