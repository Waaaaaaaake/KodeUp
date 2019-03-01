package com.example.stanislavlukanov.kodeup.Auth.SQL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stanislavlukanov.kodeup.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SQL_Tab1_Adapter extends RecyclerView.Adapter<SQL_Tab1_Adapter.MyViewHolder>  {
    private Context context;
    private List<SQL_Tab1> notesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView note;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }

    public SQL_Tab1_Adapter(Context context, List<SQL_Tab1> notesList){
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sql_tab1_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SQL_Tab1 note = notesList.get(position);

        holder.note.setText(note.getNote());

        holder.dot.setText(Html.fromHtml("&#8226;"));

        holder.timestamp.setText(formatDate(note.getTimestamp()));
    }

    @Override
    public int getItemCount() { return notesList.size(); }

    private String formatDate(String dateStr) {

        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");

            return fmtOut.format(date);
        } catch (ParseException e) { }

        return "";
    }
}
