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

class NewCity{

		Integer cityCode;

		List<Integer> centreCodes;

		NewCity(Integer cityCode, String centres){

				this.cityCode = cityCode;
				String[] codes = centres.split("-", -1);	
				this.centreCodes = new ArrayList<Integer>();
				for(String city: codes ){
						if( city.trim().length() > 0 ){   
								this.centreCodes.add( new Integer( city.trim() ) );
						}	
				}
		}

}


class CentreDataMismatch{

		Centre centre;
		City city;
		Session session;

		CentreDataMismatch(Centre centre, City city, Session session){
				this.centre = centre;
				this.city = city;
				this.session = session;	
		}

		static void header(){
				System.err.println("CentreCode, Session, City, City-Name, Max, Allocation");
		}

		void print(){
				System.err.println( centre.centreCode+", "+session.sessionId+", "+city.cityCode+", "+city.cityName+", "+session.maxCapacity+", "+session.capacity);
		}


}

public class Allocator{

		static Map<Integer, Zone> zones = new TreeMap<Integer, Zone>();

		static Map<Integer, City> cities = new TreeMap<Integer, City>();

		static Map<String, PaperWiseSession> paperWiseSessions = new TreeMap<String, PaperWiseSession>();

		static Map<Integer, TreeMap<String, PaperWiseSession>>  paperWiseSessionZone  = new TreeMap<Integer, TreeMap<String, PaperWiseSession>>();

		static Map<String, CentreDataMismatch> centreDataMissMatches = new TreeMap<String, CentreDataMismatch>();

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

		static List<String> pwdRelex = new ArrayList<String>();

