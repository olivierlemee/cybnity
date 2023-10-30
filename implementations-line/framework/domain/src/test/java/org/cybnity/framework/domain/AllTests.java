package org.cybnity.framework.domain;

import org.cybnity.framework.domain.model.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite of all technical and behavior tests regarding the domain components
 * capabilities.
 *
 * @author olivier
 */
@Suite
@SelectClasses({IdentifierStringBasedUseCaseTest.class, ValueObjectUseCaseTest.class, DomainEventUseCaseTest.class,
        NotificationLogUseCaseTest.class, UnidentifiableFactNotificationLogUseCaseTest.class,
        EventStoreUseCaseTest.class, ContextUseCaseTest.class, UserAccountAggregateUseCaseTest.class,
        UserAccountAggregateStoreUseCaseTest.class, UserAccountCQRSCollaborationUseCaseTest.class,
        StringBasedNaturalKeyBuilderUseCaseTest.class, TenantUseCaseTest.class, ObjectMapperBuilderUseCaseTest.class})
public class AllTests {
}