package com.sen.designmode.Dahua.chapter22_BridgeMode.version1;

/**
 * Created by secon on 2016/5/2.
 */
public class PhoneXiaoMI extends HandSetBrand {
    private PhoneSoft soft;

    public void setSoft(PhoneSoft soft) {
        this.soft = soft;
    }

    public void play(){
        soft.run();
    }
}
