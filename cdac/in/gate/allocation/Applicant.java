package cdac.in.gate.allocation;

public class Applicant{

	String enrollment;
	String name;
	String gender;	
	String paperCode;
	boolean isPwD;
	boolean isScribeReq;
	String[] choices;

	String registrationId;
	Centre centre;
	Session session;
	int allotedChoice;

	Applicant(String enrollment, String name, String gender, String isPD, String isScribe, String paperCode, String choice1, String choice2, String choice3){

		this.enrollment = enrollment;
		this.name = name;
		this.gender = gender;
		this.paperCode = paperCode;
		if( isPD.equals("t") )
			this.isPwD = true;
		if( isScribe.equals("t") )
			this.isScribeReq = true;

		this.choices = new String[3];
		this.choices[0] = choice1;
		this.choices[1] = choice2;
		this.choices[2] = choice3;
			
		
		this.registrationId = null;
		this.centre = null;
		this.session = null;	
		this.allotedChoice = -1;	
	}
} 

