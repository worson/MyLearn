package com.sen.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.sen.view.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hud.haliai.com.share.utils.HaloLogger;


public class CardLayout extends LinearLayout
{

	private static final String TAG = CardLayout.class.getSimpleName();

	private Context mContext;
	private final int DEFAULT_VIEW_NUMBER = 4;
	private LayoutParams mDefalutLayoutParams;

	private static final int[] TextViewIDS = new int[]{R.id.title_textvew1, R.id.title_textvew2, R.id.title_textvew3};

	private List<View> mRemovedViewList;

	public CardLayout(Context context) {
		super(context);
		init(context);
	}

	public CardLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	private void addDisplayView(int index){
		View view = mRemovedViewList.remove(0);
		view.setLayoutParams(mDefalutLayoutParams);
		addView(view);
	}
	private void removeDisplayView(int index){
		View view = getChildAt(index);
		removeView(view);
		mRemovedViewList.add(view);
	}
	private void init(Context context){
		mContext = context;
		mDefalutLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
		setOrientation(HORIZONTAL);

		mRemovedViewList = new ArrayList<>();
		for (int i = 0; i <DEFAULT_VIEW_NUMBER ; i++) {
			mRemovedViewList.add(new CardView(mContext));
		}

//		testAddView();
	}
	private void testAddView(){
		mRemovedViewList.clear();
		int itemWidth = getWidth()/DEFAULT_VIEW_NUMBER;
		HaloLogger.logI(TAG,"parent width is :"+getWidth());
		LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,3);
		for (int i = 0; i <DEFAULT_VIEW_NUMBER ; i++) {
			CardView view = new CardView(mContext);
			view.setLayoutParams(params);
			addView(view);
		}
	}
	public void setContent(List<Map<String,Object>> contents){
		if (contents == null) {
			HaloLogger.logI(TAG,"contents is null ");
			return;
		}
		/*
		* layout中没有添加view时，直接添加view
		* 有相关的view时，更新view中的内容
		* */

		int diffNuber = contents.size()-getChildCount();
		if(diffNuber>0){//需要增加view
			for (int i = 0; i < Math.abs(diffNuber); i++) {
				if (mRemovedViewList.size()<=0){
					HaloLogger.logI(TAG,"new a CardView");
					mRemovedViewList.add(new CardView(mContext));
				}
				HaloLogger.logI(TAG,"add a CardView");
				addDisplayView(i);
			}

		}else if(diffNuber<0){//需要移除view
			for (int i = 0; i <Math.abs(diffNuber) ; i++) {
				HaloLogger.logI(TAG,"remove a CardView");
				removeDisplayView(0);
			}
		}
		//更新view中的内容
		for (int i = 0; i <contents.size() ; i++) {
			Map<String,Object> content = contents.get(i);
			HaloLogger.logI(TAG,"update a CardView");
			CardView view = (CardView)getChildAt(i);
			view.update(content);
		}

	}


}
