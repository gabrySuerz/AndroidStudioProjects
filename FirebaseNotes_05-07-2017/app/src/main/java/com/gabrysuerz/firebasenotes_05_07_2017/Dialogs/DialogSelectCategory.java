package com.gabrysuerz.firebasenotes_05_07_2017.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gabrysuerz.firebasenotes_05_07_2017.Firebase.CategoryHolder;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Category;
import com.gabrysuerz.firebasenotes_05_07_2017.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabrysuerz on 03/07/17.
 */

public class DialogSelectCategory extends DialogFragment {

    private IOnCategory mListener;
    private DatabaseReference databaseRef;
    private FirebaseAuth auth;
    private FirebaseRecyclerAdapter mAdapter;

    public interface IOnCategory {
        void onSelect(Category cat);
    }

    public static DialogSelectCategory getInstance() {
        return new DialogSelectCategory();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialog_recycler_view);
        DatabaseReference catRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new FirebaseRecyclerAdapter<Category, CategoryHolder>(
                Category.class,
                R.layout.cell_category,
                CategoryHolder.class,
                catRef.orderByChild("categoryName")) {

            @Override
            public void populateViewHolder(CategoryHolder holder, Category category, int position) {
                holder.setCategory(category);
            }

            @Override
            public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CategoryHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new CategoryHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position, Category category) {
                        mListener.onSelect(category);
                    }

                    @Override
                    public void onItemLongClick(View view, int position, Category category) {

                    }
                });
                return viewHolder;
            }
        };
        recyclerView.setAdapter(mAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scorri e scegli la categoria").setView(view).setNegativeButton("ANNULLA", null);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IOnCategory) {
            mListener = (IOnCategory) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
}
