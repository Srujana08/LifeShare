package com.microsoft.bvrith.lifesharefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SrujanaParupudi on 3/11/2017.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    ArrayList<String> DonorName;
    ArrayList<String> Donor_PhoneNumber;
    public CustomAdapter(
            Context context2,
            ArrayList<String> name,
            ArrayList<String> phone
    )
    {

        this.context = context2;
        this.DonorName = name;
        this.Donor_PhoneNumber = phone;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return DonorName.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);

            holder = new Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.textViewNAME);
            holder.textviewphone_number = (TextView) child.findViewById(R.id.textViewPHONE_NUMBER);
            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewname.setText(DonorName.get(position));
        holder.textviewphone_number.setText(Donor_PhoneNumber.get(position));

        return child;
    }

    public class Holder {
        TextView textviewname;
        TextView textviewphone_number;
    }
}
