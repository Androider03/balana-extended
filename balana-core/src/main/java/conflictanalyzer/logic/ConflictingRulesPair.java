/**
 * 
 */
package conflictanalyzer.logic;

import org.wso2.balana.AbstractPolicy;
import org.wso2.balana.Rule;
import org.wso2.balana.ctx.AbstractResult;

/**
 * @author Guido Marilli
 *
 */
public class ConflictingRulesPair {
	
	/**
	 * First conflicting rule.
	 */
	private Rule firstRule;
	
	/**
	 * Second conflicting rule.
	 */
	private Rule secondRule;

	/**
	 * @param firstRule the first conflicting rule
	 * @param secondRule the second conflicting rule
	 */
	public ConflictingRulesPair(AbstractPolicy firstPolicy, Rule firstRule, 
			AbstractPolicy secondPolicy, Rule secondRule) {
		this.firstRule = firstRule;
		this.secondRule = secondRule;
	}

	/**
	 * @return the firstRule
	 */
	public Rule getFirstRule() {
		return firstRule;
	}

	/**
	 * @return the secondRule
	 */
	public Rule getSecondRule() {
		return secondRule;
	}
	
	// TODO creare un metodo che stabilisca quale delle due rule prevale, in base alle priorit�
	// delle policy cui appartengono.
	
	/**
	 * @return the <code>Rule</code> that has the highest priority between the two
	 */
	public Rule getWinningRule() {
		if(firstRule.getPriority() < secondRule.getPriority())
			return firstRule;
		else if(firstRule.getPriority() > secondRule.getPriority()) 
			return secondRule;
		else return null;
	}
	
	/**
	 * @return 1 if the decision is established by the first rule<br/>
	 * 		   2 if the decision is established by the second rule<br/>
	 * 		   3 if policies have the same priority level (INDETERMINATE state)
	 */
	public int getWinningRuleNumber() {
		if(firstRule.getPriority() < secondRule.getPriority())
			return 1;
		else if(firstRule.getPriority() > secondRule.getPriority()) 
			return 2;
		else return 3;
	}
	
	/**
	 * @return the winning decision (indeterminate in case both the rules have the same
	 * priority level)
	 */
	public int getWinningDecision() {
		Rule winningRule = getWinningRule();
		if(winningRule != null)
			return winningRule.getEffect();
		else return AbstractResult.DECISION_INDETERMINATE;
	}
	
	/**
	 * @return a <code>String</code> representation of the winning decision
	 */
	public String getWinningDecisionStringFormat() {
		int decision = getWinningDecision();
		String res = null;
		switch (decision) {
		case 0:
			res = "PERMIT";
			break;
		case 1:
			res = "DENY";
			break;
		case 2:
			res = "INDETERMINATE";
			break;
		case 3:
			res = "NOT APPLICABLE";
			break;
		default:
			res = "ERROR";
			break;
		}
		return res;
	}
	
	private String getReadableOfWinningDecision() {
		int decision = getWinningDecision();
		String result = "";
		if(decision == 0)
			result = "permit";
		else if(decision == 1)
			result = "deny";
		else result = "undecidable";
		return result;
	}
	
	public String getWinningRuleStringRepresentation() {
		Rule rule = getWinningRule();
		if(rule != null)
			return "[" + rule.getPolicy().getId() + "," + rule.getId() + "]";
		return "the rules have the same priority";
	}

	// TODO this method will probably have to become pretty big, since it will have to show a
	// detailed report about the conflicts between the rules
	@Override
	public String toString() {
		return "ConflictingRulesPair {"
				+ "\n   Policy: " + firstRule.getPolicy().getId() + " - Rule: " 
				+ firstRule.getId()
				+ "\n   Policy: " + secondRule.getPolicy().getId()  + " - Rule: " 
				+ secondRule.getId() + "\n}";
	}
	
	public String getCompactRepresentation() {
		return "[[" + firstRule.getPolicy().getId()  + "," + firstRule.getId() +
				"][" + secondRule.getPolicy().getId() + "," + secondRule.getId() + "]]";
	}
	
	public String getCompactRepresentationWithWinningRule() {
		return "[[" + firstRule.getPolicy().getId()  + "," + firstRule.getId() +
				"][" + secondRule.getPolicy().getId() + "," + secondRule.getId() + "]]"
				+ " - Winning rule: " + getWinningRuleStringRepresentation()
				+ " - Decision: " + getReadableOfWinningDecision();
	}
	
	// TODO maybe some methods for a more precise description of the conflict could be implemented
	// TODO More precisely, we'll have to retrieve the "critical" AllOf elements, as well as
	// TODO the critical condition elements
}