package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.PenggunaHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PenggunaAPIHelper {
    @GET("pengguna/{id}/detail")
    Call<List<PenggunaHandler>> penggunaRetrieveData(
            @Path("id") Integer id
    );
    
    @FormUrlEncoded
    @POST("pengguna/checkUserPass")
    Call<PenggunaHandler> checkUsernamePassword(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("pengguna/tambah")
    Call<PenggunaHandler> penggunaInsertData(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telpon") String no_telpon,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("minat_membaca") String minat_membaca
    );

    @PUT("pengguna/{id}/delete")
    Call<PenggunaHandler> penggunaDeleteData(
            @Path("id") Integer id
    );
}
