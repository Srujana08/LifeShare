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

public class RequestAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    ArrayList<String> ReqBloodGroup;
    ArrayList<String> ReqDate;
    public RequestAdapter(
            Context context2,
            ArrayList<String> bg,
            ArrayList<String> rd
    )
    {

        this.context = context2;
        this.ReqBloodGroup = bg;
        this.ReqDate = rd;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return ReqBloodGroup.size();
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

        RequestAdapter.Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.reqviewdatalayout, null);

            holder = new RequestAdapter.Holder();

            holder.textviewbg = (TextView) child.findViewById(R.id.textViewBG);
            holder.textviewrd = (TextView) child.findViewById(R.id.textViewRD);
            child.setTag(holder);

        } else {

            holder = (RequestAdapter.Holder) child.getTag();
        }
        holder.textviewbg.setText(ReqBloodGroup.get(position));
        holder.textviewrd.setText(ReqDate.get(position));

        return child;
    }

    public class Holder {
        TextView textviewbg;
        TextView textviewrd;
    }
}
