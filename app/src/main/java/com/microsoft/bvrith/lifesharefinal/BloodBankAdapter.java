package com.microsoft.bvrith.lifesharefinal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SrujanaParupudi on 3/11/2017.
 */

public class BloodBankAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    ArrayList<String> BBName;
    ArrayList<String> BBAddress;
    ArrayList<String> BBPhone;
    public BloodBankAdapter(
            Context context2,
            ArrayList<String> name,
            ArrayList<String> address,
            ArrayList<String> phone
    )
    {

        this.context = context2;
        this.BBName = name;
        this.BBAddress = address;
        this.BBPhone = phone;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return BBName.size();
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

        BloodBankAdapter.Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.bbviewdatalayout, null);

            holder = new BloodBankAdapter.Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.textViewName);
            holder.textviewaddress = (TextView) child.findViewById(R.id.textViewAddress);
            holder.textviewphone = (TextView) child.findViewById(R.id.textViewPhone);

            child.setTag(holder);

        } else {

            holder = (BloodBankAdapter.Holder) child.getTag();
        }
        holder.textviewname.setText(BBName.get(position));
        holder.textviewaddress.setText(BBAddress.get(position));
        holder.textviewphone.setText(BBPhone.get(position));

        return child;
    }

    public class Holder {
        TextView textviewname;
        TextView textviewaddress;
        TextView textviewphone;
    }
}
