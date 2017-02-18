package com.example.gabry.fragmentnogui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlankFragment vFrag = (BlankFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (vFrag == null) {
            vFrag = BlankFragment.getInstance();
            FragmentTransaction vTrans = getSupportFragmentManager().beginTransaction();
            //vTrans.add(R.id.container, vFrag, TAG);
            vTrans.add(vFrag, TAG);
            vTrans.commit();
        }
    }
}
