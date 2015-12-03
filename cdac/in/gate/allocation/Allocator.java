package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collections;
import java.io.File;

public class Allocator{

	static Map<Integer, Zone> zones = new TreeMap<Integer, Zone>();
	static Map<Integer, City> cities = new TreeMap<Integer, City>();

	static Map<String, String> paperSession = new TreeMap<String, String>();

	static Map<Integer, String> sessionPaper = new TreeMap<Integer, String>();

	static Map<String, Integer> cityCodes = new TreeMap<String, Integer>();
	static Map<Integer, String> codeCities = new TreeMap<Integer, String>();

	static List<Applicant> applicants = new ArrayList<Applicant>();
	static List<Applicant> PwDApplicants = new ArrayList<Applicant>();
	static List<Applicant> otherApplicants = new ArrayList<Applicant>();
	static List<Applicant> femaleApplicants = new ArrayList<Applicant>();

	static List<Applicant> allAllocatedApplicants = new ArrayList<Applicant>();
	static List<Applicant> allNotAllocatedApplicants = new ArrayList<Applicant>();

	static Map<Integer, Analysis> zoneWiseAnalysis = new TreeMap<Integer, Analysis>();

	public Allocator(){

		paperSession.put("ME","1,2,3");
		paperSession.put("EC","1,3,4");
		paperSession.put("CS","5,6");
		paperSession.put("CE","5,7");
		paperSession.put("EE","6,8");
		paperSession.put("BT","2");
		paperSession.put("CH","2");
		paperSession.put("GG","2");
		paperSession.put("MN","2");
		paperSession.put("PH","2");
		paperSession.put("AR","4");
		paperSession.put("CY","4");
		paperSession.put("IN","4");
		paperSession.put("MA","4");
		paperSession.put("PE","4");
		paperSession.put("AG","7");
		paperSession.put("EY","7");
		paperSession.put("MT","7");
		paperSession.put("PI","7");
		paperSession.put("AE","8");
		paperSession.put("TF","8");
		paperSession.put("XL","8");
		paperSession.put("XE","8");

		sessionPaper.put(new Integer(1),"ME,EC");	
		sessionPaper.put(new Integer(2),"ME,BT,CH,GG,MN,PH");	
		sessionPaper.put(new Integer(3),"ME,EC");	
		sessionPaper.put(new Integer(4),"EC,AR,CY,IN,MA,PE");	
		sessionPaper.put(new Integer(5),"CS,CE");	
		sessionPaper.put(new Integer(6),"CS,EE");	
		sessionPaper.put(new Integer(7),"CE,AG,EY,MT,PI");
		sessionPaper.put(new Integer(8),"EE,AE,TF,XL,XE");	

	}

	void zoneWiseAllocationDetails(){
		System.out.println("-----------------------------------------------------------");	
		System.out.println("ZoneId, Total Applicant, Allocated, Not Allocated ");	
		Set<Integer> zoneIds = zones.keySet();
		for( Integer zoneId: zoneIds ){
			Zone zone = zones.get( zoneId );
			System.out.println(zone.zoneId+", "+zone.applicants.size()+", "+zone.allocatedApplicants.size()+", "+zone.notAllocatedApplicants.size() );
		}
	}

	void printDataDetails(){

		System.out.println("-----------------------------------------------------------");	
		System.out.println("Total Applicant  : "+applicants.size() );
		System.out.println("PwD Applicant    : "+PwDApplicants.size() );
		System.out.println("Female Applicant : "+femaleApplicants.size() );
		System.out.println("!PwD  Applicant  : "+otherApplicants.size() );

		System.out.println("-----------------------------------------------------------");	


		int total = 0;
		boolean flag = true;

		Set<Integer> zoneCodes = zones.keySet();
		for(Integer zoneCode: zoneCodes){
			Zone zone = zones.get( zoneCode );
			Set<String> paperCodes = zone.paperWiseApplicant.keySet();
			if( flag ){
				System.out.print("ZoneId, Total");
				for(String paperCode: paperCodes){
					System.out.print(", "+paperCode);
				}		
				System.out.println();
				flag = false;
			}		

			total += zone.applicants.size();
			System.out.print(zone.zoneId+", "+zone.applicants.size());
			for(String paperCode: paperCodes){
				System.out.print(", "+zone.paperWiseApplicant.get(paperCode).size() );
			}	
			System.out.println();
		}	

		if( total != applicants.size() ){
			System.err.println("Error in Applicant data Count mismatch "+total+" != "+applicants.size() );
		}
	}

