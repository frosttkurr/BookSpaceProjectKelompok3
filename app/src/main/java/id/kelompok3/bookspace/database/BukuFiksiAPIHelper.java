package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.BukuHandler;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BukuFiksiAPIHelper {
    @GET("buku/fiksi")
    Call<List<BukuHandler>> bukuFiksiRetrieveData();
}
