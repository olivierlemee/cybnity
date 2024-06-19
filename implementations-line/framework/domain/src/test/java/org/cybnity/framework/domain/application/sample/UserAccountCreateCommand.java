package org.cybnity.framework.domain.application.sample;

import org.cybnity.framework.domain.Attribute;
import org.cybnity.framework.domain.Command;
import org.cybnity.framework.domain.model.DomainEntity;
import org.cybnity.framework.immutable.EntityReference;
import org.cybnity.framework.immutable.ImmutabilityException;
import org.cybnity.framework.immutable.utility.VersionConcreteStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Example of command creating a new user account into a system.
 *
 * @author olivier
 */
public class UserAccountCreateCommand extends Command {

    private static final long serialVersionUID = 1L;
    public String accountUID;
    public EntityReference userIdentity;

    public UserAccountCreateCommand() {
        super();
    }

    public UserAccountCreateCommand(DomainEntity identifiedBy) {
        super(identifiedBy);
    }

    /**
     * This implementation do nothing
     *
     * @param eventIdentifier Mandatory defined identifier. None assignment when not defined or empty parameter.
     */
    @Override
    public void assignCorrelationId(String eventIdentifier) {

    }

    /**
     * Do nothing.
     *
     * @return Null.
     */
    @Override
    public Attribute type() {
        return null;
    }

    @Override
    public Attribute correlationId() {
        return null;
    }

    /**
     * Implement the generation of version hash regarding this class type according
     * to a concrete strategy utility service.
     */
    @Override
    public String versionHash() {
        return new VersionConcreteStrategy().composeCanonicalVersionHash(getClass());
    }

    @Override
    public Serializable immutable() throws ImmutabilityException {
        return null;
    }

    @Override
    public Collection<Attribute> specification() {
        return new ArrayList<>();
    }

    @Override
    public boolean appendSpecification(Attribute specificationCriteria) {
        return false;
    }
}
