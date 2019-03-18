package com.example.deopendagapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Workshoplistadapter extends BaseAdapter {
    private Context mContext;
    private List<Workshops> mWorkshoplist;

    public Workshoplistadapter(Context mContext, List<Workshops> mWorkshoplist) {
        this.mContext = mContext;
        this.mWorkshoplist = mWorkshoplist;
    }

    @Override
    public int getCount() {
        return mWorkshoplist.size();
    }

    @Override
    public Object getItem(int i) {
        return mWorkshoplist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.custom_listview, null);
        TextView textview_subject = (TextView)v.findViewById(R.id.textview_subject);
        TextView textview_study = (TextView)v.findViewById(R.id.textview_study);
        TextView textview_roomcode = (TextView)v.findViewById(R.id.textview_roomcode);
        TextView textview_starttime = (TextView)v.findViewById(R.id.textview_starttime);
        TextView textview_timeleft = (TextView)v.findViewById(R.id.textview_timeleft);

        textview_subject.setText(mWorkshoplist.get(i).getSubject());
        textview_study.setText(mWorkshoplist.get(i).getStudy());
        textview_roomcode.setText(mWorkshoplist.get(i).getRoomcode());
        textview_starttime.setText(mWorkshoplist.get(i).getStarttime());
        textview_timeleft.setText("Start over " + mWorkshoplist.get(i).getTimeleft());

        return v;
    }
}
