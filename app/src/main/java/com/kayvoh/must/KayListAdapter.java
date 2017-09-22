package com.kayvoh.must;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

/**
 * Created by Nderitu Kelvin on 2/8/2017.
 */

public class KayListAdapter extends BaseAdapter {
    private Context kContext;
    private List<KayList> kayList;

    public KayListAdapter(Context kContext, List<KayList> kayList) {
        this.kContext = kContext;
        this.kayList = kayList;
    }

    @Override
    public int getCount() {
        return kayList.size();
    }

    @Override
    public Object getItem(int position) {
        return kayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View kv = View.inflate(kContext, R.layout.customlisty, null);
        TextView tvName = (TextView)kv.findViewById(R.id.tvName);
        TextView tvRegNo = (TextView)kv.findViewById(R.id.tvRegNo);
        TextView tvStatus = (TextView)kv.findViewById(R.id.tvStatus);

        RelativeLayout custLayout = (RelativeLayout)kv.findViewById(R.id.custlayout);
        tvName.setText(kayList.get(position).getName());
        tvRegNo.setText(kayList.get(position).getRegNo());
        tvStatus.setText(kayList.get(position).getStatus());
        custLayout.setBackgroundColor(Color.rgb(180, 50, 50));
        String stato = tvStatus.getText().toString();
        if(Objects.equals(kayList.get(position).getStatus(), "Scanned")){
            custLayout.setBackgroundColor(Color.rgb(50, 180, 50));
        }

        kv.setTag(kayList.get(position).getName());
        return kv;
    }
}
