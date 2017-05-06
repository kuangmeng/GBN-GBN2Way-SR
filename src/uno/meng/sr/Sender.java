package uno.meng.sr;
import uno.meng.SR;
import uno.meng.swing.SRWin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Sender extends Thread {

    private static int Final ;
    private static int begin = 0, end;
    private static int[] ACKInfo;
    private static int Segments;
    private static int Remain;
    static Timer[] timers;
    private static InetAddress inetAddress;
    private static DatagramSocket ClientSocket;
    private byte[] receive = new byte[20];
    private byte[] send = new byte[1024];
    public Sender(){
        try {
            inetAddress = InetAddress.getByName("localhost");
        }catch (UnknownHostException e){
        }
        end = begin + SR.WindowSize -1;
        Segments = SR.SEGMENTS;
        Remain = Segments;
        Final = SR.Final;
        timers = new Timer[Final];
        ACKInfo = new int[Final];
        for(int i = 0; i < ACKInfo.length; i++){
            ACKInfo[i] = 0;
        }
        try {
            ClientSocket = new DatagramSocket();
        }catch (SocketException e){
        }
        System.out.println("******************* 客户端即将发送" + Segments +"个数据包（0到"+(Segments - 1)+"）！******************* ");
        //首先发送窗格大小个数的数据包
        for (int i = begin; i <= end; i++){
            if(i / 10 == 0 ){
                send = (new String(i+"k "+"seq")).getBytes();
            }else if(i / 100 == 0){
                send = (new String(i+" "+"seq")).getBytes();
            }
            DatagramPacket sendPacket = new DatagramPacket(send,send.length,inetAddress, SR.Port);
            try {
                ClientSocket.send(sendPacket);
                Remain--;
                //设置定时器，设置时间为3秒
                timers[i] = new Timer(3000, new DelayActionListener(ClientSocket,i,timers));
                timers[i].start();
                System.out.println("**--> 客户端发送数据包："+i);
                SRWin.senddata.setText(SRWin.senddata.getText()+"\r\n发送数据包："+i);
            }catch (IOException e){
            }
        }
    }
    public void run(){
        while (true){
            DatagramPacket receivePacket = new DatagramPacket(receive,receive.length);
            try{
                ClientSocket.receive(receivePacket);
                int ackNum = -1;
                if(receive[4] == 'm'){
                    ackNum = receive[3]-'0';
                } else {
                    ackNum = (receive[3]-'0')*10 + (receive[4]-'0');
                }
                System.out.println("**<-- 客户端接收到ACK序号："+ackNum);
                if(ackNum == Segments -1){
                		System.out.println("******************* 客户端数据全部发送完毕！******************* ");
                		Component jPanel = null;
    					JOptionPane.showMessageDialog(jPanel, "数据发送完毕！", "接收成功",JOptionPane.WARNING_MESSAGE); 
    					System.exit(0);
                }
                //关闭定时器
                timers[ackNum].stop();
                if(ackNum > begin){
                    ACKInfo[ackNum] = 1;
                }else if(ackNum == begin && Remain > 0){
                    int moveNum = 0;
                    ACKInfo[begin] = 1;
                    //移动窗口
                    for (int i = begin; i <= end; i++){
                        if(ACKInfo[i] == 1) {
                            moveNum++;
                            ACKInfo[i] = 0;
                            if(Remain > 0){
                                int next = i - begin + 1 + end;
                                if (next / 10 == 0) {
                                    send = (new String(next + "k " + "seq")).getBytes();
                                } else if (next / 100 == 0) {
                                    send = (new String(next + " " + "seq")).getBytes();
                                }
                                DatagramPacket sendPacket = new DatagramPacket(send, send.length, inetAddress, SR.Port);
                                try {
                                    ClientSocket.send(sendPacket);
                                    Remain--;
                                    //设置定时器
                                    timers[next] = new Timer(3000, new DelayActionListener(ClientSocket, next, timers));
                                    timers[next].start();
                                    System.out.println("**--> 客户端发送数据包：" + next);
                                    SRWin.senddata.setText(SRWin.senddata.getText()+"\r\n发送数据包："+next);
                                } catch (IOException e) {
                                }
                            } else {
                                System.out.println("******************* 客户端数据全部发送完毕！******************* ");
                                Component jPanel = null;
            					  JOptionPane.showMessageDialog(jPanel, "数据发送完毕！", "接收成功",JOptionPane.WARNING_MESSAGE); 
              					System.exit(0);
                            }
                        }else {
                            break;
                        }
                    }
                    begin+= moveNum;
                    end+= moveNum;
                  //不能越界
                    if(end  > Final - 1){
                        end = 0;
                    }
                    if(begin > Final - 1){
                        begin = 0;
                    }   
                }
            }catch (IOException e){
            }
        }
    }
}
//计时器的设置
class DelayActionListener implements ActionListener {
    private DatagramSocket socket;
    private int seqNo;
    private Timer[] timers;
    public DelayActionListener(DatagramSocket ClientSocket, int seqNo, Timer[] timers) {
        this.socket = ClientSocket;
        this.seqNo = seqNo;
        this.timers = timers;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timers[seqNo].stop();
        byte[] sendDate = null;
        InetAddress serverAdress = null;
        try {
            serverAdress = InetAddress.getByName("localhost");
            if (seqNo / 10 == 0) {
                sendDate = (new String(seqNo + "k " + "seq").getBytes());
            } else if (seqNo / 100 == 0) {
                sendDate = (new String(seqNo + " " + "seq").getBytes());
            }
            DatagramPacket sendPacket = new DatagramPacket(sendDate, sendDate.length, serverAdress, SR.Port);
            socket.send(sendPacket);
            timers[seqNo].start();
            System.out.println("!!--> 客户端重传数据包： " + seqNo);
            SRWin.senddata.setText(SRWin.senddata.getText()+"\r\n重传数据包： " + seqNo);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
