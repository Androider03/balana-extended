/**
 * 
 */
package utilities;

import java.util.List;
import java.util.Random;

import org.wso2.balana.AbstractPolicy;

/**
 * @author Guido Marilli
 *
 */
public class PriorityRandomizer {
	
	/**
	 * Assigns a random (legal) value to the specified policy's priority
	 * @param policy
	 */
	public static void randomizePriority(AbstractPolicy policy) {
		Random random = new Random();
		int randomPriority = random.nextInt(AbstractPolicy.LOWEST_PRIORITY_LEVEL + 1);
		policy.setPriority(randomPriority);
	}
	
	/**
	 * Randomizes the priorities of a set of policies.
	 * @param policies the policies whose priorities have to be randomized
	 */
	public static void randomizePoliciesPriorities(List<AbstractPolicy> policies) {
		for (AbstractPolicy abstractPolicy : policies) {
			randomizePriority(abstractPolicy);
		}
	}
	
}
