package com.isas.contactapps.model;

import com.isas.contactapps.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by EB-NB21 on 2/17/2017.
 */

@Singleton
@Component(modules = {ContactPersonModule.class})
public interface DiComponent {

    ContactPersonService provideContactPersonService();

}
