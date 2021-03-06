/************************************
 * Workshop #11
 * Course: JAC444 - Semester 4
 * Last Name: Truong
 * First Name: Hung
 * ID: 147779193
 * Section: NEE
 * This assignment represents my own work in accordance with Seneca Academic Policy.
 * Signature
 * Date: 4/17/2021
 */

package workshop11.task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LiveLab {
	
	public static final String connectionName = "jdbc:sqlite:C:\\\\Users\\\\HungD\\\\sqlite-tools-win32-x86-3350400\\\\JAC444WS11.db";
	
	public Connection connect() {
		try {
			Connection conn = DriverManager.getConnection(connectionName);
			if(conn != null) {
				return conn;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void createTable() {
		Connection conn = connect();
		try {
			Statement stmt = conn.createStatement();
			crtAGSStudent(stmt);
			crtExerciseAssigned(stmt);
			crtAGSLog(stmt);
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dropTable() {
		Connection conn = connect();
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute("DROP TABLE IF EXISTS AGSStudent");
			stmt.execute("DROP TABLE IF EXISTS ExerciseAssigned");
			stmt.execute("DROP TABLE IF EXISTS AGSLog");
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//Methods to select tables and display on console
	public void showAGSStudent() {
		Connection conn = connect();
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute("SELECT * FROM AGSStudent");
			ResultSet results = stmt.getResultSet();
			
			System.out.println("----------------------------------");
			System.out.println("AGSStudent Table                 |");
			System.out.println("----------------------------------");
			while(results.next()) {
				System.out.println(results.getString("username") + " " 
						+ results.getString("password") + " " 
						+ results.getString("fullname") + " "
						+ results.getString("instructorEmail"));
			}
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void showExerciseAssigned() {
		Connection conn = connect();
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute("SELECT * FROM ExerciseAssigned");
			ResultSet results = stmt.getResultSet();
			
			System.out.println("----------------------------------");
			System.out.println("ExerciseAssigned Table           |");
			System.out.println("----------------------------------");
			while(results.next()) {
				System.out.println(results.getString("instructorEmail") + " " 
						+ results.getString("exerciseName") + " " 
						+ results.getDouble("maxScore"));
			}
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void showAGSLog() {
		Connection conn = connect();
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute("SELECT * FROM AGSLog");
			ResultSet results = stmt.getResultSet();
			
			System.out.println("----------------------------------");
			System.out.println("AGSLog Table                     |");
			System.out.println("----------------------------------");
			while(results.next()) {
				System.out.println(results.getString("username") + " " 
						+ results.getString("exerciseName") + " " 
						+ results.getDouble("score") + " "
						+ results.getInt("Submitted"));
			}
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Methods to create Live Lab tables if not present already
	public void crtAGSStudent(Statement stmt) {
		try {
			stmt.execute("CREATE TABLE IF NOT EXISTS AGSStudent ("
					+ "username VARCHAR(50) NOT NULL, "
					+ "password VARCHAR(50) NOT NULL, "
					+ "fullname VARCHAR(200) NOT NULL, "
					+ "instructorEmail VARCHAR(100) NOT NULL, "
					+ "CONSTRAINT pkAGSStudent PRIMARY KEY (username))");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void crtExerciseAssigned(Statement stmt) {
		try {
			stmt.execute("CREATE TABLE IF NOT EXISTS ExerciseAssigned ("
					+ "instructorEmail VARCHAR(100), "
					+ "exerciseName VARCHAR(100), "
					+ "maxscore DOUBLE DEFAULT 10, "
					+ "CONSTRAINT pkCustomExercise PRIMARY KEY (instructorEmail, exerciseName))");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void crtAGSLog(Statement stmt) {
		try {
			stmt.execute("CREATE TABLE IF NOT EXISTS AGSLog ("
					+ "username VARCHAR(50), "
					+ "exerciseName VARCHAR(100), "
					+ "score DOUBLE DEFAULT NULL, "
					+ "submitted BIT DEFAULT 0, "
					+ "CONSTRAINT pkLog PRIMARY KEY (username, exerciseName))");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Methods to add records to Live Lab tables
	public void arAGSStudent(Statement stmt, Student src) throws SQLException {
		try {
			stmt.execute("INSERT INTO AGSStudent(username, password, fullname, instructorEmail) VALUES ('"
					+ src.getUsername() + "', '"
					+ src.getPassword() + "', '"
					+ src.getFullname() + "', '"
					+ src.getInstructorEmail() + "')");
			stmt.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void arExerciseAssigned(Statement stmt, String iE, String eN, double mS) throws SQLException {
		try {
			stmt.execute("INSERT INTO ExerciseAssigned(instructorEmail, exerciseName, maxScore) VALUES ('"
					+ iE + "', '"
					+ eN + "', "
					+ mS + ")");
			stmt.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void arAGSLog(Statement stmt, String un, String eN, double score, int submitted) throws SQLException {
		try {
			stmt.execute("INSERT INTO AGSLog(username, exerciseName, score, submitted) VALUES ('"
					+ un + "', '"
					+ eN + "', "
					+ score + ", "
					+ submitted + ")");
			stmt.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void noSubmit() throws SQLException {
		Connection conn = connect();
		boolean exist = false;
		try {
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			Statement stmt3 = conn.createStatement();
			Statement temp = conn.createStatement();
			
			stmt.execute("SELECT * FROM AGSStudent");
			ResultSet student = stmt.getResultSet();
			while(student.next()) {
				stmt2.execute("SELECT * FROM ExerciseAssigned WHERE instructorEmail = '"
						+ student.getString("instructorEmail") + "'");
				ResultSet exercise = stmt2.getResultSet();
				while(exercise.next()) {
					stmt3.execute("SELECT * FROM AGSLog WHERE username = '"
							+ student.getString("username") + "'");
					ResultSet log = stmt3.getResultSet();
					while(log.next()) {
						if(exercise.getString("exerciseName").contains(log.getString("exerciseName"))) {
							exist = true;
						}
					}
					if(!exist) {
						arAGSLog(temp, student.getString("username"), exercise.getString("exerciseName"), 0, 0);
					}
					exist = false;
				}
			}
		
			stmt.close();
			stmt2.close();
			stmt3.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		conn.close();
	}
}
