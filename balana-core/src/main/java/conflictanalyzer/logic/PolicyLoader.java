/**
 * 
 */
package conflictanalyzer.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;
import org.wso2.balana.*;

/**
 * @author Guido Marilli
 * An object that contains all the policies in a specified base path.
 */
public class PolicyLoader {
	
	private FileBasedPolicyFinderModule finderModule;
	
	private PolicyFinder policyFinder;
	
	/**
	 * Contains all the policies found in the files that respect the path: 
	 * basePath/policiesBaseName_xxx.xml
	 */
	private ArrayList<AbstractPolicy> policies;
	
	/**
	 * The base directory in which the policies files are stored.
	 */
	private String basePath;
	
	/**
	 * The prefix common to all the files in the base directory that contain the policies.
	 */
	private String policiesBaseName;
	
	/**
	 * @param basePath the base path in which the policies are stored
	 * @param policiesBaseName the prefix with every policy file starts
	 */
	public PolicyLoader(String basePath, String policiesBaseName) {
		finderModule = new FileBasedPolicyFinderModule();
		policyFinder = new PolicyFinder();
		policies = new ArrayList<AbstractPolicy>();
		this.basePath = basePath;
		this.policiesBaseName = policiesBaseName;
	}

	/**
	 * @return the basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @return the policiesBaseName
	 */
	public String getPoliciesBaseName() {
		return policiesBaseName;
	}

	/**
	 * @param policiesBaseName the policiesBaseName to set
	 */
	public void setPoliciesBaseName(String policiesBaseName) {
		this.policiesBaseName = policiesBaseName;
	}

	/**
	 * @return the policies
	 */
	public ArrayList<AbstractPolicy> getPolicies() {
		return policies;
	}
	
	/**
	 * Loads all the policies in the files in the <code>basePath</code>, whose names start
	 * with the specified <code>policiesBaseName</code>.</br>
	 * The file names must be specified with the format:
	 * <code>policiesBaseName</code>_000x.xml<br/>
	 * where x is the progression number (starting from 1). At the file missing the loading 
	 * process will stop.
	 */
	public void loadPolicies() {
		String filePath = "";
		File folder = new File(basePath);
		File[] listOfFiles = folder.listFiles();
		List<Integer> suffixes = new ArrayList<Integer>();
		int beginIndex = policiesBaseName.length() + 1;
		
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile() 
					&& listOfFiles[i].getName().startsWith(policiesBaseName + "_")) {
				String fileNumber = listOfFiles[i].getName().substring(beginIndex, beginIndex + 4);
				suffixes.add(Integer.parseInt(fileNumber));
			}
		}
		
		int startIndex = suffixes.get(0);
		int endIndex = suffixes.get(0);
		for(int j = 1; j < suffixes.size(); j++) {	// Finds the minimum index
			if(suffixes.get(j) < startIndex)
				startIndex = suffixes.get(j);
			if(suffixes.get(j) > endIndex)
				endIndex = suffixes.get(j);
		}
		
		for(int i = startIndex; i <= endIndex; i++) {
			filePath = getCompletePath(i);
			if (new File(filePath).exists()) {
				AbstractPolicy policy = finderModule.loadPolicy(filePath, policyFinder);
				policy.setPolicyFile(new File(filePath));
				policies.add(policy);
			}
		}
	}
	
	/**
	 * @param index the index (counter), with which the file name finishes
	 * @return the complete path to the XML file which contains a policy
	 */
	private String getCompletePath(int index) {
		if(index < 10)
			return basePath + File.separator + policiesBaseName + "_000" + index + ".xml";
		else if(index < 100)
			return basePath + File.separator + policiesBaseName + "_00" + index + ".xml";
		else if(index < 1000)
			return basePath + File.separator + policiesBaseName + "_0" + index + ".xml";
		else return basePath + File.separator + policiesBaseName + "_" + index + ".xml";
	}
}
