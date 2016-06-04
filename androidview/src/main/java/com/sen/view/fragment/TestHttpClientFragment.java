package com.sen.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sen.view.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestHttpClientFragment extends Fragment {


    public TestHttpClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_http_client, container, false);
    }
    private void sendRequestWithHttpClient() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    // 指定访问的服务器地址是电脑本机
                    HttpGet httpGet = new HttpGet(
                            "http://10.0.2.2/get_data.json");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        // 请求和响应都成功了
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");
                        parseJSONWithGSON(response);
                        // parseJSONWithJSONObject(response);
                        // parseXMLWithPull(response);
                        // parseXMLWithSAX(response);
                        // Message message = new Message();
                        // message.what = SHOW_RESPONSE;
                        // // 将服务器返回的结果存放到Message中
                        // message.obj = response.toString();
                        // handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

}
