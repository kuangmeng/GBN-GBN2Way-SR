package uno.meng.swing;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import uno.meng.SR;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SRWin {

	private JFrame frmsr;
	public static JTextField srwinsize;
	public static JTextField segmentsnum;
	 public static int SEGMENTS = 50;
	public static int WindowSize = 4;
	public static JTextArea senddata = new JTextArea();
	public static JTextArea acknum = new JTextArea();
	public static JTextArea receive = new JTextArea();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SRWin window = new SRWin();
					window.frmsr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SRWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmsr = new JFrame();
		frmsr.setTitle("匡盟盟SR收发程序");
		frmsr.setBounds(100, 100, 450, 300);
		frmsr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmsr.getContentPane().setLayout(springLayout);
		
		JLabel lblNewLabel = new JLabel("SR窗口大小：");
		frmsr.getContentPane().add(lblNewLabel);
		
		srwinsize = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, srwinsize, 95, SpringLayout.WEST, frmsr.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 5, SpringLayout.NORTH, srwinsize);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -6, SpringLayout.WEST, srwinsize);
		springLayout.putConstraint(SpringLayout.NORTH, srwinsize, 10, SpringLayout.NORTH, frmsr.getContentPane());
		frmsr.getContentPane().add(srwinsize);
		srwinsize.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("序列长度：");
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, 0, SpringLayout.EAST, lblNewLabel);
		frmsr.getContentPane().add(lblNewLabel_1);
		
		segmentsnum = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 5, SpringLayout.NORTH, segmentsnum);
		springLayout.putConstraint(SpringLayout.NORTH, segmentsnum, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, segmentsnum, 0, SpringLayout.WEST, srwinsize);
		frmsr.getContentPane().add(segmentsnum);
		segmentsnum.setColumns(10);
		
		JButton btnNewButton = new JButton("开始模拟");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SEGMENTS = Integer.parseInt(segmentsnum.getText());
				 WindowSize = Integer.parseInt(srwinsize.getText());
				SR.main(null);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 7, SpringLayout.NORTH, frmsr.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, 0, SpringLayout.SOUTH, segmentsnum);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -45, SpringLayout.EAST, frmsr.getContentPane());
		frmsr.getContentPane().add(btnNewButton);
		
		JLabel label = new JLabel("发送数据信息：");
		springLayout.putConstraint(SpringLayout.NORTH, label, 6, SpringLayout.SOUTH, segmentsnum);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblNewLabel);
		frmsr.getContentPane().add(label);
		
		JLabel lblack = new JLabel("发送ACK信息：");
		springLayout.putConstraint(SpringLayout.NORTH, lblack, 6, SpringLayout.SOUTH, segmentsnum);
		springLayout.putConstraint(SpringLayout.WEST, lblack, 55, SpringLayout.EAST, label);
		frmsr.getContentPane().add(lblack);
		
		JLabel label_1 = new JLabel("接收数据信息：");
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, label);
		springLayout.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, btnNewButton);
		frmsr.getContentPane().add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 9, SpringLayout.WEST, frmsr.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 183, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 132, SpringLayout.WEST, frmsr.getContentPane());
		frmsr.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, lblack);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 9, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 183, SpringLayout.SOUTH, lblack);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 144, SpringLayout.EAST, scrollPane);
		
		scrollPane.setViewportView(senddata);
		frmsr.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_2, 6, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_2, 29, SpringLayout.EAST, scrollPane_1);
		
		scrollPane_1.setViewportView(acknum);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_2, 183, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -21, SpringLayout.EAST, frmsr.getContentPane());
		frmsr.getContentPane().add(scrollPane_2);
		
		scrollPane_2.setViewportView(receive);
	}
}
