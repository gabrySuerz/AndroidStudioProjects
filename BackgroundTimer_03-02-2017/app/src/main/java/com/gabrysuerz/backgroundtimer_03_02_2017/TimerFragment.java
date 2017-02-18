package com.gabrysuerz.backgroundtimer_03_02_2017;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    public interface IOnTimerUpdate {
        void onUpdateTimer(int hour, int min, int sec);

        void onCancelTimer();
    }

    private static final String FRAGMENT_TAG = "FRAGMENT_TIMER";
    private IOnTimerUpdate mListener;
    private MyAsyncTask mAsyncTask;


    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment getInstance() {
        return new TimerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Serve per preservare il timer durante la rotazione dello schermo
        setRetainInstance(true);
        return null;
    }

    public void start() {
        mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        private int hh = 0;
        private int mm = 0;
        private int ss = 0;

        @Override
        protected String doInBackground(Void... voids) {
            while (!isCancelled()) {
                ss++;
                if (ss == 60) {
                    ss = 0;
                    mm += 1;
                } if (mm == 60) {
                    hh += 1;
                    mm = 0;
                }
                try {
                    Thread.sleep(1000);
                    publishProgress(hh, mm, ss);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "FINISH";
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // ad ogmni au
            mListener.onUpdateTimer(values[0], values[1], values[2]);
        }

        @Override
        protected void onCancelled() {
            mListener.onCancelTimer();
        }
    }

    public void delete() {
        Log.d("TAG", "Sono passato per di qua");
        mAsyncTask.cancel(true);
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
        // Uso mListener per assegnare il riferimento dell'istanza di timerUpdate nella mainActivity
        if (context instanceof IOnTimerUpdate) {
            mListener = (IOnTimerUpdate) context;
        }
    }
}