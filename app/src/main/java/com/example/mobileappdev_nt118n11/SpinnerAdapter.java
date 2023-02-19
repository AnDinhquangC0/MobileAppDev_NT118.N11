package com.example.mobileappdev_nt118n11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileappdev_nt118n11.Model.TypeFood;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<TypeFood> {
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TypeFood> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_selected,parent,false);
        TextView tvType = convertView.findViewById(R.id.tv_selected);

        TypeFood type = this.getItem(position);
        if (type != null)
            tvType.setText(type.getName());
        else
            Toast.makeText(parent.getContext(), "Type getview null",Toast.LENGTH_LONG).show();
        return convertView;
    }

    @Override
    public int getPosition(@Nullable TypeFood item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public TypeFood getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodtype,parent,false);
        TextView tvType = convertView.findViewById(R.id.tv_item_type);

        TypeFood type = this.getItem(position);
        if (type != null)
            tvType.setText(type.getName());
        else
            Toast.makeText(parent.getContext(), "Type DropDown null",Toast.LENGTH_LONG).show();
        return convertView;
    }

    @Override
    public void add(@Nullable TypeFood object) {
        super.add(object);
    }
}
