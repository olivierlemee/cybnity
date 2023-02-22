package org.cybnity.framework.immutable.data;

import java.time.OffsetDateTime;

import org.cybnity.framework.immutable.Group;
import org.cybnity.framework.immutable.Identifier;

/**
 * Example of logical group regarding an organization.
 * 
 * @author olivier
 *
 */
public class Department implements Group {
    private String label;
    private Identifier id;
    private OffsetDateTime at;

    public Department(String label, Identifier id) {
	this.label = label;
	this.id = id;
	this.at = OffsetDateTime.now();
    }

    @Override
    public Identifier identified() {
	return new IdentifierImpl(new String(id.name()), new String(id.value().toString()));
    }

    @Override
    public OffsetDateTime occurredAt() {
	return this.at;
    }

    @Override
    public Object immutable() throws CloneNotSupportedException {
	return new Department(this.label, this.id).at = this.at;
    }
}