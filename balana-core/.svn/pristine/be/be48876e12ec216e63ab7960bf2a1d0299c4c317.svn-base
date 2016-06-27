/**
 * 
 */
package varioustests;

import org.wso2.balana.Policy;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

/**
 * @author Guido Marilli
 */
public class TestFileBasedPolicyFinderModule {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileBasedPolicyFinderModule fileBasedPolicyFinderModule = 
				new FileBasedPolicyFinderModule();
		String policyPath = "C:\\Users\\Guido\\workspace2\\balana-release-1.0.2\\modules"
				+ "\\balana-core\\src\\test\\resources\\basic\\3\\policies\\TestPolicy_0001.xml";
		PolicyFinder policyFinder = new PolicyFinder();
		
		Policy policy = (Policy) fileBasedPolicyFinderModule.loadPolicy(policyPath, policyFinder);
		System.out.println(policy.encode());
	}

}
