package cdac.in.gate.allocation;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Centre{

	String centreCode;
	String centreName;
	boolean pwdFriendly;

	Map<String, Session> sessions;

	Centre( String centreCode, String centreName, List<Session> sessions, String PwDFriendly ){

		this.centreName = centreName;
		this.centreCode = centreCode;

		if( PwDFriendly.equals("YES") || PwDFriendly.equals("Yes") || PwDFriendly.indexOf("Y") >= 0 || PwDFriendly.indexOf("y") >=0 )
			this.pwdFriendly = true;
		this.sessions = new TreeMap<String, Session>();

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
	
	void print(String zone, String cityCode){

		System.out.print( zone+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);
		Set<String> sessionIds = sessions.keySet();
		for(String sessionId: sessionIds){
			Session session = sessions.get( sessionId );
			System.out.print(", ("+session.maxCapacity + "|"+ session.capacity +"|"+ session.allocated +"|"+ session.pwdAllocated +")");
		}
		System.out.println();
	}
} 

