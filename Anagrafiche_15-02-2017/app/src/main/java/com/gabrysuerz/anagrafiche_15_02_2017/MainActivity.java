package com.gabrysuerz.anagrafiche_15_02_2017;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;

import static com.activeandroid.Cache.getContext;

public class MainActivity extends AppCompatActivity {

    public final static String ID = "ID";
    PeopleCursorAdapter mAdapter;
    ListView mList;
    Toolbar toolbar;
    EditText mSearch;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        /*toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSearch = (EditText) findViewById(R.id.edit_search);
        mSearch.setText("");*/

        mContext = this;
        mList = (ListView) findViewById(R.id.people_list);

        mAdapter = new PeopleCursorAdapter(this, null);
        mList.setAdapter(mAdapter);

        Item p = new Item("a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a");
        MainActivity.this.getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Log.d("TAG", ContentProvider.createUri(Item.class, null).toString());
                return new CursorLoader(MainActivity.this,
                        ContentProvider.createUri(Item.class, null), null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetails(id);
            }
        });

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new Delete().from(Item.class).where("Id = ?", id).execute();
                return true;
            }
        });

        Button btn = (Button) findViewById(R.id.btn_add_person);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson();
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(this, AddPersonActivity.class));
            return true;
        } else if (id == R.id.action_search) {
            setGUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void setGUI() {
        if (mSearch.getVisibility() == View.GONE) {
            mSearch.setVisibility(View.VISIBLE);

            //search(mSearch.getText().toString());
        } else
            mSearch.setVisibility(View.GONE);
    }

    private void search(String s) {
        mSearch.setText("");
    }

    private void goToDetails(long id) {
        Intent vIntent = new Intent(this, PersonDetail.class);
        Bundle vBun = new Bundle();
        vBun.putLong(ID, id);
        vIntent.putExtras(vBun);
        startActivity(vIntent);
    }

    public void addPerson() {
        startActivity(new Intent(this, AddPersonActivity.class));
    }


}
