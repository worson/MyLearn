package hud.haliai.com.share.utils;

import android.util.Log;

/**
 * 输出日志到控制台
 * @author Created by Harry Moo
 */
public class HaloLoggerConsole implements IHaloLogger {

    @Override
    public void logI(String tag, String msg) {
        Log.i("HALO+"+tag, msg);
    }

    @Override
    public void logW(String tag, String msg) {
        Log.w("HALO+"+tag, msg);
    }

    public void logE(String tag, String msg) {
        Log.e("HALO+"+tag, msg);
    }

	@Override
	public void logD(String tag, String msg) {
//		if (isNaviFeatureLog(tag))
			Log.d("DEBUGNavi-" + tag, msg);
	}
	
	/*
	 * filter示例
	 * 该函数中对比自己想要的TAG类名，在各个接口函数中调用filter，只打印需要的日志TAG；注意：在提交代码时谨记一定要恢复原实现；
	private boolean isNaviFeatureLog(String tag) {
		if (tag.contains("Navi") || tag.equalsIgnoreCase("HudIOSession"))
			return true;
		
		return false;
	}
	 */
}
