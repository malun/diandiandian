package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

import javax.swing.*;

import atmed.Administrator;
public class LoadInterface extends JFrame implements ActionListener{
	Container cp;
	JLabel accountlabel; 
	JLabel passwordlabel;
	JTextField accountfield;
	JPasswordField passwordfield;
	JButton loadbtn;
	JButton cancelbtn;
	static String account;
	static String password;
	static String name;
	static LoadInterface load;
	static int i=0;
	public LoadInterface(){
		super("LoadInterface");
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		
		accountlabel = new JLabel("账号："); 
		accountlabel.setFont(new Font("黑体",Font.BOLD,20));
		passwordlabel = new JLabel("密码：");
		passwordlabel.setFont(new Font("黑体",Font.BOLD,20));
		accountfield =new  JTextField(10);
	    accountfield.addKeyListener(new A());
		
		passwordfield = new JPasswordField(10);
	
		
		loadbtn = new JButton("登陆");
		loadbtn.addActionListener(this);
		
		cancelbtn = new JButton("取消");
		cancelbtn.addActionListener(this);
		
	    
		cp.add(accountlabel,new GridBagConstraints(1,0,1,1,1,1,
		        GridBagConstraints.SOUTHEAST,GridBagConstraints.NONE,new Insets(200, 0, 0, 0),0,0));
		cp.add(accountfield,new GridBagConstraints(2,0,1,1,1,1,
				GridBagConstraints.SOUTHWEST,GridBagConstraints.NONE,new Insets(200, 0, 0, 0),80,10));
		cp.add(passwordlabel,new GridBagConstraints(1,1,1,1,1,1,
				GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(50, 0, 30, 0),0,0));
		cp.add(passwordfield,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(50, 0, 30, 0),80,10));
		cp.add(loadbtn,new GridBagConstraints(0,2,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 25, 0),0,0));
		cp.add(cancelbtn,new GridBagConstraints(3,2,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 25, 0),0,0));
		ImageIcon ii = new ImageIcon("src/background.jpg");
        JLabel bdlab = new JLabel(ii);
        bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
        this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)cp;
        jp.setOpaque(false);  
		setBounds(300,40,ii.getIconWidth(), ii.getIconHeight());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new Connect();
		load= new LoadInterface();
		
	}
	class A extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			if(accountfield.getText().length()>=11){
				try {
					Statement state = Connect.dbConn.createStatement();
					String sql = "select * from userMessage where account = '"+accountfield.getText()+"'";
					ResultSet rs = state.executeQuery(sql);
					if(!rs.next()){
						JOptionPane.showMessageDialog(load, "账号输入有误");
						accountfield.setText("");
					}
					else{
						if(rs.getInt("closee")==1){
							JOptionPane.showMessageDialog(load, "账号输入有误");
							accountfield.setText("");
						}
						else if(rs.getInt("freeze")==1){
							JOptionPane.showMessageDialog(load, "账户已冻结");
							accountfield.setText("");
						}
						else{
							account = accountfield.getText();
						}
					}
					rs.close();
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton )e.getSource();
        if(btn==loadbtn){
        	String pwd = String.valueOf(passwordfield.getPassword());
        	try {
				Statement state = Connect.dbConn.createStatement();		
				String sql = "select * from userMessage where account = '"+account+"'";
				ResultSet rs =state.executeQuery(sql);
				rs.next();
				if(rs.getString("password").equals(pwd)){
					password=pwd;
					name=rs.getString("name");
					if(rs.getInt("admin")==1){
						new Administrator();	
						this.dispose();
					}
					else{
						new UserInterface();
					    this.dispose();
					}
					
				}
				else{
					JOptionPane.showMessageDialog(this, "密码错误");
					passwordfield.setText("");
					i++;
					if(i==3){
						Statement state1 = Connect.dbConn.createStatement();
						String sql1 = "update userMessage set freeze = 1 where account = '"+account+"'";
						int a1 = state1.executeUpdate(sql1);
						if(a1>0){
							JOptionPane.showMessageDialog(this, "您已输错三次密码，卡号已被冻结");
							accountfield.setText("");
							passwordfield.setText("");
						}
						state1.close();
					}
				}
				rs.close();
				state.close();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}   
        }
        if(btn==cancelbtn){
        	try {
				Connect.dbConn.close();
				System.out.println("连接关闭");
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
        	this.dispose();
        }
	}
}

