package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class PaperWiseSession{
		String paperCode;
		Map<Integer, Integer> sessionWiseCount; 
		
		PaperWiseSession(String paperCode){
			this.paperCode = paperCode;
			this.sessionWiseCount = new TreeMap<Integer, Integer>();
			this.sessionWiseCount.put(1, 0);
			this.sessionWiseCount.put(2, 0);
			this.sessionWiseCount.put(3, 0);
			this.sessionWiseCount.put(4, 0);
			this.sessionWiseCount.put(5, 0);
			this.sessionWiseCount.put(6, 0);
			this.sessionWiseCount.put(7, 0);
			this.sessionWiseCount.put(8, 0);
		}
		
		static void header(){
			System.out.println("PaperCode\t\tS1\tS2\tS3\tS4\tS5\tS6\tS7\tS8");
		}

		void print(){
			 System.out.print(paperCode+"\t");
			 Set<Integer> sessions = sessionWiseCount.keySet();
			 for(Integer session: sessions){
					System.out.print("\t"+sessionWiseCount.get(session) );
			 } 	
			 System.out.println();
		}
}

