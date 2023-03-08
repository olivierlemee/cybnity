package org.cybnity.framework.immutable.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.cybnity.framework.immutable.IVersionable;
import org.cybnity.framework.immutable.ImmutabilityException;
import org.cybnity.framework.immutable.NaturalKeyIdentifierGenerator;
import org.cybnity.framework.immutable.StringBasedNaturalKeyBuilder;
import org.cybnity.framework.immutable.Unmodifiable;
import org.cybnity.framework.immutable.utility.VersionConcreteStrategy;
import org.cybnity.framework.support.annotation.Requirement;
import org.cybnity.framework.support.annotation.RequirementCategory;

/**
 * Represent a naming (e.g based on common naming for graph edge like "to->from"
 * vs "in->out" vs "source->target"; or based on logical relation name like
 * "composed of" or "delivered by") of a relation (e.g between several facts)
 * which is categorized (e.g relation type class) to allow relations
 * classification (e.g all relation based on a moment, or based on a proximity,
 * or based on a center of interest) and including a direction.
 * 
 * @author olivier
 *
 */
@Requirement(reqType = RequirementCategory.Robusteness, reqId = "REQ_ROB_3")
public class RelationRole implements Unmodifiable, IVersionable, IUniqueness, Serializable {

    /**
     * Version of this class type.
     */
    private static final long serialVersionUID = new VersionConcreteStrategy()
	    .composeCanonicalVersionHash(RelationRole.class).hashCode();
    /**
     * Configuration of minimum length of identifier automatically generated by this
     * class for a role new uid.
     */
    private static int minLetterQty = 50;

    /**
     * Logical name of the role.
     * 
     * To-From: For a pair of vertices, there can be an edge going from one and to
     * the other
     * 
     * In-Out: For a given vertex, some edges go in, some edges go out
     * 
     * Source-Target: For a given edge, one vertex is the source, and one vertex is
     * the target
     */
    private String name;

    /**
     * Define or auto-generated identifier of this role.
     */
    private String id;

    /**
     * Type of the predecessor fact which is owner of this named relationship as
     * role.
     */
    private FactType relationDeclaredByOwnerType;

    /**
     * Type of the successor fact which is targeted by the predecessor fact.
     */
    private FactType relationTargetingOtherFactType;

    /**
     * Default constructor.
     * 
     * @param roleName                       Mandatory label naming this role (e.g
     *                                       based on common naming for graph edge
     *                                       like "to->from" vs "in->out" vs
     *                                       "source->target"; or based on logical
     *                                       relation name like "composed of" or
     *                                       "delivered by").
     * @param relationDeclaredByOwnerType    Mandatory type of the predecessor fact
     *                                       which is owner of this named
     *                                       relationship as role.
     * @param relationTargetingOtherFactType Mandatory type of the successor fact
     *                                       which is targeted by the predecessor
     *                                       fact.
     * @param identifier                     Optional identifying information value.
     *                                       When not defined, this role identifier
     *                                       is automatically generated
     *                                       (location-independent identifier based
     *                                       on roleName).
     * @throws IllegalArgumentException When any mandatory parameter is missing.
     */
    public RelationRole(String roleName, FactType relationDeclaredByOwnerType, FactType relationTargetingOtherFactType,
	    String identifier) throws IllegalArgumentException {
	if (roleName == null || roleName.equals(""))
	    throw new IllegalArgumentException("roleName parameter is required!");
	if (relationDeclaredByOwnerType == null)
	    throw new IllegalArgumentException("relationDeclaredByOwnerType parameter is required!");
	if (relationTargetingOtherFactType == null)
	    throw new IllegalArgumentException("relationTargetingOtherFactType parameter is required!");
	this.name = roleName;
	this.relationDeclaredByOwnerType = relationDeclaredByOwnerType;
	this.relationTargetingOtherFactType = relationTargetingOtherFactType;
	this.id = identifier;
	if (this.id == null || this.id.equals("")) {
	    try {
		// Generate automatic location-independant identifier regarding the role name
		StringBasedNaturalKeyBuilder builder = new StringBasedNaturalKeyBuilder(this.name, minLetterQty);
		NaturalKeyIdentifierGenerator gen = new NaturalKeyIdentifierGenerator(builder);
		gen.build();
		id = builder.getResult();
	    } catch (Exception e) {
		// Generation problem that should never arrive because build() method called
		// before result read

		// TODO: Add technical log in case of implementation evolution of the
		// builder.getResult() method usage requirements
	    }
	}
    }

    /**
     * Default constructor with automatic identifier generation based on the role
     * name.
     * 
     * @param roleName                       Mandatory label naming this role (e.g
     *                                       based on common naming for graph edge
     *                                       like "to->from" vs "in->out" vs
     *                                       "source->target"; or based on logical
     *                                       relation name like "composed of" or
     *                                       "delivered by").
     * @param relationDeclaredByOwnerType    Mandatory type of the predecessor fact
     *                                       which is owner of this named
     *                                       relationship as role.
     * @param relationTargetingOtherFactType Mandatory type of the successor fact
     *                                       which is targeted by the predecessor
     *                                       fact.
     * @throws IllegalArgumentException When any mandatory parameter is missing.
     */
    public RelationRole(String roleName, FactType relationDeclaredByOwnerType, FactType relationTargetingOtherFactType)
	    throws IllegalArgumentException {
	this(roleName, relationDeclaredByOwnerType, relationTargetingOtherFactType, null);
    }

    @Override
    public Set<Field> basedOn() {
	Set<Field> uniqueness = new HashSet<>();
	try {
	    uniqueness.add(this.getClass().getDeclaredField("name"));
	    uniqueness.add(this.getClass().getDeclaredField("relationDeclaredByOwnerType"));
	} catch (NoSuchFieldException e) {
	    // Problem of implementation that shall never be thrown
	    // TODO: add log for developer error notification
	}
	return uniqueness;
    }

    @Override
    public Serializable immutable() throws ImmutabilityException {
	return new RelationRole(this.name, this.relationDeclaredByOwnerType, this.relationTargetingOtherFactType,
		this.id);
    }

    /**
     * Implement the generation of version hash regarding this class type according
     * to a concrete strategy utility service.
     */
    @Override
    public String versionHash() {
	return new VersionConcreteStrategy().composeCanonicalVersionHash(getClass());
    }
}
