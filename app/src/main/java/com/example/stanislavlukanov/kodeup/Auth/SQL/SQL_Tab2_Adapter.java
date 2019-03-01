package com.example.stanislavlukanov.kodeup.Auth.SQL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stanislavlukanov.kodeup.R;

import java.util.List;

public class SQL_Tab2_Adapter extends RecyclerView.Adapter <SQL_Tab2_Adapter.MyViewHolder> {
    private Context context;
    private List<SQL_Tab2> contactsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView contact;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);
            contact = view.findViewById(R.id.contact);
            description = view.findViewById(R.id.description);
        }
    }

    public SQL_Tab2_Adapter(Context context, List<SQL_Tab2> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public SQL_Tab2_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sql_tab2_list_row, parent, false);

        return new SQL_Tab2_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SQL_Tab2_Adapter.MyViewHolder holder, int position) {
        SQL_Tab2 contact = contactsList.get(position);

        holder.contact.setText(contact.getContact());

        holder.description.setText(contact.getDescription());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
