package com.isas.contactapps.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isas.contactapps.api.ApiInteractor;
import com.isas.contactapps.db.DatabaseHelper;
import com.isas.contactapps.db.contract.ContactContract;
import com.isas.contactapps.db.util.RegisterTable;
import com.isas.contactapps.model.ContactPerson;
import com.isas.contactapps.model.ContactPersonModule;
import com.isas.contactapps.model.ContactPersonService;
import com.isas.contactapps.model.DaggerDiComponent;
import com.isas.contactapps.model.DiComponent;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by EB-NB21 on 2/16/2017.
 */

public class MainViewModel {


    private ApiInteractor interactor;
    private Scheduler scheduler;

    private Context context;
    private ContactContract contactContract;
    private SQLiteDatabase db;

    DiComponent diComponent;

    public MainViewModel(Context context ,ApiInteractor interactor, Scheduler scheduler) {
        this.context = context;
        this.interactor = interactor;
        this.scheduler = scheduler;

        contactContract = new ContactContract();
        db = DatabaseHelper.getInstance(context,
                RegisterTable.getInstance().getContracts()).getReadableDatabase();
        diComponent = DaggerDiComponent.builder().contactPersonModule(new ContactPersonModule()).build();

    }

    public Observable<ContactPerson[]> getListContact(){
        return interactor.getListContact().observeOn(scheduler);
    }

    public void saveDataToDatabase(ContactPerson[] listContact){
        contactContract.deleteAll(db);
        for (int i = 0; i < listContact.length; i++) {
            contactContract.insert(db,listContact[i].getId(),
                    listContact[i].getFirstName(),
                    listContact[i].getLastName(),
                    listContact[i].getProfilePic(),
                    listContact[i].getUrl(),
                    "0");
        }
    }

    public ArrayList<ContactPerson> getListContactFromDB(){
        ArrayList<ContactPerson>listContact = new ArrayList<>();
        Cursor c = contactContract.getListContactFromDB(db);
        if(c != null && c.moveToFirst()){
            do{
//                ContactPerson cp = new ContactPerson(c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_ID)),
//                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_FIRST_NAME)),
//                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_LAST_NAME)),
//                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_PICTURE)),
//                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_URL)));
//                listContact.add(cp);
                ContactPersonService cPerson = diComponent.provideContactPersonService();

                cPerson.addNewContactPerson(c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_ID)),
                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_LAST_NAME)),
                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_PICTURE)),
                        c.getString(c.getColumnIndex(ContactContract.Contact.COLUMN_NAME_CONTACT_URL)));
                listContact.add(cPerson.getContactPerson());
            }while (c.moveToNext());
        }

        return  listContact;

    }
}
