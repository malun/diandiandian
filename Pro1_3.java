//取款页面
package Pace;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;


public class Pro1_3 extends JFrame implements ActionListener{
	int time=60;
	JLabel lab2=new JLabel();
	String a1="2_1_100.png";
	String a2="1_1_1000.png";
	String a3="2_1_300.png";
	String a4="1_1_2500.png";
	String a5="2_1_500.png";
	String a6="1_1_5000.png";
	String a7="2_1_取消.png";
	String a8="1_1_确认.png";
	String b1="2_2_100.png";
	String b2="1_2_1000.png";
	String b3="2_2_300.png";
	String b4="1_2_2500.png";
	String b5="2_2_500.png";
	String b6="1_2_5000.png";
	String b7="2_2_取消.png";
	String b8="1_2_确认.png";
	String c1="2_3_100.png";
	String c2="1_3_1000.png";
	String c3="2_3_300.png";
	String c4="1_3_2500.png";
	String c5="2_3_500.png";
	String c6="1_3_5000.png";
	String c7="2_3_取消.png";
	String c8="1_3_确认.png";
	boolean kg=true;
	Connect h=null;
	JFrame jf=new JFrame("取款界面");
	MyButton btn1=new MyButton(a1,b1,c1);
	MyButton btn2=new MyButton(a2,b2,c2);
	MyButton btn3=new MyButton(a3,b3,c3);
	MyButton btn4=new MyButton(a4,b4,c4);
	MyButton btn5=new MyButton(a5,b5,c5);
	MyButton btn7=new MyButton(a7,b7,c7);
	MyButton btn6=new MyButton(a6,b6,c6);
	MyButton btn8=new MyButton(a8,b8,c8);
	JLabel lab1=new JLabel("输入取款金额:");
	JTextField txf1=new JTextField();
	String s=null;
	String z=null;
	public Pro1_3(String c,Connect k){
		s=c;
		h=k;
		Container cp=jf.getContentPane();
		jf.setSize(1024,640);
		jf.setLocation(180,50);
		String path="主页面2.jpg";
		Image backGround=new ImageIcon(path).getImage();
		JLabel label = new aLabel(backGround);
		label.setBounds(0, 0, jf.getWidth(), jf.getHeight());
		JPanel imagePanel = (JPanel) jf.getContentPane();  
        imagePanel.setOpaque(false);  
        // 把背景图片添加到分层窗格的最底层作为背景  
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		lab2.setFont(new   java.awt.Font("Dialog",   1,   40));
		lab1.setFont(new   java.awt.Font("Dialog",   1,   25));
		txf1.setFont(new   java.awt.Font("Dialog",   1,   25));
		lab2.setForeground(Color.green);
		lab2.setOpaque(true);
		lab2.setBackground(Color.black);
		cp.setLayout(null);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		lab2.setBounds(500,80,70,40);
		btn1.setBounds(1,300,198,47);
		btn2.setBounds(810,300,198,47);
		btn3.setBounds(1,360,198,47);
		btn4.setBounds(810,360,198,47);
		btn5.setBounds(1,420,198,47);
		btn7.setBounds(1,480,198,47);
		btn6.setBounds(810,420,198,47);
		btn8.setBounds(810,480,198,47);
		lab1.setBounds(450,150,200,40);
		txf1.setBounds(430,220,200,40);
		cp.add(btn1);
		cp.add(btn2);
		cp.add(btn3);
		cp.add(btn4);
		cp.add(btn5);
		cp.add(btn6);
		cp.add(btn7);
		cp.add(btn8);
		cp.add(lab1);
		cp.add(txf1);
		cp.add(lab2);
		cp.add(label);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setVisible(kg);
		new Thread(new MyThread()).start();
	}
	class MyThread implements Runnable{
		public void run(){
			while(time>0){
				if(kg==false)break;
				time--;
				lab2.setText(time+"");
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			kg=false;
			if(time==0){
			new Pro1_1();
			jf.setVisible(false);
			}
		}
	}
	void sqlconnq(String x){
		  try{
				Connection dbConn=DriverManager.getConnection(h.dbURL,h.userName,h.userPwd);
				Statement sql1=dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			  String str="SELECT*FROM "+x+"";
			  ResultSet rs=sql1.executeQuery(str);
			  int g=0;
			  rs.last();
			  g=rs.getInt(1)+1;
			  String a=txf1.getText();
			  float b=Float.parseFloat(a);
			  float f=0;
			  GetTime t=new GetTime();
			  String c=t.mDateTime;
			  f=b+f;
			  if(f<100)
			  {
				  f=0;
				  new Pro2_14();
				  txf1.setText(null);
			  }else
			  {
				  
				  if((f%100)==0)
				  {
			  float d=0;
			  float z=0;
			  float y=0;
			  g=g+1;
				  float fl1=rs.getFloat(7);
				  if(fl1>=f){
					  if(f<=5000)
					  {
				  fl1=fl1-f;
				  String Gengstr="('"+g+"','"+c+"','"+d+"','"+z+"','"+y+"','"+f+"','"+fl1+"')";
					String xinstr="INSERT INTO "+x+" VALUES"+Gengstr;
					sql1.executeUpdate(xinstr);
					sqlconnA(x);
					new Pro1_2(s,h);
					kg=false;
					jf.setVisible(kg);
					new Pro2_16();
					  }else
					  {
						  new Pro2_31();
						  txf1.setText(null);
						  time=60;
					  }
				  }else{
					  new Pro2_29();
					  txf1.setText(null);
					  time=60;
				  }
				  }else
				  {
					  new Pro2_15();
					  txf1.setText(null);
					  time=60;
				  }
			  }
		  }catch(SQLException e1){
				e1.printStackTrace();
			}
	}
	void sqlconnd(){
		  try{
			  Connection dbConn=DriverManager.getConnection(h.dbURL,h.userName,h.userPwd);
			  Statement sql=dbConn.createStatement();
			  String str="SELECT*FROM 储蓄卡表 WHERE 账户号='"+s+"'";
			  ResultSet rs=sql.executeQuery(str);
			  while(rs.next()){
				  boolean b=rs.getBoolean(4);
				  if(b){
					  new Pro1_2(s,h);
					  kg=false;
					  jf.setVisible(kg);
					  new Pro2_22();
				  }else
				  {
					  String x=rs.getString(2);
					  sqlconnq(x);
				  }
			  }
			  dbConn.close();
		  }catch(SQLException e1){
				e1.printStackTrace();
			}
	}
	void sqlconnA(String x){  
		  try{
			  Connection dbConn=DriverManager.getConnection(h.dbURL,h.userName,h.userPwd);
			  Statement  sql2=dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			  String str="SELECT*FROM ATM表";
			  ResultSet rs=sql2.executeQuery(str);
			  int g=0;
			  rs.last();
				  int sb=rs.getInt(1);
					  g=sb+1;
			  String a=txf1.getText();
			  float b=Float.parseFloat(a);
			  float f=0;
			  GetTime t=new GetTime();
			  String c=t.mDateTime;
			  f=b+f;
              g=g+1;
			  float d=0;
				  float fl1=rs.getFloat(5);
				  if(fl1>=f){
				  fl1=fl1-f;
				  String Gengstr="('"+g+"','"+s+"','"+d+"','"+f+"','"+fl1+"','"+c+"')";
					String xinstr="INSERT INTO ATM表 VALUES"+Gengstr;
					sql2.executeUpdate(xinstr);
				  }else{
					  new Pro2_23();
					  txf1.setText(null);
					  time=60;
				  }
		  }catch(SQLException e1){
				e1.printStackTrace();
			}
	}
	
public void actionPerformed(ActionEvent e){
	JButton btn=(JButton)e.getSource();
	if(btn==btn1)
		{
		txf1.setText("100");
		time=60;
		}
	if(btn==btn2)
		{
		txf1.setText("1000");
		time=60;
		}
	if(btn==btn3)
		{
		txf1.setText("300");
		time=60;
		}
	if(btn==btn4)
		{
		txf1.setText("2500");
		time=60;
		}
	if(btn==btn5)
		{
		txf1.setText("500");
		time=60;
		}
	if(btn==btn6)
	{
	txf1.setText("5000");
	time=60;
	}
	if(btn==btn7)
	{
	new Pro1_2(s,h);
	kg=false;
	jf.setVisible(false);
	}
	if(btn==btn8)
		{
		sqlconnd();
		}
	}
}
