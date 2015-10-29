package atmed;
import javax.swing.*;

import atm.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
public class OpenAccount extends JFrame implements ActionListener {
	JLabel lab1 = new JLabel("请输入个人信息");
	JLabel lab2 = new JLabel("姓名");
	JLabel lab3 = new JLabel("身份证号码");
	JLabel lab4 = new JLabel("卡号");
    JLabel lab5 = new JLabel("密码");
	JLabel lab6 = new JLabel("再次输入密码");
	JTextField txf1 = new JTextField();
	JTextField txf2 = new JTextField();
	JTextField txf3 = new JTextField();
	JPasswordField txf4 = new JPasswordField();
	JPasswordField txf5 = new JPasswordField();
	JButton btn1 = new JButton("确定");
	JButton btn2 = new JButton("返回");
	Container cp = getContentPane();
	public OpenAccount(){
		super("开户界面");
		cp.setLayout(null);
		lab1.setFont(new Font("黑体", Font.PLAIN, 16));
		lab1.setBounds(300,130,150,30);
		lab2.setBounds(250,180,70,30);
		lab3.setBounds(220,245,70,30); 
		lab4.setBounds(250,310,70,30);
		lab5.setBounds(250,375,100,30);
		lab6.setBounds(200,440,100,30);
		txf1.setBounds(305,180,150,30);
		txf2.setBounds(305,245,150,30);
		txf3.setBounds(305,310,150,30);
		txf4.setBounds(305,375,150,30);
		txf5.setBounds(305,440,150,30);
		btn1.setBounds(0,450,70,30);
    	btn2.setBounds(722,450,70,30);
		cp.add(lab1);
		cp.add(lab2);
		cp.add(lab3);
		cp.add(lab4);
		cp.add(lab5);
		cp.add(lab6);
		cp.add(txf1);
		cp.add(txf2);
		cp.add(txf3);
		cp.add(txf4);
		cp.add(txf5);
		cp.add(btn1);
		cp.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		ImageIcon ii = new ImageIcon("src/background.jpg");
        JLabel bdlab = new JLabel(ii);
        bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
        this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)cp;
        jp.setOpaque(false);  
		setBounds(300,40,ii.getIconWidth(), ii.getIconHeight());
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	public static void main(String args[]){
		new OpenAccount();
	}
	public void actionPerformed(ActionEvent e){
		JButton btn = (JButton)e.getSource();
		if(btn == btn1){
			if(!String.valueOf(txf4.getPassword()).equals(String.valueOf(txf5.getPassword()))){
				JOptionPane.showMessageDialog(this, "密码输入不一致");
			}
			else{
				try {
					Statement state2 = Connect.dbConn.createStatement();
					String sql = "insert into userMessage(name,account,password,id,freeze,closee,admin,balance)values('"+txf1.getText()+"','"+txf3.getText()+"','"+String.valueOf(txf4.getPassword())+"','"+txf2.getText()+"',0,0,0,0)";
					int a=state2.executeUpdate(sql);
					if(a>0){
						JOptionPane.showMessageDialog(this, "开户成功");
						new Administrator();
						this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(this, "开户失败");
					}
					state2.close();
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		}
		if(btn == btn2){
			new Administrator();
			this.dispose();
		}
	}
	
	
	

}
