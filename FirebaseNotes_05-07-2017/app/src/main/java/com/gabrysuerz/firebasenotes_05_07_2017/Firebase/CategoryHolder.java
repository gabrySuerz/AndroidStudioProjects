package com.gabrysuerz.firebasenotes_05_07_2017.Firebase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gabrysuerz.firebasenotes_05_07_2017.Models.Category;
import com.gabrysuerz.firebasenotes_05_07_2017.R;

/**
 * Created by gabrysuerz on 04/07/17.
 */

public class CategoryHolder extends RecyclerView.ViewHolder {

    private final TextView mCategoryField;

    private Category category;

    private ClickListener mClickListener;


    //Interface to send callbacks...
    public interface ClickListener {
        void onItemClick(View view, int position, Category category);

        void onItemLongClick(View view, int position, Category category);
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
                mClickListener.onItemClick(v, getAdapterPosition(), category);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition(), category);
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