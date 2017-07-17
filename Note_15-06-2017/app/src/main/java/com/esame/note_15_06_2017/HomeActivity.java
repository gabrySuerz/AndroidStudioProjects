package com.esame.note_15_06_2017;

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
import android.widget.TextView;

import com.esame.note_15_06_2017.FirebaseStuff.NoteHolder;
import com.esame.note_15_06_2017.Models.Category;
import com.esame.note_15_06_2017.Models.Note;
import com.esame.note_15_06_2017.Models.User;
import com.esame.note_15_06_2017.Utils.Constants;
import com.esame.note_15_06_2017.Dialogs.DialogDelete;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
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

public class HomeActivity extends AppCompatActivity implements DialogDelete.IOnDelete {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "tag";
    private static final String DIALOG = "dialog";

    private FirebaseAuth auth;
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private DatabaseReference databaseRef;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference notesRef;

    private User user;
    private String username = "";
    private String email = "";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_notes)
    RecyclerView recyclerNoteList;

    @OnClick(R.id.fab_new_note)
    void submit() {
        startActivity(new Intent(HomeActivity.this, NewNoteActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.userAuthentication();
        this.setupHeaderDrawer(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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

    private void userAuthentication() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser current = auth.getCurrentUser();
        if (current != null) {
            user = new User(current.getEmail(), current.getDisplayName(), current.getPhotoUrl().toString());
            username = user.getUserName();
            email = user.getEmailAddress();

            databaseRef = FirebaseDatabase.getInstance().getReference();
            this.setNotesList();
        } else {
            login();
        }
    }

    private void setupHeaderDrawer(Bundle savedInstanceState) {
        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(username).withEmail(email).withIcon(GoogleMaterial.Icon.gmd_account)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        mDrawer = new DrawerBuilder()
                .withAccountHeader(mHeader)
                .withActivity(HomeActivity.this)
                .withToolbar((Toolbar) findViewById(R.id.toolbar))
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

                        new PrimaryDrawerItem()
                                .withName(R.string.settings)
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withIdentifier(Constants.SETTINGS),

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
        toolbar.setTitle(R.string.title_activity_list_notes);
    }

    private void showSnackbar(int sign_in_cancelled) {
        Snackbar.make(toolbar, sign_in_cancelled, Snackbar.LENGTH_LONG).show();
    }

    private void onTouchDrawer(int identifier) {
        switch (identifier) {
            case Constants.CATEGORIES:
                startActivity(new Intent(HomeActivity.this, CategoriesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case Constants.SETTINGS:
                break;
            case Constants.LOGOUT:
                logout();
                break;
            case Constants.DELETE:
                delete();
                break;
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

    private void setNotesList() {
        notesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes");
        recyclerNoteList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Note, NoteHolder>(
                Note.class,
                R.layout.cell,
                NoteHolder.class,
                notesRef.orderByKey()) {

            private Note note;

            @Override
            public void populateViewHolder(NoteHolder holder, Note note, int position) {
                this.note = note;
                holder.setNote(note);
            }

            @Override
            public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                NoteHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new NoteHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        itemClicked(note);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        longItemClicked(note);
                    }
                });
                return viewHolder;
            }
        };
        recyclerNoteList.setAdapter(mAdapter);
    }

    private void itemClicked(Note note) {
        if (findViewById(R.id.txt_title_list) != null) {
            ((TextView) findViewById(R.id.txt_title_list)).setText(note.getTitle());
            ((TextView) findViewById(R.id.txt_content_list)).setText(note.getContent());
            DatabaseReference categoryRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("category").child(note.getCategory());
            categoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Category c = dataSnapshot.getValue(Category.class);
                    ((TextView) findViewById(R.id.txt_category_list)).setText(c.getCategoryName());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Bundle bun = new Bundle();
            bun.putString(DetailNoteActivity.TAG,note.getNoteId());
            startActivity(new Intent(this, DetailNoteActivity.class).putExtras(bun));
        }
    }

    private void longItemClicked(Note note) {
        DialogDelete diag = DialogDelete.getInstance(note.getNoteId());
        diag.show(getFragmentManager(), DIALOG);
    }

    @Override
    public void onYes(String id) {
        DatabaseReference deleteRef = notesRef.child(id);
        deleteRef.removeValue();
    }
}
