package com.isas.contactapps.viewmodel;

import com.isas.contactapps.api.ApiInteractor;
import com.isas.contactapps.model.ContactPerson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by EB-NB21 on 2/16/2017.
 */

public class AddContactViewModel {
    private ApiInteractor interactor;
    private Scheduler scheduler;

    public AddContactViewModel(ApiInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }

    public Observable<ContactPerson> postContact(HashMap<String, Object> params) {
        return interactor.postContact(params).observeOn(scheduler);
    }

    public Observable<ContactPerson> postContactWithPicture(String first_name, String last_name, String email, String phone_number, File profile_picture) {
        return interactor.postContactWithImage(first_name,last_name,email,phone_number,profile_picture).observeOn(scheduler);
    }

    public String cekContactComponent(String fName, String lName, String email, String phoneNumber) {
        String caution = "";
        if (fName.equals("")) {
            caution += " " + "First Name";
        }
        if (lName.equals("")) {
            caution += " " + "Last Name";
        }

        if (email.equals("")) {
            caution += " " + "Email";
        }
        String con0="", con62="", conPlus62="";
        if (phoneNumber.equals("")) {
            caution += " Phone Number ";
        } else if (phoneNumber.length() > 5) {
            con0 = phoneNumber.substring(0, 1);
            con62 = phoneNumber.substring(0, 2);
            conPlus62 = phoneNumber.substring(0, 3);
        }
        if(!caution.equals("")) {
            caution = "Please fill "+caution;
        } else if (!con0.equals("0") && !con62.equals("62") && !conPlus62.equals("+62")) {
            caution +=" Phone number must use format +62XX/62XX/08XX ";

        }
        return caution;
    }
}
