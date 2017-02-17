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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by EB-NB21 on 2/14/2017.
 */

public class ListContactsAdapter extends BaseAdapter {
    ArrayList<ContactPerson> contactPerson;
    static class ViewHolder {
        @InjectView(R.id.tv_contact_list_alphabet2)
        TextView tvAlphabet;
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

    public ListContactsAdapter(Context mContext, ArrayList<ContactPerson> contactPerson) {
        this.mContext = mContext;
        this.contactPerson = contactPerson;
    }

    @Override
    public int getCount() {
        return contactPerson.size();
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
                .load(contactPerson.get(position).getProfilePic())
                .placeholder(R.drawable.placeholder_people)
                .error(R.drawable.placeholder_people)
                .into(holder.ivPictureContact);
        holder.tvNameContact.setText(contactPerson.get(position).getFullname());
        if(position>0){
            if(contactPerson.get(position).getFullname().charAt(0)!=contactPerson.get(position-1).getFullname().charAt(0)){
                holder.tvAlphabet.setVisibility(View.VISIBLE);
                holder.tvAlphabet.setText(""+Character.toUpperCase(contactPerson.get(position).getFullname().charAt(0)));
            }else{
                holder.tvAlphabet.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.tvAlphabet.setVisibility(View.VISIBLE);
            holder.tvAlphabet.setText(""+Character.toUpperCase(contactPerson.get(position).getFullname().charAt(0)));
        }


        return view;
    }


}
