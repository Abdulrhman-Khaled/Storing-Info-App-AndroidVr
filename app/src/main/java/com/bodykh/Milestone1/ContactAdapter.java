package com.bodykh.Milestone1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> implements Filterable {

    Context context;
    int resource;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, parent, false  );
        TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
        TextView tvPhone = (TextView)convertView.findViewById(R.id.tvPhone);
        ImageView imgUser = (ImageView) convertView.findViewById(R.id.imgUser);

        Contact currentContact = getItem(position);

        tvName.setText(currentContact.getName());
        tvPhone.setText(String.valueOf(currentContact.getPhone()));

        Bitmap bitmap = BitmapFactory.decodeByteArray(currentContact.getImage(), 0, currentContact.getImage().length);
        imgUser.setImageBitmap(bitmap);


        return convertView;
    }
}
