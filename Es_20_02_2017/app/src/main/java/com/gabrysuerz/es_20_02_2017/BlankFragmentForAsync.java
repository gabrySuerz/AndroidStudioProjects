package com.gabrysuerz.es_20_02_2017;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabrysuerz on 20/02/17.
 */

public class BlankFragmentForAsync extends Fragment {

    public BlankFragmentForAsync() {
    }

    public static BlankFragmentForAsync getInstance() {
        return new BlankFragmentForAsync();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IOnAsync) {
            mListener = (IOnAsync) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public interface IOnAsync {
        void onUpdate(int value);

        void onCancel();
    }

    private IOnAsync mListener = new IOnAsync() {
        @Override
        public void onUpdate(int value) {

        }

        @Override
        public void onCancel() {

        }
    };
    private MyAsyncTask mAsync;
    public void startAsync () {
        mAsync = new MyAsyncTask();
        mAsync.execute();
    }

    public void cancelAsync () {
        mAsync.cancel(true);
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            return null;
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
            super.onProgressUpdate(values);
            mListener.onUpdate(values[0]);
        }

        @Override
        protected void onCancelled() {
            mListener.onCancel();
        }
    }
}
