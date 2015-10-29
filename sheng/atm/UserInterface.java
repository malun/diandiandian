package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UserInterface extends JFrame implements ActionListener{
	private Container cp;
	private JButton querybtn;
	private	JButton transferbtn;
	private JButton changepwdbtn;
	private JButton exitbtn;
	private JButton withdrawalsbtn;
	private JButton savebtn;
	private JLabel transactionlab;
	
	public UserInterface(){
		super("UserInterface");
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		
		transactionlab = new JLabel("请您选择交易");
		transactionlab.setFont(new Font("黑体",Font.BOLD,25));
		
		querybtn = new JButton("余额查询");  
		querybtn.addActionListener(this);
		
		transferbtn = new JButton("转账");
		transferbtn.addActionListener(this);
		
		
		withdrawalsbtn = new JButton("取款");
		withdrawalsbtn.addActionListener(this);
		
		changepwdbtn = new JButton("改密");
		changepwdbtn.addActionListener(this);
		
		savebtn = new JButton("存款");
		savebtn.addActionListener(this);
		
		exitbtn = new JButton("退卡");
		exitbtn.addActionListener(this);
		
		cp.add(transactionlab,new GridBagConstraints(0,0,3,1,1,1,
				GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets(80, 0, 0, 0),0,0));
		cp.add(querybtn,new GridBagConstraints(0,1,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(20, 0, 0, 0),0,0));
		cp.add(transferbtn,new GridBagConstraints(0,2,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(exitbtn,new GridBagConstraints(0,3,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(withdrawalsbtn,new GridBagConstraints(2,1,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(20, 0, 0, 0),0,0));
		cp.add(changepwdbtn,new GridBagConstraints(2,2,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		cp.add(savebtn,new GridBagConstraints(2,3,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0));
		
		ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel)cp;
		jp.setOpaque(false);
		
		setBounds(300,40,ii.getIconWidth(), ii.getIconHeight());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
	    new UserInterface();
	}

	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == querybtn){
			new QueryBalance();
		}else if(btn == transferbtn){
			new Transfer();
			this.dispose();
		}else if(btn == changepwdbtn){
			new ChangePassword1();
		}else if(btn == withdrawalsbtn){
			new Withdrawals();
		}else if(btn == savebtn){
			new Save();
		}else if(btn == exitbtn){
			new LoadInterface();
		}
		this.dispose();
	}

}
