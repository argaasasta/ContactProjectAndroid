package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

public interface ApiServices {
    @GET("contacts.json")
    Observable<ContactPerson[]> getListContact();

    @GET("contacts/{id}.json")
    Observable<ContactPerson> getContact(@Path("id") String id);

    @FormUrlEncoded
    @POST("/contacts.json")
    Observable<ContactPerson> postContact(@FieldMap HashMap<String, Object> params);

}
