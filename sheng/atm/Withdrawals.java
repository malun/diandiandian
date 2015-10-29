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

public class Withdrawals extends JFrame implements ActionListener{
	private Container cp;
	private JLabel inputlab;
	private JTextField inputtf;
	private JLabel tipslab;
	private JButton onehdbtn;
	private JButton threehdbtn;
	private JButton fivehdbtn;
	private JButton onethsbtn;
	private JButton twothsandfivebtn;
	private JButton fivethsbtn;
	private JButton backbtn;
	private JButton ackbtn;
	static double withdrawls;
	static double todayMoney;
	public Withdrawals(){
		super("withdrawalInterface");
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		
		inputlab = new JLabel("请您选择或输入取款金额");
		inputlab.setFont(new Font("黑体",Font.BOLD,25));
		
		inputtf = new JTextField(7);
		tipslab = new JLabel();
		tipslab.setText("<html>本机提供面额为100的纸币<br>取款金额为面额的<br>单笔最大取款 金额为5000元</html>");
		tipslab.setFont(new Font("黑体",Font.BOLD,15));
		
		onehdbtn = new JButton("100");
		onehdbtn.addActionListener(this);
		threehdbtn = new JButton("300");
		threehdbtn.addActionListener(this);
		fivehdbtn = new JButton("500");
		fivehdbtn.addActionListener(this);
		onethsbtn = new JButton("1000");
		onethsbtn.addActionListener(this);
		twothsandfivebtn = new JButton("2500");
		twothsandfivebtn.addActionListener(this);
		fivethsbtn = new JButton("5000");
		fivethsbtn.addActionListener(this);
		backbtn = new JButton("回主菜单");
		backbtn.addActionListener(this);
		
		ackbtn = new JButton("确认");
		ackbtn.addActionListener(this);
		
		cp.add(inputlab,new GridBagConstraints(0,0,3,1,1,1,
				GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets(100, 0, 0, 0),0,0));
		cp.add(inputtf,new GridBagConstraints(1,1,1,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(25, 0, 0, 0),40,10));
		cp.add(tipslab,new GridBagConstraints(1,2,1,2,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(10, 0, 0, 0),0,0));
		cp.add(onehdbtn,new GridBagConstraints(0,1,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(10, 0, 0, 0),0,0));
		cp.add(threehdbtn,new GridBagConstraints(0,2,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(fivehdbtn,new GridBagConstraints(0,3,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(backbtn,new GridBagConstraints(0,4,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		cp.add(onethsbtn,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(10, 0, 0, 0),0,0));
		cp.add(twothsandfivebtn,new GridBagConstraints(2,2,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(fivethsbtn,new GridBagConstraints(2,3,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(ackbtn,new GridBagConstraints(2,4,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 5, 0),0,0));
		
		ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel) cp;
		jp.setOpaque(false);
		
		setBounds(300,100,ii.getIconWidth(),ii.getIconHeight());
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String args[]){
		new Withdrawals();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JButton btn = (JButton) e.getSource();
		if(btn==backbtn){
			new UserInterface();
			this.dispose();
		}
	
		if(btn==onehdbtn){
            withdrawls=100;
            try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0); 		
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1); 		
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
				
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
		if(btn==threehdbtn){
			withdrawls=300;
			try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0);
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1);
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
	    if(btn == fivehdbtn){
	    	withdrawls=500;
	    	try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0);
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1);
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
	    if(btn==onethsbtn){
	    	withdrawls=1000;
	    	try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0);
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1);
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}	
		}
	    if(btn==twothsandfivebtn){
	    	withdrawls=2500;
	    	try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0);
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1);
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
	    if(btn==fivethsbtn){
	    	withdrawls=5000;
	    	try {
            	Date now0 = new Date();
                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
                Statement state0=Connect.dbConn.createStatement();
                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
    			ResultSet rs0=state0.executeQuery(sql0);
    			while(rs0.next()){
    				todayMoney=todayMoney+rs0.getDouble(1);
    			}
    			if(todayMoney+withdrawls>20000){
    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
    			}
    			else{
    				Statement state = Connect.dbConn.createStatement();
    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
    				ResultSet rs = state.executeQuery(sql1);
    				rs.next();
    				String balance=rs.getString(1);
    				if(Double.parseDouble(balance)<withdrawls){
    					JOptionPane.showMessageDialog(this, "余额不足");
    				}
    				else{
    					double balance1=Double.parseDouble(balance)-withdrawls;
        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
        				int i = state.executeUpdate(sql);
        				if(i>0){
        				    Statement state1=Connect.dbConn.createStatement();
        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
        				    int a=state1.executeUpdate(sql2);
        				    if(a>0){
        				    	JOptionPane.showMessageDialog(this, "请取钞票");
        						new UserInterface();
        						this.dispose();
        				    }
        				    else{
        				    	JOptionPane.showMessageDialog(this, "取款失败");
        				    }
        				    state1.close();
        				}
        				rs.close();
        				state.close();
    				}
    				
    			}
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
	    
	    
		if(btn == ackbtn){
			withdrawls=Double.parseDouble(inputtf.getText());
			if((int)withdrawls%100!=0){
				JOptionPane.showMessageDialog(this, "请输入100的整数倍");
			}
			else {
				if(withdrawls>5000){
					JOptionPane.showMessageDialog(this, "单笔取款不得超过5000");
					inputtf.setText("");
				}
				else {
					try {
		            	Date now0 = new Date();
		                SimpleDateFormat matter0=new SimpleDateFormat("yyyy-MM-dd");
		                Statement state0=Connect.dbConn.createStatement();
		                String sql0="select tradeMoney from trade where account ='"+LoadInterface.account+"'and tradeDate='"+matter0.format(now0)+"' and remark='取出'";
		    			ResultSet rs0=state0.executeQuery(sql0);
		    			while(rs0.next()){
		    				todayMoney=todayMoney+rs0.getDouble(1);
		    			}
		    			if(todayMoney+withdrawls>20000){
		    				JOptionPane.showMessageDialog(this, "当日取款不得大于20000");
		    			}
		    			else{
		    				Statement state = Connect.dbConn.createStatement();
		    				String sql1 = "select balance from userMessage where account = '"+LoadInterface.account+"'";
		    				ResultSet rs = state.executeQuery(sql1);
		    				rs.next();
		    				String balance=rs.getString(1);
		    				if(Double.parseDouble(balance)<withdrawls){
		    					JOptionPane.showMessageDialog(this, "余额不足");
		    				}
		    				else{
		    					double balance1=Double.parseDouble(balance)-withdrawls;
		        			    String sql = "update userMessage set balance='"+balance1+"'where account = '"+LoadInterface.account+"'";
		        				int i = state.executeUpdate(sql);
		        				if(i>0){
		        				    Statement state1=Connect.dbConn.createStatement();
		        				    String sql2= "insert into trade(tradeDate,tradeMoney,remark,account)values('"+matter0.format(now0)+"','"+withdrawls+"','取出','"+LoadInterface.account+"')";
		        				    int a=state1.executeUpdate(sql2);
		        				    if(a>0){
		        				    	JOptionPane.showMessageDialog(this, "请取钞票");
		        						new UserInterface();
		        						this.dispose();
		        				    }
		        				    else{
		        				    	JOptionPane.showMessageDialog(this, "取款失败");
		        				    }
		        				    state1.close();
		        				}
		        				rs.close();
		        				state.close();
		    				}
		    				
		    			}
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}	
			}
			
		}
		}
	
	}


