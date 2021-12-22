package id.kelompok3.bookspace.database;

import java.util.List;

import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PinjamAPIHelper {
    @GET("pinjam")
    Call<List<PinjamHandler>> pinjamRetrieveData();

    @FormUrlEncoded
    @POST("pinjam/tambah")
    Call<PinjamHandler> pinjamInsertData(
                    @Field("nama") String nama,
                    @Field("judul") String judul,
                    @Field("alamat") String alamat,
                    @Field("no_telpon") String no_telpon,
                    @Field("tgl_pinjam") String tgl_pinjam,
                    @Field("tgl_kembali") String tgl_kembali,
                    @Field("status") String status
            );
}
