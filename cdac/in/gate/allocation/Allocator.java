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

	Map<String, Zone> zones;
	Map<String, City> cities;

	Map<String, String> paperSession;
	Map<String, String> sessionPaper;
	Map<String, String> cityCodes;

	Map<String, String> cityChangeMap;

	List<Applicant> applicants;

	List<Applicant> PwDApplicants;
	List<Applicant> otherApplicants;
	List<Applicant> femaleApplicants;

	List<Applicant> notAllocatedApplicants;
	List<Applicant> allocatedApplicants;
		

	public Allocator(){

		applicants = new ArrayList<Applicant>();
		notAllocatedApplicants = new ArrayList<Applicant>();
		allocatedApplicants = new ArrayList<Applicant>();

		PwDApplicants = new ArrayList<Applicant>();
		femaleApplicants = new ArrayList<Applicant>();
		otherApplicants = new ArrayList<Applicant>();

		zones = new TreeMap<String, Zone>(); 
		cities = new TreeMap<String, City>();

		paperSession = new TreeMap<String, String>();
		cityChangeMap = new TreeMap<String, String>();


		/*	
			paperSession.put("ME,EC","1");	
			paperSession.put("ME,BT,CH,GG,MN,PH","2");	
			paperSession.put("ME,EC","3");	
			paperSession.put("EC,AR,CY,IN,MA,PE","4");	
			paperSession.put("CS,CE","5");	
			paperSession.put("CS,EE","6");	
			paperSession.put("CE,AG,EY,MT,PI","7");
			paperSession.put("EE,AE,TF,XL,XE","8");	
		*/

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


		sessionPaper = new TreeMap<String, String>();

		sessionPaper.put("1","ME,EC");	
		sessionPaper.put("2","ME,BT,CH,GG,MN,PH");	
		sessionPaper.put("3","ME,EC");	
		sessionPaper.put("4","EC,AR,CY,IN,MA,PE");	
		sessionPaper.put("5","CS,CE");	
		sessionPaper.put("6","CS,EE");	
		sessionPaper.put("7","CE,AG,EY,MT,PI");
		sessionPaper.put("8","EE,AE,TF,XL,XE");	
		sessionPaper.put("8","");	

		cityCodes = new TreeMap<String, String>();
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
				Zone zone = zones.get( tk[0].trim() );
				System.out.println( "==> Zone"+tk[0].trim()+", "+tk[1].trim()+", "+tk[2].trim() );
				zone.cityChange.put( tk[1].trim(), tk[2].trim() );
				count++;
            }
			System.err.println("Number of City Change Request: "+count);
			System.out.println("Number of City Change Request: "+count);

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
				cityCodes.put( tk[0].trim(), tk[2].trim() );
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
				zones.put( applicant.zoneId, zone);

				if( count % 100000 == 0){
					System.out.println(count+" Applicant Read!");
					System.err.println(count+" Applicant Read!");
				}


			}

			System.out.println(PwDApplicants.size()+" Total PwD Applicant Read!");	
			System.out.println(femaleApplicants.size()+" Total Female Applicant Read!");	
			System.out.println(otherApplicants.size()+" Total other (not PwD) Applicant Read!");	
			System.out.println(applicants.size()+" Total Applicant Read!");	

			System.err.println("Number of Applicant Read: "+count);
			System.out.println("Number of Applicant Read: "+count);

			int total = 0;
			Set<String> zoneCodes = zones.keySet();

			for(String zoneCode: zoneCodes){
				Zone zone = zones.get( zoneCode );
				total += zone.applicants.size();
				System.out.print(zone.zoneId+", "+zone.applicants.size());
				Set<String> paperCodes = zone.paperWiseApplicant.keySet();
				int ptotal = 0;
				for(String paperCode: paperCodes){
					ptotal += zone.paperWiseApplicant.get(paperCode).size();
					System.out.print(", "+paperCode+":"+zone.paperWiseApplicant.get(paperCode).size() );
				}	
				System.out.println(", Total:"+ptotal);
			}	

			System.out.println("TOTAL: "+total);

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

				String zoneId = tk[0].trim();
				String zoneName = tk[1].trim();
				String cityCode = tk[2].trim();
				/*
				cityCode = cityCodes.get( cityCode );
				if( cityCode == null){
					System.out.println("City: "+tk[2].trim()+" Not Found!");
					System.exit(0);						
				}
				*/	
				String tcsCode = tk[3].trim();
				String centerCode = tk[4].trim();
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

				Session session = new Session("1", ( S1EC1 + S1ME1 ), maxCapacity,  "30th January 2016(Saturday)", "9.00 AM to 12.00 Noon" ) ;
				session.paperCapacities.put("EC",new PaperCapacity( S1EC1 ) ) ;
				session.paperCapacities.put("ME",new PaperCapacity(S1ME1) ) ;
				sessions.add( session );

				session = new Session("2", ( S2ME2 + S2BTCHGGMNPH ), maxCapacity, "30th January 2016 (Saturday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("ME", new PaperCapacity( S2ME2 ) );

				PaperCapacity pc = new PaperCapacity( S2BTCHGGMNPH );
				session.paperCapacities.put("BT", pc );
				session.paperCapacities.put("CH", pc );
				session.paperCapacities.put("GG", pc );
				session.paperCapacities.put("MN", pc );
				session.paperCapacities.put("PH", pc );

				sessions.add( session );

				session = new Session("3", ( S3ME3 + S3EC2 ), maxCapacity, "31st January 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
				session.paperCapacities.put("ME", new PaperCapacity( S3ME3 ) );
				session.paperCapacities.put("EC",new PaperCapacity( S3EC2 ) );
				sessions.add( session );

				session = new Session("4", ( S4EC3 + S4ARCYINMAPE ), maxCapacity, "31st January 2016 (Sunday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("EC", new PaperCapacity(  S4EC3 ) );
				pc = new PaperCapacity( S4ARCYINMAPE );
				session.paperCapacities.put("AR", pc );
				session.paperCapacities.put("CY", pc );
				session.paperCapacities.put("IN", pc );
				session.paperCapacities.put("MA", pc );
				session.paperCapacities.put("PE", pc );
				sessions.add( session );

				session = new Session("5", ( S5CE1 + S5CS1 ), maxCapacity, "6th Febuary 2016 (Saturday)", "9.00 AM to 12.00 Noon");
				session.paperCapacities.put("CE",new PaperCapacity( S5CE1 ) );
				session.paperCapacities.put("CS",new PaperCapacity( S5CS1 ) );
				sessions.add( session );

				session = new Session("6", ( S6CS2 + S6EE1), maxCapacity, "6th Febuary 2016 (Saturday)", "2.00 PM to 5.00 PM" );
				session.paperCapacities.put("CS", new PaperCapacity( S6CS2 ) );
				session.paperCapacities.put("EE", new PaperCapacity( S6EE1 ) );
				sessions.add( session );

				session = new Session("7", ( S7CE2 + S7AGEYMTPI ), maxCapacity, "7th Febuary 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
				session.paperCapacities.put("CE",new PaperCapacity( S7CE2 ) );
				pc = new PaperCapacity( S7AGEYMTPI );
				session.paperCapacities.put("AG", pc );
				session.paperCapacities.put("EY", pc );
				session.paperCapacities.put("MT", pc );
				session.paperCapacities.put("PI", pc );
				sessions.add( session );

				session = new Session("8", ( S8EE2 + S8AETFXLXE ), maxCapacity, "7th Febuary 2016 (Sunday)", "2.00 PM to 5.00 PM" );
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

				City city = zone.cityMap.get( cityCode );

				if( city == null){
					city = new City( cityCode, cityName );
				}

				Centre centre = new Centre( centerCode, centerName, sessions, PwDFriendly);
				city.centres.add( centre );

				cities.put( cityCode, city );	
				zone.cityMap.put( cityCode, city );
				zones.put( zoneId, zone );
				count++;
			}	
			System.err.println("Number of Centre Read: "+count);
			System.out.println("Number of Centre Read: "+count);

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


	void allocate(List<Applicant> applicants, int choiceNumber, boolean isMaxCapcityUse ){

		for(Applicant applicant: applicants){

			if( applicant.isAllocated )
				continue;
			
			String choice = applicant.choices[ choiceNumber ];
			City city = cities.get( choice );
			
			if( city == null){
				System.err.println( choice +" Not Found");
				continue;
			}

			for(Centre centre: city.centres){

				if( applicant.isPwD && ( !centre.pwdFriendly ) ){
					continue;	
				}

				String[] sessionIds = paperSession.get( applicant.paperCode ).split(",",-1);

				for(String sessionId: sessionIds){

					Session session = centre.sessions.get( sessionId );

					PaperCapacity pc = session.paperCapacities.get( applicant.paperCode );	

					if( pc == null ){
						System.err.println( session.sessionId+", "+applicant.paperCode+", PC not found");
						System.exit(0);
					}
					
					if( applicant.isPwD && session.pwdAllocated == 5 )
						continue;

					if( ( ( pc.capacity - pc.allocated ) > 0 && ( session.capacity - session.allocated ) > 0) || ( isMaxCapcityUse && ( session.maxCapacity - session.allocated ) > 0 )  ){

						applicant.centre = centre;
						applicant.isAllocated = true;
						applicant.session = session;
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
						allocatedApplicants.add ( applicant );
					        break;	
					}	

				}		
				if( applicant.isAllocated )
					break;
			}
		}	
	}

	String generateRegistration(Applicant applicant){
			String count = "000"+(applicant.session.registrationGenerated + 1);
			applicant.session.registrationGenerated++;
			String registrationId = applicant.paperCode+"16S"+applicant.session.sessionId+""+applicant.centre.centreCode+""+count.substring( count.length() - 3 );			return registrationId;
	}

	void centerAllocate(City city){
			
			for( Centre centre: city.centres ){
					Set<String> sessionIds = centre.sessions.keySet();
					for(String sessionId: sessionIds ){
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

	void centreAllocation(){

		   Set<String> cityCodes = cities.keySet();
		   for(String cityCode: cityCodes){
		   		City city = cities.get( cityCode );
		   		System.err.println( "Allocation for "+city.cityCode+" | "+city.cityName);
		   		centerAllocate( city );
		   }
	}

	void print(){

		Centre.header();
		Set<String> zoneIds = zones.keySet();
		for(String zoneId: zoneIds){
			Zone zone = zones.get( zoneId );
			Set<String> cityCodes = zone.cityMap.keySet();
			for(String cityCode: cityCodes){
				City city = cities.get( cityCode );
				city.print( zone.zoneId );
			}
		}

		Applicant.header();
		for(Applicant applicant: allocatedApplicants ){
			applicant.print();
		}

		System.out.println("------------------ Not Allocated Candidate ---------------");
		Applicant.header();

		for(Applicant applicant: notAllocatedApplicants ){
			applicant.print();
		}
		System.out.println("Not Allocated candidate "+notAllocatedApplicants.size());
		System.out.println("Allocated candidate "+allocatedApplicants.size());
	}

	void cityChangeUpdate(List<Applicant> applicants, Map<String, String> cityChange){

		int count = 0;

		for( Applicant applicant: applicants){

				if( !applicant.isAllocated ){

					String newChoice = cityChange.get( applicant.choices[0] );

					if( newChoice != null ){
						applicant.originalFirstChoice = applicant.choices[0];
						applicant.choices[0] = newChoice;
						count++;
					}
				}
		}
	}

	void allocate(String zoneId){

		Zone zone = zones.get( zoneId );	
		Collections.sort( zone.pwdApplicants , new ApplicantComp() );	
		Collections.sort( zone.applicants , new ApplicantComp() );	

		/* Don't use Maxcapacity */

		allocate( zone.pwdApplicants, 0, false );
		allocate( zone.applicants, 0, false );

		cityChangeUpdate(  zone.pwdApplicants, zone.cityChange );	
		cityChangeUpdate(  zone.applicants, zone.cityChange );	
	
		/* Utilised Max Capacity */ 

		allocate( zone.pwdApplicants, 0, true );
		allocate( zone.applicants, 0, true );



		for( Applicant applicant: zone.applicants){
			if( !applicant.isAllocated )
				notAllocatedApplicants.add( applicant ); 				
		}

		System.out.println("Allocated Applicant From Zone: "+zoneId+" => "+ allocatedApplicants.size() );
		System.out.println("Not Allocated Applicant From Zone: "+zoneId+" => "+ notAllocatedApplicants.size() );
	}

	void allocate( ){

		Collections.sort( PwDApplicants , new ApplicantComp() );	
		Collections.sort( otherApplicants , new ApplicantComp() );	

		allocate( PwDApplicants, 0, false );
		allocate( otherApplicants, 0, false );
	}

	public static void main(String[] args){

		try{

			Allocator allocator = new Allocator();
			allocator.readApplicants("./data/gate-applicant-20151129.csv", true);
			//allocator.readCentres("./data/zone7.csv", true);
			allocator.readCentres("./data/zone5.csv", true);
			allocator.readCityChangeMapping("./data/city-change.csv",true);

			//allocator.readCityCodeMapping("./data/gate-examcity-code.csv", true);
			//allocator.print();

			//allocator.allocate("7");
			allocator.allocate("5");

			allocator.centreAllocation();

			allocator.print();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
} 

