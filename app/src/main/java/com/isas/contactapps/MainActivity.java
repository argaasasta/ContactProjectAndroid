package com.isas.contactapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.isas.contactapps.adapter.ListContactsAdapter;
import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.api.ApiServices;
import com.isas.contactapps.model.ContactPerson;
import com.isas.contactapps.model.ContactPersonService;
import com.isas.contactapps.viewmodel.DetailContactViewModel;
import com.isas.contactapps.viewmodel.MainViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.list_view_contacts)
    ListView listViewContacts;

//    @Inject
//    ContactPersonService contactPersonService;

    private ListContactsAdapter listContactsAdapter;
    private CompositeSubscription subscription = new CompositeSubscription();
    private MainViewModel mainViewModel;

    ArrayList<ContactPerson> contactPersonAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddContactActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        listViewContacts.setOnItemClickListener(this);

        contactPersonAll=new ArrayList<>();
        mainViewModel = new MainViewModel(getApplicationContext(),new ApiInteractorImpl(),AndroidSchedulers.mainThread());
        getData();

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
        if (id == R.id.action_refresh) {
            getData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getApplicationContext(),DetailContactActivity.class);
        ContactPerson c = contactPersonAll.get(position);
        intent.putExtra("ID",c.getId());
        startActivity(intent);
    }

    public void getData(){
        subscription.add(mainViewModel.getListContact()
                .subscribe(new Observer<ContactPerson[]>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ContactPerson contactPerson[]) {
                        mainViewModel.saveDataToDatabase(contactPerson);
                        contactPersonAll = mainViewModel.getListContactFromDB();
                        listContactsAdapter = new ListContactsAdapter(getApplicationContext(),contactPersonAll);
                        listViewContacts.setAdapter(listContactsAdapter);
                        System.out.println("JUMLAH "+contactPerson.length);
                    }

                }));
    }

//    - Use MVP and Unit Testing
//    - Use Espresso, Mock Webserver and Activity Instrumentation Tests
//    - Use Android Databinding and MVVM pattern
//    - Use RxJava + RxAndroid + Retrofit 2 + OkHttp 3 + Dagger + ButterKnife + Glide

}
