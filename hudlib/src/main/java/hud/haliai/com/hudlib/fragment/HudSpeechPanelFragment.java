package hud.haliai.com.hudlib.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import hud.haliai.com.hudlib.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HudSpeechPanelFragment extends Fragment {


    private RelativeLayout mMainView;

    private LinearLayout mNaviPanel,mMusicPanel,mCallPanel,mNewMainPanel;
    private RelativeLayout mMainPanel;
    private RelativeLayout mCommonPanel;
    private ImageView mCommonPanelImageView;
    private RelativeLayout mMainNavingPanel; //导航时二级菜单

    //导航指示
    private ImageView mNaviInstructionImageview;
    private TextView mNaviDistanceTextView;

    /**
     * 语音菜单的枚举类型
     * */
    public enum PanelType{
        MainPanelType,
        NaviPanelType,
        MusicPanelType,
        CallPanelType,
        MainPanelTypeNavi,
        NaviPanelTypeCall,
        NaviExitType
    }


    private PanelType mCurrentPanelType = PanelType.MainPanelType;

//    private int[] mCommandsId = new int[]{R.array.prompt_navi_demo_array, R.array.prompt_music_demo_array, R.array.prompt_call_demo_array, R.array.prompt_recoder_demo_array};
//    private int[] mBackBackgrouds = new int[]{R.drawable.speech_main_panel,R.drawable.speech_navi_panel,R.drawable.speech_music_panel,R.drawable.speech_call_panel,R.drawable.speech_main_naving_panel,R.drawable.speech_exit_navi};
    private  final int UPGRADE_SPEECH_TEXT=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = (RelativeLayout)inflater.inflate(R.layout.fragment_hud_speech_panel, container, false);
        initView();
        upgradePanel(PanelType.MainPanelTypeNavi);
        return mMainView;
    }

    private void initView(){
        mMainPanel = (RelativeLayout)mMainView.findViewById(R.id.speech_main_panel_viewgroup);
        mMainNavingPanel = (RelativeLayout)mMainView.findViewById(R.id.speech_main_navi_panel_viewgroup);
        mNaviInstructionImageview = (ImageView) mMainNavingPanel.findViewById(R.id.direction_indicate_imageview);
        mNaviDistanceTextView = (TextView) mMainNavingPanel.findViewById(R.id.distance_indicate_textview);
        mCommonPanel = (RelativeLayout)mMainView.findViewById(R.id.speech_common_panel_viewgroup);
        mCommonPanelImageView = (ImageView) mCommonPanel.findViewById(R.id.common_panel_imageview);


/*        mNaviPanel = (LinearLayout)mMainView.findViewById(R.id.speech_navi_panel_viewgroup);
        mCallPanel = (LinearLayout)mMainView.findViewById(R.id.speech_call_panel_viewgroup);
        mMusicPanel = (LinearLayout)mMainView.findViewById(R.id.speech_music_panel_viewgroup);

        mNewMainPanel = (LinearLayout)mMainView.findViewById(R.id.new_speech_main_panel_viewgroup);*/

        /*for (int i = 0; i < mCommandsId.length; i++) {
            String[] strCmds = getActivity().getResources().getStringArray(mCommandsId[i]);
            List<String> cmdList =  Arrays.asList(strCmds);
            HudCardView mHudCardView = HudCardView.Builer.inflate(getActivity().getApplicationContext(),mNewMainPanel);
            mHudCardView.updateContent(cmdList);
//            ViewGroup.LayoutParams params  = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            View cardView = mHudCardView.getLayout();
            ViewGroup.LayoutParams params  = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mNewMainPanel.addView(cardView,params);
        }
        mNewMainPanel.setVisibility(View.INVISIBLE);*/




    }

    /**
     * 界面切换时更新资源
     * */
    private void updateResource() {

    }
    /**
     * 更新导航的指示和距离显示
     * */
    public void displayNaviPanel(int instruction,int distance) {
        if (mNaviInstructionImageview != null) {
            mNaviInstructionImageview.setImageResource(getInstructionDrawable(instruction));
        }
        if (mNaviDistanceTextView != null && distance>=0) {
            mNaviDistanceTextView.setText(distance+"米");
        }
    }

    /**
     * 获取指示资源
     * */

    public int getInstructionDrawable(int instruction){
        // TODO: 16/6/12
        return 0;
    }
    /**
     * 更新对应指示菜单类型的界面，并相关的显示文本内容
     * */
    public void upgradePanel(PanelType panelType, List<String> textList){

    }
    /**
     * 更新仅指示菜单类型的界面
     * */

    public void upgradePanel(PanelType panelType) {
        updateResource();
        mCurrentPanelType = panelType;
        switch (panelType){
            case MainPanelType:
                mMainPanel.setVisibility(View.VISIBLE);
                mMainPanel.bringToFront();
                break;
            case MainPanelTypeNavi:
                mMainNavingPanel.setVisibility(View.VISIBLE);
                mMainNavingPanel.bringToFront();
                break;
            default:
                switch (panelType){
                    case NaviPanelType:
                        mCommonPanelImageView.setImageResource(R.drawable.speech_navi_panel);
                        break;
                    case NaviPanelTypeCall:
                        mCommonPanelImageView.setImageResource(R.drawable.speech_call_panel_naving);
                        break;
                    case MusicPanelType:
                        mCommonPanelImageView.setImageResource(R.drawable.speech_music_panel);
                        break;
                    case CallPanelType:
                        mCommonPanelImageView.setImageResource(R.drawable.speech_call_panel);
                        break;
                    case NaviExitType:
                        mCommonPanelImageView.setImageResource(R.drawable.speech_call_panel_naving);
                        break;
                }
                mCommonPanel.setVisibility(View.VISIBLE);
                mCommonPanel.bringToFront();
                break;
        }
    }

    /**
     * 获取当前的语音菜单类型
     * */
    public PanelType getCurrentPanelType() {
        return mCurrentPanelType;
    }

}
