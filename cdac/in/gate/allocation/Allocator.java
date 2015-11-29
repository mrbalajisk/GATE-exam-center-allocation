package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

public class Allocator{

		Map<String, Zone> zones;
		Map<String, City> cities;

		Map<String, String> paperSession;
		Map<String, String> sessionPaper;
		Map<String, String> cityCodes;

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

				paperSession.put("ME,EC","1");	
				paperSession.put("ME,BT,CH,GG,MN,PH","2");	
				paperSession.put("ME,EC","3");	
				paperSession.put("EC,AR,CY,IN,MA,PE","4");	
				paperSession.put("CS,CE","5");	
				paperSession.put("CS,EE","6");	
				paperSession.put("CE,AG,EY,MT,PI","7");
				paperSession.put("EE,AE,TF,XL,XE","8");	
				

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
								}
							
					
						}

						System.out.println(PwDApplicants.size()+" Total PwD Applicant Read!");	
						System.out.println(femaleApplicants.size()+" Total Female Applicant Read!");	
						System.out.println(otherApplicants.size()+" Total other (not PwD) Applicant Read!");	
						System.out.println(applicants.size()+" Total Applicant Read!");	

						Set<String> zoneCodes = zones.keySet();

						int total = 0;

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
							System.out.println(", Total: "+ptotal);
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

						while( ( line =  br.readLine() ) != null ){

								if( withHeader ){
										withHeader = false; 
										continue;
								}

								String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

								String zoneId = tk[0].trim();
								String zoneName = tk[1].trim();
								String cityCode = tk[2].trim();

								cityCode = cityCodes.get( cityCode );
								if( cityCode == null){
									System.out.println("City: "+tk[2].trim()+" Not Found!");
									System.exit(0);						
								}

								String tcsCode = tk[3].trim();
								String centerCode = tk[4].trim();
								String centerName = tk[5].trim();
								String cityName = tk[10].trim();
								String state = tk[11].trim();

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

								Session session = new Session("1", ( S1EC1 + S1ME1 ), "30th January 2016(Saturday)", "9.00 AM to 12.00 Noon" ) ;
								session.paperCapacities.put("EC",new PaperCapacity( S1EC1 ) ) ;
								session.paperCapacities.put("ME",new PaperCapacity(S1ME1) ) ;
								sessions.add( session );

								session = new Session("2", ( S2ME2 + S2BTCHGGMNPH ), "30th January 2016 (Saturday)", "2.00 PM to 5.00 PM" );
								session.paperCapacities.put("ME", new PaperCapacity( S2ME2 ) );
								session.paperCapacities.put("BT,CH,GG,MN,PH", new PaperCapacity( S2BTCHGGMNPH ) );
								sessions.add( session );

								session = new Session("3", ( S3ME3 + S3EC2 ), "31st January 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
								session.paperCapacities.put("ME", new PaperCapacity( S3ME3 ) );
								session.paperCapacities.put("EC",new PaperCapacity( S3EC2 ) );
								sessions.add( session );

								session = new Session("4", ( S4EC3 + S4ARCYINMAPE ), "31st January 2016 (Sunday)", "2.00 PM to 5.00 PM" );
								session.paperCapacities.put("EC", new PaperCapacity(  S4EC3 ) );
								session.paperCapacities.put("AR,CY,IN,MA,PE",new PaperCapacity( S4ARCYINMAPE ) );
								sessions.add( session );

								session = new Session("5", ( S5CE1 + S5CS1 ), "6th Febuary 2016 (Saturday)", "9.00 AM to 12.00 Noon");
								session.paperCapacities.put("CE",new PaperCapacity( S5CE1 ) );
								session.paperCapacities.put("CS",new PaperCapacity( S5CS1 ) );
								sessions.add( session );

								session = new Session("6", ( S6CS2 + S6EE1), "6th Febuary 2016 (Saturday)", "2.00 PM to 5.00 PM" );
								session.paperCapacities.put("CS",new PaperCapacity( S6CS2 ) );
								session.paperCapacities.put("EE",new PaperCapacity( S6EE1 ) );
								sessions.add( session );

								session = new Session("7", ( S7CE2 + S7AGEYMTPI ), "7th Febuary 2016 (Sunday)", "9.00 AM to 12.00 Noon" );
								session.paperCapacities.put("CE",new PaperCapacity( S7CE2 ) );
								session.paperCapacities.put("AG,EY,MT,PI",new PaperCapacity( S7AGEYMTPI ) );
								sessions.add( session );

								session = new Session("8", ( S8EE2 + S8AETFXLXE ),"7th Febuary 2016 (Sunday)", "2.00 PM to 5.00 PM" );
								session.paperCapacities.put("CS",new PaperCapacity( S8EE2 ) );
								session.paperCapacities.put("AETFXLXE",new PaperCapacity( S8AETFXLXE ) );
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

								System.out.println(zone.zoneId+", "+city.cityCode+", "+centre.centreCode);

								city.centres.put( centerCode, centre );
								cities.put( cityCode, city );	
								zone.cityMap.put( cityCode, city );
								zones.put( zoneId, zone );
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


		void allocate(List<Applicant> applicants, int choiceNumber){

		}

		void centerAllocate(City city){

		}

		String generateRegistrationId(){

			return null;
		}

		void centreAllocation(){
				
				/*
				Set<String> cityCodes = cityMap.keySet();
				for(String cityCode: cityCodes){
						City city = cityMap.get( cityCode );
						System.err.println( "Allocation for "+city.cityCode+" | "+city.cityName);
						centerAllocate( city );
				}
				*/
		}

		void print( ){

				/*
				Set<String> zones = zoneMap.keySet();
				//Centre.header();
				for(String zoneId: zones){
						Zone zone = zoneMap.get( zoneId );
						Set<String> cities = zone.cityMap.keySet();
						for(String cityCode: cities){
								City city = cityMap.get( cityCode );
								city.print( zone.zoneId );
						}
				}
				Applicant.header();
				for(Applicant applicant: allocatedApplicants ){
						applicant.print();
				}
				System.out.println("------------------ Not Allocated Candidate ---------------");
				Applicant.header();
				for(Applicant applicant: applicants ){
						if( !allocatedApplicants.contains( applicant ) ){
								notAllocatedApplicants.add( applicant );
								applicant.print();
						}	
				}
				System.out.println("Not Allocated candidate "+notAllocatedApplicants.size());
				System.out.println("Allocated candidate "+allocatedApplicants.size());
				*/
		}

		void allocate(){
				int i = 0;	
				while( i < 3){ 
					System.out.print("Pwd: ");
					allocate( PwDApplicants, i );
					System.out.print("Others: ");
					allocate( applicants, i );
					i++;
				}
		}

		public static void main(String[] args){

				try{
						Allocator allocator = new Allocator();
						allocator.readCityCodeMapping("./data/gate-examcity-code.csv", true);
						allocator.readCentres("./data/zone7.csv", true);
						allocator.readApplicants("./data/gate-applicant-20151129.csv", true);
						allocator.allocate();
						allocator.centreAllocation();
						allocator.print();

				}catch(Exception e){
						e.printStackTrace();
				}
		}

} 

