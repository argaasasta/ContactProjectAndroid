package com.isas.contactapps.model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by EB-NB21 on 2/17/2017.
 */


@Module
public class ContactPersonModule {

    @Provides
    public ContactPerson provideContactPerson() {
        return new ContactPerson();
    }

    @Provides
    public ContactPersonService provideContactPersonService() {
        return new ContactPersonService(new ContactPerson());
    }

}

