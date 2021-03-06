package uno.meng.gbn2way;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import uno.meng.GBN2way;
import uno.meng.swing.GBN2wayWin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

public class Send_Receiver extends Thread {

    private static int Final ;
    private static int begin = 0, end;
    private static int Segments;
    private static int Remain;
    static Timer timer;
    private static InetAddress inetAddress;
    private static DatagramSocket ClientSocket;
    private byte[] receive = new byte[20];
    private byte[] send = new byte[1024];

    public Send_Receiver(){
        try {
            inetAddress = InetAddress.getByName("localhost");
        }catch (UnknownHostException e){
        }
        end = begin + GBN2way.WindowSize1 -1;
        Segments = GBN2way.SEGMENTS1;
        Remain = Segments;
        Final = GBN2way.Final;
        try {
            ClientSocket = new DatagramSocket();
        }catch (SocketException e){
        }
        System.out.println("******************* 服务器即将发送" + Segments +"个数据包（0到"+(Segments - 1)+"）！******************* ");
        //设置计时器，定时为3秒
        timer = new Timer(3000,new DelayActionListener2(ClientSocket,begin));
        timer.start();
        //首先发送窗格大小个数的数据包
        for (int i = begin; i <= end; i++){
            if(i  / 10 == 0 ){
                send = (new String(i+"k "+"seq")).getBytes();
            }
            else if(i  / 100 == 0){
                send = (new String(i+" "+"seq")).getBytes();
            }
            DatagramPacket sendPacket = new DatagramPacket(send,send.length,inetAddress, GBN2way.Port2);
            try {
                ClientSocket.send(sendPacket);
                Remain--;
                System.out.println("--> 服务器发送数据包："+i);
                GBN2wayWin.serversend.setText(GBN2wayWin.serversend.getText()+"\r\n发送数据包："+i);
            }catch (IOException e){
            }
        }
    }

    @Override
    public void run(){
        while (true){
            DatagramPacket receivePacket = new DatagramPacket(receive,receive.length);
            try{
                ClientSocket.receive(receivePacket);
                int ackNum = -1;
                if(receive[4] == 'm'){
                    ackNum = receive[3]-'0';
                }
                else {
                    ackNum = (receive[3]-'0')*10 + (receive[4]-'0');
                }
                System.out.println("<-- 服务器接收到ACK序号："+ackNum);
                if(ackNum == Segments -1){
                    System.out.println("******************* 服务器数据全部发送完毕！******************* ");
                    Component jPanel = null;
					JOptionPane.showMessageDialog(jPanel, "服务器数据发送完毕！", "接收成功",JOptionPane.WARNING_MESSAGE); 
                    timer.stop();
                    return;
                }else if(ackNum == begin && Remain > 0){
                	//未接收完毕！
                    timer.stop();
                    //窗口移动
                    begin++;
                    end++;
                    //不能越界
                    if(end  > Final - 1){
                        end = 0;
                    }
                    if(begin > Final - 1){
                        begin = 0;
                    }
                    if(end / 10 == 0 ){
                        send = (new String(end+"k "+"seq")).getBytes();
                    }
                    else if(end / 100 == 0){
                        send = (new String(end+" "+"seq")).getBytes();
                    }
                    DatagramPacket sendPacket = new DatagramPacket(send,send.length,inetAddress,GBN2way.Port2);
                    try {
                        ClientSocket.send(sendPacket);
                        Remain--;
                        //设置定时器
                        timer = new Timer(3000,new DelayActionListener2(ClientSocket,begin));
                        timer.start();
                        System.out.println("--> 服务器发送数据包："+end);
                        GBN2wayWin.serversend.setText(GBN2wayWin.serversend.getText()+"\r\n发送数据包："+end);
                    }catch (IOException e){
                    }
                }
            }catch (IOException e){
            }
//            try{
//                Thread.sleep(200);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
        }
    }
}

//关于计时器的设置
class DelayActionListener2 implements ActionListener{
    private DatagramSocket socket;
    private int seqNo;
    public DelayActionListener2(DatagramSocket ClientSocket, int seqNo){
        this.socket = ClientSocket;
        this.seqNo = seqNo;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Send_Receiver.timer.stop();
        Send_Receiver.timer = new Timer(3000,new DelayActionListener2(socket,seqNo));
        Send_Receiver.timer.start();
        int end = seqNo+GBN2way.WindowSize1 -1;
        System.out.println("!!--> 准备重传数据包： " + seqNo +"--" + end);
        GBN2wayWin.serversend.setText(GBN2wayWin.serversend.getText()+"\r\n重传数据包： " + seqNo +"--" + end);
        for(int i = seqNo; i <= end; i++){
            byte[] sendData = null;
            InetAddress serverAddress = null;
            try {
                serverAddress = InetAddress.getByName("localhost");
                if(i  / 10 == 0 ){
                    sendData = (new String(i+"k "+"seq")).getBytes();
                }
                else if(i / 100 == 0){
                    sendData = (new String(i+" "+"seq")).getBytes();
                }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, GBN2way.Port2);
                socket.send(sendPacket);
                System.out.println("--> 服务器发送数据包：" + i);
                GBN2wayWin.serversend.setText(GBN2wayWin.serversend.getText()+"\r\n发送数据包："+i);
            } catch (Exception e1) {
            }
        }
    }
}
