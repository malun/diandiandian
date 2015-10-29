package atm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class Transfer extends JFrame implements ActionListener{
	Container cp;
	private JLabel showrecevierlab;
	private JTextField inputtf;
	private JLabel tipslab;
	private JButton backbtn;
	private JButton ackbtn;
	static String name;
	static String account;
	public Transfer(){
		super("TransferInterface");
		cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		
		showrecevierlab = new JLabel("请输入转入卡号");
		showrecevierlab.setFont(new Font("黑体",Font.BOLD,25));
		
		inputtf = new JTextField(10);
		tipslab =new JLabel();
		
		backbtn = new JButton("回主菜单");
		backbtn.addActionListener(this);
		
		ackbtn = new JButton("确认");
		ackbtn.addActionListener(this);
		cp.add(showrecevierlab,new GridBagConstraints(0,0,3,1,1,1,
				GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets(100, 0, 0, 0),0,0));
		cp.add(inputtf,new GridBagConstraints(0,1,3,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(25, 0, 0, 0),130,10));
		cp.add(tipslab,new GridBagConstraints(1,2,1,3,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0, 0, 0, 10),0,0));

		cp.add(ackbtn,new GridBagConstraints(2,5,1,1,1,1,
				GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(1, 0, 5, 0),0,0));
		cp.add(backbtn,new GridBagConstraints(0,5,1,1,1,1,
				GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1, 0, 5, 0),0,0));
		
		ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0,0,ii.getIconWidth(),ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel)cp;
		jp.setOpaque(false);
        
		setBounds(300, 100, ii.getIconWidth(), ii.getIconHeight());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new Transfer();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		if(btn == backbtn){
			new UserInterface();
			this.dispose();
		}
		if(btn==ackbtn){
		    try {
				Statement state = Connect.dbConn.createStatement();
				String sql="select * from userMessage where account ='"+inputtf.getText()+"'";
				ResultSet rs = state.executeQuery(sql);
				rs.next();
				if(rs.getRow()==0){
					JOptionPane.showMessageDialog(this, "账号输入有误");
				}
				else{
					if(rs.getInt("admin")==1){
						JOptionPane.showMessageDialog(this, "此账号为管理员账号");
					}
					if(rs.getInt("closee")==1){
						JOptionPane.showMessageDialog(this, "账号输入有误");
					}
					if(rs.getInt("freeze")==1){
						JOptionPane.showMessageDialog(this, "此账号已冻结");
					}
					else{
						name=rs.getString("name");
						account = rs.getString("account");
						new Transfer3();
						this.dispose();
					}
				}
				rs.close();state.close();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}			
	}
}
