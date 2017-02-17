package com.isas.contactapps;

/**
 * Created by EB-NB21 on 2/16/2017.
 */
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.viewmodel.AddContactViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.android.schedulers.AndroidSchedulers;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddContactViewModelTesting {

    AddContactViewModel addContactViewModel;
    @Before
    public void setUp(){
        addContactViewModel =  new AddContactViewModel(new ApiInteractorImpl(), AndroidSchedulers.mainThread());
    }

    @Test
    public void testCekKomponen() {
        assertNotNull(addContactViewModel.cekContactComponent("1","123","123","123"));
    }
}
