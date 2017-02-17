package com.isas.contactapps;

/**
 * Created by EB-NB21 on 2/16/2017.
 */
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.viewmodel.AddContactViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.android.schedulers.AndroidSchedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddContactViewModelTesting {

    AddContactViewModel addContactViewModel;
    @Rule
    public ActivityTestRule<AddContactActivity> activityRule = new ActivityTestRule<>(AddContactActivity.class);

    @Before
    public void setUp(){
        addContactViewModel =  new AddContactViewModel(new ApiInteractorImpl(), AndroidSchedulers.mainThread());
    }

    @Test
    public void testCekKomponen() {
        assertNotNull(addContactViewModel.cekContactComponent("1","123","123","123"));
    }

    @Test
    public void testUIResult() {
        onView(withId(R.id.ed_fname_add_contact)).perform(typeText("aaa"), closeSoftKeyboard());
        onView(withId(R.id.ed_lname_add_contact)).perform(typeText("bbb"), closeSoftKeyboard());
        onView(withId(R.id.ed_phone_add_contact)).perform(typeText("0893939293"), closeSoftKeyboard());
        onView(withId(R.id.ed_email_add_contact)).perform(typeText("ccc"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_add_contact)).perform(click());
    }
}
