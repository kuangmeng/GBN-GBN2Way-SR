package uno.meng.gbn;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import uno.meng.GBN;
import uno.meng.swing.GBNWin;
public class Receiver extends Thread {
    private static DatagramPacket receivePacket;
    private static DatagramPacket SendACK;
    private static DatagramSocket Service;
    private static byte[] receive = new byte[1024];
    private static byte[] send = new byte[20];
    private static int last;
    private static int Segments;//待发送报文段
    public Receiver(){
    	
        last = -1;
        Segments = GBN.SEGMENTS;
        try {
            Service = new DatagramSocket(GBN.Port);
        }catch (SocketException e){
        		System.out.println("接收方Socket无法打开！");
        }
    }
    @Override
    public void run(){
        while (true){
            receivePacket = new DatagramPacket(receive,receive.length);
            try{
                Service.receive(receivePacket);
            }catch (IOException e){
            		System.out.println("接收失败！");
            }
            int Sequences = -1;
            if(receive[1] == 'k'){
                Sequences = receive[0] - '0';
            }else {
                Sequences = (receive[0]-'0')*10+(receive[1]-'0');
            }
            //通过随机数来指定丢包概率
            if(Math.random()<0.8){
                if (Sequences == last+1){
                    System.out.println("<-- 成功接收序列号为"+Sequences+"的数据包！");
                    GBNWin.receive.setText(GBNWin.receive.getText()+"\r\n接收序列号"+Sequences+"数据包");
                    //如果接收正确，构造ACK报文，并发回给客户端
                    if(Sequences / 10 == 0){
                        send = new String("ACK"+Sequences+"m").getBytes();
                    }
                    else if(Sequences / 100 == 0){
                        send = new String("ACK"+Sequences).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    SendACK = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(SendACK);
                        Segments--;
                        last++;
                    }catch (IOException e){
                    }
                    System.out.println("--> 服务器发送序列为"+Sequences+"的ACK.");
                    GBNWin.acknum.setText(GBNWin.acknum.getText()+"\r\n发送序列号"+Sequences+"ACK");
                }else if(Sequences < (last+1)){
                    if(Sequences / 10 == 0){
                        send = new String("ACK"+Sequences+"m").getBytes();
                    }else if(Sequences / 100 == 0){
                        send = new String("ACK"+Sequences).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    SendACK = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(SendACK);
                    }catch (IOException e){
                    }
                } else if(last != -1 && Segments>0){
                    System.out.println("！！！！！出现丢包！当前序列号："+Sequences);
                    System.out.println("！！！！！应当接收序号号："+(last+1));
                    //返回序号为上次成功接受到的ACK
                    if(last / 10 == 0){
                        send = new String("ACK"+last+"m").getBytes();
                    }
                    else if(last / 100 == 0){
                        send = new String("ACK"+last).getBytes();
                    }
                    InetAddress inetAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    SendACK = new DatagramPacket(send,send.length,inetAddress,clientPort);
                    try{
                        Service.send(SendACK);
                        Segments ++;
                    }catch (IOException e){
                    }
                    System.out.println("--> 服务器发送序列为"+last+"的ACK.");
                    GBNWin.acknum.setText(GBNWin.acknum.getText()+"\r\n发送序列号"+last+"ACK");
                }
            }else{
            	//丢包
            }
        }
    }
}
