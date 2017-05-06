package uno.meng;


import uno.meng.gbn.Receiver;
import uno.meng.gbn.Sender;
import uno.meng.swing.GBNWin;

public class GBN {
	public static final int WindowSize = GBNWin.WindowSize;
    public static final int Final = 50;
    public static final int SEGMENTS =GBNWin.SEGMENTS;
    public static final int Port = 7777;
    public static void main(String[] args){
    	GBNWin.senddata.setText("开始发送数据：");
    	GBNWin.acknum.setText("开始发送ACK：");
    	GBNWin.receive.setText("开始接收数据：");
        Receiver receiver = new Receiver();
        receiver.start();
        Sender sender = new Sender();
        sender.start();
    }
}
