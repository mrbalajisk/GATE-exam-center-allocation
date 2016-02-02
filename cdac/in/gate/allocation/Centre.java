package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Centre{

	Integer centreCode;
	String centreName;
	String address1;
	String address2;
	String address3;
	String pincode;
	String city;
	String state;
	Integer max;
	boolean pwdFriendly;

	Map<Integer, Session> sessions;

	Centre(Integer centreCode, String centreName,String add1, String add2, String add3, String pincode, String city, String state, Integer max, List<Session> sessions, String PwDFriendly ){

		this.centreName = centreName.replaceAll("\"","");
		this.address1 = add1.replaceAll("\"","");
		this.address2 = add2.replaceAll("\"","");
		this.address3 = add3.replaceAll("\"","");
		this.pincode = pincode;
		this.city = city;
		this.state = state;
		this.max = max;
		
		this.centreCode = new Integer( centreCode );

		if( PwDFriendly.equals("YES") || PwDFriendly.equals("Yes") || PwDFriendly.indexOf("Y") >= 0 || PwDFriendly.indexOf("y") >=0 )
			this.pwdFriendly = true;
		this.sessions = new TreeMap<Integer, Session>();

		for(Session session: sessions){
			this.sessions.put( session.sessionId, session );
		}

	}

    static void header( boolean iiscFormate){
		if( iiscFormate ){
		System.out.println("Zone ID	Zone	City Code	TCS ID 	Centre code	LISP	Add1	Add2	Add3	Pincode	City	State	Max	S1-ME1	S1-EC1	S2-ME2	S2-BT,CH,GG,MN,PH	S3-ME3	S3-EC2	S4-EC3	S4-AR,CY,IN,MA,PE	S5-CS1	S5-CE1	S6-CS2	S6-EE1	S7-CE2	S7-AG,EY,MT,PI	S8-EE2	S8-AE,TF,XL,XE");
		//	System.out.println("Zone ID	Zone	City Code	TCS ID 	Centre code	LISP	Add1	Add2	Add3	Pincode	City	State	Max	S1	S2	S3	S4	S5	S6	S7	S8");
		}
		else{
			System.out.print("Zone, CityCode, Centre-Code, CentreName, Pwd-Friendly");
			for(int i = 1; i < 9; i++){
				System.out.print(", ( S"+i+"| MaxCapacity | Capacity | Allocated | PwD)");	
			}
			System.out.println();
		}
	
	}
	
	void print(Zone zone, Integer cityCode, boolean iiscFormate){


		if( iiscFormate ){
				/*
				System.out.print(zone.zoneId+"\t"+zone.name+"\t"+cityCode+"\t\t"+centreCode+"\t"+centreName+"\t"+address1+"\t"+address2+"\t"+address3+"\t"+pincode+"\t"+city+"\t"+state+"\t"+max);
				System.out.print("\t"+ sessions.get( 1 ).maxCapacity +"|"+ ( sessions.get( 1 ).paperCapacities.get("ME").allocated + sessions.get( 1 ).paperCapacities.get("EC").allocated ) );
				System.out.print("\t"+ sessions.get( 2 ).maxCapacity +"|"+ ( sessions.get( 2 ).paperCapacities.get("ME").allocated + sessions.get( 2 ).paperCapacities.get("BT").allocated ) );
				System.out.print("\t"+ sessions.get( 3 ).maxCapacity +"|"+ ( sessions.get( 3 ).paperCapacities.get("ME").allocated + sessions.get( 3 ).paperCapacities.get("EC").allocated ) );
				System.out.print("\t"+ sessions.get( 4 ).maxCapacity +"|"+ ( sessions.get( 4 ).paperCapacities.get("EC").allocated + sessions.get( 4 ).paperCapacities.get("AR").allocated ) );
				System.out.print("\t"+ sessions.get( 5 ).maxCapacity +"|"+ ( sessions.get( 5 ).paperCapacities.get("CS").allocated + sessions.get( 5 ).paperCapacities.get("CE").allocated ) );
				System.out.print("\t"+ sessions.get( 6 ).maxCapacity +"|"+ ( sessions.get( 6 ).paperCapacities.get("CS").allocated + sessions.get( 6 ).paperCapacities.get("EE").allocated ) );
				System.out.print("\t"+ sessions.get( 7 ).maxCapacity +"|"+ ( sessions.get( 7 ).paperCapacities.get("CE").allocated + sessions.get( 7 ).paperCapacities.get("AG").allocated ) );
				System.out.print("\t"+ sessions.get( 8 ).maxCapacity +"|"+ ( sessions.get( 8 ).paperCapacities.get("EE").allocated + sessions.get( 8 ).paperCapacities.get("AE").allocated ) );
				System.out.println();

				*/

				System.out.print(zone.zoneId+"\t"+zone.name+"\t"+cityCode+"\t\t"+centreCode+"\t"+centreName+"\t"+address1+"\t"+address2+"\t"+address3+"\t"+pincode+"\t"+city+"\t"+state+"\t"+max);
				System.out.print("\t"+ sessions.get( 1 ).paperCapacities.get("ME").allocated );
				System.out.print("\t"+ sessions.get( 1 ).paperCapacities.get("EC").allocated );
				System.out.print("\t"+ sessions.get( 2 ).paperCapacities.get("ME").allocated );
				System.out.print("\t"+ sessions.get( 2 ).paperCapacities.get("BT").allocated );
				System.out.print("\t"+ sessions.get( 3 ).paperCapacities.get("ME").allocated );
				System.out.print("\t"+ sessions.get( 3 ).paperCapacities.get("EC").allocated );
				System.out.print("\t"+ sessions.get( 4 ).paperCapacities.get("EC").allocated );
				System.out.print("\t"+ sessions.get( 4 ).paperCapacities.get("AR").allocated );
				System.out.print("\t"+ sessions.get( 5 ).paperCapacities.get("CS").allocated );
				System.out.print("\t"+ sessions.get( 5 ).paperCapacities.get("CE").allocated );
				System.out.print("\t"+ sessions.get( 6 ).paperCapacities.get("CS").allocated );
				System.out.print("\t"+ sessions.get( 6 ).paperCapacities.get("EE").allocated );
				System.out.print("\t"+ sessions.get( 7 ).paperCapacities.get("CE").allocated );
				System.out.print("\t"+ sessions.get( 7 ).paperCapacities.get("AG").allocated );
				System.out.print("\t"+ sessions.get( 8 ).paperCapacities.get("EE").allocated );
				System.out.print("\t"+ sessions.get( 8 ).paperCapacities.get("AE").allocated );
				System.out.println();

		}else{

				System.out.print( zone.zoneId+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);
				Set<Integer> sessionIds = sessions.keySet();

				for(Integer sessionId: sessionIds){
					Session session = sessions.get( sessionId );
					System.out.print(", ("+session.maxCapacity + "|"+session.capacity +"|"+ session.allocated +"|"+ session.pwdAllocated +")");
				}

				System.out.println();
				System.out.print( zone.zoneId+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);

				for(Integer sessionId: sessionIds){
					Session session = sessions.get( sessionId );
					Set<String> paperCodes = session.paperAllocatedApplicant.keySet();
					System.out.print(", S"+sessionId+"[ ");
					boolean flag = true;
					for(String paperCode: paperCodes){
						List<Applicant> applicants =  session.applicants.get( paperCode );
						//PaperCapacity pc = session.paperCapacities.get( paperCode );
						if( flag ){
							flag = false;
							//System.out.print(paperCode+":"+pc.allocated);	
							System.out.print(paperCode+":"+applicants.size() );	
						}else{
							//System.out.print(" | "+paperCode+":"+pc.allocated);	
							System.out.print(" | "+paperCode+":"+applicants.size());	
						}
					}	
					System.out.print(" ] ");
				}

				System.out.println();
		}
	}

} 

