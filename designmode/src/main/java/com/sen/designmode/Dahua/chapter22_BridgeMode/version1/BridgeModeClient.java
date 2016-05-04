package com.sen.designmode.Dahua.chapter22_BridgeMode.version1;

/**
 * Created by secon on 2016/5/2.
 * 不是什么关系都要用继承来实现的，本例中PhoneSoft只是一个关联项目，故只要声明一个成员变量即可实现此需求。
 */
public class BridgeModeClient {
    public static void main(String[] args) {
        PhoneXiaoMI xiaoMI = new PhoneXiaoMI();
        xiaoMI.setSoft(new HandSetGame());//此增加可增加任何类型的软件 。
        xiaoMI.play();
    }
}
