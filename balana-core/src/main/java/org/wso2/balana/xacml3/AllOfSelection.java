/*
*  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/


package org.wso2.balana.xacml3;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.balana.*;
import org.wso2.balana.ctx.EvaluationCtx;
import org.wso2.balana.ctx.Status;
import org.wso2.balana.TargetMatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Represents AllOfType in the XACML 3.0 policy schema.
 */
public class AllOfSelection {

    /**
     * List of SubjectMatch, ResourceMatch, ActionMatch, or EnvironmentMatch
     */
    List<TargetMatch> matches;

    /**
     * Constructor that creates a <code>AllOfSelection</code> from components.
     *
     * @param matches a <code>List</code> of <code>TargetMatch</code> elements
     */
    public AllOfSelection(List<TargetMatch> matches) {
        this.matches = matches;
    }

    /**
     * creates a new <code>AllOfSelection</code> by parsing DOM node.
     *
     * @param root DOM node
     * @param metaData policy meta data
     * @return <code>AllOfSelection</code>
     * @throws ParsingException throws, if the DOM node is invalid
     */
    public static AllOfSelection getInstance(Node root, PolicyMetaData metaData) throws ParsingException {

        List<TargetMatch> targetMatches = new ArrayList<TargetMatch>();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if ("Match".equals(DOMHelper.getLocalName(child))) {
                targetMatches.add(TargetMatch.getInstance(child, metaData));
            }
        }

        if(targetMatches.isEmpty()){
            throw new ParsingException("AllOf must contain at least one Match");
        }

        return new AllOfSelection(targetMatches);
    }

    /**
     *
     * Determines whether this <code>AllOfSelection</code> matches the input request (whether it
     * is applicable).
     *
     * @param context the representation of the request
     *
     * @return the result of trying to match the group with the context
     */
    public MatchResult match(EvaluationCtx context){

        // there are specific matching elements, so prepare to iterate
        // through the list
        Status firstIndeterminateStatus = null;
        MatchResult result;

        for (TargetMatch targetMatch : matches ) {
            result = targetMatch.match(context);
            if (result.getResult() == MatchResult.NO_MATCH){
                return result;
            }

            if (result.getResult() == MatchResult.INDETERMINATE){
                if(firstIndeterminateStatus == null){
                    firstIndeterminateStatus = result.getStatus();
                }
            }
        }

        // if we got here, then none of the sub-matches passed, so
        // we have to see if we got any INDETERMINATE cases
        if (firstIndeterminateStatus == null)
            return new MatchResult(MatchResult.MATCH);
        else
            return new MatchResult(MatchResult.INDETERMINATE,
                                   firstIndeterminateStatus);

    }


    /**
     * Encodes this <code>AnyOfSelection</code> into its XML form and writes this out to the provided
     * <code>StringBuilder<code>
     *
     * @param builder string stream into which the XML-encoded data is written
     */
    public void encode(StringBuilder builder) {

        builder.append("<AllOf>\n");

        if(matches != null){
            for(TargetMatch match : matches){
                match.encode(builder);
            }
        }

        builder.append("</AllOf>\n");
    }

	/**
	 * @return the matches
	 */
	public List<TargetMatch> getMatches() {
		return matches;
	}
	
	/**
	 * @param otherAllOf the AllOf element that contains the <code>TargetMatch</code>
	 * elements to compare
	 * @return the intersection of the <code>TargetMatch</code> elements between this
	 * and the specified <code>AllOfSelection</code>
	 */
	public Set<TargetMatch> intersection(AllOfSelection otherAllOf) {
		Set<TargetMatch> intersection = new HashSet<TargetMatch>();
		//System.out.println();
		for(int i = 0; i < matches.size(); i++) {
			for(int j = 0; j < otherAllOf.getMatches().size(); j++) {
				if(matches.get(i).equals(otherAllOf.getMatches().get(j)))
					intersection.add(matches.get(i));
				/*System.out.println(
						"Match: " + (i+1) + " - Match: " + (j+1) + " - Comparison result: "
						+ matches.get(i).equals(otherAllOf.getMatches().get(j)));*/
			}
		}
		return intersection;
	}
	
	/**
	 * @param other the <code>AllOfSelection</code> element to merge
	 * @return a new <code>AllOfSelection</code> which contains all the matches of this
	 * <code>AllOfSelection</code>, followed by all the matches of the specified one.
	 */
	public AllOfSelection merge(AllOfSelection other) {
		List<TargetMatch> mergedAllOfs = new ArrayList<TargetMatch>();
		mergedAllOfs.addAll(matches);
		mergedAllOfs.addAll(other.getMatches());
		AllOfSelection result = new AllOfSelection(mergedAllOfs);
		return result;
	}
	
	// La versione sopra quella riordinata, conservo questa perché "non si sa mai"
	/*public Set<TargetMatch> intersection(AllOfSelection otherAllOf) {
		// Match elements from this AllOf element
		//Set<TargetMatch> intersection = new HashSet<TargetMatch>(matches);
		// Match elements of the other AllOf
		Set<TargetMatch> otherAllOfSet = new HashSet<TargetMatch>(otherAllOf.getMatches());*/
		//-----
		/*Iterator<TargetMatch> iter1 = intersection.iterator();
		while(iter1.hasNext()) {
			System.out.println(iter1.next());
		}
		Iterator<TargetMatch> iter2 = otherAllOfSet.iterator();
		while(iter2.hasNext()) {
			System.out.println(iter2.next());
		}*/
		//-----
		//intersection.retainAll(otherAllOfSet);
		/*Set<TargetMatch> intersection = new HashSet<TargetMatch>();
		
		for(int i = 0; i < matches.size(); i++) {
			for(int j = 0; j < otherAllOf.getMatches().size(); j++) {
				if(matches.get(i).equals(otherAllOf.getMatches().get(j)))
					intersection.add(matches.get(i));
			}
		}*/
		
		// CODE FOR TESTS
		/*System.out.println("Intersection result:");
		Iterator<TargetMatch> iterator = intersection.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("AllOf intersections: " + intersection.size());*/
		//return intersection;
	//}
}
