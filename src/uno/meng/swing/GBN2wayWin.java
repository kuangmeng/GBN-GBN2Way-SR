package uno.meng.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import uno.meng.GBN2way;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GBN2wayWin {

	private JFrame frmgbn;
	private JTextField winsize1;
	private JTextField seqsize1;
	public static int SEGMENTS1 = 20;
	public static int WindowSize1 = 3;
	public static JTextArea clientsend = new JTextArea();
	public static JTextArea clientreceiver = new JTextArea();
	public static JTextArea serversend = new JTextArea();
	public static JTextArea serverreceiver = new JTextArea();
	private JTextField winsize2;
	private JTextField seqsize2;
	public static int SEGMENTS2 = 20;
	public static int WindowSize2 = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GBN2wayWin window = new GBN2wayWin();
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
	public GBN2wayWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmgbn = new JFrame();
		frmgbn.setTitle("匡盟盟GBN双向传输");
		frmgbn.setBounds(100, 100, 600, 300);
		frmgbn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmgbn.getContentPane().setLayout(springLayout);
		
		JLabel lblC = new JLabel("S-窗口大小：");
		frmgbn.getContentPane().add(lblC);
		
		winsize1 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, winsize1, 10, SpringLayout.NORTH, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, winsize1, 99, SpringLayout.WEST, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblC, 5, SpringLayout.NORTH, winsize1);
		springLayout.putConstraint(SpringLayout.EAST, lblC, -6, SpringLayout.WEST, winsize1);
		frmgbn.getContentPane().add(winsize1);
		winsize1.setColumns(10);
		
		JLabel lblC_1 = new JLabel("S-序列长度：");
		springLayout.putConstraint(SpringLayout.WEST, lblC_1, 0, SpringLayout.WEST, lblC);
		frmgbn.getContentPane().add(lblC_1);
		
		seqsize1 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, seqsize1, 1, SpringLayout.SOUTH, winsize1);
		springLayout.putConstraint(SpringLayout.NORTH, lblC_1, 5, SpringLayout.NORTH, seqsize1);
		springLayout.putConstraint(SpringLayout.WEST, seqsize1, 0, SpringLayout.WEST, winsize1);
		frmgbn.getContentPane().add(seqsize1);
		seqsize1.setColumns(10);
		
		JButton btnNewButton = new JButton("开始模拟");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 7, SpringLayout.NORTH, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, 0, SpringLayout.SOUTH, seqsize1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SEGMENTS1 = Integer.parseInt(seqsize1.getText());
				WindowSize1 = Integer.parseInt(winsize1.getText());
				SEGMENTS2 = Integer.parseInt(seqsize2.getText());
				WindowSize2 = Integer.parseInt(winsize2.getText());
				GBN2way.main(null);
			}
		});
		frmgbn.getContentPane().add(btnNewButton);
		
		JLabel label_2 = new JLabel("客户端发送");
		springLayout.putConstraint(SpringLayout.NORTH, label_2, 16, SpringLayout.SOUTH, lblC_1);
		springLayout.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, lblC);
		frmgbn.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("客户端接收");
		springLayout.putConstraint(SpringLayout.WEST, label_3, 68, SpringLayout.EAST, label_2);
		springLayout.putConstraint(SpringLayout.SOUTH, label_3, 0, SpringLayout.SOUTH, label_2);
		frmgbn.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("服务器发送");
		springLayout.putConstraint(SpringLayout.NORTH, label_4, 72, SpringLayout.NORTH, frmgbn.getContentPane());
		frmgbn.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("服务器接收");
		springLayout.putConstraint(SpringLayout.EAST, label_4, -91, SpringLayout.WEST, label_5);
		springLayout.putConstraint(SpringLayout.SOUTH, label_5, 0, SpringLayout.SOUTH, label_2);
		springLayout.putConstraint(SpringLayout.EAST, label_5, -54, SpringLayout.EAST, frmgbn.getContentPane());
		frmgbn.getContentPane().add(label_5);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 7, SpringLayout.SOUTH, label_2);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmgbn.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 178, SpringLayout.SOUTH, label_2);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 127, SpringLayout.WEST, frmgbn.getContentPane());
		frmgbn.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 7, SpringLayout.SOUTH, label_3);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 17, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 178, SpringLayout.SOUTH, label_3);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 141, SpringLayout.EAST, scrollPane);
		
		scrollPane.setViewportView(clientsend);
		frmgbn.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_2, 9, SpringLayout.SOUTH, label_4);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_2, 24, SpringLayout.EAST, scrollPane_1);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_2, 180, SpringLayout.SOUTH, label_4);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, 148, SpringLayout.EAST, scrollPane_1);
		scrollPane_1.setViewportView(clientreceiver);
		frmgbn.getContentPane().add(scrollPane_2);
		JScrollPane scrollPane_3 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, scrollPane_3);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_3, 7, SpringLayout.SOUTH, label_5);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_3, 26, SpringLayout.EAST, scrollPane_2);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_3, 178, SpringLayout.SOUTH, label_5);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_3, 156, SpringLayout.EAST, scrollPane_2);
		scrollPane_2.setViewportView(serversend);
		frmgbn.getContentPane().add(scrollPane_3);
		scrollPane_3.setViewportView(serverreceiver);
		
		JLabel lblS = new JLabel("C-窗口大小：");
		springLayout.putConstraint(SpringLayout.NORTH, lblS, 0, SpringLayout.NORTH, lblC);
		springLayout.putConstraint(SpringLayout.WEST, lblS, 13, SpringLayout.EAST, winsize1);
		frmgbn.getContentPane().add(lblS);
		
		JLabel lblNewLabel = new JLabel("C-序列长度：");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, lblC_1);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, lblS);
		frmgbn.getContentPane().add(lblNewLabel);
		
		winsize2 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, winsize2, 0, SpringLayout.WEST, label_4);
		springLayout.putConstraint(SpringLayout.SOUTH, winsize2, 0, SpringLayout.SOUTH, winsize1);
		frmgbn.getContentPane().add(winsize2);
		winsize2.setColumns(10);
		
		seqsize2 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, seqsize2, 0, SpringLayout.WEST, label_4);
		springLayout.putConstraint(SpringLayout.SOUTH, seqsize2, 0, SpringLayout.SOUTH, seqsize1);
		frmgbn.getContentPane().add(seqsize2);
		seqsize2.setColumns(10);
	}
}
