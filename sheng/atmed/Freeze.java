package atmed;
import javax.swing.*;

import atm.Connect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Freeze extends JFrame implements ActionListener{
    JLabel lab1 = new JLabel("账号");
    JTextField txf1 = new JTextField();
    JButton btn1 = new JButton("确定");
    JButton btn2 = new JButton("返回");
    Container cp = getContentPane();
    public Freeze(){
    	super("冻结界面"); 
    	cp.setLayout(null);
    	lab1.setBounds(200,250,100,30);
    	txf1.setBounds(300,250,300,40);
    	btn1.setBounds(0,400,70,30);
    	btn2.setBounds(722,400,70,30);
    	cp.add(lab1);
    	cp.add(txf1);
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
    	new Freeze();
    }
    public void actionPerformed(ActionEvent e){
    	JButton btn = (JButton) e.getSource();
    	if(btn == btn1){
    		try {
				Statement state = Connect.dbConn.createStatement();
				String sql="select * from userMessage where account ='"+txf1.getText()+"'and freeze=0 and closee=0 and admin=0";
				ResultSet rs = state.executeQuery(sql);
				rs.next();
				if(rs.getRow()==0){
					JOptionPane.showMessageDialog(this, "输入有误");
				}
				else{
					Statement state1 = Connect.dbConn.createStatement();
					String sql1="update userMessage set freeze=1 where account ='"+txf1.getText()+"'";
					int a= state1.executeUpdate(sql1);
					if(a>0){
						JOptionPane.showMessageDialog(this, "冻结成功");
						new Administrator();
						this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(this, "冻结失败");
					}
					state1.close();
				}
				rs.close();
				state.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	}
    	if(btn == btn2){
    		new Administrator();
    	    this.dispose();
    	}
    }
}
