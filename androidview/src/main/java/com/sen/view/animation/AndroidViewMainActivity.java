package com.sen.view.animation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sen.view.R;
import com.sen.view.TestWidgetActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidViewMainActivity extends AppCompatActivity {
    private Context mContext;
    private List<Map<String,Object>> moduleListData;
    private String[] mModuleTitles = new String[]{"动画 ","自定义View"};
    private Class[] mActivityArray = new Class[]{TestMenuAnimation.class, TestWidgetActivity.class};

    private ViewGroup mMainView;
    private ListView moduleListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //setContentView(R.layout.activity_android_view_main);
        moduleListView = new ListView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(moduleListView,params);
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
            moduleListData.add(map1);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,moduleListData,R.layout.main_module_item, new String[]{"nametext"},new int[]{R.id.module_item_title});
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
