package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PinjamRequestData {
    @GET("pinjam")
    Call<List<PinjamHandler>> pinjamRetrieveData();
}
