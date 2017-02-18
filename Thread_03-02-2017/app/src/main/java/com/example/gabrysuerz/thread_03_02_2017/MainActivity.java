package com.example.gabrysuerz.thread_03_02_2017;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "COUNTER";
    TextView mLabel;
    private MyAsyncTask mAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLabel = (TextView) findViewById(R.id.txt);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLabel.setText("INIZIO");
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        timeConsuming();
                    }
                }).start();*/
                MyAsyncTask vAsync = new MyAsyncTask();
                mAsync = vAsync;
                vAsync.execute();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAsync.cancel(true);
            }
        });

    }

    private void timeConsuming() {
        try {
            for (int vIndex = 1; vIndex < 10; vIndex++) {
                Log.d(TAG, "COUNTER " + vIndex);
                final int vCounter = vIndex;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLabel.setText("" + vCounter);
                    }
                });
                Thread.sleep(1000);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLabel.setText("FINE");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            int vIndex = 0;
            //for (int vIndex = 0; vIndex < 10; vIndex++) {
            while (!isCancelled()) {
                vIndex++;
                Log.d(TAG, "COUNTER " + vIndex);
                try {
                    Thread.sleep(1000);
                    publishProgress(vIndex);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "FINISH";
        }

        @Override
        protected void onPreExecute() {
            mLabel.setText("LET'S START");
        }

        @Override
        protected void onPostExecute(String s) {
            mLabel.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mLabel.setText("" + values[0]);
        }

        @Override
        protected void onCancelled() {
            mLabel.setText("CANCELLED");
        }
    }
}
