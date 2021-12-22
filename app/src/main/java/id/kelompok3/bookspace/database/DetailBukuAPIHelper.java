package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DetailBukuAPIHelper {
    @GET("buku/{id}/detail")
    Call<List<BukuHandler>> detailBukuRetrieveData(
            @Path("id") Integer id
    );

    @FormUrlEncoded
    @PUT("buku/{id}/update")
    Call<BukuHandler> detailBukuUpdateData(
            @Path("id") Integer id,
            @Field("judul") String judul,
            @Field("kategori") String kategori
    );

    @DELETE("buku/{id}/delete")
    Call<BukuHandler> detailBukuDeleteData(
            @Path("id") Integer id
    );
}
