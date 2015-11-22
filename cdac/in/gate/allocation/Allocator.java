package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

public class Allocator{

	Map<String, Zone> zoneMap;

	List<Applicant> applicants;
	List<Applicant> PwDApplicants;
	List<Applicant> otherApplicants;
	List<Applicant> femaleApplicants;


	public Allocator(){

		applicants = new ArrayList<Applicant>();
		PwDApplicants = new ArrayList<Applicant>();
		femaleApplicants = new ArrayList<Applicant>();
		otherApplicants = new ArrayList<Applicant>();

		zoneMap = new TreeMap<String, Zone>(); 
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
				if( tk.length < 9)	
					continue;
				Applicant applicant = new Applicant( tk[0].trim(), tk[1].trim(), tk[2].trim(), tk[3].trim(), tk[4].trim(), tk[5].trim(), tk[6].trim(), tk[7].trim(), tk[8].trim() );

				applicants.add( applicant );	
				
				if( tk[2].trim().equals("Female") ){
					femaleApplicants.add( applicant );
				}

				if( tk[3].trim().equals("t") ){	
					PwDApplicants.add( applicant );	
				}else{
					otherApplicants.add( applicant );		
				}	
				if( count % 100000 == 0){
					System.out.println(count+" Applicant Read!");
				}
			}

			System.out.println(PwDApplicants.size()+" Total PwD Applicant Read!");	
			System.out.println(femaleApplicants.size()+" Total Female Applicant Read!");	
			System.out.println(otherApplicants.size()+" Total other (not PwD) Applicant Read!");	
			System.out.println(applicants.size()+" Total Applicant Read!");	

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

		try{

			br = new BufferedReader( new FileReader(new File(filename) ) );	

			String line = null;	
			while( ( line =  br.readLine() ) != null ){

				if( withHeader ){
					withHeader = false; 
					continue;
				}

				String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				String zoneId = tk[0].trim();
				String zoneName = tk[1].trim();
				String cityCode = tk[2].trim();
				String centerCode = tk[3].trim();
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
					city = new City( cityCode );
				}

				city.centreMap.put(centerCode, new Centre( centerCode, sessions, PwDFriendly) );

				/* City Session Capacity Calculation START */
				i = 0;
				for( int s = 1; i < sessions.size(); i++, s++){

					Session session = city.sessionMap.get( s+"" );			
				    if( session == null ){
						session = new Session( s+"", Integer.parseInt( sessions.get(i) ) );
					}else{
						session.capacity += Integer.parseInt( sessions.get(i) ) ;
					}		
					city.sessionMap.put( s+"", session );
				}

				/* City Session Capacity Calculation  END */
				
				zone.cityMap.put( cityCode, city );
				zoneMap.put( zoneId, zone );
			}	

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

	void readConstraints(String filename){
		if( filename == null || filename.trim().length() == 0)	
			return;

		BufferedReader br =  null; 
		try{
			br = new BufferedReader( new FileReader(new File(filename) ) );	
			String line = null;	
			while( ( line =  br.readLine() ) != null ){
				String[] tk = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			}	

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

	void allocate(){

	}

	void print(){

	}

	public static void main(String[] args){

		try{

			Allocator allocator = new Allocator();
			//allocator.readApplicants("./data/applicant-20151120.csv", true);
			allocator.readCentres("./data/IISc.csv", true);
			allocator.readConstraints("");
			allocator.allocate();
			allocator.print();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

} 

