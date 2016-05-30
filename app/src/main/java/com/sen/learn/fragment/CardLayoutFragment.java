package com.sen.learn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sen.learn.R;
import com.sen.view.widget.CardLayout;
import com.sen.view.widget.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hud.haliai.com.share.utils.DisplayUtil;
import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardLayoutFragment extends Fragment  implements View.OnClickListener{
    private static String TAG = CardLayoutFragment.class.getName();

    private ViewGroup mMainView;
    private CardLayout mCardLayout;
    private int[] buttonsID = new int[]{R.id.cardlayout_button_one,R.id.cardlayout_button_three,R.id.cardlayout_button_four,R.id.cardlayout_button_row};

    private int[] mCommandsId = new int[]{R.array.prompt_navi_demo_array,R.array.prompt_music_demo_array,R.array.prompt_call_demo_array,R.array.prompt_recoder_demo_array};
    private int[] m3rdCallSpeechMenuCmd = new int[]{R.array.speech_3rd_call_menu};


    private List<Map<String,Object>> mContentList;

    private void initCardContent(List<Map<String,Object>> contentList,int length){
        if(length<0 || contentList == null) return;
        if(contentList.size()>0){
            contentList.clear();
        }
        List<String> titleList = new ArrayList<>();
        if(length==4){
            int[] aligns = new int[]{CardView.ALIGN_PARENT_RIGHT,CardView.ALIGN_PARENT_LEFT,CardView.ALIGN_PARENT_LEFT,CardView.ALIGN_PARENT_LEFT};
            for (int i = 0; i <length ; i++) {
                Map mapContent = new HashMap<String,Object>();
                String[] strCmds = getResources().getStringArray(mCommandsId[i]);
                List<String> cmdList =  Arrays.asList(strCmds);
                mapContent.put(CardView.CONTENT_KEY,cmdList);
                mapContent.put(CardView.ICON_KEY,new Integer(R.drawable.cardview_icon_music));
                mapContent.put(CardView.TITLE_ALIGN,aligns[i]);
                contentList.add(mapContent);

            }
        }else if(length==3){
            int[] aligns = new int[]{CardView.ALIGN_PARENT_RIGHT,CardView.ALIGN_PARENT_LEFT,CardView.ALIGN_PARENT_LEFT,CardView.ALIGN_PARENT_LEFT};
            for (int i = 0; i <3 ; i++) {
                Map mapContent = new HashMap<String,Object>();
                String[] strCmds = getResources().getStringArray(mCommandsId[i]);
                List<String> cmdList =  Arrays.asList(strCmds);
                mapContent.put(CardView.CONTENT_KEY,cmdList);
                mapContent.put(CardView.ICON_KEY,new Integer(R.drawable.cardview_icon_music));
                mapContent.put(CardView.TITLE_ALIGN,aligns[i]);
                contentList.add(mapContent);

            }
        }else if(length==1){
            for (int i = 0; i <1 ; i++) {
                Map mapContent = new HashMap<String,Object>();
                String[] strCmds = getResources().getStringArray(m3rdCallSpeechMenuCmd[0]);
                List<String> cmdList =  Arrays.asList(strCmds);
                mapContent.put(CardView.CONTENT_KEY,cmdList);
                mapContent.put(CardView.LAYOUT_RESOURCE,R.layout.speech_3rd_call_menu);
                contentList.add(mapContent);

            }
        }


    }

    public CardLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = (ViewGroup)inflater.inflate(R.layout.fragment_card_layout, container, false);
        for (int i = 0; i <buttonsID.length ; i++) {
            Button button = (Button)mMainView.findViewById(buttonsID[i]);
            button.setOnClickListener(this);
        }

//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(DisplayUtil.dip2px(this,465),DisplayUtil.dip2px(this,113));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getContext(),113));
        mCardLayout = new CardLayout(getContext());
        mCardLayout.setLayoutParams(params);
        mMainView.addView(mCardLayout);
        mContentList = new ArrayList<Map<String,Object>>();

        return mMainView;
    }

    private void setCard(int length){
        HaloLogger.logI(TAG,"setCard");
        initCardContent(mContentList,length);
        mCardLayout.setContent(mContentList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardlayout_button_one:
                setCard(1);
                break;
            case R.id.cardlayout_button_three:
                setCard(3);
                break;
            case R.id.cardlayout_button_four:
                setCard(4);
                break;
            case R.id.cardlayout_button_row:
                setCard(1);
                break;
            default:
                break;
        }

    }

}
