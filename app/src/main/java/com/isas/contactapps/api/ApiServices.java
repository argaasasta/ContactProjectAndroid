package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import java.io.File;
import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("/contacts.json")
    Observable<ContactPerson> postContactWithImage(@Part("contact[first_name]") String first_name,
                                                   @Part("contact[last_name]") String last_name,
                                                   @Part("contact[email]") String email,
                                                   @Part("contact[phone_number]") String phone_number,
                                                   @Part("contact[profile_pic]") File profile_picture);

}