	void readCityChangeMapping(String filename, boolean withHeader){

		if( filename == null || filename.trim().length() == 0)
			return;

		BufferedReader br =  null;
		String line = null;

		try{
			br = new BufferedReader( new FileReader(new File(filename) ) );
			boolean header = true;
			int count = 0;
			while( ( line =  br.readLine() ) != null ){

				if( withHeader ){
					withHeader = false;
					continue;
				}
				String[] tk = line.split(",");

				Integer zoneId = new Integer( tk[0].trim() );
				Zone zone = zones.get( zoneId );

				System.err.println("Zone"+tk[0].trim()+", "+tk[1].trim()+", "+tk[2].trim() );

				zone.cityChange.put( new Integer(tk[1].trim()), new Integer( tk[2].trim() ) );

				count++;
			}
			System.err.println("Number of City Change Request: "+count);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Line: "+line);
			System.exit(0);
		}finally{
			if( br != null){
				try{
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

	}

	void readCityCodeMapping(String filename, boolean withHeader){

		if( filename == null || filename.trim().length() == 0)	
			return;

		BufferedReader br =  null; 
		String line = null;	
		try{
			br = new BufferedReader( new FileReader(new File(filename) ) );	
			boolean header = true;
			int count = 0;
			while( ( line =  br.readLine() ) != null ){
				if( withHeader ){
					withHeader = false; 
					continue;
				}

				String[] tk = line.split(",");
				cityCodes.put( tk[0].trim(), new Integer( tk[2].trim() ) );
				codeCities.put( new Integer( tk[2].trim() ), tk[0].trim() );
			}

		}catch(Exception e){
			e.printStackTrace();	
			System.out.println("Line: "+line);
			System.exit(0);
		}finally{
			if( br != null){
				try{	
					br.close();
				}catch(Exception e){
					e.printStackTrace();	
				}		
			}		
		}		

	}

	void readApplicants(String filename, boolean withHeader){

		if( filename == null || filename.trim().length() == 0)	
			return;

		BufferedReader br =  null; 
		try{
			br = new BufferedReader( new FileReader(new File(filename) ) );	
			String line = null;	
			boolean header = true;
			int count = 0;
			while( ( line =  br.readLine() ) != null ){

				if( withHeader ){
					withHeader = false; 
					continue;
				}
				count++;

				String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				if( tk.length < 10)	
					continue;

				Applicant applicant = new Applicant( tk[0].trim(), tk[1].trim(), tk[2].trim(), tk[3].trim(), tk[4].trim(), tk[5].trim(), tk[6].trim(), tk[7].trim(), tk[8].trim(), tk[9].trim() );
				applicant.originalFirstChoice = new Integer( tk[6].trim() ) ;

				applicants.add( applicant );	

				if( tk[2].trim().equals("Female") ){
					femaleApplicants.add( applicant );
				}

				if( tk[3].trim().equals("t") ){	
					PwDApplicants.add( applicant );	
				}else{
					otherApplicants.add( applicant );		
				}	

				Zone zone = zones.get( applicant.zoneId );
				if( zone == null ){
					zone = new Zone(applicant.zoneId);
				}

				zone.add( applicant );
				zones.put( new Integer( applicant.zoneId ), zone);

				if( count % 100000 == 0){
					System.err.println(count+" Applicant Read!");
				}

			}


		}catch(Exception e){
			e.printStackTrace();	
			System.exit(0);
		}finally{
			if( br != null){
				try{	
					br.close();
				}catch(Exception e){
					e.printStackTrace();	
				}		
			}		
		}		

	}

	void readCentres(String filename, boolean withHeader){

		if( filename == null || filename.trim().length() == 0)	
			return;
		BufferedReader br =  null; 
		String line = null;	
		try{

			br = new BufferedReader( new FileReader(new File(filename) ) );	

			int count = 0;
			while( ( line =  br.readLine() ) != null ){

				if( withHeader ){
					withHeader = false; 
					continue;
				}

				String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				Integer zoneId = new Integer( tk[0].trim() );
				String zoneName = tk[1].trim();
				Integer cityCode =  new Integer( tk[2].trim() );

				/*
				   cityCode = cityCodes.get( cityCode );
				   if( cityCode == null){
				   	System.out.println("City: "+tk[2].trim()+" Not Found!");
				   	System.exit(0);						
				   }
				*/	

				String tcsCode = tk[3].trim();
				Integer centerCode = new Integer( tk[4].trim() );
				String centerName = tk[5].trim();
				String cityName = tk[10].trim();
				String state = tk[11].trim();

				int maxCapacity = Integer.parseInt( tk[12].trim() );

				int S1EC1 = Integer.parseInt( tk[13].trim() );
				int S1ME1 = Integer.parseInt( tk[14].trim() );

				int S2ME2 = Integer.parseInt( tk[15].trim() );
				int S2BTCHGGMNPH = Integer.parseInt( tk[16].trim() );

				int S3ME3 = Integer.parseInt( tk[17].trim() );
				int S3EC2 = Integer.parseInt( tk[18].trim() );

				int S4EC3 = Integer.parseInt( tk[19].trim() );
				int S4ARCYINMAPE = Integer.parseInt( tk[20].trim() );

				int S5CE1 = Integer.parseInt( tk[21].trim() );
				int S5CS1 = Integer.parseInt( tk[22].trim() );

				int S6CS2 = Integer.parseInt( tk[23].trim() );
				int S6EE1 = Integer.parseInt( tk[24].trim() );

				int S7CE2 = Integer.parseInt( tk[25].trim() );
				int S7AGEYMTPI = Integer.parseInt( tk[26].trim() );

				int S8EE2 = Integer.parseInt( tk[27].trim() );
				int S8AETFXLXE = Integer.parseInt( tk[28].trim() );

				//String PwDFriendly = tk[29].trim();
				String PwDFriendly = "YES";

				List<Session> sessions = new ArrayList<Session>();

				Session session = new Session(new Integer(1), ( S1EC1 + S1ME1 ), maxCapacity,  "30th January 2016(Saturday)", "9.00 AM to 12.00 Noon" ) ;
				session.paperCapacities.put("EC",new PaperCapacity( S1EC1 ) ) ;
				session.paperCapacities.put("ME",new PaperCapacity(S1ME1) ) ;
				sessions.add( session );

				session = new Session(new Integer(2), ( S2ME2 + S2BTCHGGMNPH ), maxCapacity, "30th January 2016 (Saturday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("ME", new PaperCapacity( S2ME2 ) );

				PaperCapacity pc = new PaperCapacity( S2BTCHGGMNPH );
				session.paperCapacities.put("BT", pc );
				session.paperCapacities.put("CH", pc );
				session.paperCapacities.put("GG", pc );
				session.paperCapacities.put("MN", pc );
				session.paperCapacities.put("PH", pc );

				sessions.add( session );

				session = new Session(new Integer(3), ( S3ME3 + S3EC2 ), maxCapacity, "31st January 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
				session.paperCapacities.put("ME", new PaperCapacity( S3ME3 ) );
				session.paperCapacities.put("EC",new PaperCapacity( S3EC2 ) );
				sessions.add( session );

				session = new Session(new Integer(4), ( S4EC3 + S4ARCYINMAPE ), maxCapacity, "31st January 2016 (Sunday)", "2.00 PM to 5.00 PM" );

				session.paperCapacities.put("EC", new PaperCapacity(  S4EC3 ) );
				pc = new PaperCapacity( S4ARCYINMAPE );
				session.paperCapacities.put("AR", pc );
				session.paperCapacities.put("CY", pc );
				session.paperCapacities.put("IN", pc );
				session.paperCapacities.put("MA", pc );
				session.paperCapacities.put("PE", pc );
				sessions.add( session );

				session = new Session(new Integer(5), ( S5CE1 + S5CS1 ), maxCapacity, "6th Febuary 2016 (Saturday)", "9.00 AM to 12.00 Noon");
				session.paperCapacities.put("CE",new PaperCapacity( S5CE1 ) );
				session.paperCapacities.put("CS",new PaperCapacity( S5CS1 ) );
				sessions.add( session );

				session = new Session(new Integer(6), ( S6CS2 + S6EE1), maxCapacity, "6th Febuary 2016 (Saturday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("CS", new PaperCapacity( S6CS2 ) );
				session.paperCapacities.put("EE", new PaperCapacity( S6EE1 ) );
				sessions.add( session );

				session = new Session(new Integer(7), ( S7CE2 + S7AGEYMTPI ), maxCapacity, "7th Febuary 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
				session.paperCapacities.put("CE",new PaperCapacity( S7CE2 ) );
				pc = new PaperCapacity( S7AGEYMTPI );
				session.paperCapacities.put("AG", pc );
				session.paperCapacities.put("EY", pc );
				session.paperCapacities.put("MT", pc );
				session.paperCapacities.put("PI", pc );
				sessions.add( session );

				session = new Session(new Integer(8), ( S8EE2 + S8AETFXLXE ), maxCapacity, "7th Febuary 2016 (Sunday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("EE",new PaperCapacity( S8EE2 ) );
				pc = new PaperCapacity( S8AETFXLXE );
				session.paperCapacities.put("AE", pc );
				session.paperCapacities.put("TF", pc );
				session.paperCapacities.put("XL", pc );
				session.paperCapacities.put("XE", pc );
				sessions.add( session );

				Zone zone = zones.get( zoneId );
				if( zone == null){
					zone = new Zone( zoneId );
				}

				City city = zone.cities.get( cityCode );

				if( city == null){
					city = new City( cityCode, cityName );
				}

				Centre centre = new Centre( centerCode, centerName, sessions, PwDFriendly);
				city.centres.add( centre );

				cities.put( cityCode, city );	
				zone.cities.put( cityCode, city );
				zones.put( zoneId, zone );
				count++;
			}	
			System.err.println("Number of Centre Read: "+count);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Line: "+line);
			System.exit(0);	
		}finally{
			if( br != null){
				try{	
					br.close();
				}catch(Exception e){
					e.printStackTrace();	
				}		
			}		
		}	

	}


	void allocate(Zone zone, List<Applicant> applicants, int choiceNumber, boolean isMaxCapcityUse, boolean femalePrefForChangeCity ){

		for(Applicant applicant: applicants){

			if( applicant.isAllocated )
				continue;

			Integer choice = applicant.choices[ choiceNumber ];

			City city = cities.get( choice );

			if( city == null){
				//System.err.println("Not Found City: "+ choice);
				continue;
			}

			for(Centre centre: city.centres){

				if( applicant.isPwD && ( !centre.pwdFriendly ) ){
					continue;	
				}

				/* for cityChnage female applicants should get the preferance */

				if( !applicant.isPwD && applicant.gender.equals("Male") && femalePrefForChangeCity && zone.cityChange.get( city.cityCode ) != null ){
					continue;
				}

				String[] sessionIds = paperSession.get( applicant.paperCode ).split(",",-1);

				for(String sessionId: sessionIds){

					Session session = centre.sessions.get( new Integer( sessionId ) );

					PaperCapacity pc = session.paperCapacities.get( applicant.paperCode );	

					if( pc == null ){
						System.err.println( session.sessionId+", "+applicant.paperCode+", PC not found");
						System.exit(0);
					}

					if( applicant.isPwD && session.pwdAllocated == 5 )
						continue;

					if( ( ( pc.capacity - pc.allocated ) > 0 && ( session.capacity - session.allocated ) > 0) || ( isMaxCapcityUse && ( session.maxCapacity - session.allocated ) > 0 ) ){

						applicant.centre = centre;
						applicant.isAllocated = true;
						applicant.session = session;
						applicant.city = city;
						applicant.allotedChoice = ( choiceNumber + 1 );
						pc.allocated++;
						session.allocated++;
						applicant.registrationId = "Y-T-B-G";

						if( applicant.isPwD )
							session.pwdAllocated++;

						List<Applicant> paperApplicants = session.paperAllocatedApplicant.get( applicant.paperCode );

						if( paperApplicants == null ){
							paperApplicants = new ArrayList<Applicant>();
						}	

						paperApplicants.add( applicant );
						session.paperAllocatedApplicant.put( applicant.paperCode, paperApplicants );
					}	
					if( applicant.isAllocated )
						break;
				}		
				if( applicant.isAllocated )
					break;
			}
		}	
	}

	String generateRegistration(Applicant applicant){
		String count = "000"+(applicant.session.registrationGenerated + 1);
		applicant.session.registrationGenerated++;
		String registrationId = applicant.paperCode+"16S"+applicant.session.sessionId+""+applicant.centre.centreCode+""+count.substring( count.length() - 3 );
	return registrationId;
	}

	void centerAllocate(City city){

		for( Centre centre: city.centres ){
			Set<Integer> sessionIds = centre.sessions.keySet();

			for(Integer sessionId: sessionIds ){

				Session session = centre.sessions.get( sessionId );
				Set<String> paperCodes = session.paperAllocatedApplicant.keySet();
				boolean run = true;
				while( run ){	
					run = false;
					for(String paperCode: paperCodes ){
						List<Applicant> applicants = session.paperAllocatedApplicant.get( paperCode );
						if( applicants != null && applicants.size() > 0){
							Applicant applicant = applicants.remove(0);
							run = true;
							applicant.registrationId = generateRegistration( applicant );
						}
					}
				}
			} 	
		} 
	}

	void centreAllocation( ){

		Set<Integer> cityCodes = cities.keySet();
		for(Integer cityCode: cityCodes){
			City city = cities.get( cityCode );
			centerAllocate( city );
		}
	}

	void centreAllocation(String zoneId){

		Zone zone = zones.get(zoneId );
		Set<Integer> cityCodes = zone.cities.keySet();
		for(Integer cityCode: cityCodes){
			City city = zone.cities.get( cityCode );
			centerAllocate( city );
		}
	}

	void printCentres(){

		System.out.println("-----------------------------------------------------------");	
		Centre.header();
		Set<Integer> zoneIds = zones.keySet();
		for(Integer zoneId: zoneIds){
			Zone zone = zones.get( zoneId );
			Set<Integer> cityCodes = zone.cities.keySet();
			for(Integer cityCode: cityCodes){
				City city = zone.cities.get( cityCode );
				city.print( zone.zoneId );
			}
		}
	}

	void printAllocation( ){

		System.out.println("-----------------------------------------------------------");	
		Applicant.header();
		for(Applicant applicant: allAllocatedApplicants ){
			applicant.print();
		}

		System.out.println("------------------ Not Allocated Candidate ---------------");
		Applicant.header();
		for(Applicant applicant: allNotAllocatedApplicants ){
			applicant.print();
		}

		System.out.println("Total Allocated    :"+ allAllocatedApplicants.size() );
		System.out.println("Total notAllocated :"+ allNotAllocatedApplicants.size() );
	}

	void cityChangeUpdate(List<Applicant> applicants, Map<Integer, Integer> cityChange){

		for( Applicant applicant: applicants){
			if( !applicant.isAllocated ){
				Integer newChoice = cityChange.get( applicant.choices[0] );
				if( newChoice != null ){
					applicant.choices[0] = new Integer( newChoice );
				}
			}
		}
	}


	void allocationAnalysis(int zoneId){

		Zone zone = zones.get( zoneId );	

		for( Applicant applicant: zone.applicants){

			if( !applicant.isAllocated ){
				
				allNotAllocatedApplicants.add( applicant );	
				zone.notAllocatedApplicants.add( applicant );

				Analysis analysis = zoneWiseAnalysis.get( applicant.zoneId );

				if( analysis == null ){
					analysis =  new Analysis( applicant.zoneId );
				}
				CityWise city = analysis.cityWise.get( applicant.choices[0] );
				if( city == null ){
					city = new CityWise( applicant.choices[0] );	
				}	
				PaperWise paper = city.paperWise.get( applicant.paperCode );
				if( paper == null){
					paper = new PaperWise( applicant.paperCode );
				}
				paper.count++;

				city.paperWise.put( applicant.paperCode, paper );
				analysis.cityWise.put( applicant.choices[0], city );
				zoneWiseAnalysis.put( applicant.zoneId, analysis );

			}else {
				allAllocatedApplicants.add( applicant );
				zone.allocatedApplicants.add( applicant);
			}
		}		
	}

	void zoneWiseAnalyisPrint(){

		System.out.println("----------------------------------------------------------");	
		System.out.println("ZoneId, CityCode(cityName), Paper:not-AllocatedCount, ...");

		Set<Integer> zoneIds = zoneWiseAnalysis.keySet();

		for(Integer zoneId: zoneIds ){
			zoneWiseAnalysis.get( zoneId ).print();
			System.out.println();
		}
	}

	void allocate(int  zoneId, int choiceNo ){

		Zone zone = zones.get( new Integer( zoneId ) );	
		Collections.sort( zone.pwdApplicants , new ApplicantComp() );	
		Collections.sort( zone.applicants , new ApplicantComp() );	

		/* Don't use Maxcapacity */

		allocate(zone, zone.pwdApplicants, choiceNo, false, false);
		allocate(zone, zone.applicants, choiceNo, false, true  /* female only for city change */ );
		allocate(zone, zone.applicants, choiceNo, false, false );

		cityChangeUpdate(  zone.pwdApplicants, zone.cityChange );	
		cityChangeUpdate(  zone.applicants, zone.cityChange );	

		/* Utilised Max Capacity */ 

		allocate(zone, zone.pwdApplicants, choiceNo, true, false );
		allocate(zone, zone.applicants, choiceNo, true, true /* female only for city change */ );
		allocate(zone, zone.applicants, choiceNo, true, false );

	}

	public static void main(String[] args){

		try{

			Allocator allocator = new Allocator();
			allocator.readApplicants("./data/gate-applicant-20151129.csv", true);

			allocator.readCentres("./data/zone4.csv", true);
			allocator.readCentres("./data/zone6.csv", true);
			allocator.readCentres("./data/zone7.csv", true);
			allocator.readCentres("./data/zone5.csv", true);

			allocator.readCityChangeMapping("./data/city-change.csv",true);
			allocator.readCityCodeMapping("./data/gate-examcity-code.csv", true);
			
			allocator.printDataDetails();

			allocator.allocate(4, 0);
			allocator.allocate(5, 0);
			allocator.allocate(6, 0);
			allocator.allocate(6, 1);
			allocator.allocate(7, 0);

			allocator.centreAllocation();

			allocator.allocationAnalysis(4);
			allocator.allocationAnalysis(5);
			allocator.allocationAnalysis(6);
			allocator.allocationAnalysis(7);

			allocator.printCentres();	
			allocator.printAllocation();
			allocator.zoneWiseAllocationDetails();
			allocator.zoneWiseAnalyisPrint();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
} 

