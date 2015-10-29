package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;



public class Transfer3 extends JFrame implements ActionListener{

	Container cp;
	private JLabel showacklab;
	private JLabel acclab;
	private JLabel showacclab;
	private JLabel namelab;
	private JLabel shownamelab;
	private JLabel transamolab;
	private JTextField amojtf;
	private JButton ackbtn;
	private JButton backbtn;
	private JButton exitbtn;
	static double money;
	public Transfer3(){
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
	
		showacklab =new JLabel("请确认账号信息无误后输入转账金额");
		showacklab.setFont(new Font("黑体",Font.BOLD,25));
		
		acclab =new JLabel("账    户：");
		acclab.setFont(new Font("黑体",Font.BOLD,20));
		
		showacclab =new JLabel("--");
		showacclab.setFont(new Font("黑体",Font.BOLD,20));
		showacclab.setText(Transfer.account);
		
		namelab =new JLabel("户    名：");
		namelab.setFont(new Font("黑体",Font.BOLD,20));
		
		
		shownamelab =new JLabel();
		shownamelab.setFont(new Font("黑体",Font.BOLD,20));
		shownamelab.setText(Transfer.name);


		
		transamolab =new JLabel("转账金额：");
		transamolab.setFont(new Font("黑体",Font.BOLD,20));
		
		amojtf = new JTextField();
		amojtf.setFont(new Font("黑体",Font.BOLD,20));
		backbtn=new JButton("回主菜单");
		backbtn.addActionListener(this);
		ackbtn=new JButton("确认");
		ackbtn.addActionListener(this);
		exitbtn=new JButton("退卡");
		exitbtn.addActionListener(this);
		
		cp.add(showacklab,new GridBagConstraints(0,0,4,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(150, 0, 0, 0),0,0));
		cp.add(acclab,new GridBagConstraints(1,1,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(40, 0, 0, 30),0,0));
		cp.add(showacclab,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(40, -25, 0, 0),0,0));
		
		cp.add(namelab,new GridBagConstraints(1,2,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 30, 30),0,0));
		cp.add(shownamelab,new GridBagConstraints(2,2,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, -25, 30, 0),0,0));
		
		cp.add(transamolab,new GridBagConstraints(1,3,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 100, 30),0,0));
		cp.add(amojtf,new GridBagConstraints(2,3,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, -25, 100, 0),75,10));
		
		cp.add(exitbtn,new GridBagConstraints(0,4,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		cp.add(ackbtn,new GridBagConstraints(3,3,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(backbtn,new GridBagConstraints(3,4,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		
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
		new Transfer3();
	}

	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn == exitbtn){
			new LoadInterface();
			this.dispose();
		}else if(btn == ackbtn){
			double transferMoney=Double.parseDouble(amojtf.getText());
			try {
				Statement state = Connect.dbConn.createStatement();
				Statement state1 = Connect.dbConn.createStatement();
				String sql = "select balance from userMessage where account = '"+LoadInterface.account+"'";
				String sql1= "select balance from userMessage where account = '"+Transfer.account+"'";
				ResultSet rs=state.executeQuery(sql);
				ResultSet rs1=state1.executeQuery(sql1);
				rs.next();
				double balance = rs.getDouble("balance")-transferMoney;
				rs1.next();
				double balance1 = transferMoney+rs1.getDouble("balance");
				Statement state3 = Connect.dbConn.createStatement();
				Statement state4 = Connect.dbConn.createStatement();
				String sql2="update userMessage set balance ='"+balance+"' where account='"+LoadInterface.account+"'";
				String sql3="update userMessage set balance = '"+balance1+"' where account='"+Transfer.account+"'";
				int a=state3.executeUpdate(sql2);
				int b=state4.executeUpdate(sql3); 
				if(a>0 && b>0){
					Date now = new Date();
					SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
					Statement state5 = Connect.dbConn.createStatement();
					Statement state6 = Connect.dbConn.createStatement();
					String sql4="insert into trade (tradeDate,tradeMoney,remark,account )values('"+matter.format(now)+"','"+transferMoney+"','转出','"+LoadInterface.account+"')";
					String sql5="insert into trade (tradeDate,tradeMoney,remark,account )values('"+matter.format(now)+"','"+transferMoney+"','转入','"+Transfer.account+"')";
					int c=state5.executeUpdate(sql4);
					int d=state6.executeUpdate(sql5); 
					if(c>0&&d>0){
						JOptionPane.showMessageDialog(this, "转账成功");
						new UserInterface();
						this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(this, "转账失败");
					}
					state5.close();
					state6.close();
				}
				else{
					JOptionPane.showMessageDialog(this, "转账失败");
				}
				rs.close();rs1.close();state.close();state1.close();state3.close();state4.close();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
			
		
		}else if(btn == backbtn){
	        new UserInterface(); 
	        this.dispose();
		}
	}
}
