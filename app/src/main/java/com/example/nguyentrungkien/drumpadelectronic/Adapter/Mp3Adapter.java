package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.DTO.Mp3DTO;
import com.example.nguyentrungkien.drumpadelectronic.MainActivity;
import com.example.nguyentrungkien.drumpadelectronic.MediaServices;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.util.List;

public class Mp3Adapter extends RecyclerView.Adapter<Mp3Adapter.ViewHolder> {
    Context context;
    List<Mp3DTO> list;


    public Mp3Adapter(Context context, List<Mp3DTO> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_mp3,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtFileName.setText(list.get(position).getFileName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService = new Intent(context, MediaServices.class);
                intentService.putExtra("filepath", list.get(position).getFilePath());
                context.startService(intentService);

                MainActivity.dialog.dismiss();
                ((Activity) context).finish();

                ((Activity) context).overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtFileName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFileName = itemView.findViewById(R.id.txtFileName);
        }
    }
}
