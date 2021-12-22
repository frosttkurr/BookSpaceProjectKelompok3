package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DetailPinjamAPIHelper {
    @GET("pinjam/{id}")
    Call<List<PinjamHandler>> detailPinjamRetrieveData(
            @Path("id") Integer id
    );

    @FormUrlEncoded
    @PUT("pinjam/update/{id}")
    Call<PinjamHandler> detailPinjamUpdateData(
            @Path("id") Integer id,
            @Field("tgl_kembali") String strTgl_kembali
    );

    @FormUrlEncoded
    @PUT("pinjam/return/{id}")
    Call<PinjamHandler> detailPinjamReturnData(
            @Path("id") Integer id,
            @Field("status") String status
    );

    @DELETE("pinjam/delete/{id}")
    Call<PinjamHandler> detailPinjamDeleteData(
            @Path("id") Integer id
    );
}
