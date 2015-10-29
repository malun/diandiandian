package atm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Connect {
	public static Connection dbConn = null;
	public Connect(){
		openConn();
		dbConn=getConn();
	}	
	public void openConn(){
		String driveName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=test";
		String userName="sa";
		String userPwd="12345678";
		
		try{
			Class.forName(driveName);//加载想要连接的数据库的驱动到JVM
//			要连接数据库，需要向java.sql.DriverManager请求并获得Connection对象，   
//		     该对象就代表一个数据库的连接。
			dbConn=DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("连接成功");
		}catch(Exception e1){
			e1.printStackTrace();
			System.out.println("连接失败");
		}  
}
	public Connection getConn(){
		return dbConn;
	}
	public static void main(String args[]){
		new Connect();
	}
}
