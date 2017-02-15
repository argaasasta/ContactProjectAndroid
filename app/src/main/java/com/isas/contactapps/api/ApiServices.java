package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

public interface ApiServices {
    @GET("contacts.json")
    Observable<ContactPerson[]> getListContact();

}
