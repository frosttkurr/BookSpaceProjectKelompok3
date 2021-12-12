package id.kelompok3.bookspace.activity.buku;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuBisnisAdapter;
import id.kelompok3.bookspace.adapter.buku.BukuNovelAdapter;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.model.BukuHandler;

public class BukuBisnisActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuBisnisAdapter;
    private ArrayList<BukuHandler> bukuHandler = new ArrayList<BukuHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_bisnis);

        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_bisnis);

        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.tampilkanBukuBisnis();
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

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        bukuBisnisAdapter = new BukuBisnisAdapter(bukuHandler, BukuBisnisActivity.this, recyclerView);
        recyclerView.setAdapter(bukuBisnisAdapter);
    }
}