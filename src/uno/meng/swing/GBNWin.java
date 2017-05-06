package uno.meng.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import uno.meng.GBN;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class GBNWin {
	private JFrame frmgbn;
	private JLabel lblGbn;
	private JTextField gbnwinsize;
	private JLabel label_2;
	private JTextField segmentsnum;
    public static int SEGMENTS = 20;
	public static int WindowSize = 3;
	public static JTextArea senddata = new JTextArea();
	public static JTextArea acknum = new JTextArea();
	public static JTextArea receive = new JTextArea();




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GBNWin window = new GBNWin();
					window.frmgbn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GBNWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmgbn = new JFrame();
		frmgbn.setTitle("匡盟盟的GBN收发程序");
		frmgbn.setBounds(100, 100, 450, 300);
		frmgbn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmgbn.getContentPane().setLayout(springLayout);
		
		JLabel label = new JLabel("发送信息框：");
		frmgbn.getContentPane().add(label);
		
		JLabel lblack = new JLabel("发送ACK信息框：");
		springLayout.putConstraint(SpringLayout.NORTH, lblack, 0, SpringLayout.NORTH, label);
		frmgbn.getContentPane().add(lblack);
		
		JLabel label_1 = new JLabel("接收信息框：");
		springLayout.putConstraint(SpringLayout.EAST, lblack, -51, SpringLayout.WEST, label_1);
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, label);
		frmgbn.getContentPane().add(label_1);
		
		lblGbn = new JLabel("GBN窗口大小：");
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblGbn);
		springLayout.putConstraint(SpringLayout.WEST, lblGbn, 22, SpringLayout.WEST, frmgbn.getContentPane());
		frmgbn.getContentPane().add(lblGbn);
		
		gbnwinsize = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, gbnwinsize, 10, SpringLayout.NORTH, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, gbnwinsize, 1, SpringLayout.EAST, lblGbn);
		springLayout.putConstraint(SpringLayout.NORTH, lblGbn, 5, SpringLayout.NORTH, gbnwinsize);
		frmgbn.getContentPane().add(gbnwinsize);
		gbnwinsize.setColumns(10);
		
		label_2 = new JLabel("序列长度：");
		springLayout.putConstraint(SpringLayout.NORTH, label, 23, SpringLayout.SOUTH, label_2);
		frmgbn.getContentPane().add(label_2);
		
		segmentsnum = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, segmentsnum, 1, SpringLayout.SOUTH, gbnwinsize);
		springLayout.putConstraint(SpringLayout.NORTH, label_2, 5, SpringLayout.NORTH, segmentsnum);
		springLayout.putConstraint(SpringLayout.EAST, label_2, -1, SpringLayout.WEST, segmentsnum);
		springLayout.putConstraint(SpringLayout.WEST, segmentsnum, 0, SpringLayout.WEST, gbnwinsize);
		frmgbn.getContentPane().add(segmentsnum);
		segmentsnum.setColumns(10);
		
		JButton action = new JButton("开始模拟");
		springLayout.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, action);
		springLayout.putConstraint(SpringLayout.NORTH, action, 10, SpringLayout.NORTH, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, action, 49, SpringLayout.EAST, gbnwinsize);
		springLayout.putConstraint(SpringLayout.SOUTH, action, 0, SpringLayout.SOUTH, label_2);
		action.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				SEGMENTS = Integer.parseInt(segmentsnum.getText());
				WindowSize = Integer.parseInt(gbnwinsize.getText());
				GBN.main(null);
			}
		});
		frmgbn.getContentPane().add(action);
		
		JScrollPane scrollPane = new JScrollPane();
		frmgbn.getContentPane().add(scrollPane);
		
		JScrollPane send = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, send, 6, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.WEST, send, -12, SpringLayout.WEST, label);
		springLayout.putConstraint(SpringLayout.SOUTH, send, 171, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.EAST, send, 138, SpringLayout.WEST, frmgbn.getContentPane());
		frmgbn.getContentPane().add(send);
		send.setViewportView(senddata);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, lblack);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 13, SpringLayout.EAST, send);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 171, SpringLayout.SOUTH, lblack);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 143, SpringLayout.EAST, send);
		frmgbn.getContentPane().add(scrollPane_1);
		
		scrollPane_1.setViewportView(acknum);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_2, 6, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_2, 29, SpringLayout.EAST, scrollPane_1);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_2, 0, SpringLayout.SOUTH, send);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -10, SpringLayout.EAST, frmgbn.getContentPane());
		frmgbn.getContentPane().add(scrollPane_2);
		
		scrollPane_2.setViewportView(receive);
		

	}
}
