package com.example.gabry.intentstest;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String mUrl = "https://www.google.com";

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == PICK_CONTACT && result_code == RESULT_OK) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGUI();
    }

    private void setupGUI() {
        Button vWeb = (Button) findViewById(R.id.btnWeb);
        vWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickWeb(mUrl);
            }
        });
        Button vSms = (Button) findViewById(R.id.btnSms);
        vSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSms("Hey Hey Hey");
            }
        });
        Button vPick = (Button) findViewById(R.id.btnPick);
        vPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPick();
            }
        });
    }

    private final int PICK_CONTACT = 1029;

    private void clickPick() {
        Intent vIntent = new Intent(Intent.ACTION_PICK);
        vIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (vIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(vIntent, PICK_CONTACT);
        }
    }

    private void clickWeb(String url) {
        Uri vWebpage = Uri.parse(url);
        Intent vIntent = new Intent(Intent.ACTION_VIEW, vWebpage);
        //vIntent.setData(vWebpage);  nel caso sopra solo action

        Intent vChooser = Intent.createChooser(vIntent, "");//chooser personalizzato

        if (vIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(vChooser);
        }

    }

    private void clickSms(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"));   // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_TEXT, "pippo");

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "pippo");
        sendIntent.setType("text/plain");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
