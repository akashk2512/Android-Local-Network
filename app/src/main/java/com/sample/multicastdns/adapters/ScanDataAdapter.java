package com.sample.multicastdns.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.multicastdns.R;
import com.sample.multicastdns.model.ScannedData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by AKASH on 26/10/19.
 */
public class ScanDataAdapter extends RecyclerView.Adapter<ScanDataAdapter.ViewHolder> {
    private Context mContext;
    private List<ScannedData> dataList = new ArrayList<>();

    public ScanDataAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_data, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ScannedData data = dataList.get(i);
        viewHolder.txt_service_ip.setText("IP Address  " + data.getHostAddress());
        viewHolder.txt_service_name.setText("Service Name  " + data.getServiceName());
        viewHolder.txt_service_type.setText("Service Type  " + data.getServiceType());
        viewHolder.txt_service_port.setText("Port  " + data.getPort());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_service_name;
        TextView txt_service_type;
        TextView txt_service_ip;
        TextView txt_service_port;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_service_port = itemView.findViewById(R.id.txt_service_port);
            txt_service_ip = itemView.findViewById(R.id.txt_service_ip);
            txt_service_name = itemView.findViewById(R.id.txt_service_name);
            txt_service_type = itemView.findViewById(R.id.txt_service_type);
        }
    }

    public void updateList(ScannedData data) {
        dataList.add(data);
        removeDuplicate();
        notifyDataSetChanged();
    }

    private void removeDuplicate() {
        TreeSet<ScannedData> treeList = new TreeSet<>(new Comparator<ScannedData>() {
            @Override
            public int compare(ScannedData o1, ScannedData o2) {
                return o1.getHostAddress().toString().compareTo(o2.getHostAddress().toString());
            }
        });
        treeList.addAll(dataList);
        dataList.clear();
        dataList.addAll(treeList);
    }

    public void refreshAdapter() {
        dataList.clear();
        notifyDataSetChanged();
    }
}
