package com.sen.view.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sen.view.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hud.haliai.com.share.utils.HaloLogger;


/*
* 支持界面类型
* 1、单行列表（对齐方向），自带长度游标，底部图标，
* 2、双列标题，优先填充设置，
* */
public class CardView extends RelativeLayout {

    private static final String TAG = CardView.class.getSimpleName();

    public static final String CONTENT_KEY = "cardview_content";
    public static final String ICON_KEY = "cardview_icon";
    public static final String TITLE_ALIGN = "TITLE_ALIGN";
    public static final String LAYOUT_RESOURCE = "LAYOUT_RESOURCE";

    private static final int DEFAULT_TEXT_SIZE = 26;

    private Context mContext;

    private boolean mUselistview = false;
    private boolean mIsNeddScaleLine = false;

    //View
    private ViewGroup mMainview;
    private View mScaleLine;
    private ImageView mCardIcon;
    private View mResourceInflateView;

    private boolean mMainviewRemoved = false;
    private List<TextView> mTextViewList;
    private static final int[] TextViewIDS = new int[]{R.id.cardview_textview_1, R.id.cardview_textview_2, R.id.cardview_textview_3};
    private static final int[] InflateTextViewIDS = new int[]{R.id.cardview_textview_1, R.id.cardview_textview_2, R.id.cardview_textview_3};

    private void initResource() {
        mTextViewList = new ArrayList<>();
    }

    private void initView(Context context) {
        mMainview = (ViewGroup) inflate(context, R.layout.cardview, this);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        mMainview = (ViewGroup) inflater.inflate(R.layout.cardview, null);
        mScaleLine = (View) findViewById(R.id.scale_view);
        mCardIcon = (ImageView) findViewById(R.id.icon_imageview);
        mTextViewList.clear();
        for (int i = 0; i < TextViewIDS.length; i++) {
            TextView textView = (TextView) findViewById(TextViewIDS[i]);
            mTextViewList.add(textView);
        }
    }


    public CardView(Context context) {
        super(context);
        mContext = context;
        initResource();
        initView(context);
    }


    public void update(Map<String, Object> cotentMap) {
        List<String> titleList = (List<String>) cotentMap.get(CONTENT_KEY);
        Integer iconResource = (Integer) cotentMap.get(ICON_KEY);
        Integer titleAlign = (Integer) cotentMap.get(TITLE_ALIGN);
        Integer layoutResource = (Integer) cotentMap.get(LAYOUT_RESOURCE);
        HaloLogger.logI(TAG, "titleList is " + titleList);

        if (layoutResource != null && layoutResource != 0) {
            setLayout(layoutResource,cotentMap);
            return;//直接返回
        }
        if(mResourceInflateView !=null){
            HaloLogger.logI(TAG,"update,clear inflate resoure view");
            mResourceInflateView.setVisibility(INVISIBLE);
            removeView(mResourceInflateView);
        }
        mMainview.setVisibility(VISIBLE);

        HaloLogger.logI(TAG, "有图标，单列类型");
        if (titleList == null || titleList.size() <= 0) {
            return;
        }
        int maxText = Math.min(titleList.size(), TextViewIDS.length);
        for (int i = 0; i < mTextViewList.size(); i++) {
            TextView view = mTextViewList.get(i);
            if (i < maxText) {
                view.setVisibility(VISIBLE);
                if (titleAlign != null){
                    LayoutParams textParams = (LayoutParams)view.getLayoutParams();
                    if (ALIGN_PARENT_LEFT == titleAlign){
                        textParams.addRule(RIGHT_OF,mScaleLine.getId());
                    }else {
                        textParams.addRule(titleAlign);
                    }
                    view.setLayoutParams(textParams);
                }
                view.setText(titleList.get(i));
            } else {
                view.setVisibility(INVISIBLE);
                view.setText("");
            }
        }
        LayoutParams scaleLineParams = (LayoutParams) mScaleLine.getLayoutParams();
        scaleLineParams.addRule(ALIGN_TOP, TextViewIDS[0]);
        scaleLineParams.addRule(ALIGN_BOTTOM, TextViewIDS[maxText - 1]);
        mScaleLine.setLayoutParams(scaleLineParams);
        if (iconResource != null) {
            mCardIcon.setBackgroundResource(iconResource);
        }else {
            mCardIcon.setVisibility(GONE);
        }

    }

    public void setLayout(int resource , Map<String,Object> cotentMap) {
        List<String> titleList = (List<String>) cotentMap.get(CONTENT_KEY);

        mMainview.setVisibility(GONE);
        //清除上一次残留
        if (mResourceInflateView != null) {
            HaloLogger.logI(TAG,"setLayout,clear inflate");
            mResourceInflateView.setVisibility(GONE);
            removeView(mResourceInflateView);
        }
        mResourceInflateView = inflate(mContext,resource,this);
        if (mResourceInflateView == null) {
            HaloLogger.logI(TAG,"setLayout,inflate is null");
            return;
        }
        HaloLogger.logI(TAG,"setLayout 文件显示");
        for (int i = 0; i <titleList.size() ; i++) {
            TextView textView = (TextView) mResourceInflateView.findViewById(InflateTextViewIDS[i]);
            textView.setText(titleList.get(i));
        }

    }


}
