package atmed;
import javax.swing.*;

import atm.LoadInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Administrator extends JFrame implements ActionListener{
    JButton btn1 = new JButton("开户");
    JButton btn2 = new JButton("销户");
    JButton btn3 = new JButton("冻结");
    JButton btn4 = new JButton("解冻");
    JButton btn5 = new JButton("退出");
    Container cp = getContentPane();
    public Administrator(){
    	super("管理员界面");
    	cp.setLayout(null);
    	btn1.setBounds(0,400,90,40);
    	btn2.setBounds(0,500,90,40);
    	btn3.setBounds(900,300,90,40);
    	btn4.setBounds(900,400,90,40);
    	btn5.setBounds(900,500,90,40);
    	cp.add(btn1);
    	cp.add(btn2);
    	cp.add(btn3);
    	cp.add(btn4);
    	cp.add(btn5);
    	btn1.addActionListener(this);
    	btn2.addActionListener(this);
    	btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        ImageIcon ii = new ImageIcon("src/5.jpg");
        JLabel bdlab = new JLabel(ii);
        bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
        this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)cp;
        jp.setOpaque(false);  
		setBounds(200,5,ii.getIconWidth(), ii.getIconHeight());
  	    setVisible(true);
  	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    public static void main(String args[]){
    	new Administrator();
    }
    public void actionPerformed(ActionEvent e){
    	JButton btn = (JButton)e.getSource();
    	if(btn == btn1){
    		new OpenAccount();
    		this.dispose();
    	}
    	if(btn == btn2){
    		new CloseAccount();
    		this.dispose();
    	}
    	if(btn == btn3){
    		new Freeze();
    		this.dispose();
    	}
    	if(btn == btn4){
    		new UnFreeze();
    		this.dispose();
    	}
    	if(btn == btn5){
    		new LoadInterface();
    		this.dispose();
    	}
    }
}
