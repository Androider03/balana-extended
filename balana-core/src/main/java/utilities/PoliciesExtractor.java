/**
 * 
 */
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Guido Marilli
 *
 */
public class PoliciesExtractor {
	
	public static void main(String[] args) throws SAXException, IOException, 
			ParserConfigurationException, XPathExpressionException, TransformerException {
		//String xml = "<A><B><id>0</id></B><B><id>1</id></B></A>";
	    String xml = readFile("C:\\Users\\Guido\\Desktop\\sample-policies\\synthetic360"
	    		+ "\\synthetic360.xml");
		/*String xml = readFile("C:\\Users\\Guido\\Desktop\\sample-policies\\sample-xacml3"
	    		+ "\\continue-a-xacml3.xml");*/
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    Document doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

	    XPath xPath = XPathFactory.newInstance().newXPath();
	    NodeList result = (NodeList) xPath.evaluate("//Policy", doc, XPathConstants.NODESET);
	    
	    //NodeList children = result.item(0).getChildNodes();
	    //System.out.println(children.item(4));
	    
	    StringWriter sw = new StringWriter();
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    
	    for(int i = 0; i < result.getLength(); i++) {
	    	sw = new StringWriter();
	    	Node node = result.item(i);
	    	((Element) node).setAttribute("xmlns", "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17");
	    	((Element) node).setAttribute("Version", "1.0");
	    	((Element) node).setAttribute("PolicyId", "policy" + getSuffix(i+1));
	    	transformer.transform(new DOMSource(node), new StreamResult(sw));
	    	// Print the content on a file
		    List<String> lines = new ArrayList<String>();
		    lines.add(sw.toString());
		    Path file = Paths.get(
					System.getProperty("user.home"),"Desktop","sample-policies","synthetic360",
					"synthetic360-policies","policy" + getSuffix(i+1));
		    /*Path file = Paths.get(
					System.getProperty("user.home"),"Desktop","sample-policies","sample-xacml3",
					"continue-a-xacml3-policies","policy" + getSuffix(i+1));*/
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    System.out.println("Policies found: " + result.getLength());
	    
	    /*int i;
	    for(i = 0; i < result.getLength(); i++)
	    	System.out.println(result.item(i).getAttributes().item(0));
	    System.out.println(i);*/
	    
	    /*for(int i = 0; i < result.getLength(); i++)
	    	System.out.println(nodeToString(result.item(i)));*/
	    
	    /*Set<Node> policiesIds = new HashSet<Node>();
	    
	    for(int i = 0; i < result.getLength(); i++)
	    	policiesIds.add(result.item(i).getAttributes().item(0));*/
	    
	    /*for (Node node : policiesIds) {
			System.out.println(node);
		}*/

	    // STAMPA SU FILE
	    /*List<String> lines = new ArrayList<String>();
	    String id = "";
	    for(int i = 0; i < result.getLength(); i++) {
	    	id = result.item(i).getAttributes().item(0).toString();
	    	lines.add(id.substring(10, id.length()-1));
	    }
	    Path file = Paths.get(
				System.getProperty("user.home"),"Desktop","sample-policies","synthetic360",
				"policies-ids.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	/*private static String nodeToString(Node node) throws TransformerException {
		StringWriter buf = new StringWriter();
		Transformer xform = TransformerFactory.newInstance().newTransformer();
		xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		xform.transform(new DOMSource(node), new StreamResult(buf));
		return(buf.toString());
	}*/
	
	private static String readFile(String path) throws IOException{
		StringBuilder sb = new StringBuilder();
		String sCurrentLine = "";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		}
		return sb.toString();
	}
	
	/**
	 * @param index the index (counter), with which the file name finishes
	 * @return the suffix in the format that is used to name the files that contain
	 * 		the policies
	 */
	private static String getSuffix(int index) {
		if(index < 10)
			return "_000" + index + ".xml";
		else if(index < 100)
			return "_00" + index + ".xml";
		else if(index < 1000)
			return "_0" + index + ".xml";
		else return "_" + index + ".xml";
	}

}
