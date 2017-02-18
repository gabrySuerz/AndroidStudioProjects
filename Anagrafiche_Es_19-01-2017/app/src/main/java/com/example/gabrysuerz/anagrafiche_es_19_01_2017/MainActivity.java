package com.example.gabrysuerz.anagrafiche_es_19_01_2017;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data.PeopleCursorAdapter;
import com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data.PersonContentProvider;
import com.example.gabrysuerz.anagrafiche_es_19_01_2017.Data.PersonHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    PeopleCursorAdapter mAdapter;
    ListView mList;
    Toolbar toolbar;
    EditText mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSearch = (EditText) findViewById(R.id.edit_search);
        mSearch.setText("");

        mList = (ListView) findViewById(R.id.people_list);

        mAdapter = new PeopleCursorAdapter(this, null);
        mList.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetails(id);
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
    }

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
        vBun.putLong(PersonHelper._ID, id);
        vIntent.putExtras(vBun);
        startActivity(vIntent);
    }

    public void addPerson() {
        startActivity(new Intent(this, AddPersonActivity.class));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, PersonContentProvider.PERSON_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
