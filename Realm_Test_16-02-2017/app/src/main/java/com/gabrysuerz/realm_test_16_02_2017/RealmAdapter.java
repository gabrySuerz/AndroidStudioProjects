package com.gabrysuerz.realm_test_16_02_2017;

/**
 * Created by gabrysuerz on 16/02/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class RealmAdapter extends RealmBaseAdapter<Person> implements ListAdapter {

    private static class ViewHolder {
        TextView name, surname;
    }

    public RealmAdapter(Context context, OrderedRealmCollection<Person> realmResults) {
        super(context, realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.txt_surname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Person item = adapterData.get(position);
        viewHolder.name.setText(item.getName());
        viewHolder.surname.setText(item.getSurname());
        return convertView;
    }
}