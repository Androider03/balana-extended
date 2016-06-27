/*
 * @(#)Apply.java
 *
 * Copyright 2003-2005 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistribution of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * 
 *   2. Redistribution in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for use in
 * the design, construction, operation or maintenance of any nuclear facility.
 */

package org.wso2.balana.cond;

import org.wso2.balana.*;
import org.wso2.balana.attr.AttributeDesignator;
import org.wso2.balana.attr.AttributeValue;
import org.wso2.balana.attr.xacml3.AttributeSelector;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.balana.ctx.EvaluationCtx;

/**
 * Represents the XACML ApplyType and ConditionType XML types.
 * <p>
 * Note well: as of 2.0, there is no longer a notion of a separate higher- order bag function.
 * Instead, if needed, it is supplied as one of the <code>Expression</code>s in the parameter list.
 * As such, when this <code>Apply</code> is evaluated, it no longer pre-evaluates all the parameters
 * if a bag function is used. It is now up to the implementor of a higher-order function to do this.
 * <p>
 * Also, as of 2.0, the <code>Apply</code> is no longer used to represent a Condition, since the
 * XACML 2.0 specification changed how Condition works. Instead, there is now a
 * <code>Condition</code> class that represents both 1.x and 2.0 style Conditions.
 * 
 * @since 1.0
 * @author Seth Proctor
 */
public class Apply implements Evaluatable {

    // the function used to evaluate the contents of the apply
    private Function function;
    
    // the paramaters to the function...ie, the contents of the apply
    private List xprs;

    /**
     * Constructs an <code>Apply</code> instance.
     * 
     * @param function the <code>Function</code> to use in evaluating the elements in the apply
     * @param xprs the contents of the apply which will be the parameters to the function, each of
     *            which is an <code>Expression</code>
     * 
     * @throws IllegalArgumentException if the input expressions don't match the signature of the
     *             function
     */
    public Apply(Function function, List xprs) throws IllegalArgumentException {
        // check that the given inputs work for the function
        function.checkInputs(xprs);

        // if everything checks out, then store the inputs
        this.function = function;
        this.xprs = Collections.unmodifiableList(new ArrayList(xprs));
    }

    /**
     * Returns an instance of an <code>Apply</code> based on the given DOM root node. This will
     * actually return a special kind of <code>Apply</code>, namely an XML ConditionType, which is
     * the root of the condition logic in a RuleType. A ConditionType is the same as an ApplyType
     * except that it must use a FunctionId that returns a boolean value.
     * <p>
     * Note that as of 2.0 there is a separate <code>Condition</code> class used to support the
     * different kinds of Conditions in XACML 1.x and 2.0. As such, the system no longer treats a
     * ConditionType as a special kind of ApplyType. You may still use this method to get a 1.x
     * style ConditionType, but you will need to convert it into a <code>Condition</code> to use it
     * in evaluation. The preferred way to create a Condition is now through the
     * <code>getInstance</code> method on <code>Condition</code>.
     * 
     * @param root the DOM root of a ConditionType XML type
     * @param xpathVersion the XPath version to use in any selectors or XPath functions, or null if
     *            this is unspecified (ie, not supplied in the defaults section of the policy)
     * @param manager <code>VariableManager</code> used to connect references and definitions while
     *            parsing
     * 
     * @throws ParsingException if this is not a valid ConditionType
     */
    public static Apply getConditionInstance(Node root, String xpathVersion, VariableManager manager)
            throws ParsingException {
        return getInstance(root, FunctionFactory.getConditionInstance(), new PolicyMetaData(
                XACMLConstants.XACML_1_0_IDENTIFIER, xpathVersion), manager);
    }

    /**
     * Returns an instance of <code>Apply</code> based on the given DOM root.
     * 
     * @param root the DOM root of an ApplyType XML type
     * @param metaData the meta-data associated with the containing policy
     * @param manager <code>VariableManager</code> used to connect references and definitions while
     *            parsing
     * 
     * @throws ParsingException if this is not a valid ApplyType
     */
    public static Apply getInstance(Node root, PolicyMetaData metaData, VariableManager manager)
            throws ParsingException {
        return getInstance(root, FunctionFactory.getGeneralInstance(), metaData, manager);
    }

    /**
     * This is a helper method that is called by the two getInstance methods. It takes a factory so
     * we know that we're getting the right kind of function.
     */
    private static Apply getInstance(Node root, FunctionFactory factory, PolicyMetaData metaData,
            VariableManager manager) throws ParsingException {
        Function function = ExpressionHandler.getFunction(root, metaData, factory);
        List xprs = new ArrayList();

        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Expression xpr = ExpressionHandler.parseExpression(nodes.item(i), metaData, manager);

            if (xpr != null)
                xprs.add(xpr);
        }

