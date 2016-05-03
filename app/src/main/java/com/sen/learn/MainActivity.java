package com.sen.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private List<Map<String,Object>> moduleListData;


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
        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("nametext", "设计模式");
        map1.put("iconid", R.mipmap.ic_launcher);

        Map<String,Object> map2=new HashMap<String, Object>();
        map2.put("nametext", "第二个功能");
        map2.put("iconid", R.mipmap.ic_launcher);


        Map<String,Object> map3=new HashMap<String, Object>();
        map3.put("nametext", "第三个功能");
        map3.put("iconid", R.mipmap.ic_launcher);

        Map<String,Object> map4=new HashMap<String, Object>();
        map4.put("nametext", "第四个功能");
        map4.put("iconid", R.mipmap.ic_launcher);

        Map<String,Object> map5=new HashMap<String, Object>();
        map5.put("nametext", "第五个功能");
        map5.put("iconid", R.mipmap.ic_launcher);

        moduleListData.add(map1);
        moduleListData.add(map2);
        moduleListData.add(map3);
        moduleListData.add(map4);
        moduleListData.add(map5);

        moduleListView = (ListView)findViewById(R.id.module_listview);
        SimpleAdapter adapter = new SimpleAdapter(this,moduleListData,R.layout.main_module_item, new String[]{"nametext","iconid"},new int[]{R.id.module_item_title,R.id.module_item_icon});
        moduleListView.setAdapter(adapter);
        moduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, com.sen.designmode.Collection.one.view.DisignModeMainActivity.class);
//                Intent intent = new Intent(mContext, DahuaMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
