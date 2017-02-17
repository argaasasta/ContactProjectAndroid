package com.isas.contactapps;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.isas.contactapps.api.ApiInteractorImpl;
import com.isas.contactapps.model.ContactPerson;
import com.isas.contactapps.viewmodel.AddContactViewModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class AddContactActivity extends AppCompatActivity {

    @InjectView(R.id.iv_prof_pic_add_contact)
    ImageView ivPictureAddContact;
    @InjectView(R.id.ed_fname_add_contact)
    EditText edFNameAddContact;
    @InjectView(R.id.ed_lname_add_contact)
    EditText edLNameAddContact;
    @InjectView(R.id.ed_phone_add_contact)
    EditText edPhoneAddContact;
    @InjectView(R.id.ed_email_add_contact)
    EditText edEmailAddContact;
    @InjectView(R.id.btn_save_add_contact)
    Button btnSaveAddContact;


    public Uri mImageCaptureUri;
    private File sendPhoto;

    private CompositeSubscription subscription = new CompositeSubscription();
    private AddContactViewModel addContactViewModel;

    private static int CROP_IMAGE = 1;
    private static int ADD_IMAGE_FROM_GALLERY = 2;
    private static int ADD_IMAGE_FROM_CAMERA = 3;
    private static int CROP_IMAGE_FROM_CAMERA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);
        addContactViewModel = new AddContactViewModel(new ApiInteractorImpl(), AndroidSchedulers.mainThread());
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

    @OnClick(R.id.iv_prof_pic_add_contact)
    public void selectImage(View view) {
        if (view.getId() == R.id.iv_prof_pic_add_contact) {
            final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
            AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
            builder.setTitle(getString(R.string.add_photo));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (options[which].equals(getString(R.string.take_photo))) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), String.valueOf(System.currentTimeMillis() + ".jpg")));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        try {
                            startActivityForResult(intent, ADD_IMAGE_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }


                    } else if (options[which].equals(getString(R.string.choose_from_gallery))) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CROP_IMAGE);

                    } else if (options[which].equals(getString(R.string.cancel))) {
                        dialog.dismiss();
                    }
                }
            });

            builder.show();
        }
    }

    @OnClick(R.id.btn_save_add_contact)
    public void saveData(View view) {
        if (view.getId() == R.id.btn_save_add_contact) {
            String fName = edFNameAddContact.getText().toString();
            String lName = edLNameAddContact.getText().toString();
            String phone = edPhoneAddContact.getText().toString();
            String email = edEmailAddContact.getText().toString();
            saveData(fName, lName, email, phone, sendPhoto);
        }
    }

    public void saveData(String fName, String lName, String email, String phone, File image) {

        String messageResult = addContactViewModel.cekContactComponent(fName, lName, email, phone);
        if (messageResult.equals("")) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("contact[first_name]", fName);
            params.put("contact[last_name]", lName);
            params.put("contact[email]", email);
            params.put("contact[phone_number]", phone);
//        params.put("contact[profile_pic]", image);

            subscription.add(addContactViewModel.postContact(params)
                    .subscribe(new Observer<ContactPerson>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "your input is invalid", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        @Override
                        public void onNext(ContactPerson contactPerson) {
                            try{
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "input success", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }


                    }));
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), messageResult, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_IMAGE_FROM_CAMERA) {
            File destination = new File(getApplicationContext().getCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {

                ExifInterface ei = new ExifInterface(mImageCaptureUri.getPath());

                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
                Bitmap newBitmapRotate = null;

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        newBitmapRotate = rotateImage(thumbnail, 90);
                        Log.d("orientation 90", "masukBitMapCamera");
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        newBitmapRotate = rotateImage(thumbnail, 180);
                        Log.d("orientation 180", "masukBitMapCamera");
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        newBitmapRotate = rotateImage(thumbnail, 270);
                        Log.d("orientation 270", "masukBitMapCamera");
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                        Log.d("orientation normal", "masukBitMapCamera");
                    default:
                        newBitmapRotate = thumbnail;
                        break;
                }

                int nh = (int) (newBitmapRotate.getHeight() * (512.0 / newBitmapRotate.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(newBitmapRotate, 512, nh, true);

                destination.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(destination);
                fos.write(bitmapdata);
                bos.flush();
                bos.close();
                fos.flush();
                fos.close();

                ivPictureAddContact.setImageBitmap(newBitmapRotate);
//                Glide.with(getApplicationContext())
//                        .load(mImageCaptureUri).asBitmap().centerCrop()
//                        .placeholder(R.drawable.placeholder_people)
//                        .error(R.drawable.placeholder_people)
//                        .into(new BitmapImageViewTarget(ivPictureAddContact) {
//                            @Override
//                            protected void setResource(Bitmap resource) {
//                                RoundedBitmapDrawable circularBitmapDrawable =
//                                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
//                                circularBitmapDrawable.setCircular(true);
//                                ivPictureAddContact.setImageDrawable(circularBitmapDrawable);
//                            }
//                        });
                sendPhoto = destination;


            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.d("masukkk", "masukk Null Pointer");
                e.printStackTrace();
            } catch (OutOfMemoryError oom) {
                Log.w("TAG", "-- OOM Error in setting image");
            }

        } else if (requestCode == CROP_IMAGE) {
            mImageCaptureUri = data.getData();

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, ADD_IMAGE_FROM_GALLERY);
        } else if (requestCode == ADD_IMAGE_FROM_GALLERY) {
            Bundle extras = data.getExtras();

            Bitmap cropped = extras.getParcelable("data");
            ivPictureAddContact.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivPictureAddContact.setImageBitmap(cropped);

            File destination = new File(getApplicationContext().getCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {
                destination.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(destination);
                fos.write(bitmapdata);
                bos.flush();
                bos.close();
                fos.flush();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            sendPhoto = destination;

        }

    }
}
