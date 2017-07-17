package com.gabrysuerz.firebasenotes_05_07_2017.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gabrysuerz.firebasenotes_05_07_2017.Dialogs.DialogSelectCategory;
import com.gabrysuerz.firebasenotes_05_07_2017.Dialogs.DialogSelectMedia;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Category;
import com.gabrysuerz.firebasenotes_05_07_2017.Models.Note;
import com.gabrysuerz.firebasenotes_05_07_2017.R;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNote extends AppCompatActivity implements DialogSelectCategory.IOnCategory, DialogSelectMedia.IOnMedia {

    public static final String ID = "idNoteEdit";

    private static final String DIALOG = "dialog_new_note";
    private static final String DIALOG_MEDIA = "dialog_media";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SKETCH_CAPTURE = 2;
    private static final int REQUEST_AUDIO_CAPTURE = 3;
    private static final String NOTE = "note";
    private static final String PHOTOPATH = "photo";
    private static final String IMGNAME = "imagename";
    private static final String AUDIO = "audio";
    private static final String SKETCH = "sketch";

    private FirebaseAuth auth;
    private DatabaseReference databaseRef;
    private DatabaseReference notesRef;
    private DatabaseReference categoriesRef;
    private StorageReference storageRef;
    private StorageReference imageRef;

    private Note editNote;
    private Category categorySelected;
    private DialogSelectCategory selectCategory;
    private String mCurrentPhotoPath;
    private String imageName;
    private Bitmap bitmap;
    private Uri audioPath;
    private Uri sketchPath;
    private MediaPlayer mPlayer;
    private boolean startPlay = false;

    @BindView(R.id.toolbar_edit_note)
    Toolbar toolbar_note;
    @BindView(R.id.edit_title_note)
    EditText title;
    @BindView(R.id.edit_content_note)
    EditText content;
    @BindView(R.id.txt_category_note)
    TextView category;
    @BindView(R.id.img_view_thumb)
    ImageView thumb;
    @BindView(R.id.txt_progress)
    TextView txt_progress;
    @BindView(R.id.btn_audio)
    Button btn_audio;
    @BindView(R.id.img_view_sketch)
    ImageView sketch;

    @OnClick(R.id.btn_audio)
    void play() {
        onPlay(!startPlay);
    }

    @OnClick(R.id.txt_category_note)
    void selectCategory() {
        selectCategory = DialogSelectCategory.getInstance();
        selectCategory.show(getFragmentManager(), DIALOG);
    }

    @OnClick(R.id.fab_edit_note)
    void addContent() {
        DialogSelectMedia.getInstance().show(getFragmentManager(), DIALOG_MEDIA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        auth = FirebaseAuth.getInstance();

        databaseRef = FirebaseDatabase.getInstance().getReference();
        notesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("notes");
        categoriesRef = databaseRef.child("users").child(auth.getCurrentUser().getUid()).child("categories");

        storageRef = FirebaseStorage.getInstance().getReference();
        imageRef = storageRef.child("users").child(auth.getCurrentUser().getUid()).child("images");

        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString(PHOTOPATH);
            imageName = savedInstanceState.getString(IMGNAME);
            audioPath = Uri.parse(savedInstanceState.getString(AUDIO));
            sketchPath = Uri.parse(savedInstanceState.getString(SKETCH));
            if (mCurrentPhotoPath != null){
                Glide.with(this).load(mCurrentPhotoPath).into(thumb);
            } if (audioPath != null){
                btn_audio.setVisibility(View.VISIBLE);
            } if (sketchPath != null){
                Glide.with(this).load(sketchPath).into(sketch);
            }
            setEditNote(savedInstanceState.getString(NOTE));
        } else if (getIntent().getExtras() != null) {
            setEditNote(getIntent().getExtras().getString(ID));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NOTE, editNote.getNoteId());
        outState.putString(PHOTOPATH, mCurrentPhotoPath);
        outState.putString(IMGNAME, imageName);
        outState.putString(AUDIO, audioPath.getPath());
        outState.putString(SKETCH, sketchPath.getPath());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /// Menu

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
            if (editNote != null) {
                sendNote(new Note(editNote.getNoteId()));
            } else {
                sendNote(new Note(databaseRef.push().getKey()));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /// Note

    private void setEditNote(String noteId) {
        final DatabaseReference noteRef = notesRef.child(noteId);
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
                            if (c != null) {
                                categorySelected = c;
                                category.setText(c.getCategoryName());
                            } else {
                                editNote.setCategory("");
                                noteRef.setValue(editNote);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (n.isCloudImageExist()) {
                    Glide.with(EditNote.this)
                            .using(new FirebaseImageLoader())
                            .load(imageRef.child(n.getImagePath()))
                            .into(thumb);
                }
                if (n.isCloudSketchExist()) {
                    Glide.with(EditNote.this)
                            .using(new FirebaseImageLoader())
                            .load(imageRef.getParent().child("sketch").child(n.getSketchPath()))
                            .into(sketch);
                }

                if (n.isCloudAudioExist()) {
                    btn_audio.setVisibility(View.VISIBLE);
                    if (getIntent().getExtras().getString("audioPath") != null) {
                        audioPath = Uri.parse(getIntent().getExtras().getString("audioPath"));
                    } else {
                        File localFile = null;
                        try {
                            localFile = File.createTempFile("audio", "3gp");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        audioPath = Uri.parse(localFile.getPath());
                        imageRef.getParent().child("audio").child(editNote.getAudioPath()).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            editNote = note;

            final DatabaseReference noteRef = notesRef.child(editNote.getNoteId());
            noteRef.setValue(editNote);

            noteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Note value = dataSnapshot.getValue(Note.class);
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] dataByte = baos.toByteArray();

                        UploadTask uploadTask = imageRef.child(imageName).putBytes(dataByte);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                txt_progress.setText((Math.round(taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount()) + "%");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                editNote.setCloudImageExist(true);
                                editNote.setImagePath(imageName);
                                noteRef.setValue(editNote);

                                imageName = null;
                                if (sketchPath == null && audioPath == null)
                                    finish();
                            }
                        });
                    }

                    if (sketchPath != null) {
                        Bitmap sketch = BitmapFactory.decodeFile(sketchPath.getPath());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        sketch.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] dataByte = baos.toByteArray();

                        UploadTask uploadTask = imageRef.getParent().child("sketch").child(sketchPath.getLastPathSegment()).putBytes(dataByte);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                txt_progress.setText((Math.round(taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100) + "%");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                editNote.setCloudSketchExist(true);
                                editNote.setSketchPath(sketchPath.getLastPathSegment());
                                noteRef.setValue(editNote);

                                sketchPath = null;
                                if (imageName == null && audioPath == null)
                                    finish();
                            }
                        });
                    }
                    if (audioPath != null) {
                        InputStream dataByte = null;
                        try {
                            dataByte = new FileInputStream(new File(audioPath.getPath()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        UploadTask uploadTask = imageRef.getParent().child("audio").child(audioPath.getLastPathSegment()).putStream(dataByte);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                txt_progress.setText((Math.round(taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100) + "%");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                editNote.setCloudAudioExist(true);
                                editNote.setAudioPath(audioPath.getLastPathSegment());
                                noteRef.setValue(editNote);

                                audioPath = null;
                                if (imageName == null && sketchPath == null)
                                    finish();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }
    }

    /// Interfaccia

    @Override
    public void onSelect(Category cat) {
        if (cat != null) {
            this.categorySelected = cat;
            this.category.setText(cat.getCategoryName());
            selectCategory.dismiss();
        } else {

        }
    }

    @Override
    public void onImage() {
        dispatchTakePictureIntent();
    }

    @Override
    public void onAudio() {
        startActivityForResult(new Intent(this, Audio.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), REQUEST_AUDIO_CAPTURE);
    }

    @Override
    public void onSketch() {
        startActivityForResult(new Intent(this, Sketch.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), REQUEST_SKETCH_CAPTURE);
    }

    /// Take images

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Glide.with(this).load(mCurrentPhotoPath).into(thumb);
        }
        if (requestCode == REQUEST_SKETCH_CAPTURE && resultCode == RESULT_OK) {
            sketchPath = data.getData();
            Glide.with(this).load(sketchPath).into(sketch);
        }
        if (requestCode == REQUEST_AUDIO_CAPTURE && resultCode == RESULT_OK) {
            audioPath = data.getData();
            btn_audio.setVisibility(View.VISIBLE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        imageName = imageFileName + ".jpg";
        return image;
    }

    // Audio

    private void onPlay(boolean start) {
        if (start) {
            startPlay = start;
            btn_audio.setText("Stop");
            startPlaying();
        } else {
            btn_audio.setText("Play");
            startPlay = start;
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioPath.getPath());
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
