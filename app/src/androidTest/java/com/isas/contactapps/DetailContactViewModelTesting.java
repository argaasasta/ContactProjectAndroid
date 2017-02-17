package com.isas.contactapps;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.viewmodel.DetailContactViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.android.schedulers.AndroidSchedulers;

import static org.junit.Assert.assertNotNull;

/**
 * Created by EB-NB21 on 2/17/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DetailContactViewModelTesting {

    DetailContactViewModel detailContactViewModel;
    @Before
    public void setUp(){
        detailContactViewModel =  new DetailContactViewModel(new ApiInteractorImpl(), AndroidSchedulers.mainThread());
    }

    @Test
    public void testGetDataAPI() {
        assertNotNull(detailContactViewModel.getContact("20"));
    }
}
