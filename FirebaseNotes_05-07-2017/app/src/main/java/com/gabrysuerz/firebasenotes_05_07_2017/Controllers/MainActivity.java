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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gabrysuerz.firebasenotes_05_07_2017.Dialogs.DialogDelete;
import com.gabrysuerz.firebasenotes_05_07_2017.Firebase.NoteHolder;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Note;
import com.gabrysuerz.firebasenotes_05_07_2017.R;
import com.gabrysuerz.firebasenotes_05_07_2017.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

public class MainActivity extends AppCompatActivity implements DialogDelete.IOnDelete {

    private static final int RC_SIGN_IN = 123;
    private static final String DIALOG = "dialog";
    private FirebaseAuth auth;
    private AccountHeader mHeader;

    public Drawer mDrawer;

    DatabaseReference databaseRef;
    Query listNotesQuery;
    FirebaseRecyclerAdapter adapter;
    private DatabaseReference notesRef;

    @BindView(R.id.toolbar_main_activity)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_notes)
    RecyclerView recyclerView;

    @OnClick(R.id.fab_main_activity)
    void clickFab() {
        Intent i = new Intent(this, EditNote.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.userAuthentication(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    /// List notes

    public void setNoteList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FirebaseRecyclerAdapter<Note, NoteHolder>(
                Note.class,
                R.layout.cell_note,
                NoteHolder.class,
                listNotesQuery) {

            @Override
            public void populateViewHolder(NoteHolder holder, Note note, int position) {
                holder.setNote(note);
            }

            @Override
            public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                NoteHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new NoteHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position, Note note) {
                        Intent i = new Intent(MainActivity.this, DetailNote.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle bun = new Bundle();
                        bun.putString(DetailNote.TAG, note.getNoteId());
                        i.putExtras(bun);
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongClick(View view, int position, Note note) {
                        DialogDelete diag = DialogDelete.getInstance(note.getNoteId());
                        diag.show(getFragmentManager(), DIALOG);
                    }
                });
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
    }

    /// Menu options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter_time) {
            listNotesQuery = notesRef.orderByKey();
            adapter.cleanup();
            setNoteList();
            return true;
        } else if (id == R.id.action_filter_name) {
            listNotesQuery = notesRef.orderByChild("title");
            adapter.cleanup();
            setNoteList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /// Authentication

    private void userAuthentication(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            databaseRef = FirebaseDatabase.getInstance().getReference();
            notesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes");
            listNotesQuery = notesRef.orderByKey();
            this.setNoteList();
            mHeader = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            new ProfileDrawerItem().withName(auth.getCurrentUser().getDisplayName()).withEmail(auth.getCurrentUser().getEmail()).withIcon(GoogleMaterial.Icon.gmd_account)
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            return false;
                        }
                    })
                    .build();
            this.setupHeaderDrawer(savedInstanceState);
        } else {
            login();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return;
            } else {
                if (response == null) {
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    private void login() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        login();
                    }
                });
    }

    private void delete() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            login();
                        } else {
                            // Deletion failed
                        }
                    }
                });
    }
/// Drawer

    private void setupHeaderDrawer(Bundle savedInstanceState) {
        mDrawer = new DrawerBuilder()
                .withAccountHeader(mHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.notes)
                                .withIcon(GoogleMaterial.Icon.gmd_view_list)
                                .withIdentifier(Constants.NOTES),
                        new PrimaryDrawerItem()
                                .withName(R.string.categories)
                                .withIcon(GoogleMaterial.Icon.gmd_folder)
                                .withIdentifier(Constants.CATEGORIES),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withName(R.string.logout)
                                .withIcon(GoogleMaterial.Icon.gmd_lock)
                                .withIdentifier(Constants.LOGOUT)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            onTouchDrawer((int) drawerItem.getIdentifier());
                        }
                        return false;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withFireOnInitialOnClick(true)
                .withSavedInstance(savedInstanceState)
                .build();

        mDrawer.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.delete).withIcon(GoogleMaterial.Icon.gmd_delete).withIdentifier(Constants.DELETE));
        toolbar.setTitle(R.string.notes);
    }

    private void showSnackbar(int sign_in_cancelled) {
        Snackbar.make(toolbar, sign_in_cancelled, Snackbar.LENGTH_LONG).show();
    }

    private void onTouchDrawer(int identifier) {
        switch (identifier) {
            case Constants.CATEGORIES:
                Intent i = new Intent(this, Categories.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case Constants.LOGOUT:
                logout();
                break;
            case Constants.DELETE:
                delete();
                break;
        }
    }

    /// Interfaccia dialog

    @Override
    public void onYes(String id) {
        DatabaseReference deleteRef = notesRef.child(id);
        deleteRef.removeValue();
    }
}
