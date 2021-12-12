package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.adapter.buku.BukuIlmiahAdapter;
import id.kelompok3.bookspace.adapter.buku.BukuSejarahAdapter;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.model.BukuHandler;

public class BukuSejarahActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuSejarahAdapter;
    private ArrayList<BukuHandler> bukuHandler = new ArrayList<BukuHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_sejarah);

        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_sejarah);

        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.tampilkanBukuSejarah();
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
        bukuSejarahAdapter = new BukuSejarahAdapter(bukuHandler, BukuSejarahActivity.this, recyclerView);
        recyclerView.setAdapter(bukuSejarahAdapter);
    }
}