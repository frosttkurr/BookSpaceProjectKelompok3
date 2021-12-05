package id.syakurr.bookspace.activity.buku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import id.syakurr.bookspace.model.BukuHandler;
import id.syakurr.bookspace.adapter.buku.BukuIlmiahAdapter;
import id.syakurr.bookspace.database.DBHelper;
import id.syakurr.bookspace.R;

public class BukuIlmiahActivity extends AppCompatActivity {
    private DBHelper database;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter bukuIlmiahAdapter;
    private ArrayList<BukuHandler> bukuHandler = new ArrayList<BukuHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_ilmiah);

        database = new DBHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.list_ilmiah);

        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.tampilkanBukuIlmiah();
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
        bukuIlmiahAdapter = new BukuIlmiahAdapter(bukuHandler, BukuIlmiahActivity.this, recyclerView);
        recyclerView.setAdapter(bukuIlmiahAdapter);
    }
}