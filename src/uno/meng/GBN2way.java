package uno.meng;


import uno.meng.gbn2way.Receive_Sender;
import uno.meng.gbn2way.Receiver;
import uno.meng.gbn2way.Send_Receiver;
import uno.meng.gbn2way.Sender;
import uno.meng.swing.GBN2wayWin;

public class GBN2way {
	public static final int WindowSize1 = GBN2wayWin.WindowSize1;
	public static final int WindowSize2 = GBN2wayWin.WindowSize2;
    public static final int Final = 50;
    public static final int SEGMENTS1 =GBN2wayWin.SEGMENTS1;
    public static final int SEGMENTS2 =GBN2wayWin.SEGMENTS2;
    public static final int Port1 = 7776;
    public static final int Port2 = 7775;
    public static void main(String[] args){
    	GBN2wayWin.clientsend.setText("客户端开始发送数据");
    	GBN2wayWin.clientreceiver.setText("客户端接收到数据");
    	GBN2wayWin.serverreceiver.setText("服务器接收到数据");
    	GBN2wayWin.serversend.setText("服务器开始发送数据");
        Receiver receiver = new Receiver(Port1);
        receiver.start();
        Sender sender = new Sender();
        sender.start();
        Receive_Sender receive_send = new Receive_Sender(Port2);
        receive_send.start();
        Send_Receiver send = new Send_Receiver();
        send.start();
    }
}
