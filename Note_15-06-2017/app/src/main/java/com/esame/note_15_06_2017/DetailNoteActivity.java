package com.esame.note_15_06_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.esame.note_15_06_2017.Models.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailNoteActivity extends AppCompatActivity {

    public static final String TAG = "NoteDetailID";
    private String noteID;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    @BindView(R.id.txt_title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_content_detail)
    TextView txtContent;
    @BindView(R.id.txt_category_detail)
    TextView txtCategory;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbar_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_detail);


        setDrawer(savedInstanceState);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            noteID = getIntent().getExtras().getString(TAG);
            getNote();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_modify) {
            Bundle bun = new Bundle();
            bun.putString(NewNoteActivity.ID, noteID);
            startActivity(new Intent(this, NewNoteActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bun));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDrawer(Bundle savedInstanceState) {

    }

    private void getNote() {
        DatabaseReference noteRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes").child(noteID);
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Note note = dataSnapshot.getValue(Note.class);
                txtTitle.setText(note.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
