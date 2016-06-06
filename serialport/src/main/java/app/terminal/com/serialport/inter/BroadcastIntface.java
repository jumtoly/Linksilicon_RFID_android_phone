package app.terminal.com.serialport.inter;

/**
 * Created by liu.yao on 2016/5/24.
 */
public interface BroadcastIntface {
    String GETREADERID_BROADCASTRECEIVER = "GETREADERID_BROADCASTRECEIVER";  //收发数据广播
    String GETCARDDATA_BROADCASTRECEIVER = "GETCARDDATA_BROADCASTRECEIVER"; //获取卡数据广播
    String CONFLICT_BROADCASTRECEIVER = "CONFLICT_BROADCASTRECEIVER"; //防冲突广播

}
