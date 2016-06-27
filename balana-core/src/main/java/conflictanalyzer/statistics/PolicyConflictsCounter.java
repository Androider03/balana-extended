/**
 * 
 */
package conflictanalyzer.statistics;

import org.wso2.balana.AbstractPolicy;

/**
 * @author Guido Marilli
 *
 */
public class PolicyConflictsCounter {

	private AbstractPolicy policy;
	
	private int numberOfConflicts;

	/**
	 * @param policy
	 * @param numberOfConflicts
	 */
	public PolicyConflictsCounter(AbstractPolicy policy, int numberOfConflicts) {
		this.policy = policy;
		this.numberOfConflicts = numberOfConflicts;
	}

	/**
	 * Generates a <code>PolicyConflictsCounter</code> where the counter is initialized at 0.
	 * @param policy
	 */
	public PolicyConflictsCounter(AbstractPolicy policy) {
		this.policy = policy;
		numberOfConflicts = 0;
	}

	/**
	 * @return the numberOfConflicts
	 */
	public int getNumberOfConflicts() {
		return numberOfConflicts;
	}

	/**
	 * @param numberOfConflicts the numberOfConflicts to set
	 */
	public void setNumberOfConflicts(int numberOfConflicts) {
		this.numberOfConflicts = numberOfConflicts;
	}

	/**
	 * @return the policy
	 */
	public AbstractPolicy getPolicy() {
		return policy;
	}
	
	/**
	 * Increases the number of conflicts by one.
	 */
	public void addConflict() {
		numberOfConflicts++;
	}

}
