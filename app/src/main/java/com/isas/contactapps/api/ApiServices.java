package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import java.io.File;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

public interface ApiServices {
    @GET("contacts.json")
    Observable<ContactPerson[]> getListContact();

    @POST("/contacts")
    @FormUrlEncoded
    Observable<String> savePost(@Field("contact[first_name]") String fName,
                                       @Field("contact[last_name]") String lName,
                                       @Field("contact[email]") String email,
                                       @Field("contact[phone_number]") String phone);

    @FormUrlEncoded
    @POST("/contacts")
    Call<ResponseBody> postContact(@FieldMap HashMap<String, Object> params);

}
