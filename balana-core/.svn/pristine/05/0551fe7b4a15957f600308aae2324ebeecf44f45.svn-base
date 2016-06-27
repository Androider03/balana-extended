package varioustests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.wso2.balana.AbstractPolicy;

import conflictanalyzer.logic.PolicyLoader;

public class PolicyLoaderTest {

	public static void main(String[] args) {
		/*PolicyLoader loader = new PolicyLoader("C:\\Users\\Guido\\workspace2\\balana-release-1.0.2\\modules"
				+ "\\balana-core\\src\\test\\resources\\basic\\3\\policies\\", "TestPolicy");*/
		PolicyLoader loader = new PolicyLoader("C:\\Users\\Guido\\Desktop\\basic-policies", 
				"TestPolicy");
		loader.loadPolicies();
		ArrayList<AbstractPolicy> policies = loader.getPolicies();
		System.out.println("Policies detected: " + policies.size());
		//System.out.println(policies.get(1).encode());
		
		// STAMPA SU FILE
		List<String> lines = new ArrayList<String>();
		lines.add(policies.get(0).encode());
		Path file = Paths.get(
				System.getProperty("user.home"),"Desktop","basic-policies","First-Policy.xml");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lines = new ArrayList<String>();
		lines.add(policies.get(1).encode());
		Path file2 = Paths.get(
				System.getProperty("user.home"),"Desktop","basic-policies","Second-Policy.xml");
		try {
			Files.write(file2, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
