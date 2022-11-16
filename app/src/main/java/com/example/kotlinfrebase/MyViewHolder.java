package com.example.kotlinfrebase;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    EditText name,condition;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.name);
        condition=itemView.findViewById(R.id.condition);

    }
}
