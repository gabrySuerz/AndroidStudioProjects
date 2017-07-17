package com.gabrysuerz.firebasenotes_05_07_2017.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Category;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Note;
import com.gabrysuerz.firebasenotes_05_07_2017.R;
import com.gabrysuerz.firebasenotes_05_07_2017.Utils.CustomView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gabrysuerz.firebasenotes_05_07_2017.Controllers.EditNote.ID;

public class DetailNote extends AppCompatActivity {

    public static final String TAG = "NoteDetailID";
    private String noteID;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private StorageReference imageRef;
    private MediaPlayer mPlayer;
    private String mAudioName;
    private boolean startPlay = false;
    private Note note;
    private DatabaseReference noteRef;

    @BindView(R.id.txt_title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_content_detail)
    TextView txtContent;
    @BindView(R.id.txt_category_detail)
    TextView txtCategory;
    @BindView(R.id.toolbar_detail_note)
    Toolbar toolbar_detail;
    @BindView(R.id.img_view_thumb)
    ImageView thumb;
    @BindView(R.id.img_view_sketch)
    ImageView sketchView;
    @BindView(R.id.btn_audio_play)
    Button btn_audio;

    @OnClick(R.id.btn_audio_play)
    void play() {
        onPlay(!startPlay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        noteID = getIntent().getExtras().getString(TAG);
        getNote();

        storageRef = FirebaseStorage.getInstance().getReference();
        imageRef = storageRef.child("users").child(auth.getCurrentUser().getUid()).child("images");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /// Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_modify) {
            Bundle bun = new Bundle();
            bun.putString(ID, noteID);
            if (mAudioName != null) {
                bun.putString("audioPath", mAudioName);
            }
            startActivity(new Intent(this, EditNote.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(bun));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /// Nota in dettaglio

    private void getNote() {
        noteRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes").child(noteID);
        noteRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Note n = dataSnapshot.getValue(Note.class);
                note = n;
                txtTitle.setText(note.getTitle());
                txtContent.setText(note.getContent());
                if (note.getCategory() != null) {
                    databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories").child(note.getCategory()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Category c = dataSnapshot.getValue(Category.class);
                            if (c != null) {
                                txtCategory.setText(c.getCategoryName());
                            } else {
                                note.setCategory("");
                                noteRef.setValue(note);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (note.isCloudImageExist()) {
                    Glide.with(DetailNote.this)
                            .using(new FirebaseImageLoader())
                            .load(imageRef.child(note.getImagePath()))
                            .into(thumb);
                }
                if (note.isCloudSketchExist()) {
                    Glide.with(DetailNote.this)
                            .using(new FirebaseImageLoader())
                            .load(imageRef.getParent().child("sketch").child(note.getSketchPath()))
                            .into(sketchView);
                }
                if (note.isCloudAudioExist()) {
                    File localFile = null;
                    try {
                        localFile = File.createTempFile("audio", "3gp");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mAudioName = localFile.getPath();
                    imageRef.getParent().child("audio").child(note.getAudioPath()).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            btn_audio.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // If there's a download in progress, save the reference so you can query it later
        if (storageRef != null) {
            outState.putString("reference", storageRef.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // If there was a download in progress, get its reference and create a new StorageReference
        final String stringRef = savedInstanceState.getString("reference");
        if (stringRef == null) {
            return;
        }
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

        // Find all DownloadTasks under this StorageReference (in this example, there should be one)
        List<FileDownloadTask> tasks = storageRef.getActiveDownloadTasks();
        if (tasks.size() > 0) {
            // Get the task monitoring the download
            FileDownloadTask task = tasks.get(0);

            // Add new listeners to the task using an Activity scope
            task.addOnSuccessListener(this, new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot state) {
                    //handleSuccess(state); //call a user defined function to handle the event.
                }
            });
        }
    }

    // Audio

    private void onPlay(boolean start) {
        if (start) {
            startPlay = start;
            btn_audio.setText("Stop");
            startPlaying();
        } else {
            startPlay = start;
            btn_audio.setText("Play");
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mAudioName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {

        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

}
