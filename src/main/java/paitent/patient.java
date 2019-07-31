package paitent;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class patient {
	private String Nid;
	private String fn;
	private String ln;
	private LocalDate dateOfBairth;
	private LocalDateTime regTime;
	
	
	
	public patient(String nid, String fn, String ln, LocalDate dateOfBairth, LocalDateTime regTime) {
		super();
		Nid = nid;
		this.fn = fn;
		this.ln = ln;
		this.dateOfBairth = dateOfBairth;
		this.regTime = regTime;
	}



	public String getNid() {
		return Nid;
	}



	public void setNid(String nid) {
		Nid = nid;
	}



	public String getFn() {
		return fn;
	}



	public void setFn(String fn) {
		this.fn = fn;
	}



	public String getLn() {
		return ln;
	}



	public void setLn(String ln) {
		this.ln = ln;
	}



	public LocalDate getDateOfBairth() {
		return dateOfBairth;
	}



	public void setDateOfBairth(LocalDate dateOfBairth) {
		this.dateOfBairth = dateOfBairth;
	}



	public LocalDateTime getRegTime() {
		return regTime;
	}



	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}
	
	
	
	
	

}
