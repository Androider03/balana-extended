/**
 * 
 */
package conflictanalyzer.statistics;

import java.util.ArrayList;

import org.wso2.balana.Rule;

/**
 * @author Guido Marilli
 * Contains a collection of rules which have in common the same amount of conflicts.
 */
public class RulesWithSameNumberOfConflicts implements Comparable<RulesWithSameNumberOfConflicts> {
	
	/**
	 * The amount of conflicts in which the rules are involved.
	 */
	private int numberOfConflicts;
	
	/**
	 * A group of rules that are involved in the same amount of conflicts.
	 */
	private ArrayList<Rule> rules;

	/**
	 * @param numberOfConflicts the number of conflicts common to the rules
	 * @param rules a list of rules that are involved in the specified amount of conflicts.
	 */
	public RulesWithSameNumberOfConflicts(int numberOfConflicts, ArrayList<Rule> rules) {
		this.numberOfConflicts = numberOfConflicts;
		this.rules = rules;
	}

	/**
	 * @return the numberOfConflicts
	 */
	public int getNumberOfConflicts() {
		return numberOfConflicts;
	}

	/**
	 * @return the rules
	 */
	public ArrayList<Rule> getRules() {
		return rules;
	}
	
	/**
	 * @param i the index
	 * @return the i-th rule
	 */
	public Rule getRule(int i) {
		return rules.get(i);
	}

	/**
	 * @return a string representation of the rules in the group
	 */
	public String getRulesIDs() {
		String res = "";
		for(int i = 0; i < rules.size() - 1; i++)
			res += rules.get(i).getId() + ", ";
		res += rules.get(rules.size()-1).getId();
		return res;
	}

	@Override
	public int compareTo(RulesWithSameNumberOfConflicts other) {
		int res = 1;
		if(numberOfConflicts < other.getNumberOfConflicts())
			res = -1;
		else if(numberOfConflicts == other.getNumberOfConflicts())
			res = 0;
		return res;
	}

}
