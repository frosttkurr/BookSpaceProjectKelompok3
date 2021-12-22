package id.kelompok3.bookspace.database;

import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TambahBukuAPIHelper {
    @FormUrlEncoded
    @POST("buku/tambah")
    Call<BukuHandler> bukuInsertData(
            @Field("judul") String judul,
            @Field("kategori") String kategori
    );
}
