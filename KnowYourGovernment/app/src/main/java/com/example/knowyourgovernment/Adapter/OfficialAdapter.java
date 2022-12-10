package com.example.knowyourgovernment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.knowyourgovernment.R;
import java.util.List;

import com.example.knowyourgovernment.Model.Official;

public class OfficialAdapter extends BaseAdapter {
    private Context context;
    private List<Official> list;

    public OfficialAdapter(Context context, List<Official> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int iPosition) {
        return list.get(iPosition);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_custom, null);
        }

        TextView tvChucVu = view.findViewById(R.id.tvChucVu);
        TextView tvTenVaDang = view.findViewById(R.id.tvTenVaDang);


        Official official = list.get(i);
        tvChucVu.setText(official.getTitle());
        tvTenVaDang.setText(official.getName()+" "+official.getParty());
        return view;
    }
}
