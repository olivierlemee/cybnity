package org.cybnity.framework.domain;

import java.io.Serializable;

import org.cybnity.framework.immutable.Identifier;
import org.cybnity.framework.immutable.ImmutabilityException;
import org.cybnity.framework.support.annotation.Requirement;
import org.cybnity.framework.support.annotation.RequirementCategory;

/**
 * Identifying information type that is based on a single text chain.
 * 
 * @author olivier
 *
 */
@Requirement(reqType = RequirementCategory.Scalability, reqId = "REQ_SCA_4")
public class IdentifierStringBased implements Identifier {

    private static final long serialVersionUID = 1L;
    private String value;
    private String name;

    public IdentifierStringBased(String name, String value) {
	this.name = name;
	this.value = value;
    }

    @Override
    public Serializable immutable() throws ImmutabilityException {
	return new IdentifierStringBased(name.toString(), value.toString());
    }

    @Override
    public String name() {
	return this.name;
    }

    @Override
    public Serializable value() {
	return this.value;
    }

    /**
     * Redefined hash code calculation method which include the functional contents
     * hash code values into the returned number.
     */
    @Override
    public int hashCode() {
	// Read the contribution values of functional equality regarding this instance
	String[] functionalValues = valueHashCodeContributors();
	int hashCodeValue = +(169065 * 179);
	if (functionalValues != null && functionalValues.length > 0) {
	    for (String s : functionalValues) {
		if (s != null) {
		    hashCodeValue = +s.hashCode();
		}
	    }
	} else {
	    // Keep standard hashcode value calculation default implementation
	    hashCodeValue = super.hashCode();
	}
	return hashCodeValue;
    }

    @Override
    public String[] valueHashCodeContributors() {
	return new String[] { this.value };
    }

    /**
     * Redefine the comparison of this fact with another based on the identifier.
     * 
     * @param fact To compare.
     * @return True if this fact is based on the same identifier(s) as the fact
     *         argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;
	if (obj != null && Identifier.class.isAssignableFrom(obj.getClass())) {
	    Identifier compared = (Identifier) obj;
	    // Compare equality based on each instance's identifier (unique or based on
	    // identifying informations combination)
	    return this.value.equals(compared.value());
	}
	return false;
    }
}
