package atm;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
public class ChangePassword2 extends JFrame implements KeyListener {
	private Container cp;
	private JLabel showinputpwdlab;
	private JLabel tipslab;
	private JButton exitbtn;
	private JPasswordField inputtf;
	
    public ChangePassword2(){
    	super("ChangePasswordInterface");
    	cp = getContentPane();
    	cp.setLayout(new GridBagLayout());
    	
    	showinputpwdlab = new JLabel("请再次输入您的新个人密码");
    	showinputpwdlab.setFont(new Font("黑体",Font.BOLD,25));
    	
    	tipslab = new JLabel("<html>请输入6位密码<br>温馨提示：为了加强保密性，提醒您尽量避免使用生日、手机<br>号码作为您账号或者银行卡的密码。</html>");
    	tipslab.setFont(new Font("黑体",Font.BOLD,15));
    	
        exitbtn = new JButton("退卡");
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
        
        setBounds(300, 40, ii.getIconWidth(), ii.getIconHeight());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String args[]){
    	new ChangePassword2();
    }

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		char[] inpswd = inputtf.getPassword();
		if(inpswd.length == 6){
            if(String.valueOf(inpswd).equals(ChangePassword1.newPassword)){
            	Statement state;
				try {
					state = Connect.dbConn.createStatement();
					String sql="update userMessage set password='"+ChangePassword1.newPassword+"'where account = '"+LoadInterface.account+"'";
	            	int a=state.executeUpdate(sql);
	            	if(a>0){
	            		JOptionPane.showMessageDialog(this, "改密成功");
	            		new LoadInterface();
	            		this.dispose();
	            	}
	            	state.close();
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
            	
            }
            else{
            	JOptionPane.showMessageDialog(this, "密码输入不一致");
            }
		}
		
	}
}