package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
public class ChangePassword1 extends JFrame implements ActionListener,KeyListener{
	private Container cp;
	private JLabel showinputpwdlab;
	private JLabel tipslab;
	private JButton exitbtn;
	private JPasswordField inputtf;
	static String newPassword;
    public ChangePassword1(){
    	super("ChangePasswordInterface");
    	cp = getContentPane();
    	cp.setLayout(new GridBagLayout());
    	
    	showinputpwdlab = new JLabel("请输入您的新个人密码");
    	showinputpwdlab.setFont(new Font("黑体",Font.BOLD,25));
    	
    	tipslab = new JLabel("<html>请输入6位密码<br>温馨提示：为了加强保密性，提醒您尽量避免使用生日、手机<br>号码作为您账号或者银行卡的密码。</html>");
    	tipslab.setFont(new Font("黑体",Font.BOLD,15));
    	
        exitbtn = new JButton("退卡");
        exitbtn.addActionListener(this);
        
        inputtf = new JPasswordField();
        inputtf.addKeyListener(this);
        
        ImageIcon ii = new ImageIcon("src/background.jpg");
        JLabel bdlab = new JLabel(ii);
        bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
        this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
        JPanel jp = (JPanel) cp;
        jp.setOpaque(false);
        
        jp.add(showinputpwdlab,new GridBagConstraints(0,0,2,1,1,1,GridBagConstraints.SOUTH,
        		GridBagConstraints.NONE,new Insets(100,0,0,0),0,0));
        jp.add(inputtf,new GridBagConstraints(0,1,2,1,1,1,GridBagConstraints.CENTER,
        		GridBagConstraints.NONE,new Insets(30,0,0,0),100,10));
        jp.add(tipslab,new GridBagConstraints(0,2,2,1,1,1,GridBagConstraints.SOUTH,
        		GridBagConstraints.NONE,new Insets(30,0,0,0),0,0));
        jp.add(exitbtn,new GridBagConstraints(0,3,1,1,1,1,GridBagConstraints.WEST,
        		GridBagConstraints.NONE,new Insets(0,0,5,0),0,0));
        
        setBounds(300,40, ii.getIconWidth(), ii.getIconHeight());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String args[]){
    	new ChangePassword1();
    }

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		if(btn == exitbtn){
			new LoadInterface();
		}
		this.dispose();
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		char[] iptpswd = inputtf.getPassword();
		if(iptpswd.length == 6){
			newPassword=String.valueOf(iptpswd);
			new ChangePassword2();
			this.dispose();
		}
		
	}
}
