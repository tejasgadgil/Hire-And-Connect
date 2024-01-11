package com.example.hireconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<TraderModel> traderModels;

    public RecyclerViewAdapter(Context context, ArrayList<TraderModel> traderModels){
        this.context = context;
        this.traderModels = traderModels;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout (give the look to rows)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //assign values to views we created in layout file based on position of recycler view
        holder.traderName.setText(traderModels.get(position).getTraderName());
        holder.phNo.setText(traderModels.get(position).getPhNo());
        holder.range.setText(traderModels.get(position).getRange());

    }

    @Override
    public int getItemCount() {
        //get item count in total

        return traderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //almost like an oncreate method

        TextView traderName, phNo, range;
        Button bookNowBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            traderName = itemView.findViewById(R.id.TraderNameText);
            phNo = itemView.findViewById(R.id.contactNoText);
            range = itemView.findViewById(R.id.RangeText);
            bookNowBtn = itemView.findViewById(R.id.bookNowBtn);
        }
    }
}
