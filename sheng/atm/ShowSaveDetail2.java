package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ShowSaveDetail2 extends JFrame implements ActionListener{

	Container cp;
	private JLabel showdetaillab;
	private JLabel accforsavelab;
	private JLabel acclab;
	private JLabel accnamelab;
	private JLabel namelab;
	private JLabel totallab;
	private JLabel totalamoutlab;
	private JButton ackbtn;
	private JButton cancelbtn;
	
	public ShowSaveDetail2(){
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		
		showdetaillab = new JLabel("存入钞票明细");
		showdetaillab.setFont(new Font("黑体",Font.BOLD,25));
		
		accforsavelab = new JLabel("存入账号：");
		accforsavelab.setFont(new Font("黑体",Font.BOLD,20));
		acclab = new JLabel("----");
		acclab.setFont(new Font("黑体",Font.BOLD,20));
		acclab.setText(LoadInterface.account);
		
		accnamelab = new JLabel("存入户名：");
		accnamelab.setFont(new Font("黑体",Font.BOLD,20));
		namelab = new JLabel("---");
		namelab.setFont(new Font("黑体",Font.BOLD,20));
		namelab.setText(LoadInterface.name);
		
		totallab = new JLabel("总 金 额：");
		totallab.setFont(new Font("黑体",Font.BOLD,20));
		totalamoutlab = new JLabel("--.00元");
		totalamoutlab.setFont(new Font("黑体",Font.BOLD,20));
		totalamoutlab.setText(""+Save.savemoney);
		
		ackbtn = new JButton("确认");
		ackbtn.addActionListener(this);
		cancelbtn = new JButton("取消");
		cancelbtn.addActionListener(this);
		
		cp.add(showdetaillab,new GridBagConstraints(0,0,4,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(140, 0, 0, 0),0,0));
		
		cp.add(accforsavelab,new GridBagConstraints(1,1,1,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(accnamelab,new GridBagConstraints(1,2,1,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(totallab,new GridBagConstraints(1,3,1,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
				
		
		cp.add(acclab,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, -50, 0, 0),0,0));
		cp.add(namelab,new GridBagConstraints(2,2,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, -50, 0, 0),0,0));
		cp.add(totalamoutlab,new GridBagConstraints(2,3,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, -50, 0, 0),0,0));
		
		
		cp.add(ackbtn,new GridBagConstraints(3,5,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		cp.add(cancelbtn,new GridBagConstraints(0,5,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		
		ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0,0,ii.getIconWidth(),ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel)cp;
		jp.setOpaque(false);
        
		setBounds(300, 40, ii.getIconWidth(), ii.getIconHeight());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new ShowSaveDetail2();
	}

	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		if(btn==ackbtn){
			new UserInterface();
			this.dispose();
		}
		if(btn==cancelbtn){
			new UserInterface();
			this.dispose();
		}
		
	}
}
