package com.sen.internetlib.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sen.internetlib.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestOkhttpFragment extends Fragment {

    private static final String TAG = TestOkhttpFragment.class.getSimpleName();
    private static final String WSX = "wangshengxing";

    public TestOkhttpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        testOkhttp();
        return inflater.inflate(R.layout.fragment_test_okhttp, container, false);
    }

    private void testOkhttp() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=all&output=json&key=0640c12d64d3dd14c90ac5f4d9dfefa1")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                HaloLogger.logI(WSX,"okhttp 获取数据失败");
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                String htmlStr =  response.body().string();
                HaloLogger.logI(WSX,"okhttp 收到数据"+htmlStr);

            }
        });
    }

}
