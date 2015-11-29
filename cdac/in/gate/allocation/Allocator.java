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

							/*

								String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

								String zoneId = tk[0].trim();
								String zoneName = tk[1].trim();
								String cityCode = tk[2].trim();
								String centerCode = tk[3].trim();
								String centerName = tk[4].trim();
								String cityName = tk[9].trim();
								String state = tk[10].trim();

								List<String> sessions =  new ArrayList<String>();
								int i = 11;
								for(int j = 0; i < tk.length; i++, j++){

										try{
												int num = Integer.parseInt( tk[i].trim() );
										}catch(Exception e){
												break;
										}	
										sessions.add( j, tk[i].trim() );
								}

								String PwDFriendly = tk[i].trim();

								Zone zone = zoneMap.get( zoneId );

								if( zone == null){
										zone = new Zone(zoneId, zoneName);
								}

								City city = zone.cityMap.get( cityCode );

								if( city == null){
										city = new City( cityCode, cityName );
								}

								Centre centre = new Centre( centerCode, centerName, sessions, PwDFriendly);

								System.out.println(zone.zoneId+", "+city.cityCode+", "+centre.centreCode);

								city.centreMap.put( centerCode, centre );

								i = 0;
								for( int s = 1; i < sessions.size(); i++, s++){

										Session session = city.sessionMap.get( s+"" );			
										if( session == null ){
												if( centre.pwdFriendly )
														session = new Session( s+"", Integer.parseInt( sessions.get(i) ), 5 );
												else
														session = new Session( s+"", Integer.parseInt( sessions.get(i) ), 0 );
										}else{
												if( centre.pwdFriendly )
														session.pwdCapacity += 5 ;

												session.capacity += Integer.parseInt( sessions.get(i) ) ;
										}		

										city.sessionMap.put( s+"", session );
								}



								cityMap.put( cityCode, city );	
								zone.cityMap.put( cityCode, city );
								zoneMap.put( zoneId, zone );
							*/
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
						allocator.readApplicants("./data/gate-applicant-20151129.csv", true);
						allocator.readCentres("./data/gate-centre-data.csv", true);
						//allocator.print();
						allocator.allocate();
						allocator.centreAllocation();
						allocator.print();

				}catch(Exception e){
						e.printStackTrace();
				}
		}

} 

