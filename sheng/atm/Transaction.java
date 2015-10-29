package atm;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Transaction extends JFrame implements ActionListener{
    private JTable table;
    private JScrollPane pane;
    private JButton btn ;
    Container cp;
    public Transaction() throws SQLException{
    	cp=getContentPane();
    	cp.setLayout(null);
    	Statement state =Connect.dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    	String sql = "select * from trade where account ='"+LoadInterface.account+"'";
    	ResultSet rs = state.executeQuery(sql);
    	rs.last();
    	int length=rs.getRow();
    	String[][] st=new String[length][3];
    	rs.beforeFirst();
    	int i=0;
    	while(rs.next()){
    		st[i][0]=rs.getString("remark");
    		st[i][1]=rs.getString("tradeDate");
    		st[i][2]=rs.getString("tradeMoney");
    		i++;
    	}
    
    	String[] a = {"交易类型","交易时间","交易金额"};
    	table= new JTable(st,a);
    	pane=new JScrollPane(table);
    	btn=new JButton("回主菜单");
    	btn.addActionListener(this);
    	pane.setBounds(100, 150, 600, 300);;
    	btn.setBounds(0,490,100,30);
    	cp.add(pane);
    	cp.add(btn); 
    	ImageIcon ii = new ImageIcon("src/background.jpg");
		JLabel bdlab = new JLabel(ii);
		bdlab.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
		this.getLayeredPane().add(bdlab,new Integer(Integer.MIN_VALUE));
		setBounds(300,40,ii.getIconWidth(), ii.getIconHeight());
		JPanel jp = (JPanel)cp;
		jp.setOpaque(false);
    	setVisible(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	rs.close();state.close();
     }
    public static void main(String args[]) throws SQLException{
    	new Transaction();
    }
	public void actionPerformed(ActionEvent arg0) {
		new UserInterface();
		this.dispose();		
	}
}
