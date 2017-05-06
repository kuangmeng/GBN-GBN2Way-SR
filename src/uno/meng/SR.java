package uno.meng;

import uno.meng.sr.Receiver;
import uno.meng.sr.Sender;
import uno.meng.swing.SRWin;

public class SR {
    public static final int WindowSize = SRWin.WindowSize;
    public static final int SEGMENTS = SRWin.SEGMENTS;
    public static final int Final = 50;
    public static int Port = 8888;
    public static void main(String[] args){
    	SRWin.senddata.setText("开始发送数据：");
    	SRWin.acknum.setText("开始发送ACK：");
    	SRWin.receive.setText("开始接收数据：");
        Receiver receiver = new Receiver();
        receiver.start();
        Sender sender = new Sender();
        sender.start();
    }
}
