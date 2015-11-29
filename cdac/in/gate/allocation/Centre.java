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

	Centre( String centreCode, String centreName, List<String>sessions, String PwDFriendly ){

		this.centreName = centreName;
		this.centreCode = centreCode;

		if( PwDFriendly.equals("YES") || PwDFriendly.equals("Yes") || PwDFriendly.indexOf("Y") >= 0 || PwDFriendly.indexOf("y") >=0 )
			this.pwdFriendly = true;

		this.sessions = new TreeMap<String, Session>();

		for(int i = 0, s = 1; i < sessions.size(); i++, s++){
			Session session =  null;
			if( pwdFriendly )	
				this.sessions.put(s+"", new Session(s+"", Integer.parseInt( sessions.get(i) ), 5 ) );
			else
				this.sessions.put(s+"", new Session(s+"", Integer.parseInt( sessions.get(i) ), 0 ) );
		}
	}

    void header(){
		System.out.print("Zone, CityCode, Centre-Code, CentreName, Pwd-Friendly");
		Set<String> sessionIds = sessions.keySet();
		for(String sessionId: sessionIds){
			System.out.print(", ( S"+sessions.get(sessionId).sessionId+"|Capacity|Allocated|PwD)");	
		}
		System.out.println();
	}
	
	void print(String zone, String cityCode){

		System.out.print( zone+", "+cityCode+", "+centreCode+", '"+centreName+"', "+pwdFriendly);
		Set<String> sessionIds = sessions.keySet();
		for(String sessionId: sessionIds){
			Session session = sessions.get( sessionId );
			System.out.print(", ("+session.capacity+"|"+session.allocated+"|"+session.pwdCount+")");
		}
		System.out.println();
	}
} 

