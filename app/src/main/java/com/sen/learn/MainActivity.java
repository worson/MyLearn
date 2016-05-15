package com.sen.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sen.view.AndroidViewMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private List<Map<String,Object>> moduleListData;
    private String[] mModuleTitles = new String[]{"设计模式","安卓交互"};
    private int[] mModuleIconIDs = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private Class[] mActivityArray = new Class[]{com.sen.designmode.Collection.one.view.DisignModeMainActivity.class,AndroidViewMainActivity.class};

//    private Activity[] moduleActivitys = new Activity[]{com.sen.designmode.Collection.one.view.DisignModeMainActivity.class};
    private ListView moduleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
    }

    public void initView(){
        initModuleView();
    }

    public void initModuleView(){
        moduleListData=new ArrayList<Map<String,Object>>();
        for (int i = 0; i <mActivityArray.length ; i++) {
            Map<String,Object> map1=new HashMap<String, Object>();
            map1.put("nametext", mModuleTitles[i]);
            map1.put("iconid",  mModuleIconIDs[i]);
            moduleListData.add(map1);
        }
        moduleListView = (ListView)findViewById(R.id.module_listview);
        SimpleAdapter adapter = new SimpleAdapter(this,moduleListData,R.layout.main_module_item, new String[]{"nametext","iconid"},new int[]{R.id.module_item_title,R.id.module_item_icon});
        moduleListView.setAdapter(adapter);
        moduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, mActivityArray[position]);
                startActivity(intent);
            }
        });
    }
}
