package com.isas.contactapps.api;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

import com.isas.contactapps.model.ContactPerson;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final ApiServices apiServices;

    public Service(ApiServices apiServices) {
        this.apiServices = apiServices;
    }

    public Subscription getCityList(final GetCityListCallback callback) {

        return apiServices.getListContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ContactPerson[]>>() {
                    @Override
                    public Observable<? extends ContactPerson[]> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ContactPerson[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ContactPerson[] listContactPerson) {
                        callback.onSuccess(listContactPerson);

                    }
                });
    }

    public interface GetCityListCallback{
        void onSuccess(ContactPerson[] listContactPerson);

        void onError(NetworkError networkError);
    }
}