		public Allocator(){


				pwdRelex.add("B547M89");	
				pwdRelex.add("B222W13");	
				pwdRelex.add("B158Q44");	
				pwdRelex.add("B616A81");	

				/* Delhi */

				pwdRelex.add("B312Z13");
				pwdRelex.add("B501U13");
				pwdRelex.add("B298T13");
				pwdRelex.add("B167R15");
				pwdRelex.add("B607T16");
				pwdRelex.add("B247Y23");
				pwdRelex.add("B629V23");
				pwdRelex.add("B622P31");
				pwdRelex.add("B461Q32");
				pwdRelex.add("B166K37");
				pwdRelex.add("B624D41");
				pwdRelex.add("B599X45");
				pwdRelex.add("B596V47");
				pwdRelex.add("B393H54");
				pwdRelex.add("B639S61");
				pwdRelex.add("B538D61");
				pwdRelex.add("B186T66");
				pwdRelex.add("B559R73");
				pwdRelex.add("B499H73");
				pwdRelex.add("B163Q75");
				pwdRelex.add("B559C79");
				pwdRelex.add("B606U88");
				pwdRelex.add("B559W89");
				pwdRelex.add("B180N93");
				pwdRelex.add("B235Z94");
				pwdRelex.add("B394V97");



				//paperSession.put("ME","1,2,3");
				paperSession.put("ME","1,3,2");
				//paperSession.put("ME","3,1,2");
				paperSession.put("EC","1,3,4");
				paperSession.put("CS","5,6");
				//paperSession.put("CE","5,7");
				paperSession.put("CE","7,5");
				//paperSession.put("EE","6,8");
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
				System.out.println("ZoneId, Total Applicant, Allocated, Not Allocated, FirstChocie, SecondChoice, ThirdChocie ");	
				Set<Integer> zoneIds = zones.keySet();
				for( Integer zoneId: zoneIds ){
						Zone zone = zones.get( zoneId );
						System.out.println(zone.zoneId+", "+zone.applicants.size()+", "+zone.allocatedApplicants.size()+", "+zone.notAllocatedApplicants.size()+", "+zone.firstChoice+", "+zone.secondChoice+", "+zone.thirdChoice );
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
								String[] tk = line.split(",", -1);

								Integer zoneId = new Integer( tk[0].trim() );
								Zone zone = zones.get( zoneId );

								System.err.println("Zone"+tk[0].trim()+", "+tk[1].trim()+", "+tk[2].trim()+", "+tk[3].trim() );

								zone.cityChange.put( new Integer( tk[1].trim() ), new NewCity( new Integer( tk[2].trim() ), tk[3].trim() ) );

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
								applicant.firstChoice = new Integer( tk[6].trim() ) ;

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
								String centerName = tk[5].trim().replaceAll("\"","");
								String address1 =  tk[6].trim().replaceAll("\"","");	
								String address2 =  tk[7].trim().replaceAll("\"","");	
								String address3 =  tk[8].trim().replaceAll("\"","");		
								String pincode = tk[9].trim().replaceAll("\"","");
								String cityName = tk[10].trim().replaceAll("\"","");
								String state = tk[11].trim().replaceAll("\"","");
								Integer max = new Integer( tk[12].trim() );

								int maxCapacity = Integer.parseInt( tk[12].trim() );

								int S1ME1 = Integer.parseInt( tk[13].trim() );
								int S1EC1 = Integer.parseInt( tk[14].trim() );

								int S2ME2 = Integer.parseInt( tk[15].trim() );
								int S2BTCHGGMNPH = Integer.parseInt( tk[16].trim() );

								int S3ME3 = Integer.parseInt( tk[17].trim() );
								int S3EC2 = Integer.parseInt( tk[18].trim() );

								int S4EC3 = Integer.parseInt( tk[19].trim() );
								int S4ARCYINMAPE = Integer.parseInt( tk[20].trim() );

								int S5CS1 = Integer.parseInt( tk[21].trim() );
								int S5CE1 = Integer.parseInt( tk[22].trim() );

								int S6CS2 = Integer.parseInt( tk[23].trim() );
								int S6EE1 = Integer.parseInt( tk[24].trim() );

								int S7CE2 = Integer.parseInt( tk[25].trim() );
								int S7AGEYMTPI = Integer.parseInt( tk[26].trim() );

								int S8EE2 = Integer.parseInt( tk[27].trim() );
								int S8AETFXLXE = Integer.parseInt( tk[28].trim() );

								String PwDFriendly = "YES";

								if( zoneId != 7 ){
										PwDFriendly = tk[29].trim().replaceAll("\"","");
								}

								List<Session> sessions = new ArrayList<Session>();

								Session session = new Session(new Integer(1), ( S1EC1 + S1ME1 ), maxCapacity,  "30 January 2016 ( Saturday )", "Forenoon" ) ;
								session.paperCapacities.put("EC",new PaperCapacity( S1EC1 ) ) ;
								session.paperCapacities.put("ME",new PaperCapacity( S1ME1 ) ) ;
								sessions.add( session );

								session = new Session(new Integer(2), ( S2ME2 + S2BTCHGGMNPH ), maxCapacity, "30 January 2016 ( Saturday )", "Afternoon" );
								session.paperCapacities.put("ME", new PaperCapacity( S2ME2 ) );

								PaperCapacity pc = new PaperCapacity( S2BTCHGGMNPH );
								session.paperCapacities.put("BT", pc );
								session.paperCapacities.put("CH", pc );
								session.paperCapacities.put("GG", pc );
								session.paperCapacities.put("MN", pc );
								session.paperCapacities.put("PH", pc );

								sessions.add( session );

								session = new Session(new Integer(3), ( S3ME3 + S3EC2 ), maxCapacity, "31 January 2016 ( Sunday )", "Forenoon" );
								session.paperCapacities.put("ME", new PaperCapacity( S3ME3 ) );
								session.paperCapacities.put("EC",new PaperCapacity( S3EC2 ) );
								sessions.add( session );

								session = new Session(new Integer(4), ( S4EC3 + S4ARCYINMAPE ), maxCapacity, "31 January 2016 ( Sunday )", "Afternoon" );

								session.paperCapacities.put("EC", new PaperCapacity(  S4EC3 ) );
								pc = new PaperCapacity( S4ARCYINMAPE );
								session.paperCapacities.put("AR", pc );
								session.paperCapacities.put("CY", pc );
								session.paperCapacities.put("IN", pc );
								session.paperCapacities.put("MA", pc );
								session.paperCapacities.put("PE", pc );
								sessions.add( session );

								session = new Session(new Integer(5), ( S5CE1 + S5CS1 ), maxCapacity, "6 Febuary 2016 ( Saturday )", "Forenoon");
								session.paperCapacities.put("CE",new PaperCapacity( S5CE1 ) );
								session.paperCapacities.put("CS",new PaperCapacity( S5CS1 ) );
								sessions.add( session );

								session = new Session(new Integer(6), ( S6CS2 + S6EE1), maxCapacity, "6 Febuary 2016 ( Saturday )", "Afternoon" );
								session.paperCapacities.put("CS", new PaperCapacity( S6CS2 ) );
								session.paperCapacities.put("EE", new PaperCapacity( S6EE1 ) );
								sessions.add( session );

								session = new Session(new Integer(7), ( S7CE2 + S7AGEYMTPI ), maxCapacity, "7 Febuary 2016 ( Sunday )", "Forenoon" );
								session.paperCapacities.put("CE",new PaperCapacity( S7CE2 ) );
								pc = new PaperCapacity( S7AGEYMTPI );
								session.paperCapacities.put("AG", pc );
								session.paperCapacities.put("EY", pc );
								session.paperCapacities.put("MT", pc );
								session.paperCapacities.put("PI", pc );
								sessions.add( session );

								session = new Session(new Integer(8), ( S8EE2 + S8AETFXLXE ), maxCapacity, "7 Febuary 2016 ( Sunday )", "Afternoon" );
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
								zone.name = zoneName;
								City city = zone.cities.get( cityCode );

								if( city == null){
										city = new City( cityCode, cityName );
								}

								Centre centre = new Centre( centerCode, centerName, address1, address2, address3, pincode, cityName, state, max, sessions, PwDFriendly);
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


		boolean allocate(Zone zone, List<Applicant> applicants, int choiceNumber, boolean maximum, boolean femalePref,boolean  secondChoiceRespect,int reducedCapacity, int pwDpercent){

				boolean allocated = false;		

				String[] papers = {"AE", "AG", "AR", "BT", "CH", "CY", "EY", "GG", "IN", "MA", "MN", "MT", "PE", "PH", "PI", "TF", "XE", "XL", "CE", "EC","EE", "CS", "ME"};

				for(String paper: papers)

						for(Applicant applicant: applicants){

								if( applicant.isAllocated ){
										continue;
								}

								/*
								 * if( applicant.isPwD && ( choiceNumber == 1 )  ) {
								 continue;
								 }
								 **/

								if( ! applicant.paperCode.equals( paper ) && ( choiceNumber == 1 || maximum ) ){
										continue;
								}

								/* for cityChnage female applicants should get the preferance */

								if( femalePref && applicant.gender.equals("Male") && zone.cityChange.get( applicant.firstChoice ) != null ){
										continue;
								}

								if( secondChoiceRespect && zone.cityChange.get( applicant.firstChoice ) != null && applicant.choices[1].intValue() == zone.cityChange.get(applicant.firstChoice).cityCode.intValue() ){
										continue;	
								}

								Integer choice = applicant.choices[ choiceNumber ];

								City city = cities.get( choice );

								if( city == null ){
										continue;
								}

								NewCity newCity = null;


								if( choiceNumber == 1  &&  zone.cityChange.get( applicant.firstChoice ) != null 
												&& choice.intValue() == zone.cityChange.get( applicant.firstChoice ).cityCode.intValue() ){ //don't give second choice incase of shift
										continue;
								}

								if( choiceNumber == 0 &&  choice.intValue() != applicant.firstChoice.intValue() ){  // during shifting only consider particular centre

										newCity = zone.cityChange.get( applicant.firstChoice );

										if(newCity != null && newCity.centreCodes.size() == 0){
												newCity = null;	
										}
								}

								for(Centre centre: city.centres){

										if( centre.centreCode.intValue() == 2057 && applicant.gender.equals("Female") ){
												applicant.print( true );
												continue;
										}

										if( applicant.isPwD && ( !centre.pwdFriendly ) && !pwdRelex.contains( applicant.enrollment ) ){
												continue;	
										}

										if( newCity != null && ! newCity.centreCodes.contains( centre.centreCode ) ){
												continue;
										}

										String[] sessionIds = paperSession.get( applicant.paperCode ).split(",",-1);

										for(String sessionId: sessionIds){

												Session session = centre.sessions.get( new Integer( sessionId ) );

												PaperCapacity pc = session.paperCapacities.get( applicant.paperCode );	


												if( applicant.isPwD && session.pwdAllocated >=  ((session.maxCapacity * pwDpercent) / 100)  )
														continue;

												if( pc == null ){
														System.err.println( session.sessionId+", "+applicant.paperCode+", PC not found");
														System.exit(0);
												}

												if( ( session.maxCapacity - session.allocated ) <= 0 &&  ( session.capacity   > session.maxCapacity )  ){
														centreDataMissMatches.put(centre.centreCode+""+session.sessionId+""+city.cityCode, new CentreDataMismatch( centre, city, session )  );
														continue;	
												}

												if( ( ( pc.capacity - reducedCapacity) > pc.allocated  &&  ( session.capacity - reducedCapacity ) > session.allocated  ) || 
																( maximum && ( session.maxCapacity - session.allocated ) > 0 && session.capacity > 0 ) ) { 

														allocated = true;		
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
														List<Applicant> tapplicants = session.applicants.get( applicant.paperCode );

														if( paperApplicants == null ){
																paperApplicants = new ArrayList<Applicant>();
																tapplicants = new ArrayList<Applicant>();
														}	

														paperApplicants.add( applicant );
														tapplicants.add( applicant );
														session.paperAllocatedApplicant.put( applicant.paperCode, paperApplicants );
														session.applicants.put( applicant.paperCode, tapplicants );
												}	
												if( applicant.isAllocated )
														break;
										}		
										if( applicant.isAllocated )
												break;
								}

						}	
				return allocated;	
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

		void printCentres( boolean iiscFormate ){

				System.out.println("-----------------------------------------------------------");	
				Centre.header( iiscFormate );

				Set<Integer> zoneIds = zones.keySet();
				for(Integer zoneId: zoneIds){
						Zone zone = zones.get( zoneId );
						Set<Integer> cityCodes = zone.cities.keySet();
						for(Integer cityCode: cityCodes){
								City city = zone.cities.get( cityCode );
								city.print( zone, iiscFormate );
						}
				}
		}

		void printAllocation( boolean cemraRedy ){

				System.out.println("-----------------------------------------------------------");	
				Applicant.header( cemraRedy );

				for(Applicant applicant: allAllocatedApplicants ){


						/* Zone Wise */

						TreeMap<String, PaperWiseSession> pwsz = paperWiseSessionZone.get( applicant.zoneId );
						if( pwsz == null){
								pwsz = new TreeMap<String, PaperWiseSession>();
						}	
						PaperWiseSession pws = pwsz.get( applicant.paperCode );
						if( pws == null){
								pws = new PaperWiseSession( applicant.paperCode );
						}
						Integer count = pws.sessionWiseCount.get( applicant.session.sessionId );
						count++;
						pws.sessionWiseCount.put( applicant.session.sessionId, count );
						pwsz.put( applicant.paperCode, pws);
						paperWiseSessionZone.put( applicant.zoneId, pwsz);

						/* Over All */

						pws = paperWiseSessions.get( applicant.paperCode );
						if( pws == null ){
								pws = new PaperWiseSession( applicant.paperCode );
						}
						count = pws.sessionWiseCount.get( applicant.session.sessionId );
						count++;
						pws.sessionWiseCount.put( applicant.session.sessionId, count );
						paperWiseSessions.put( applicant.paperCode, pws);


						applicant.print( cemraRedy );
				}

				System.out.println("------------------ Not Allocated Candidate ---------------");

				if( allNotAllocatedApplicants.size() > 0 ){
						Applicant.header( false );
						for(Applicant applicant: allNotAllocatedApplicants ){
								applicant.print( false );
						}
				}

				System.out.println("Total Allocated    :"+ allAllocatedApplicants.size() );
				System.out.println("Total notAllocated :"+ allNotAllocatedApplicants.size() );
		}

		void cityChangeUpdate(List<Applicant> applicants, Map<Integer, NewCity> cityChange, Integer onlyCity){
				for( Applicant applicant: applicants){
						if( !applicant.isAllocated ){
								NewCity newCity = cityChange.get( applicant.choices[0] );
								if( onlyCity.intValue() != -1 && applicant.choices[0].intValue() == onlyCity.intValue() ){
									if( newCity != null ){
											applicant.choices[0] = newCity.cityCode;
									}
								}else if ( onlyCity.intValue() == -1){

									if( newCity != null ){
											applicant.choices[0] = newCity.cityCode;
									}
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
								if( applicant.allotedChoice == 1)
										zone.firstChoice++;
								else if ( applicant.allotedChoice == 2 )		
										zone.secondChoice++;
								else if ( applicant.allotedChoice == 3)
										zone.thirdChoice++;
						}
				}		
		}

		void zoneWiseAnalyisPrint(){

				if( zoneWiseAnalysis.keySet().size() > 0 ){

						System.out.println("----------------------------------------------------------");	
						System.out.println("ZoneId, CityCode(cityName), Paper:not-AllocatedCount, ...");

						Set<Integer> zoneIds = zoneWiseAnalysis.keySet();

						for(Integer zoneId: zoneIds ){
								zoneWiseAnalysis.get( zoneId ).print();
								System.out.println();
						}
				}
		}

		void cityChangeUpdate(int zoneId, Integer onlyCity){

				Zone zone = zones.get( new Integer( zoneId ) );	
				cityChangeUpdate(  zone.applicants, zone.cityChange, onlyCity );	
		}

		void allocate(int  zoneId, int choiceNo, boolean maximum, int pwDpercent ){

				Zone zone = zones.get( new Integer( zoneId ) );	

				Collections.sort( zone.pwdApplicants , new ApplicantComp() );	
				Collections.sort( zone.applicants , new ApplicantComp() );	

				int tchoiceNo = choiceNo;
				int tpwDpercent = pwDpercent;

				while( allocate( zone, zone.pwdApplicants, 0, maximum, false, false, 1, ++tpwDpercent ) ) ;

				System.out.println("first Choice Final PwD (%): "+ tpwDpercent );

				tpwDpercent = pwDpercent;

				while( allocate( zone, zone.pwdApplicants, 1, true, false, false, 1, ++tpwDpercent ) ) ;

				System.out.println("Second Choice Final PwD (%): "+ tpwDpercent );

				allocate(zone, zone.applicants, choiceNo, maximum, true, false, 1, 0 /* female only for city change */ );
				allocate(zone, zone.applicants, choiceNo, maximum, false, true, 1, 0 /* for maximum second choice */ );
				allocate(zone, zone.applicants, choiceNo, maximum, false, false, 1, 0 );

				allocate(zone, zone.applicants, choiceNo, maximum, true, false, 0, 0  /* female only for city change */ );
				allocate(zone, zone.applicants, choiceNo, maximum, false, true, 0, 0  /* for maximum second choice */ );
				allocate(zone, zone.applicants, choiceNo, maximum, false, false, 0, 0 );
		}


		void allocate(int zone, boolean second, int pwDpercent){

				cityChangeUpdate( zone , 159);

				allocate(zone, 0, false, pwDpercent);

				if( zone == 7){
						if( zones.get( zone ).cityChange.keySet().size() > 0 ){
								cityChangeUpdate( zone , -1);
								allocate(zone, 0, true, pwDpercent);
						}
				}

				boolean maximum = true;

				if( second ){
						allocate(zone, 1, false, pwDpercent);
						allocate(zone, 1, maximum, pwDpercent);
				}
				
				if( zones.get( zone ).cityChange.keySet().size() > 0 ){

						cityChangeUpdate( zone , -1);
						allocate(zone, 0, maximum, pwDpercent);
				}
		}

		void printErrorData(){
				Set<String> keys = centreDataMissMatches.keySet();
				if( keys.size() > 0){
						CentreDataMismatch.header();
						for(String key: keys){
								centreDataMissMatches.get( key ).print();	
						}
				}	

		}

		void printSessionWisePaper(){

				Set<String> papers = paperWiseSessions.keySet();
				PaperWiseSession.header();
				for(String paper: papers){
						paperWiseSessions.get( paper ).print();
				}

				Set<Integer>  zones = paperWiseSessionZone.keySet();
				System.out.println("----------------------------------------------------------------");
				System.out.println("ZoneId\tPaperCode\t\tS1\tS2\tS3\tS4\tS5\tS6\tS7\tS8");
				for(Integer zone: zones){
						TreeMap<String, PaperWiseSession> pws = paperWiseSessionZone.get( zone );
						papers = pws.keySet();
						for(String paper: papers){
								System.out.print(zone+"\t");
								pws.get( paper ).print();
						}
						System.out.println("----------------------------------------------------------------");
				}

		}

		public static void main(String[] args){

				boolean cemraRedy = false;
				int i = 0;
				while( i < args.length ){
						if( args[i].equals("-cr") )
								cemraRedy = true;
						i++;
				}

				try{

						Allocator allocator = new Allocator();

						//allocator.readApplicants("./data/applicant-2015-12-03.csv", true);

						allocator.readApplicants("./data/applicant-2015-12-08.csv", true);

						allocator.readCentres("./data/zone2.csv", true);
						allocator.readCentres("./data/zone3.csv", true);
						allocator.readCentres("./data/zone4.csv", true);
						allocator.readCentres("./data/zone5.csv", true);
						allocator.readCentres("./data/zone6.csv", true);
						allocator.readCentres("./data/zone7.csv", true);
						allocator.readCentres("./data/zone8.csv", true);

						allocator.readCityChangeMapping("./data/city-change.csv",true);
						allocator.readCityCodeMapping("./data/gate-examcity-code.csv", true);

						allocator.printDataDetails();

						allocator.allocate(2, true, 1);
						allocator.allocate(3, true, 1);
						allocator.allocate(3, true, 1);
						allocator.allocate(3, true, 1);
						allocator.allocate(4, true, 1);
						allocator.allocate(5, false, 1);
						allocator.allocate(6, true, 1);
						allocator.allocate(7, true, 1);
						allocator.allocate(8, true, 1);

						allocator.centreAllocation();

						allocator.allocationAnalysis(2);
						allocator.allocationAnalysis(3);
						allocator.allocationAnalysis(4);
						allocator.allocationAnalysis(5);
						allocator.allocationAnalysis(6);
						allocator.allocationAnalysis(7);
						allocator.allocationAnalysis(8);

						allocator.printErrorData();
						allocator.printCentres( true );	
						//allocator.printCentres( false );
						allocator.printAllocation( cemraRedy );
						allocator.zoneWiseAllocationDetails();
						allocator.zoneWiseAnalyisPrint();
						allocator.printSessionWisePaper();

				}catch(Exception e){
						e.printStackTrace();
				}
		}
} 
