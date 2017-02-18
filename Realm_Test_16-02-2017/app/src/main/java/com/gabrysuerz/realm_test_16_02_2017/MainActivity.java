package com.gabrysuerz.realm_test_16_02_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realmInstance;
    RealmAsyncTask transaction;
    RealmAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realmInstance = Realm.getDefaultInstance();
        mAdapter = new RealmAdapter(this, realmInstance.where(Person.class).findAll());


        addPerson();

        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetails(mAdapter.getItem(position));
            }
        });
    }

    public void onStop() {
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
        super.onStop();
    }

    private void goToDetails(Person person) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bun = new Bundle();
        bun.putLong(DetailsActivity.ID, person.getId());
        intent.putExtras(bun);
        startService(intent);
    }

    private void addPerson() {
        /*realmInstance.beginTransaction();
        Person p = realmInstance.copyToRealm(new Person("","","","","","","","","","","","")); // Create a new object
        p.setName("John");
        p.setEmail("john@corporation.com");
        realmInstance.commitTransaction();*/

        transaction = realmInstance.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person p = realmInstance.copyToRealm(new Person("", "", "", "", "", "", "", "", "", "", "", "")); // Create a new object
                p.setName("John");
                p.setEmail("john@corporation.com");
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });
    }
}
