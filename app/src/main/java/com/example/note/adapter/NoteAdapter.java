package com.example.note.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.note.R;
import com.example.note.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter implements Filterable {

    private Context mContext;//上下文

    private List<Note> backList;//用来备份原始数据
    private List<Note> noteList;//当前的笔记
    private MyFilter myFilter;

    public NoteAdapter(Context mContext, List<Note> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
        backList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.DayTheme);
        //定义视图，获取组件
        View view = View.inflate(mContext, R.layout.note_layout,null);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_time = view.findViewById(R.id.tv_time);

        //获取文本内容，并赋值
        String allText = noteList.get(position).getContent();
        String time = noteList.get(position).getTime();
        tv_content.setText(allText);
        tv_time.setText(time);

        //保存笔记的主键
        view.setTag(noteList.get(position).getId());
        return view;
    }

    @Override
    public Filter getFilter() {
        if(myFilter==null){
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    class MyFilter extends Filter {
        //定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Note> list;
            //当过滤的关键字为空的时候，显示所有的数据
            if (TextUtils.isEmpty(constraint)) {
                list = backList;
            } else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (Note note : backList) {
                    //判断note。getContent（）中是否包含constraint
                    if (note.getContent().contains(constraint)) {
                        list.add(note);
                    }
                }
            }
            results.values = list;//将得到的集合保存到FilterResults的values变量中
            results.count = list.size();//将集合的大小保存到FilterResults的count变量中
            return results;
        }

        //让适配器更新界面
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteList = (List<Note>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();//通知数据发生了改变            }
            } else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }
}