package com.example.gabry.listees11112016_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gabry on 11/11/2016.
 */

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<NewItem> myList;

    public CustomAdapter(Context aContext, ArrayList<NewItem> aList){
        mContext = aContext;
        myList = aList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public NewItem getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getmID();
    }

    public class ViewHolder{
        ProgressBar mProg;
        RatingBar mRate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vCell;
        if(convertView == null){
            LayoutInflater mLayout = LayoutInflater.from(mContext);
            vCell = mLayout.inflate(R.layout.item_layout, null);

            ProgressBar vProg = (ProgressBar) vCell.findViewById(R.id.progressBar);
            RatingBar vRate = (RatingBar) vCell.findViewById(R.id.ratingBar);

            ViewHolder vHolder = new ViewHolder();
            vHolder.mProg = vProg;
            vHolder.mRate = vRate;

            vCell.setTag(vHolder);
        }else{
            vCell = convertView;
        }

        NewItem vItem = getItem(position);
        ViewHolder vVH = (ViewHolder) vCell.getTag();

        vVH.mProg.setProgress(vItem.getmProgress());
        vVH.mRate.setRating(vItem.getmStars());

        return vCell;
    }
}
