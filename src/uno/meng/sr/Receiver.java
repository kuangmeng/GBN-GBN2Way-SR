package uno.meng.sr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import uno.meng.SR;
import uno.meng.swing.SRWin;

public class Receiver extends Thread {

    private static DatagramPacket receivePacket;
    private static DatagramPacket sendPacket;
    private static DatagramSocket Service;
    private static int last;
    private static byte[] receive = new byte[1024];
    private static byte[] send = new byte[20];
    private static int Segments;
    private static int Remain;
    private static int begin = 0,end;
    private static int[] DataInfo;

    public Receiver(){
    	    last = -1;
        Segments = SR.SEGMENTS;
        Remain = Segments;
        end = begin + SR.WindowSize - 1;
        DataInfo = new int[SR.Final];
        for (int i = 0; i < DataInfo.length; i++){
            DataInfo[i] = 0;
        }
        try {
            Service = new DatagramSocket(SR.Port);
        }catch (SocketException e){
        }
    }
    @Override
    public void run(){
        while (true){
            receivePacket = new DatagramPacket(receive,receive.length);
            try{
                Service.receive(receivePacket);
            }catch (IOException e){
            }
            int Sequences = -1;
            if(receive[1] == 'k'){
                Sequences = receive[0] - '0';
            }else {
                Sequences = (receive[0]-'0')*10+(receive[1]-'0');
            }
            //指定丢包概率
            if(Math.random()<0.8){
                DataInfo[Sequences] = 1;
                System.out.println("<-- 服务器成功接收序列"+Sequences+"数据包！");
                SRWin.receive.setText(SRWin.receive.getText()+"\r\n接收序列号"+Sequences+"数据包");
                if(Sequences >= begin && Sequences <= end && Remain > 0){
                    //构造ACK报文，并发回给客户端
                    if(Sequences / 10 == 0){
                        send = new String("ACK"+Sequences+"m").getBytes();
                    }
                    else if(Sequences / 100 == 0){
                        send = new String("ACK"+Sequences).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    sendPacket = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(sendPacket);
                        Remain--;
                    }catch (IOException e){
                    }
                    System.out.println("--> 服务器发送序列为"+Sequences+"的ACK.");
                    SRWin.acknum.setText(SRWin.acknum.getText()+"\r\n发送序列号"+Sequences+"ACK");
                    if(Remain == 0 ){
    					    System.exit(0);
                    }
                    if(Sequences == begin){
                        int moveNum = 0;
                        for(int i = begin; i <= end; i++){
                            if(DataInfo[i] == 1){
                                moveNum++;
                                DataInfo[i] = 0;
                            } else {
                                break;
                            }
                        }
                        begin += moveNum;
                        end += moveNum;
                    }
                }else if(Sequences < begin && Remain > 0){
                    if(Sequences / 10 == 0){
                        send = new String("ACK"+Sequences+"m").getBytes();
                    } else if(Sequences < 100){
                        send = new String("ACK"+Sequences).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    sendPacket = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(sendPacket);
                        System.out.println("--> 服务器发送序列为"+Sequences+"的ACK.");
                        SRWin.acknum.setText(SRWin.acknum.getText()+"\r\n发送序列号"+Sequences+"ACK");
                        DataInfo[Sequences] = 0;
                    }catch (IOException e){
                    }
                }else {
                    System.out.println("！！！！！产生丢包！当前序号："+Sequences);
                    System.out.println("！！！！！应当接收序号："+(last+1));
                    //返回序号为上次成功接受到的ACK
                    if(last / 10 == 0){
                        send = new String("ACK"+last+"m").getBytes();
                    }
                    else if(last / 100 == 0){
                        send = new String("ACK"+last).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    sendPacket = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(sendPacket);
                        Remain --;
                    }catch (IOException e){
                    }
                    System.out.println("--> 服务器发送序列为"+last+"的ACK.");
                    SRWin.acknum.setText(SRWin.acknum.getText()+"\r\n发送序列号"+last+"ACK");
                    if(Remain == 0 ){
                    		System.exit(0);
                  }
                }
            }else{
            	//丢包
            }
        }
    }
}
