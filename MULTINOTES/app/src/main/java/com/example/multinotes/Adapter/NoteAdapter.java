package com.example.multinotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.multinotes.R;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.multinotes.Model.Note;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;

    public NoteAdapter(Context context, List<Note> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.layout_note_item, null);
            }

            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtTime = view.findViewById(R.id.txtTime);
            TextView edtContent_item = view.findViewById(R.id.edtContent_item);

            Note note = list.get(i);
            txtTitle.setText(note.getTitle());
            SimpleDateFormat sdf = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sdf = new SimpleDateFormat("dd/MM/YYYY, HH:mm:ss");
            }
            txtTime.setText(sdf.format(note.getTime()));
            edtContent_item.setText(note.getContent());

            return view;
    }
}
