package uno.meng.gbn;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import uno.meng.GBN;
import uno.meng.swing.GBNWin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

public class Sender extends Thread {

    private static int Final ;
    private static int begin = 0, end;
    private static int Segments;
    private static int Remain;
    static Timer timer;
    private static InetAddress inetAddress;
    private static DatagramSocket ClientSocket;
    private byte[] receive = new byte[20];
    private byte[] send = new byte[1024];

    public Sender(){
        try {
            inetAddress = InetAddress.getByName("localhost");
        }catch (UnknownHostException e){
        }
        end = begin + GBN.WindowSize -1;
        Segments = GBN.SEGMENTS;
        Remain = Segments;
        Final = GBN.Final;
        try {
            ClientSocket = new DatagramSocket();
        }catch (SocketException e){
        }
        System.out.println("******************* 客户端即将发送" + Segments +"个数据包（0到"+(Segments - 1)+"）！******************* ");
        //设置计时器，定时为3秒
        timer = new Timer(3000,new DelayActionListener(ClientSocket,begin));
        timer.start();
        //首先发送窗格大小个数的数据包
        for (int i = begin; i <= end; i++){
            if(i  / 10 == 0 ){
                send = (new String(i+"k "+"seq")).getBytes();
            }
            else if(i  / 100 == 0){
                send = (new String(i+" "+"seq")).getBytes();
            }
            DatagramPacket sendPacket = new DatagramPacket(send,send.length,inetAddress, GBN.Port);
            try {
                ClientSocket.send(sendPacket);
                Remain--;
                System.out.println("**--> 客户端发送数据包："+i);
                GBNWin.senddata.setText(GBNWin.senddata.getText()+"\r\n发送数据包："+i);
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
                System.out.println("**<-- 接收到ACK序号："+ackNum);
                if(ackNum == Segments -1){
                    System.out.println("******************* 客户端数据全部发送完毕！******************* ");
                    Component jPanel = null;
					JOptionPane.showMessageDialog(jPanel, "数据发送完毕！", "接收成功",JOptionPane.WARNING_MESSAGE); 
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
                    DatagramPacket sendPacket = new DatagramPacket(send,send.length,inetAddress,GBN.Port);
                    try {
                        ClientSocket.send(sendPacket);
                        Remain--;
                        //设置定时器
                        timer = new Timer(3000,new DelayActionListener(ClientSocket,begin));
                        timer.start();
                        System.out.println("**--> 客户端发送数据包："+end);
                        GBNWin.senddata.setText(GBNWin.senddata.getText()+"\r\n发送数据包："+end);
                    }catch (IOException e){
                    }
                }
            }catch (IOException e){
            }
        }
    }
}

//关于计时器的设置
class DelayActionListener implements ActionListener{
    private DatagramSocket socket;
    private int seqNo;
    public DelayActionListener(DatagramSocket ClientSocket, int seqNo){
        this.socket = ClientSocket;
        this.seqNo = seqNo;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Sender.timer.stop();
        Sender.timer = new Timer(3000,new DelayActionListener(socket,seqNo));
        Sender.timer.start();
        int end = seqNo+GBN.WindowSize -1;
        System.out.println("!!--> 准备重传数据包： " + seqNo +"--" + end);
        GBNWin.senddata.setText(GBNWin.senddata.getText()+"\r\n重传数据包： " + seqNo +"--" + end);
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
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, GBN.Port);
                socket.send(sendPacket);
                System.out.println("**--> 客户端发送数据包：" + i);
                GBNWin.senddata.setText(GBNWin.senddata.getText()+"\r\n发送数据包："+i);
            } catch (Exception e1) {
            }
        }

    }
}
