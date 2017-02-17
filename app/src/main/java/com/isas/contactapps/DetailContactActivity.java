package com.isas.contactapps;

import android.content.DialogInterface;
import android.content.Intent;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.databinding.ActivityDetailContactBinding;
import com.isas.contactapps.model.ContactPerson;
import com.isas.contactapps.viewmodel.DetailContactViewModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class DetailContactActivity extends AppCompatActivity {
    @InjectView(R.id.iv_prof_pic_contact) ImageView ivPictureContact;
    @InjectView(R.id.iv_fav_contact) ImageView ivFavContact;
    @InjectView(R.id.tv_name_contact) TextView tvNameContact;
    @InjectView(R.id.layout_phone_number_contact) LinearLayout layoutPhoneContact;
    @InjectView(R.id.tv_phone_number_contact) TextView tvPhoneContact;
    @InjectView(R.id.layout_email_contact) LinearLayout layoutEmailContact;
    @InjectView(R.id.tv_email_contact) TextView tvEmailContact;

    ActivityDetailContactBinding binding;
    private CompositeSubscription subscription = new CompositeSubscription();
    private DetailContactViewModel detailContactViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        String id = getIntent().getStringExtra("ID");
        detailContactViewModel = new DetailContactViewModel(new ApiInteractorImpl(),AndroidSchedulers.mainThread());
        getData(id);

    }

    public void getData(String id){
        subscription.add(detailContactViewModel.getContact(id)
                .subscribe(new Observer<ContactPerson>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ContactPerson contactPerson) {
                        binding.setUser(contactPerson);
                        Glide.with(getApplicationContext())
                                .load(contactPerson.getProfilePic()).asBitmap().centerCrop()
                                .placeholder(R.drawable.placeholder_people)
                                .error(R.drawable.placeholder_people)
                                .into(new BitmapImageViewTarget(ivPictureContact) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        ivPictureContact.setImageDrawable(circularBitmapDrawable);
                                    }
                                });
                    }

                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();break;
            case R.id.action_share:
                String shareBody =  tvNameContact.getText().toString()+" Phone : "+tvPhoneContact.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share"));break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_fav_contact)
    void addToFavContact(View view) {
        if (view.getId() == R.id.iv_fav_contact) {

            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.layout_phone_number_contact)
    void onClickPhoneNumber(View view) {
        if (view.getId() == R.id.layout_phone_number_contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Choose Action")
                    .setCancelable(true)
                    .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+tvPhoneContact.getText()));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Message", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                            smsIntent.setType("vnd.android-dir/mms-sms");
                            smsIntent.putExtra("address", tvPhoneContact.getText());
                            startActivity(Intent.createChooser(smsIntent, "SMS:"));
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    @OnClick(R.id.layout_email_contact)
    void onClickEmail(View view){
        if (view.getId() == R.id.layout_email_contact) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{tvEmailContact.getText().toString()});

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
    }
}
