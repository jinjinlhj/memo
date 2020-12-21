package com.example.lhj20173155;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;
    public ListAdapter(ArrayList<ItemData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }
    @Override
    public int getCount() {
        Log.i("TAG","getCount");
        return nListCnt;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if(inflater ==null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item,parent,false);

        }
        TextView oTextTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.textDate);
        TextView oTextContent = (TextView) convertView.findViewById(R.id.textContent);
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height=300;
        convertView.setLayoutParams(layoutParams);




        if(m_oData.get(position).strTitle.equals(""))
            oTextTitle.setText("제목이 없습니다.");
        else
            oTextTitle.setText(m_oData.get(position).strTitle);
        if(m_oData.get(position).strContent.equals(""))
            oTextContent.setText(" ");
        else
            oTextContent.setText(m_oData.get(position).strContent);
        oTextDate.setText(m_oData.get(position).strDate);

        return convertView;
    }
}
