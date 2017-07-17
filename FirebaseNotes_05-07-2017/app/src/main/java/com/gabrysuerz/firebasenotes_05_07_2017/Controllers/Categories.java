package com.gabrysuerz.firebasenotes_05_07_2017.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gabrysuerz.firebasenotes_05_07_2017.Dialogs.DialogDelete;
import com.gabrysuerz.firebasenotes_05_07_2017.Dialogs.DialogSelectCategory;
import com.gabrysuerz.firebasenotes_05_07_2017.Firebase.CategoryHolder;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Category;
import com.gabrysuerz.firebasenotes_05_07_2017.R;
import com.gabrysuerz.firebasenotes_05_07_2017.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class Categories extends AppCompatActivity implements DialogDelete.IOnDelete {

    private static final String DIALOG = "delete";
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;
    private FirebaseRecyclerAdapter adapterCategories;
    private DatabaseReference categoryRef;

    @BindView(R.id.toolbar_category)
    Toolbar toolbar_category;
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView;
    @BindView(R.id.edit_category_name)
    EditText edit_cat_name;
    @BindView(R.id.btn_new_category)
    Button btn_new_category;

    @OnClick(R.id.btn_new_category)
    void newCategory() {
        setNewCategory(new Category(databaseRef.push().getKey(), edit_cat_name.getText().toString()));
    }

    @OnTextChanged(R.id.edit_category_name)
    void editChanged() {
        btn_new_category.setEnabled(edit_cat_name.getText().toString().length() > 3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_category.setTitle(R.string.categories);

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        categoryRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories");
        setCategoryList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /// Category

    public void setCategoryList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterCategories = new FirebaseRecyclerAdapter<Category, CategoryHolder>(
                Category.class,
                R.layout.cell_category,
                CategoryHolder.class,
                categoryRef.orderByChild("categoryName")) {

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

                    }

                    @Override
                    public void onItemLongClick(View view, int position, Category category) {
                        longItemClicked(category);
                    }
                });
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapterCategories);
    }

    private void longItemClicked(Category category) {
        DialogDelete.getInstance(category.getCategoryId()).show(getFragmentManager(), DIALOG);
    }

    private void setNewCategory(Category category) {
        if (databaseRef != null) {
            DatabaseReference noteRef = categoryRef.child(category.getCategoryId());
            noteRef.setValue(category);

            noteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Category value = dataSnapshot.getValue(Category.class);
                    edit_cat_name.setText("");
                    // Log.e(TAG, value.getTitle());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.e(TAG, "Fuck you");
                    //Log.e(TAG, error.toString());
                }
            });
        }
    }

    @Override
    public void onYes(String id) {
        DatabaseReference catRef = categoryRef.child(id);
        catRef.removeValue();
    }

}
