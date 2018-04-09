package com.example.tucker.calcexam.subclases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.tucker.calcexam.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by John on 4/8/2018.
 */

public class ToDoViewHolder extends RecyclerView.ViewHolder{
    public HtmlTextView html;
    public ImageView Deleate;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        html = itemView.findViewById(R.id.html_text);
        Deleate = itemView.findViewById(R.id.delete);
    }
}
