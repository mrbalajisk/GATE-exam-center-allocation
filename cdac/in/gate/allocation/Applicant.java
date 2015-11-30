package cdac.in.gate.allocation;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.security.MessageDigest;
import java.util.LinkedHashMap;

public class Applicant{

	String zoneId;	
	String enrollment;
	String name;
	String gender;	
	String paperCode;
	String hashCode;

	boolean isPwD;
	boolean isScribeReq;
	String[] choices;

	String registrationId;
	boolean isAllocated;
	Centre centre;
	Session session;
	int allotedChoice;

	String originalFirstChoice;


	Applicant(String enrollment, String name, String gender, String isPD, String isScribe, String paperCode, String choice1, String choice2, String choice3, String zoneId){
		this.enrollment = enrollment;
		this.name = name;
		this.gender = gender;
		this.zoneId = zoneId;

		this.paperCode = paperCode;

		if( isPD.equals("t") )
			this.isPwD = true;
		if( isScribe.equals("t") )
			this.isScribeReq = true;

		this.choices = new String[3];
		this.choices[0] = choice1;
		this.choices[1] = choice2;
		this.choices[2] = choice3;
		
		this.originalFirstChoice = null;	
		this.registrationId = null;
		this.isAllocated = false;
		this.centre = null;
		this.session = null;	
		this.allotedChoice = -1;	

		
			
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			String code = enrollment.substring( enrollment.length() - 2)+""+name.substring(name.length() - 2) ;

			byte[] array = md.digest( code.getBytes() );
			this.hashCode = array.toString();
			this.hashCode = ""+code.hashCode();

		}catch(Exception e){
			System.out.println(enrollment+", "+name);
			e.printStackTrace();
		}	
		
	}

	static void header(){
		System.out.println("Zone, Enrollment, PwD-Status, PaperCode, CenterCode, registrationId, City-Choice1, City-Choice2, City-Choice3, Alloted-Session, ChoiceNumber, OriginalFirstChoice");
	}

	void print(){
		if( centre != null ){
			System.out.println("Zone"+zoneId+", "+enrollment+", "+isPwD+", "+paperCode+", "+centre.centreCode+", "+registrationId+", "+choices[0]+", "+choices[1]+", "+choices[2]+", "+session.sessionId+", "+allotedChoice +", "+originalFirstChoice);
		}else{
			System.out.println("Zone"+zoneId+", "+enrollment+", "+isPwD+", "+paperCode+", null, "+registrationId+", "+choices[0]+", "+choices[1]+", "+choices[2]+",  nulll, -1, "+originalFirstChoice );
		}	
	}
} 

class ApplicantComp implements Comparator<Applicant>{
 
    @Override
    public int compare(Applicant a1, Applicant a2) {
		return a1.hashCode.compareTo( a2.hashCode );
    }
}

