package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
public class QueryBalance extends JFrame implements ActionListener{
	private Container cp;
	private JButton backbtn;
	private JButton transdetailsbtn;
	private JLabel showbalancelab;
	private JLabel accountbanllab;

	String balance1;
	public QueryBalance(){
		super("QueryBalance");
		cp=getContentPane();
		cp.setLayout(new GridBagLayout());
		
		
		backbtn= new JButton("回主菜单");
		backbtn.addActionListener(this);
		
		transdetailsbtn = new JButton("交易明细");

		
		
		showbalancelab = new JLabel("您的账户余额信息");
		showbalancelab.setFont(new Font("黑体",Font.BOLD,25));
		
		accountbanllab = new JLabel();
		accountbanllab.setFont(new Font("黑体",Font.BOLD,20));
		Statement state;
		try {
			state = Connect.dbConn.createStatement();
			String sql = "select balance from userMessage where account ='"+LoadInterface.account+"'";
			ResultSet rs = state.executeQuery(sql);
			rs.next();
			balance1=rs.getString(1);
			rs.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		accountbanllab.setText("您的账户余额为："+balance1);
		
		
		cp.add(accountbanllab,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets(5, 0, 0, 30),0,0));

		cp.add(backbtn,new GridBagConstraints(3,7,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(10, 0, 5, 0),0,0));

		cp.add(transdetailsbtn,new GridBagConstraints(0,7,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(10, 0, 5, 0),0,0));
		backbtn.addActionListener(this);
		transdetailsbtn.addActionListener(this);
		ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel)cp;
		jp.setOpaque(false);
		
		setBounds(300,40,ii.getIconWidth(), ii.getIconHeight());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new QueryBalance();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		if(btn == backbtn){
			new UserInterface();
		}else if(btn == transdetailsbtn){
			try {
				new Transaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		this.dispose();
	}
}
