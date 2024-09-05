package studentDbManagerJdbcProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentDbManager {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// for database creation
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
//			PreparedStatement preparedStatement = connection.prepareStatement("create database jdbcproject1studentdbmanager");
//			preparedStatement.execute();
//			connection.close();

			// for table creation
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject1studentdbmanager","root","root");
//			PreparedStatement preparedStatement = connection.prepareStatement("create table Student(USN varchar(10) primary key, Name varchar(30) not null, Address varchar(100) not null, Phone_Number bigInt(10) unique not null check(length(Phone_Number)=10), Gender varchar(1) not null, Stream varchar(20) not null)");
//			preparedStatement.execute();
//			connection.close();

			// change the constraint of Phone_Number
//			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject1studentdbmanager", "root", "root");
//			PreparedStatement preparedStatement=connection.prepareStatement("alter table Student modify Phone_Number bigInt(10) unique not null check(length(Phone_Number)=10 AND floor(Phone_Number/1000000000)>=6)");
//			preparedStatement.execute();
//			System.out.println("Phone number quality updated successfully");
//			connection.close();

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject1studentdbmanager",
					"root", "root");
			String reset = "\u001B[0m";
			String cyan = "\u001B[36m";
			String red = "\u001B[31m";
			String green = "\u001B[32m";
			String blue = "\u001B[34m";
			String magenta = "\u001B[35m";
			Scanner sc = new Scanner(System.in);
//			System.out.println(cyan+"Select your Operation by typing the number of the respective Operation:"+blue+"\n\t1. Add Student\n\t2. Fetch Student\n\t3. Remove Student\n\t4. Update Student Stream\n\t5. Fetch all the Students\n\t6. Exit"+reset);
			boolean flag = true;
//			System.out.println("hhh");
//			sc.next();
//			System.out.println("ggg");
//			sc.nextLine();
			while (flag) {
				System.out.println(cyan + "Select your Operation by typing the number of the respective Operation:"
						+ blue
						+ "\n\t1. Add Student\n\t2. Fetch Student\n\t3. Remove Student\n\t4. Update Student Stream\n\t5. Fetch all the Students\n\t6. Exit"
						+ reset);
				switch (sc.nextInt()) {
				case 1: {
					try {
						PreparedStatement preparedStatement = connection
								.prepareStatement("insert into Student values(?,?,?,?,?,?)");
						System.out.print(cyan + "Enter the USN of the Student : " + reset);
						preparedStatement.setString(1, sc.next());
						sc.nextLine();
						System.out.print(cyan + "Enter the Name of the Student : " + reset);
						preparedStatement.setString(2, sc.nextLine());
						System.out.print(cyan + "Enter the address of the Student : " + reset);
						preparedStatement.setString(3, sc.nextLine());
						System.out.print(cyan + "Enter the Student Phone Number : " + reset);
						preparedStatement.setLong(4, sc.nextLong());
						System.out.print(cyan + "Enter the Gender of a Student : " + reset);
						preparedStatement.setString(5, sc.next().substring(0, 1));
						sc.nextLine();
						System.out.print(cyan + "Enter the Stream of the Student : " + reset);
						preparedStatement.setString(6, sc.nextLine());
						preparedStatement.executeUpdate();
						System.out.println(green + "\nEntered details successfully saved" + reset);
						System.out.println();
					} catch (Exception e) {
						System.out.println(
								red + "\nMistakenly you might gave us wrong details. Please provide the correct details"
										+ reset);
						System.out.println();
					}
					break;
				}
				case 2: {
					System.out.println(cyan + "Enter the USN of the Student, whom details you want to see : " + reset);
					PreparedStatement preparedStatement = connection
							.prepareStatement("select * from Student where USN=?");
					preparedStatement.setString(1, sc.next());
					ResultSet res = preparedStatement.executeQuery();
					res.next();
					System.out.println("USN : " + res.getString(1));
					System.out.println("Name : " + res.getString(2));
					System.out.println("Address : " + res.getString(3));
					System.out.println("Contact Number : " + res.getLong(4));
					System.out.println("Gender : " + res.getString(5));
					System.out.println("Stream : " + res.getString(6));
					System.out.println();
					break;
				}
				case 3: {
					System.out
							.println(cyan + "Enter the USN of the Student, whom details you want to remove : " + reset);
					PreparedStatement preparedStatement = connection
							.prepareStatement("delete from Student where USN=?");
					preparedStatement.setString(1, sc.next());
					preparedStatement.executeUpdate();
					System.out.println(green + "Removed Successfully" + reset);
					System.out.println();
					break;
				}
				case 4: {
					PreparedStatement preparedStatement = connection
							.prepareStatement("update Student set Stream=? where USN=?");
					System.out
							.println(cyan + "Enter the USN of the Student, whom stream you want to update : " + reset);
					String usnData = sc.next();
					preparedStatement.setString(2, usnData);
					System.out.print(cyan + "Enter the new Stream of the Student : " + reset);
					sc.nextLine();
					String streamData = sc.nextLine();
					preparedStatement.setString(1, streamData);
					preparedStatement.executeUpdate();
					PreparedStatement preparedStatement1 = connection
							.prepareStatement("update Student set USN=? where USN=?");
					String updUSN = usnData.substring(0, 5) + streamData.substring(0, 2) + usnData.substring(7);
					preparedStatement1.setString(1, updUSN);
					preparedStatement1.setString(2, usnData);
					preparedStatement1.executeUpdate();
					System.out.println(green + "Stream is updated successfully" + reset);
					System.out.println();
					break;
				}
				case 5: {
					PreparedStatement preparedStatement = connection.prepareStatement("select * from Student");
					ResultSet res = preparedStatement.executeQuery();
					while (res.next()) {
						System.out.println("USN : " + res.getString(1));
						System.out.println("Name : " + res.getString(2));
						System.out.println("Address : " + res.getString(3));
						System.out.println("Contact Number : " + res.getLong(4));
						System.out.println("Gender : " + res.getString(5));
						System.out.println("Stream : " + res.getString(6));
						System.out.println();
					}
					break;
				}
				case 6: {
					flag = false;
					System.out.println(magenta + "Thank you" + reset);
					break;
				}
				default: {
					System.out.println(red + "Invalid Choose. Please select the valid option" + reset);
					System.out.println();
				}
				}

			}
			connection.close();
		} catch (InputMismatchException ei) {
			System.out.println("\u001B[31m"
					+ "Invalid Choose. kindly rerun the program and enter the valid option number as provided"
					+ "\u001B[0m");
		}

	}

}
