package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import rx.Observable;

/**
 * Created by EB-NB21 on 2/16/2017.
 */
public interface ApiInteractor {
    Observable<ContactPerson[]> getListContact();
    Observable<ContactPerson> getContact(String id);
    Observable<ContactPerson> postContact(HashMap<String, Object> params);
    Observable<ContactPerson> postContactWithImage(String first_name,
                                                   String last_name,
                                                   String email,
                                                   String phone_number,
                                                   File profile_picture);
}
