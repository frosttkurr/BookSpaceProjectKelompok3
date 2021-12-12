package id.kelompok3.bookspace.activity.buku;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuBisnisAdapter;
import id.kelompok3.bookspace.adapter.buku.BukuMajalahAdapter;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.model.BukuHandler;

public class BukuMajalahActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuMajalahAdapter;
    private ArrayList<BukuHandler> bukuHandler = new ArrayList<BukuHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_majalah);

        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_majalah);

        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.tampilkanBukuMajalah();
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
        bukuMajalahAdapter = new BukuMajalahAdapter(bukuHandler, BukuMajalahActivity.this, recyclerView);
        recyclerView.setAdapter(bukuMajalahAdapter);
    }
}