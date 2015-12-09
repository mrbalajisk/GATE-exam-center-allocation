package cdac.in.gate.allocation;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.security.MessageDigest;
import java.util.LinkedHashMap;

public class Applicant{

	Integer zoneId;	
	String enrollment;
	String name;
	String gender;	
	String paperCode;
	String hashCode;

	boolean isPwD;
	boolean isScribeReq;
	Integer[] choices;

	String registrationId;
	boolean isAllocated;
	Centre centre;
	City city;
	Session session;
	int allotedChoice;

	Integer firstChoice;


	Applicant(String enrollment, String name, String gender, String isPD, String isScribe, String paperCode, String choice1, String choice2, String choice3, String zoneId){
		this.enrollment = enrollment;
		this.name = name;
		this.gender = gender;
		this.zoneId = new Integer( zoneId );

		this.paperCode = paperCode;

		if( isPD.equals("t") )
			this.isPwD = true;
		if( isScribe.equals("t") )
			this.isScribeReq = true;

		this.choices = new Integer[3];
		this.choices[0] = new Integer( choice1 );
		this.choices[1] = new Integer( choice2 );
		this.choices[2] = new Integer( choice3 );
		
		this.firstChoice = new Integer( choice1 );	

		this.registrationId = null;
		this.isAllocated = false;
		this.centre = null;
		this.session = null;	
		this.city = null;
		this.allotedChoice = -1;	

		String code = enrollment.substring( enrollment.length() - 2)+""+name.substring(name.length() - 2) ;
		this.hashCode = ""+code.hashCode();
		
	}

	static void header(){
		System.out.println("Zone, Enrollment, Gender, PwD-Status, PaperCode, CenterCode, City, registrationId, City-Choice1, City-Choice2, City-Choice3, Alloted-Session, ChoiceNumber, OriginalFirstChoice");
	}

	void print(){
		if( centre != null ){
			System.out.println("Zone"+zoneId+", "+enrollment+", "+gender+", "+isPwD+", "+paperCode+", "+centre.centreCode+", "+city.cityCode+", "+registrationId+", "+choices[0]+", "+choices[1]+", "+choices[2]+", "+session.sessionId+", "+allotedChoice +", "+firstChoice);
		}else{
			System.out.println("Zone"+zoneId+", "+enrollment+", "+gender+", "+isPwD+", "+paperCode+", null, null, "+registrationId+", "+choices[0]+", "+choices[1]+", "+choices[2]+",  nulll, -1, "+firstChoice );
		}	
	}
} 

class ApplicantComp implements Comparator<Applicant>{
    @Override
    public int compare(Applicant a1, Applicant a2) {
		return a1.hashCode.compareTo( a2.hashCode );
    }
}

