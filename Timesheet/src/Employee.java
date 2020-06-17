import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Employee {
	
	private static BufferedReader keyboard = new BufferedReader( new InputStreamReader( System.in ) );

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		try ( FileInputStream fis = new FileInputStream( "conf.properties" ) ) {
			props.load( fis );
		}
		
		// Connexion à la base de données
		Class.forName( props.getProperty( "jdbc.driver.class" ) );
		
		String url = props.getProperty( "jdbc.url" );
		String dbLogin = props.getProperty( "jdbc.login" );
		String dbPassword = props.getProperty( "jdbc.password" );
		
		try ( Connection connection = DriverManager.getConnection( url, dbLogin, dbPassword ) ) {
			
			//Ajoute un employé dans la base de donnée
			String addEmployee = "INSERT INTO Employee (Login, Password)" + "VALUES ('Mickey', 'Mouse')";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(addEmployee);		
			}
			
			
			String readedLogin = "";
		
			// L'employé se connecte (login = Jean + password = Pierre) avec preparedStatement
			System.out.println( "Please sign in" );
			while( true ) {
				System.out.print( "Login: ");
				String login = keyboard.readLine();
				System.out.print( "Password: ");
				String password = keyboard.readLine();
				
				String strSql = "SELECT * FROM Employee WHERE Login=? AND Password=?";
				try ( PreparedStatement statement = connection.prepareStatement(strSql) ) {
					statement.setString( 1,  login );
					statement.setString( 2,  password );
					try (ResultSet resultSet = statement.executeQuery()){
						if ( resultSet.next() ) {
							strSql = "UPDATE Employee SET connectionNumber=connectionNumber+1 WHERE idEmployee=?";
							try ( PreparedStatement stUpdate = connection.prepareStatement(strSql) ) {
								stUpdate.setInt( 1, resultSet.getInt("IdEmployee") );
								stUpdate.executeUpdate();
							}
						readedLogin = resultSet.getString( "login" );
						break;
					}
				}		
				
				// Ordre SQL est concaténer et permet l'injection SQL
				/*String ajoutEmploye = "SELECT * FROM Employee WHERE Login='" + login + "' AND password='" + password + "'";
				try ( Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery( ajoutEmploye ) ) {
					if ( resultSet.next() ) {
						strSql = "UPDATE Employee SET connectionNumber=connectionNumber+1 WHERE idEmployee=" + resultSet.getInt( "idEmployee" );
						try ( Statement stUpdate = connection.createStatement() ) {
							stUpdate.executeUpdate( strSql );
						}
						
						readedLogin = resultSet.getString( "login" );
						break;
					}*/
					System.out.println( "Wrong Login/Password - Try again" );
				}
			}
			
			System.out.println( readedLogin + " is connected" );
		}
		
	}

}

