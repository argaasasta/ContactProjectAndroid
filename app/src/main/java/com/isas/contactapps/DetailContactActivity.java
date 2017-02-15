package com.isas.contactapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class DetailContactActivity extends AppCompatActivity {
    @InjectView(R.id.iv_prof_pic_contact) ImageView ivPictureContact;
    @InjectView(R.id.iv_fav_contact) ImageView ivFavContact;
    @InjectView(R.id.tv_name_contact) TextView tvNameContact;
    @InjectView(R.id.layout_phone_number_contact) LinearLayout layoutPhoneContact;
    @InjectView(R.id.tv_phone_number_contact) TextView tvPhoneContact;
    @InjectView(R.id.layout_email_contact) LinearLayout layoutEmailContact;
    @InjectView(R.id.tv_email_contact) TextView tvEmailContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        Glide.with(getApplicationContext())
                .load("lala").asBitmap().centerCrop()
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

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
