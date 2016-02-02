package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

public class Zone{

	Integer zoneId;
	String  name;

	Map<Integer, City> cities;
	Map<Integer, NewCity> cityChange;

	List<Applicant> applicants;
	List<Applicant> pwdApplicants;

	Map<String, ArrayList<Applicant> > paperWiseApplicant; 
	List<Applicant> allocatedApplicants;
	List<Applicant> notAllocatedApplicants;

	int allocated;
	int firstChoice = 0;
	int secondChoice = 0;
	int thirdChoice = 0;

	Zone(Integer zoneId){

		this.zoneId = zoneId;
		this.name = "";
		this.cities = new TreeMap<Integer, City>();
		this.applicants = new ArrayList<Applicant>();	
		this.pwdApplicants = new ArrayList<Applicant>();	

		this.allocatedApplicants = new ArrayList<Applicant>();
		this.notAllocatedApplicants = new ArrayList<Applicant>();

		this.paperWiseApplicant = new TreeMap<String, ArrayList<Applicant>>();
		this.cityChange = new TreeMap<Integer, NewCity>();
		this.allocated = 0 ;
	}

	void add(Applicant applicant){

		if( applicant.isPwD )
			pwdApplicants.add( applicant );

		applicants.add( applicant );

		ArrayList<Applicant> pApplicants = paperWiseApplicant.get( applicant.paperCode );

		if( pApplicants == null ){
			pApplicants = new ArrayList<Applicant>();
		}

		pApplicants.add( applicant );
		paperWiseApplicant.put( applicant.paperCode,  pApplicants );
	}

	void print( boolean iiscformate ){
		Set<Integer> cityCodes = cities.keySet();
		for(Integer cityCode: cityCodes){
			cities.get( cityCode ).print( this, iiscformate );	
		}			
	}
} 

