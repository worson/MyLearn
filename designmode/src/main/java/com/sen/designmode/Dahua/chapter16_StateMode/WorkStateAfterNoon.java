package com.sen.designmode.Dahua.chapter16_StateMode;

/**
 * Created by secon on 2016/5/2.
 */
public class WorkStateAfterNoon extends WorkState {
    @Override
    public void doWork(Work work) {
        if (work.getHour()<17){
            output.output("Afternoon state is good, continue to work hard ");//下午状态还不错 ，继续努力
        }else if (work.getHour()>=17){
            if(work.isFinish()){
                output.output("Work finished, go home from work.  ");//工作完成，下班回家喽
            }else {
                if (work.getHour()<21){
                    output.output("Overtime, tired ");//加班，疲累
                }else{
                    output.output("No, I fell asleep. ");//不行了，睡着了
                }
            }
        }

    }
}
