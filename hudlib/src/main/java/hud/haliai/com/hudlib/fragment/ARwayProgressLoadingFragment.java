package hud.haliai.com.hudlib.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import hud.haliai.com.hudlib.R;
import hud.haliai.com.share.utils.HaloLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class ARwayProgressLoadingFragment extends Fragment {
    public static final String TAG = ARwayProgressLoadingFragment.class.getSimpleName();


    public ARwayProgressLoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arway_progress_loading, container, false);

    }

    private int mOnTouchEventCnt = 0;
    private ProgressDialog mProgressDialog = null;
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(mOnTouchEventCnt%2 == 0){
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(getActivity(),"","正在加载");
                    mProgressDialog.setMax(100);
                    mProgressDialog.setProgress(0);
                }else {
//                    mProgressDialog.create();
                    mProgressDialog.show();
                }
                HaloLogger.logE(TAG,"mProgressDialog.show();");
            }
        }else {
            mProgressDialog.dismiss();
//            mProgressDialog.hide();
            HaloLogger.logE(TAG,"mProgressDialog.hide();");
        }
        mOnTouchEventCnt++;
        return true;
    }

}
