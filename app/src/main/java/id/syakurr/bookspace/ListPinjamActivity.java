package id.syakurr.bookspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
//            noData.setVisibility(View.GONE); //hilangin gak ada data tapi tampilin data
            while (!cursor.isAfterLast()) {
                PinjamHandler pinjamHandlerList = new PinjamHandler();
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
        } else if (cursor.getCount() == 0) {
//            noData.setVisibility(View.VISIBLE); //nampilin gak ada data
        }

//        detail_pinjam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gotoDetail = new Intent(ListPinjamActivity.this, DetailPinjamActivity.class);
//                ListPinjamActivity.this.startActivity(gotoDetail);
//                ListPinjamActivity.this.finish();
//            }
//        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listPinjamAdapter = new ListPinjamAdapter(pinjamHandler, ListPinjamActivity.this, recyclerView);
        recyclerView.setAdapter(listPinjamAdapter);
    }
}