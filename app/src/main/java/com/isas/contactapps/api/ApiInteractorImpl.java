package com.isas.contactapps.api;

import com.isas.contactapps.model.ContactPerson;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ocittwo on 10/30/16.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */
public class ApiInteractorImpl implements ApiInteractor {

    private ApiServices apiServices;

    public ApiInteractorImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gojek-contacts-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiServices = retrofit.create(ApiServices.class);
    }

    @Override
    public Observable<ContactPerson[]> getListContact() {
        return apiServices.getListContact().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ContactPerson> getContact(String id) {
        return apiServices.getContact(id).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ContactPerson> postContact(HashMap<String, Object> params) {
        return apiServices.postContact(params).subscribeOn(Schedulers.io());
    }
}
