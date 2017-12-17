package com.example.saglik.redcarpetapp.UnusedClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.saglik.redcarpetapp.R;

import java.util.List;

/**
 * Created by SAGLIK on 17-Dec-17.
 */

public class CustomAdapter extends BaseAdapter{
    Context context;
    List<RowItem> rowItems;
    CustomAdapter(Context context, List<RowItem> rowItems){
        this.context=context;
        this.rowItems=rowItems;
    }


    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder{
        ImageView partyPic;
        TextView partyName;
        TextView partyLocation;
        Switch partyCheckIn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder=null;
        LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();

            holder.partyName=(TextView)convertView.findViewById(R.id.party_name);
            holder.partyPic=(ImageView) convertView.findViewById(R.id.party_pic);
            holder.partyLocation=(TextView)convertView.findViewById(R.id.party_location);
            holder.partyCheckIn=(Switch)convertView.findViewById(R.id.party_checkin);

            RowItem row_pos = rowItems.get(position);
            holder.partyPic.setImageResource(row_pos.getPartyImageID());
            holder.partyName.setText(row_pos.getPartyName());
            holder.partyLocation.setText(row_pos.getPartyLocation());
            holder.partyCheckIn.setChecked(row_pos.isPartyCheckIn());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;

    }
}
