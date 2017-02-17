package com.isas.contactapps.viewmodel;

import com.isas.contactapps.api.ApiInteractor;
import com.isas.contactapps.model.ContactPerson;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by EB-NB21 on 2/16/2017.
 */

public class DetailContactViewModel {
    private ApiInteractor interactor;
    private Scheduler scheduler;

    public DetailContactViewModel(ApiInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }

    public Observable<ContactPerson> getContact(String id){
        return interactor.getContact(id).observeOn(scheduler);
    }
}
