package businesscomponents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FilenameUtils;
import org.apache.derby.tools.JDBCDisplayUtil;

import com.sun.jna.platform.FileUtils;

import decrypter.Password_Decrypter;
import supportlibraries.ScriptHelper;

public class Database extends CommonFunctions {

	public Database(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}

	public void createQuery() throws ClassNotFoundException, SQLException, FileNotFoundException {
		String[] query = dataTable.getData("WorkFlow", "Queries").split(";");
		String strDBLogin = dataTable.getData("General_Data", "DBUserName").trim();
		String strDecryptedPWD = Password_Decrypter.decrypt(dataTable.getData("General_Data", "DBPassword").trim());
		File f = new File(System.getProperty("user.dir") + "\\database.txt");
		PrintStream o = new PrintStream(f);
		System.setOut(o);
		// TODO Auto-generated method stub
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@sjidmodbqag01.na.gilead.com:1521:IIQSQA01",
				strDBLogin, strDecryptedPWD);
		Statement s = con.createStatement();
		try {

			for (String Query : query) {
				ResultSet rs = s.executeQuery(Query);
				JDBCDisplayUtil.DisplayResults(o, s, con);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
