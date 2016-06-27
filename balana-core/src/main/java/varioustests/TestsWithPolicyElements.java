package varioustests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wso2.balana.AbstractPolicy;
import org.wso2.balana.DOMHelper;
import org.wso2.balana.ParsingException;
import org.wso2.balana.Policy;
import org.wso2.balana.PolicyMetaData;
import org.wso2.balana.PolicySet;
import org.wso2.balana.TargetFactory;
import org.wso2.balana.XACMLConstants;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.xacml3.AllOfSelection;
import org.wso2.balana.xacml3.AnyOfSelection;
import org.wso2.balana.xacml3.Target;

import conflictanalyzer.logic.PolicyLoader;

public class TestsWithPolicyElements {
	
	private static Log log = LogFactory.getLog(TestsWithPolicyElements.class);
	
	public static void main(String[] args) {
		PolicyLoader loader = new PolicyLoader("C:\\Users\\Guido\\workspace2\\balana-release-1.0.2\\modules"
				+ "\\balana-core\\src\\test\\resources\\basic\\3\\policies\\", "TestPolicy");
		loader.loadPolicies();
		/*ArrayList<AbstractPolicy> policies = loader.getPolicies();
		
		Policy policy = (Policy) policies.get(0);*/
		
		TargetFactory targetFactory = new TargetFactory();
		
		PolicyMetaData metaData = new PolicyMetaData(XACMLConstants.XACML_VERSION_3_0, 
				PolicyMetaData.XPATH_VERSION_UNSPECIFIED);
		
		//--------------------------------
		
		AbstractPolicy policy = null;
        InputStream stream = null;
        
        PolicyFinder finder = new PolicyFinder();
        NodeList children = null;
        
        try {
            // create the factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setNamespaceAware(true);
            factory.setValidating(false);

            // create a builder based on the factory & try to load the policy
            DocumentBuilder db = factory.newDocumentBuilder();
            stream = new FileInputStream("C:\\Users\\Guido\\workspace2\\balana-release-1.0.2"
            		+ "\\modules\\balana-core\\src\\test\\resources\\basic\\3\\policies"
            		+ "\\TestPolicy_0001.xml");
            Document doc = db.parse(stream);

            // handle the policy, if it's a known type
            Element root = doc.getDocumentElement();
            String name = DOMHelper.getLocalName(root);

            if (name.equals("Policy")) {
                policy = Policy.getInstance(root);
            } else if (name.equals("PolicySet")) {
                policy = PolicySet.getInstance(root, finder);
            }
            
            // NEW CODE
            children = root.getChildNodes();
        } catch (Exception e) {
            // just only logs
            log.error("Fail to load policy : " + "\\TestPolicy_0001.xml" , e);
        } finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("Error while closing input stream");
                }
            }
        }
        
        //System.out.println(children.item(3));
        
        try {
			Target policyTarget = (Target) targetFactory.getTarget(children.item(3), metaData);
			List<AnyOfSelection> anyOfSelections = policyTarget.getAnyOfSelections();
			StringBuilder stringBuilder = new StringBuilder();
			//anyOfSelections.get(0).encode(stringBuilder);
			//System.out.println(stringBuilder);
			List<AllOfSelection> allOfSelections = anyOfSelections.get(0).getAllOfSelections();
			allOfSelections.get(0).encode(stringBuilder);
			System.out.println(stringBuilder);
		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}

}