        return new Apply(function, xprs);
    }

    /**
     * Returns the <code>Function</code> used by this <code>Apply</code>.
     * 
     * @return the <code>Function</code>
     */
    public Function getFunction() {
        return function;
    }

    /**
     * Returns the <code>List</code> of children for this <code>Apply</code>. The <code>List</code>
     * contains <code>Expression</code>s. The list is unmodifiable, and may be empty.
     * 
     * @return a <code>List</code> of <code>Expression</code>s
     */
    public List getChildren() {
        return xprs;
    }

    /**
     * Evaluates the apply object using the given function. This will in turn call evaluate on all
     * the given parameters, some of which may be other <code>Apply</code> objects.
     * 
     * @param context the representation of the request
     * 
     * @return the result of trying to evaluate this apply object
     */
    public EvaluationResult evaluate(EvaluationCtx context) {
        // Note that prior to the 2.0 codebase, this method was much more
        // complex, pre-evaluating the higher-order functions. Because this
        // was never really the right behavior (there's no reason that a
        // function can only be at the start of an Apply), we no longer make
        // assumptions at this point, so the higher order functions are
        // left to evaluate their own parameters.
        return function.evaluate(xprs, context);
    }

    /**
     * Returns the type of attribute that this object will return on a call to <code>evaluate</code>
     * . In practice, this will always be the same as the result of calling
     * <code>getReturnType</code> on the function used by this object.
     * 
     * @return the type returned by <code>evaluate</code>
     */
    public URI getType() {
        return function.getReturnType();
    }

    /**
     * Returns whether or not the <code>Function</code> will return a bag of values on evaluation.
     * 
     * @return true if evaluation will return a bag of values, false otherwise
     */
    public boolean returnsBag() {
        return function.returnsBag();
    }

    /**
     * Returns whether or not the <code>Function</code> will return a bag of values on evaluation.
     * 
     * 
     * @deprecated As of 2.0, you should use the <code>returnsBag</code> method from the
     *             super-interface <code>Expression</code>.
     * 
     * @return true if evaluation will return a bag of values, false otherwise
     */
    public boolean evaluatesToBag() {
        return function.returnsBag();
    }

    /**
     * Encodes this <code>Apply</code> into its XML form
     *
     * @return <code>String</code>
     */
    public String encode() {
        StringBuilder builder = new StringBuilder();
        encode(builder);
        return builder.toString();
    }


    /**
     * Encodes this <code>Apply</code> into its XML form and writes this out to the provided
     * <code>StringBuilder<code>
     *
     * @param builder string stream into which the XML-encoded data is written
     */
    public void encode(StringBuilder builder) {

        builder.append("<Apply FunctionId=\"").append(function.getIdentifier()).append("\">\n");

        Iterator it = xprs.iterator();
        while (it.hasNext()) {
            Expression xpr = (Expression) (it.next());
            xpr.encode(builder);
        }

        builder.append("</Apply>\n");
    }
    
	@Override
	public boolean equals(Object obj) {
		Apply otherApply;
		if(obj instanceof Apply)
			otherApply = (Apply) obj;
		else return false;
		boolean result = true;
		
		for(int i = 0; i < xprs.size(); i++) {
			if(xprs.get(i) instanceof AttributeDesignator)
				result = result && ((AttributeDesignator) xprs.get(i))
				.equals((AttributeDesignator) otherApply.getChildren().get(i));
			else if(xprs.get(i) instanceof AttributeValue)
				result = result && ((AttributeValue) xprs.get(i))
				.equals((AttributeValue) otherApply.getChildren().get(i));
			else if(xprs.get(i) instanceof Function)
				result = result && ((Function) xprs.get(i))
				.equals((Function) otherApply.getChildren().get(i));
			else if(xprs.get(i) instanceof AttributeSelector)
				result = result && ((AttributeSelector) xprs.get(i))
				.equals((AttributeSelector) otherApply.getChildren().get(i));
			else if(xprs.get(i) instanceof VariableReference)
				result = result && ((VariableReference) xprs.get(i))
				.equals((VariableReference) otherApply.getChildren().get(i));
		}
		
		return function.equals(otherApply.getFunction()) && result;
	}
	
	public List<Apply> intersection(Apply otherMainApply) {
		/*
		 *  If we're here, it means that we're analyzing an OR Apply structure.
		 *  In this case, both the Apply elements will only contain other Apply elements
		 *  (at the first level of depth).
		 */
		List<Apply> thisApplyElements = new ArrayList<Apply>();
		List<Apply> otherApplyElements = new ArrayList<Apply>();
		List<Apply> intersection = new ArrayList<Apply>();
		
		for(int i = 0; i < xprs.size(); i++)
			if(xprs.get(i) instanceof Apply)
				thisApplyElements.add((Apply) xprs.get(i));
		
		for(int i = 0; i < otherMainApply.getChildren().size(); i++)
			if(otherMainApply.getChildren().get(i) instanceof Apply)
				otherApplyElements.add((Apply) otherMainApply.getChildren().get(i));
		
		for(int i = 0; i < thisApplyElements.size(); i++) {
			for(int j = 0; j < otherApplyElements.size(); j++) {
				if(thisApplyElements.get(i).equals(otherApplyElements.get(j))) {
					intersection.add(thisApplyElements.get(i));
					break;
				}
			}
		}
		
		return intersection;
	}
	
	/**
	 * @param otherMainApply the other "main" Apply object to compare
	 * @return <code>true</code> if all the sub-Apply elements are included in the Apply
	 * elements of the specified <code>Apply</code> object
	 */
	public boolean isSubsetOf(Apply otherMainApply) {
		boolean isSubsetOf = true;
		/* If we're here, this means that we're analyzing an AND condition, so
		 * this Apply, as well, as the other one, only contain Apply elements in their
		 * xprs lists. This strongly simplifies the following analysis.
		 */
		List<Apply> thisApplyElements = new ArrayList<Apply>();
		List<Apply> otherApplyElements = new ArrayList<Apply>();
		
		for(int i = 0; i < xprs.size(); i++)
			if(xprs.get(i) instanceof Apply)
				thisApplyElements.add((Apply) xprs.get(i));
		
		for(int i = 0; i < otherMainApply.getChildren().size(); i++)
			if(otherMainApply.getChildren().get(i) instanceof Apply)
				otherApplyElements.add((Apply) otherMainApply.getChildren().get(i));
		
		if(thisApplyElements.size() > otherApplyElements.size())
			isSubsetOf = false;
		else {
			int elementsFound = 0;
			for(int i = 0; i < thisApplyElements.size(); i++) {
				boolean found = false;
				for(int j = 0; j < otherApplyElements.size() && !found; j++) {
					if(thisApplyElements.get(i).equals(otherApplyElements.get(j))) {
						found = true;
						elementsFound++;
					}
				}
			}
			if(elementsFound < thisApplyElements.size())
				isSubsetOf = false;		// The Apply elements of this Apply aren't a subset of the Apply elements of the other Apply
		}
		
		return isSubsetOf;
	}
	
	@Override
	public String toString() {
		String result = "Apply ["
						//+ "\n  function=" + function 
						+ "\n  xprs=";
		//for(int i = 0; i < xprs.size(); i++)
			//result += "\n      " + (i+1) + ") " + xprs.get(i).toString();
		result += "\n      " + (2) + ") " + xprs.get(1).toString();
		return result;
	}
	
	// TODO non gestisce adeguatamente intersezioni a livello più profondo del primo
		/**
		 * @param otherMainApply the other condition's main Apply element to compare
		 * @return the intersection of the Apply elements inside the two main Apply elements or</br>
		 * 		<code>null</code> if the function of the main Apply elements is different
		 */
		/*public Set<Apply> intersection(Apply otherMainApply) {
			Set<Apply> result = new HashSet<Apply>(xprs);
			// It makes sense to analyze the contents of the main Apply elements only
			// if their function is the same
			//System.out.println(this.toString() + "\n" + otherMainApply.toString() + "\n");
			if(getFunction().equals(otherMainApply.getFunction())) {
				Set<Apply> otherApply = new HashSet<Apply>(otherMainApply.getChildren());
				result.retainAll(otherApply);
			} else result.clear();
			return result;
		}*/
	
	// TODO questa dovrebbe essere la versione corretta
	/**
	 * @param otherMainApply the other condition's main Apply element to compare
	 * @return the intersection of the Apply elements inside the two main Apply elements or</br>
	 * 		<code>null</code> if the function of the main Apply elements is different
	 */
	/*public Set<Apply> intersection(Apply otherMainApply) {
		Set<Apply> result = new HashSet<Apply>();
		
		if(xprs.isEmpty() && otherMainApply.getChildren().isEmpty()) {
			if(function.equals(otherMainApply.getFunction()))
				result.add(this);
		} else {
			for(int i = 0; i < xprs.size(); i++) {
				for(int j = 0; j < otherMainApply.getChildren().size(); j++) {
					
				}
			}
		}
		//...
		return result;
	}*/

}
