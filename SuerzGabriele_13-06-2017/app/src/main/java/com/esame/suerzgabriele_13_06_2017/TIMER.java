package com.esame.suerzgabriele_13_06_2017;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabrysuerz on 13/06/17.
 */

public class TIMER extends Fragment {

    private static final String FRAGMENT_TAG = "Fragment";
    private IOnTimer mListener;
    private MyAsyncTimer async;

    public interface IOnTimer {
        void onUpdateTimer(int min, int sec);

        void onLapTimer(int min, int sec);

        void onStopTimer();

        void onCancelTimer();
    }

    public static TIMER getInstance() {
        return new TIMER();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);

        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(FRAGMENT_TAG, "onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(FRAGMENT_TAG, "onAttach");
        if (context instanceof IOnTimer) {
            mListener = (IOnTimer) context;
        }
    }

    public void start() {
        async = new MyAsyncTimer();
        async.execute();
    }

    public void lapStop() {
        async.setMmLap(00);
        async.setSsLap(00);
    }

    public void cancel(boolean cancel) {
        async.setType(cancel);
        async.cancel(true);
    }

    public class MyAsyncTimer extends AsyncTask<Void, Integer, String> {

        private int mm = 00;
        private int ss = 00;
        private int ssLap = 00;
        private int mmLap = 00;
        private boolean cancel = false;

        public void setSsLap(int ssLap) {
            this.ssLap = ssLap;
        }

        public void setMmLap(int mmLap) {
            this.mmLap = mmLap;
        }

        public void setType(boolean cancel) {
            this.cancel = cancel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mListener.onUpdateTimer(values[0], values[1]);
            mListener.onLapTimer(values[2], values[3]);
        }

        @Override
        protected void onCancelled() {
            if (cancel)
                mListener.onCancelTimer();
            else
                mListener.onStopTimer();
        }

        @Override
        protected String doInBackground(Void... params) {
            while (!isCancelled()) {
                ss++;
                ssLap++;
                if (ss == 60) {
                    ss = 0;
                    mm += 1;
                } if (ssLap == 60){
                    ssLap = 0;
                    mmLap += 1;
                }
                try {
                    Thread.sleep(1000);
                    publishProgress(mm, ss, mmLap, ssLap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "FINISH";
        }
    }
}
