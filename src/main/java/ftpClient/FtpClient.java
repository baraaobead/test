package ftpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xbib.io.ftp.client.FTP;
import org.xbib.io.ftp.client.FTPClient;
import org.xbib.io.ftp.client.FTPFile;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import paitent.patient;

public class FtpClient {
	
	
	private	FTPClient ftpClient = new FTPClient();
	private static final Logger log= LogManager.getLogger(FtpClient.class.getName());
	LocalDate dateOfBirth = null;
	LocalDateTime ResistrationTime = null;
	patient person = null;
	

	public FtpClient() {
		super();
		try {
		ftpClient.connect("10.20.20.101", 21);
		ftpClient.login("vas03", "test03@123");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
		ftpClient.changeWorkingDirectory("/queue");
		
		
		}
		catch(Exception e) {
			log.error(e.getMessage());
			
		}
		}
	public List<patient> startWork() throws IOException {
		List<patient> pationt = null;
		try {
		readFromFileAndPrintOnLocal();
		pationt =getAllPatient ();}
		catch(Exception e) {
		log.error(e.getMessage());}
		
		return pationt;
	}
	public void readFromFileAndPrintOnLocal() throws IOException {
		OutputStream fos;
		try {
	
			FTPFile[] files=getAllFile();
			if(files.length==0) {
				log.info("no file in queue");
			}
			for(int x=0;x<files.length;x++) {
			
			log.info("this is to inform you that we are in reading stage");
			fos = new FileOutputStream(files[x].getName());
			ftpClient.retrieveFile("/queue/" + files[x].getName(), fos);
			InputStream in = new BufferedInputStream(new FileInputStream("C:/Users/arabiacell02/eclipse-workspace/task2/"+files[x].getName()));

			ftpClient.changeWorkingDirectory("/processes");
			System.out.println("store file :"+ftpClient.storeFile(files[x].getName(), in));
			
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		log.error(e.getMessage());
		}

	
		
		
	}

	public List<patient> checkFromPatient(List<String>person){
		List<patient>x= new ArrayList<patient>();
		String[]readLine=null;
		
			for(int i=0;i<person.size();i++) {
				try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			readLine = person.get(i).split(",");
			if(checkFromNationalid(readLine[0].trim())) {
				String nationalNumber = readLine[0].trim();
				String firstName = readLine[1].trim();
				String lastName = readLine[2].trim();
				String dateOfBirths = readLine[3].trim();
				LocalDate dateOfBirth = LocalDate.parse(dateOfBirths);
				String registrationDate = readLine[4].trim();
				LocalDateTime ResistrationTime = LocalDateTime.parse(registrationDate, formatter);
				patient persons = new patient(nationalNumber, firstName, lastName, dateOfBirth, ResistrationTime);
				x.add(persons);
				
					
				} 
			else
				log.error("\n NationalID not Correct:   (" +readLine[0].trim()+")" );
			
		}
			
		catch(Exception e) {
			
			log.error(" cant insert  this object : (" + readLine[0].trim() +","+readLine[1].trim()+","+readLine[2].trim() + "," +readLine[3].trim()+","+readLine[4].trim() +")"+"\n error type:"+e.getMessage());
		} 
				}
		
		return x;
		
	}
	
	 private static Boolean checkFromNationalid(String nationalID) {
		    for(char c : nationalID.toCharArray())
		        if(!Character.isDigit(c))
		            return false;
		    return true;
		}


	
	
	
	public List<patient> getAllPatient () {
		//List<patient>x=new ArrayList<patient>();
		List<String>persone= new ArrayList<String>();
		try {
			FTPFile[] files=getAllFile();
			for(int x=0; x<files.length;x++) {
				
				
				
				
				/*File excelFile = new File("C:\\Users\\arabiacell02\\eclipse-workspace\\task2\\"+files[x].getName());
				 FileInputStream fis = new FileInputStream(excelFile);
				 XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheetAt(0);
				
				Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();
				Iterator<Cell> cellIterator;
				
				org.apache.poi.ss.usermodel.Row row;
				Cell cell;
				while(rowIterator.hasNext()) {
					row = rowIterator.next();
					
					cellIterator = row.iterator();
					String cellData="";
					while(cellIterator.hasNext()) {
						cell = cellIterator.next();
						cellData+=String.valueOf(cell)+",";
						
					
					}
				persone.add(cellData);
				*/
				
				
				
		BufferedReader br = Files.newBufferedReader(Paths.get("C:/Users/arabiacell02/eclipse-workspace/task2/"+files[x].getName()));
		String reader;
		
			while ((reader = br.readLine()) != null) {
				
				persone.add(reader);

			
			}
			
			
			
			
			
			System.out.println("delete file:"+ftpClient.deleteFile("/queue/"+files[x].getName()));
}
				}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return checkFromPatient(persone);
	
	
	}
	
	
	
	public FTPFile[] getAllFile() {
		
		try {
			ftpClient.changeWorkingDirectory("/queue");
			return ftpClient.listFiles();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("can't retrive files "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
