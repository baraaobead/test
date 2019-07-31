package DBclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import paitent.patient;

public class DB {
	
	
	static final String Data_Base= "jdbc:mysql://localhost:3306/patient";
	private Connection connection=null ;
	private PreparedStatement statmenet=null;
	private ResultSet result=null ;
	
	private static final Logger log= LogManager.getLogger(DB.class.getName());

	public DB() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(Data_Base, "root", "ROOT");
			System.out.println("connect to data base done \n \n \n ");
		}
		catch(Exception e) {
			System.out.println("error :"+e.getMessage());
		}
	}

	public void insertPationet(List<patient> person) {
		
		
		for(int k=0;k<person.size();k++) {
		if(patientExistsByNationalid(person.get(k).getNid()))
		{
			
			try {
				statmenet= connection.prepareStatement("INSERT INTO patient (national_id,first_name,last_name,date_ofbairth,registration_date) VALUES (?,?,?,?,?)");
				statmenet.setString(1, person.get(k).getNid());
				statmenet.setString(2, person.get(k).getFn());
				statmenet.setString(3, person.get(k).getLn());
				statmenet.setString(4,String.valueOf( person.get(k).getDateOfBairth()));
				statmenet.setString(5, String.valueOf(person.get(k).getRegTime()));
				
			int y =statmenet.executeUpdate();
			if(y!=0) {System.out.println("insert done  patient:("+person.get(k).getNid()+","+person.get(k).getFn()+","+person.get(k).getLn()+","+person.get(k).getDateOfBairth()+","+person.get(k).getRegTime()+")");
			person.remove(k);
				}
			else {
				System.out.println("cant insert this object"+ "(" + person.get(k).getNid()  +","+ person.get(k).getLn()+","+person.get(k).getDateOfBairth() + "," +person.get(k).getRegTime() +")");
				
					
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else {
			System.out.println("this object was inserted  ("+ person.get(k).getNid()  +","+person.get(k).getLn()+","+person.get(k).getDateOfBairth() + "," +person.get(k).getRegTime() +")");
			person.remove(k);
		}
		
//	person.clear();
		
		}
		
	}
	private Boolean patientExistsByNationalid(String nationalNumber) {
		
		try {
			statmenet= connection.prepareStatement("SELECT * from patient where national_id = ? ");
			statmenet.setString(1, nationalNumber);
			result=statmenet.executeQuery();
			if(!result.next()) {return true;}
			else return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		}
	
	}
	
}
