package id.kelompok3.bookspace.activity.pinjam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.adapter.pinjam.ListPinjamAdapter;
import id.kelompok3.bookspace.model.PinjamHandler;
import id.kelompok3.bookspace.R;

public class ListPinjamActivity extends AppCompatActivity {
    private DBHelper database;
//    private ImageView detail_pinjam;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter listPinjamAdapter;
    private ArrayList<PinjamHandler> pinjamHandler = new ArrayList<PinjamHandler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pinjam);

        database = new DBHelper(this);
//        detail_pinjam = (ImageView) findViewById(R.id.detail_pinjam);
        recyclerView = (RecyclerView)findViewById(R.id.list_pinjam);

        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.tampilkanPinjam();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                PinjamHandler pinjamHandlerList = new PinjamHandler();
                pinjamHandlerList.setId((cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
                pinjamHandlerList.setJudul((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
                pinjamHandlerList.setNama((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
                pinjamHandlerList.setAlamat((cursor.getString(cursor.getColumnIndexOrThrow("alamat"))));
                pinjamHandlerList.setTgl_pinjam((cursor.getString(cursor.getColumnIndexOrThrow("tgl_pinjam"))));
                pinjamHandlerList.setTgl_kembali((cursor.getString(cursor.getColumnIndexOrThrow("tgl_kembali"))));
                pinjamHandlerList.setStatus((cursor.getString(cursor.getColumnIndexOrThrow("status"))));
                pinjamHandler.add(pinjamHandlerList);
                cursor.moveToNext();
            }
            dh.close();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listPinjamAdapter = new ListPinjamAdapter(pinjamHandler, ListPinjamActivity.this, recyclerView);
        recyclerView.setAdapter(listPinjamAdapter);
    }
}