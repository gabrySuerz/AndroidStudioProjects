package com.esame.note_15_06_2017.FirebaseStuff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esame.note_15_06_2017.Models.Category;
import com.esame.note_15_06_2017.R;

import java.text.SimpleDateFormat;

/**
 * Created by gabrysuerz on 04/07/17.
 */

public class CategoryHolder extends RecyclerView.ViewHolder {

    private final TextView mCategoryField;

    private Category category;

    private ClickListener mClickListener;


    //Interface to send callbacks...
    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public CategoryHolder(View itemView) {
        super(itemView);
        mCategoryField = (TextView) itemView.findViewById(R.id.txt_category_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }

    public void setCategory(Category category) {
        this.category = category;
        setGUI();
    }

    public Category getCategory() {
        return category;
    }

    private void setGUI() {
        if (category == null) {
            mCategoryField.setText("Non ci sono categorie");
        } else {
            mCategoryField.setText(category.getCategoryName());
        }
    }
}