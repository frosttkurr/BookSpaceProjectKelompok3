package id.kelompok3.bookspace.adapter.pinjam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.kelompok3.bookspace.model.CalendarHandler;
import id.kelompok3.bookspace.model.PinjamHandler;
import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.pinjam.DetailPinjamActivity;

public class ListPinjamAdapter extends RecyclerView.Adapter<ListPinjamAdapter.ViewHolder> {
    private List<PinjamHandler> pinjamHandlerList;
    private Context context;
    private RecyclerView recyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemJudul;
        TextView itemNama;
        TextView itemTglPinjam;
        TextView itemTglKembali;
        TextView itemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemJudul = itemView.findViewById(R.id.buku_pinjam);
            itemNama = itemView.findViewById(R.id.value_nama_pinjam);
            itemTglPinjam = itemView.findViewById(R.id.value_tgl_pinjam);
            itemTglKembali = itemView.findViewById(R.id.value_tgl_kembali);
            itemStatus = itemView.findViewById(R.id.value_status);
        }
    }

    public ListPinjamAdapter(List<PinjamHandler> pinjamHandlerList, Context context, RecyclerView recyclerView) {
        this.pinjamHandlerList = pinjamHandlerList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ListPinjamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_pinjam, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListPinjamAdapter.ViewHolder holder, int position) {
        PinjamHandler pinjamHandler = pinjamHandlerList.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date datePinjam = null;
        Date dateKembali = null;
        try {
            datePinjam = format.parse(String.valueOf(pinjamHandler.getTgl_pinjam()));
            dateKembali = format.parse(String.valueOf(pinjamHandler.getTgl_kembali()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calPinjam = Calendar.getInstance();
        Calendar calKembali = Calendar.getInstance();
        calPinjam.setTime(datePinjam);
        calKembali.setTime(dateKembali);
        String tglPinjam = DateFormat.getDateInstance().format(calPinjam.getTime());
        String tglKembali = DateFormat.getDateInstance().format(calKembali.getTime());
        CalendarHandler calendarHandler = new CalendarHandler(context);
        String newTglPinjam = calendarHandler.convertTanggalIndo(tglPinjam);
        String newTglKembali = calendarHandler.convertTanggalIndo(tglKembali);

        holder.itemJudul.setText(String.valueOf(pinjamHandler.getJudul()));
        holder.itemNama.setText(String.valueOf(pinjamHandler.getNama()));
        holder.itemTglPinjam.setText(String.valueOf(newTglPinjam));
        holder.itemTglKembali.setText(String.valueOf(newTglKembali));
        holder.itemStatus.setText(String.valueOf(pinjamHandler.getStatus()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(), String.valueOf(pinjamHandler.getId()), Toast.LENGTH_SHORT).show();
                Integer itemId = Integer.valueOf(pinjamHandler.getId());
                Intent gotoDetail = new Intent(holder.itemView.getContext(), DetailPinjamActivity.class);
                gotoDetail.putExtra("id", itemId);
                holder.itemView.getContext().startActivity(gotoDetail);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return pinjamHandlerList.size();
    }
}
