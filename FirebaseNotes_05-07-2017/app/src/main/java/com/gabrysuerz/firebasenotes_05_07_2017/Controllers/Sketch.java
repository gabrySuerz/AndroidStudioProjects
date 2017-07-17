package com.gabrysuerz.firebasenotes_05_07_2017.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gabrysuerz.firebasenotes_05_07_2017.R;
import com.gabrysuerz.firebasenotes_05_07_2017.Utils.Constants;
import com.gabrysuerz.firebasenotes_05_07_2017.Utils.CustomView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gabrysuerz on 06/07/17.
 */

public class Sketch extends AppCompatActivity {

    private final static String LOG_TAG = Sketch.class.getSimpleName();
    private File sketchDirectory;
    private File sketchFile;

    @OnClick(R.id.btn_save)
    void save() {
        saveThisDrawing();
    }

    @OnClick(R.id.btn_clean)
    void clean() {
        customView.clear();
    }

    @OnClick(R.id.btn_cancel)
    void cancel() {
        finish();
    }

    @BindView(R.id.custom_view)
    CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);
        ButterKnife.bind(this);

    }


    public void saveThisDrawing() {
        sketchDirectory = new File(Environment.getExternalStorageDirectory(), Constants.ATTACHMENTS_FOLDER);
        if (!sketchDirectory.exists()) {
            sketchDirectory.mkdir();
        }

        customView.setDrawingCacheEnabled(true);


        try {
            String imTitle = "Sketch" + "_" + System.currentTimeMillis() + Constants.MIME_TYPE_SKETCH_EXT;
            sketchFile = new File(sketchDirectory, imTitle);
            if (!sketchFile.exists()) {
                sketchFile.createNewFile();
            }
            sketchFile.setReadable(true);

            customView.setDrawingCacheEnabled(true);
            FileOutputStream fOut = new FileOutputStream(sketchFile);
            Bitmap bm = customView.getDrawingCache();
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);


        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED);
            finish();

        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED);
            finish();
        }

        String signatureFilePath = sketchFile.getPath();
        Intent data = new Intent();
        data.setData(Uri.parse(signatureFilePath));
        setResult(RESULT_OK, data);
        finish();
        customView.destroyDrawingCache();
    }


}
