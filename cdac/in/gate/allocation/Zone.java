package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

public class Zone{

		String zoneId;

		Map<String, City> cityMap;

		List<Applicant> applicants;
		List<Applicant> pwdApplicants;

		Map<String, ArrayList<Applicant> > paperWiseApplicant; 
		int allocated;

		Zone(String zoneId){

			this.zoneId = zoneId;
			this.cityMap = new TreeMap<String, City>();
			this.applicants = new ArrayList<Applicant>();	
			this.pwdApplicants = new ArrayList<Applicant>();	
			this.paperWiseApplicant = new TreeMap<String, ArrayList<Applicant>>();
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

		void print(){
				Set<String> cities = cityMap.keySet();
				for(String city: cities){
						cityMap.get( city ).print( zoneId );	
				}			
		}
} 

