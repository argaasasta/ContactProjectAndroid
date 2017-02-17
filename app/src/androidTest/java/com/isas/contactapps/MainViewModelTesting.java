package com.isas.contactapps;

/**
 * Created by EB-NB21 on 2/16/2017.
 */
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.model.ContactPerson;
import com.isas.contactapps.viewmodel.MainViewModel;

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
public class MainViewModelTesting {

    MainViewModel mainViewModel;
    @Before
    public void setUp(){
        mainViewModel =  new MainViewModel(InstrumentationRegistry.getTargetContext(),new ApiInteractorImpl(), AndroidSchedulers.mainThread());
    }

    @Test
    public void testGetDataAPI() {
        assertNotNull(mainViewModel.getListContact());
    }

    @Test
    public void testSaveData() {
        ContactPerson[] cpList= {new ContactPerson("222","aaa","bbb","http://","url")};
        mainViewModel.saveDataToDatabase(cpList);
    }
}
