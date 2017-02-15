package com.isas.contactapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.isas.contactapps.R;
import com.isas.contactapps.model.ContactPerson;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

public class ListContactsAdapter extends BaseAdapter {
    ContactPerson [] contactPerson;
    static class ViewHolder {
        @InjectView(R.id.iv_contact_list_alphabet)
        ImageView ivAlphabet;
        @InjectView(R.id.iv_contact_list_picture)
        ImageView ivPictureContact;
        @InjectView(R.id.tv_contact_list_name)
        TextView tvNameContact;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    Context mContext;
    int dummy;

    public ListContactsAdapter(Context mContext, ContactPerson [] contactPerson) {
        this.mContext = mContext;
        this.contactPerson = contactPerson;
    }

    @Override
    public int getCount() {
        return contactPerson.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_contact, viewGroup, false);
        holder = new ViewHolder(view);

        Glide.with(mContext)
                .load(contactPerson[position].getProfile_pic())
                //placeholder berfungsi menampilkan gambar sebelum dimuat
                .placeholder(R.drawable.placeholder_people)
                //error menampilkan gambar jika terjadi eror saat load gambar dari url
                .error(R.drawable.placeholder_people)
                .into(holder.ivPictureContact);
        System.out.println("KOK NAMANYA "+contactPerson[position].getFirst_name());
        holder.tvNameContact.setText(contactPerson[position].getFirst_name()+" "+contactPerson[position].getLast_name());

        return view;
    }


}
