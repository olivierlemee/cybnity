package org.cybnity.framework.immutable;

import java.time.OffsetDateTime;

import org.cybnity.framework.support.annotation.Requirement;
import org.cybnity.framework.support.annotation.RequirementCategory;

/**
 * A historical fact contains only identifying information(s)
 * 
 * @author olivier
 *
 */
@Requirement(reqType = RequirementCategory.Maintainability, reqId = "REQ_MAIN_5")
public interface HistoricalFact extends Unmodifiable {

    /**
     * A time when the fact was created or observed.
     * 
     * @return An immutable time.
     */
    public OffsetDateTime occurredAt();
}
