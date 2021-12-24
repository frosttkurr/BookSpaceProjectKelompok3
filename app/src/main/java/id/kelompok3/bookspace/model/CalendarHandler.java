package id.kelompok3.bookspace.model;

import android.content.Context;

public class CalendarHandler {
    Context context;

    public CalendarHandler(Context context) {
        this.context = context;
    }

    public String convertTanggalIndo(String date){
        String tanggal = date.substring(4,6);
        String bulan = date.substring(0,3);
        String tahun = date.substring(8,12);

        return tanggal+" "+bulan+" "+tahun;
    }
}
