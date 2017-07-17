package com.esame.note_15_06_2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.esame.note_15_06_2017.Dialogs.DialogSelectCategory;
import com.esame.note_15_06_2017.Dialogs.DialogSelectMedia;
import com.esame.note_15_06_2017.Models.Category;
import com.esame.note_15_06_2017.Models.Note;
import com.esame.note_15_06_2017.Models.User;
import com.esame.note_15_06_2017.Utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
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

public class NewNoteActivity extends AppCompatActivity implements DialogSelectCategory.IOnCategory {

    public static final String ID = "idNoteEdit";

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "tag";
    private static final String DIALOG = "dialog_new_note";
    private static final String DIALOG_MEDIA = "dialog_media";

    private FirebaseAuth auth;
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private DatabaseReference databaseRef;
    private DatabaseReference notesRef;
    private DatabaseReference categoriesRef;

    private User user;
    private String username = "";
    private String email = "";
    private Note editNote;
    private Category categorySelected;
    private DialogSelectCategory selectCategory;

    @BindView(R.id.toolbar_note)
    Toolbar toolbar_note;
    @BindView(R.id.edit_title_note)
    EditText title;
    @BindView(R.id.edit_content_note)
    EditText content;
    @BindView(R.id.txt_category_note)
    TextView category;


    @OnClick(R.id.txt_category_note)
    void selectCategory() {
        selectCategory = DialogSelectCategory.getInstance();
        selectCategory.show(getFragmentManager(), DIALOG);
    }

    @OnClick(R.id.fab_add_content)
    void addContent() {
        DialogSelectMedia.getInstance().show(getFragmentManager(), DIALOG_MEDIA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_note);

        auth = FirebaseAuth.getInstance();

        this.userAuthentication(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            //saveMenu.setEnabled(false);
            if (editNote != null) {
                sendNote(new Note(editNote.getNoteId()));
            } else {
                sendNote(new Note(databaseRef.push().getKey()));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setEditNote() {
        String noteId = getIntent().getExtras().getString(ID);
        DatabaseReference noteRef = notesRef.child(noteId);
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Note n = dataSnapshot.getValue(Note.class);
                editNote = n;
                title.setText(n.getTitle());
                content.setText(n.getContent());
                if (n.getCategory() != null) {
                    categoriesRef.child(n.getCategory()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Category c = dataSnapshot.getValue(Category.class);
                            category.setText(c.getCategoryName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    private void userAuthentication(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser current = auth.getCurrentUser();
        if (current != null) {
            user = new User(current.getEmail(), current.getDisplayName(), current.getPhotoUrl().toString());
            username = user.getUserName();
            email = user.getEmailAddress();

            databaseRef = FirebaseDatabase.getInstance().getReference();
            notesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes");
            categoriesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories");
            //this.setupHeaderDrawer(savedInstanceState);
            if (getIntent().getExtras() != null) {
                this.setEditNote();
            }
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
                .withActivity(this)
                .withToolbar(toolbar_note)
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
        toolbar_note.setTitle(R.string.title_activity_new_note);
    }

    private void showSnackbar(int sign_in_cancelled) {
        Snackbar.make(toolbar_note, sign_in_cancelled, Snackbar.LENGTH_LONG).show();
    }

    private void onTouchDrawer(int identifier) {
        switch (identifier) {
            case Constants.NOTES:
                startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case Constants.CATEGORIES:
                startActivity(new Intent(this, CategoriesActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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
                        //startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                        //finish();
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
                            // Deletion succeeded
                        } else {
                            // Deletion failed
                        }
                    }
                });
    }

    private void sendNote(Note note) {
        if (databaseRef != null) {
            note.setTitle(title.getText().toString());
            note.setContent(content.getText().toString());
            if (categorySelected != null) {
                note.setCategory(categorySelected.getCategoryId());
                categorySelected.setCount(categorySelected.getCount() + 1);
                categoriesRef.child(categorySelected.getCategoryId()).setValue(categorySelected);
            }

            DatabaseReference noteRef = notesRef.child(note.getNoteId());
            noteRef.setValue(note);

            noteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Note value = dataSnapshot.getValue(Note.class);
                    onBackPressed();
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
    public void onSelect(Category cat) {
        if (cat != null) {
            this.categorySelected = cat;
            this.category.setText(cat.getCategoryName());
            selectCategory.dismiss();
        } else {

        }
    }
}
