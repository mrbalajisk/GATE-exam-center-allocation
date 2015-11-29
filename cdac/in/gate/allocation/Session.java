package cdac.in.gate.allocation;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.Set;

class PaperCapacity{

	int capacity;
	int allocated;
	
	PaperCapacity(int capacity){
		this.capacity = capacity;
		this.allocated = 0;
	}
}


public class Session{

	String sessionId;

	int capacity;
	int allocated;
	int pwdAllocated;
	
	String date;
	String time;

	Map<String, PaperCapacity> paperCapacities;
	Map<String, List<Applicant>> paperAllocatedApplicant;

	Session(String sessionId, int capacity, String date, String time){

		this.sessionId = sessionId;
		this.capacity = capacity;
		this.date = date;
		this.time = time;

		this.pwdAllocated = 0;
		this.allocated = 0;
		this.paperCapacities = new TreeMap<String, PaperCapacity>();
		this.paperAllocatedApplicant = new TreeMap<String, List<Applicant>>();

	}
	
	boolean isFull(){
		if( allocated >= capacity)
			return true;
		return false;	
	}

	void print(String zoneCode, String cityCode, String centreCode, String centreName){
		System.out.print(zoneCode+", "+cityCode+", "+centreCode+", "+centreName+", "+sessionId+", "+capacity+", "+allocated+", "+pwdAllocated);
		Set<String> paperCodes = paperCapacities.keySet();
		for(String paperCode: paperCodes){
				System.out.print(", ("+paperCode+"|"+paperCapacities.get( paperCode ).capacity+"|"+paperCapacities.get( paperCode ).allocated+")");
		}
	}
} 
