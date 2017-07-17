package com.esame.note_15_06_2017;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.esame.note_15_06_2017.Dialogs.DialogDelete;
import com.esame.note_15_06_2017.FirebaseStuff.CategoryHolder;
import com.esame.note_15_06_2017.Models.Category;
import com.esame.note_15_06_2017.Models.User;
import com.esame.note_15_06_2017.Utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity implements DialogDelete.IOnDelete {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "tag";
    private static final String DIALOG = "dialog";

    private FirebaseAuth auth;
    private DatabaseReference databaseRef;
    private DatabaseReference categoriesRef;
    private FirebaseRecyclerAdapter mAdapter;

    @BindView(R.id.toolbar_categories)
    Toolbar toolbarCategories;
    @BindView(R.id.edit_category_name)
    EditText edit_title;
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerCategoriesList;

    @OnClick(R.id.btn_new_category)
    void click() {
        this.newCategory(new Category(databaseRef.push().getKey(), edit_title.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarCategories);

        auth = FirebaseAuth.getInstance();

        databaseRef = FirebaseDatabase.getInstance().getReference();
        this.getCategories();
    }

    private void getCategories() {
        categoriesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories");
        recyclerCategoriesList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Category, CategoryHolder>(
                Category.class,
                R.layout.cell_category,
                CategoryHolder.class,
                categoriesRef.orderByKey()) {

            private Category category;

            @Override
            public void populateViewHolder(CategoryHolder holder, Category category, int position) {
                this.category = category;
                holder.setCategory(category);
            }

            @Override
            public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CategoryHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new CategoryHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        longItemClicked(category);
                    }
                });
                return viewHolder;
            }
        };
        recyclerCategoriesList.setAdapter(mAdapter);
    }

    private void longItemClicked(Category category) {
        DialogDelete diag = DialogDelete.getInstance(category.getCategoryId());
        diag.show(getFragmentManager(), DIALOG);
    }

    private void newCategory(Category category) {
        if (databaseRef != null) {
            DatabaseReference noteRef = categoriesRef.child(category.getCategoryId());
            noteRef.setValue(category);

            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(edit_title.getWindowToken(), 0);

            noteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Category value = dataSnapshot.getValue(Category.class);
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
        DatabaseReference catRef = categoriesRef.child(id);
        catRef.removeValue();
    }
}
