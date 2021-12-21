package id.kelompok3.bookspace.activity.pinjam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.database.PinjamRequestData;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.adapter.pinjam.ListPinjamAdapter;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.PinjamHandler;
import id.kelompok3.bookspace.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPinjamActivity extends AppCompatActivity {
    private DBHelper database;
//    private ImageView detail_pinjam;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter listPinjamAdapter;
//    private ArrayList<PinjamHandler> pinjamHandler = new ArrayList<PinjamHandler>();
    private List<PinjamHandler> pinjamHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pinjam);

        database = new DBHelper(this);
//        detail_pinjam = (ImageView) findViewById(R.id.detail_pinjam);
        recyclerView = (RecyclerView)findViewById(R.id.list_pinjam);

//        final DBHelper dh = new DBHelper(getApplicationContext());
//        Cursor cursor = dh.tampilkanPinjam();
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            while (!cursor.isAfterLast()) {
//                PinjamHandler pinjamHandlerList = new PinjamHandler();
//                pinjamHandlerList.setId((cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
//                pinjamHandlerList.setJudul((cursor.getString(cursor.getColumnIndexOrThrow("judul"))));
//                pinjamHandlerList.setNama((cursor.getString(cursor.getColumnIndexOrThrow("nama"))));
//                pinjamHandlerList.setAlamat((cursor.getString(cursor.getColumnIndexOrThrow("alamat"))));
//                pinjamHandlerList.setTgl_pinjam((cursor.getString(cursor.getColumnIndexOrThrow("tgl_pinjam"))));
//                pinjamHandlerList.setTgl_kembali((cursor.getString(cursor.getColumnIndexOrThrow("tgl_kembali"))));
//                pinjamHandlerList.setStatus((cursor.getString(cursor.getColumnIndexOrThrow("status"))));
//                pinjamHandler.add(pinjamHandlerList);
//                cursor.moveToNext();
//            }
//            dh.close();
//        }
//
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        listPinjamAdapter = new ListPinjamAdapter(pinjamHandler, ListPinjamActivity.this, recyclerView);
//        recyclerView.setAdapter(listPinjamAdapter);
        retrieveData();
    }

    public void retrieveData(){
        PinjamRequestData pinjamData = RetroHelper.connectRetrofit().create(PinjamRequestData.class);
        Call<List<PinjamHandler>> getListPinjam = pinjamData.pinjamRetrieveData();

        getListPinjam.enqueue(new Callback<List<PinjamHandler>>() {
            @Override
            public void onResponse(Call<List<PinjamHandler>> call, Response<List<PinjamHandler>> response) {
                pinjamHandler = response.body();
                listPinjamAdapter = new ListPinjamAdapter(pinjamHandler, ListPinjamActivity.this, recyclerView);
                recyclerView.setAdapter(listPinjamAdapter);
                listPinjamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PinjamHandler>> call, Throwable t) {
                Toast.makeText(ListPinjamActivity.this, "Gagal mengambil data pinjaman : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}