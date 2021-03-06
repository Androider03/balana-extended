/**
 * 
 */
package varioustests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import conflictanalyzer.logic.ConflictingRulesPair;
import conflictanalyzer.logic.PolicyConflictAnalyzer;
import conflictanalyzer.statistics.ConflictsCollection;

/**
 * @author Guido
 *
 */
public class PolicyConflictAnalyzerTest {
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		//PolicyConflictAnalyzer conflictAnalyzer = new PolicyConflictAnalyzer("C:\\Users\\Guido\\Desktop\\continueA-policies", "Policy");
		//PolicyConflictAnalyzer conflictAnalyzer = new PolicyConflictAnalyzer("C:\\Users\\Guido\\Desktop\\basic-policies", "TestPolicy");
		
		//PolicyConflictAnalyzer conflictAnalyzer = new PolicyConflictAnalyzer("C:\\Users\\Guido\\Desktop\\ACDs", "ACD");
		PolicyConflictAnalyzer conflictAnalyzer = new PolicyConflictAnalyzer("C:\\Users\\Guido\\Desktop\\sample-policies\\synthetic360\\synthetic360-policies", "policy");
		//PolicyConflictAnalyzer conflictAnalyzer = new PolicyConflictAnalyzer("C:\\Users\\Guido\\Desktop\\sample-policies\\sample-xacml3\\continue-a-xacml3-policies", "policy");
		
		List<ConflictingRulesPair> conflicts = conflictAnalyzer.getConflicts();
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		int roundedDuration = (int) (duration/10000000);
		double durationInSeconds = (double) roundedDuration/100;
		
		/* STAMPA SU STANDARD OUTPUT
		if(conflicts.size() != 0) {
			for(ConflictingRulesPair conflictingRulesPair : conflicts)
				System.out.println(conflictingRulesPair);
		} else System.out.println("No conflicts detected.");*/
		
		System.out.println("\nConflicts detected: " + conflicts.size());
		System.out.println("Execution time for conflict detection: " + durationInSeconds 
				/*+ " \u00B5"*/ + "s");
		//Print statistical info
		ConflictsCollection collection = new ConflictsCollection(conflicts);
		System.out.println(collection.getStatisticalInfo());
		
		// STAMPA SU FILE
		List<String> lines = new ArrayList<String>();
		lines.add("\nConflicts detected: " + conflicts.size());
		lines.add("Execution time: " + durationInSeconds /*+ " \u00B5"*/ + "s");
		for(ConflictingRulesPair conflictingRulesPair : conflicts)
			lines.add(conflictingRulesPair.getCompactRepresentationWithWinningRule());
		Path file = Paths.get(
				System.getProperty("user.home"),"Desktop","sample-policies","sample-xacml3",
				"continue-a-xacml3-policies","Conflicts.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
