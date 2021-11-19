package id.syakurr.bookspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListPinjamAdapter extends RecyclerView.Adapter<ListPinjamAdapter.ViewHolder> {
    private List<PinjamHandler> pinjamHandlerList;
    private Context context;
    private RecyclerView recyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemJudul;
        TextView itemNama;
        TextView itemTglPinjam;
        TextView itemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemJudul = itemView.findViewById(R.id.buku_pinjam);
            itemNama = itemView.findViewById(R.id.value_nama_pinjam);
            itemTglPinjam = itemView.findViewById(R.id.value_tgl_pinjam);
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
        holder.itemJudul.setText(String.valueOf(pinjamHandler.getJudul()));
        holder.itemNama.setText(String.valueOf(pinjamHandler.getNama()));
        holder.itemTglPinjam.setText(String.valueOf(pinjamHandler.getTgl_pinjam()));
        holder.itemStatus.setText(String.valueOf(pinjamHandler.getStatus()));
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
