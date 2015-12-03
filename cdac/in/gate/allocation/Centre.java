package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Centre{

	Integer centreCode;
	String centreName;
	boolean pwdFriendly;

	Map<Integer, Session> sessions;

	Centre(Integer centreCode, String centreName, List<Session> sessions, String PwDFriendly ){

		this.centreName = centreName;
		this.centreCode = new Integer( centreCode );

		if( PwDFriendly.equals("YES") || PwDFriendly.equals("Yes") || PwDFriendly.indexOf("Y") >= 0 || PwDFriendly.indexOf("y") >=0 )
			this.pwdFriendly = true;
		this.sessions = new TreeMap<Integer, Session>();

		for(Session session: sessions){
			this.sessions.put( session.sessionId, session );
		}

	}

    static void header(){
		System.out.print("Zone, CityCode, Centre-Code, CentreName, Pwd-Friendly");
		for(int i = 1; i < 9; i++){
			System.out.print(", ( S"+i+"| MaxCapacity | Capacity | Allocated | PwD)");	
		}
		System.out.println();
	}
	
	void print(Integer zone, Integer cityCode){

		System.out.print( zone+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);
		Set<Integer> sessionIds = sessions.keySet();

		for(Integer sessionId: sessionIds){
			Session session = sessions.get( sessionId );
			System.out.print(", ("+session.maxCapacity + "|"+ session.capacity +"|"+ session.allocated +"|"+ session.pwdAllocated +")");
		}

		System.out.println();
		System.out.print( zone+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);

		for(Integer sessionId: sessionIds){
			Session session = sessions.get( sessionId );
			Set<String> paperCodes = session.paperAllocatedApplicant.keySet();
			System.out.print(", S"+sessionId+"[ ");
			boolean flag = true;

			for(String paperCode: paperCodes){
				PaperCapacity pc = session.paperCapacities.get( paperCode );
				if( flag ){
					flag = false;
					System.out.print(paperCode+":"+pc.allocated);	
				}else{
					System.out.print(" | "+paperCode+":"+pc.allocated);	
				}
			}	
			System.out.print(" ] ");
		}

		System.out.println();
	}
} 

