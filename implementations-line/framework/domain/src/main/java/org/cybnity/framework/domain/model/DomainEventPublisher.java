package org.cybnity.framework.domain.model;

import org.cybnity.framework.domain.DomainEvent;
import org.cybnity.framework.domain.ISubscribable;
import org.cybnity.framework.support.annotation.Requirement;
import org.cybnity.framework.support.annotation.RequirementCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a publishing service from a domain model. Repository service for
 * Aggregates that need to notify any subscribers about change events. It's a
 * utility class managing subscribers registrations lifecycle.
 *
 * @author olivier
 */
@Requirement(reqType = RequirementCategory.Scalability, reqId = "REQ_SCA_4")
public class DomainEventPublisher implements ISubscribable {

    @SuppressWarnings("rawtypes")
    private static final ThreadLocal<List> subscribers = new ThreadLocal<List>();

    private static final ThreadLocal<Boolean> publishing = new ThreadLocal<Boolean>() {
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    /**
     * Get an instance of the domain event publisher.
     *
     * @return A publishing instance from the domain model.
     */
    public static DomainEventPublisher instance() {
        return new DomainEventPublisher();
    }

    private DomainEventPublisher() {
        super();
    }

    /**
     * Add a subscriber to the register.
     *
     * @param aSubscriber The mandatory subscriber to add into the registry.
     */
    @Override
    public <T> void subscribe(IDomainEventSubscriber<T> aSubscriber) {
        if (aSubscriber != null) {
            if (publishing.get()) {
                return;
            }
            @SuppressWarnings("unchecked")
            List<IDomainEventSubscriber<T>> registeredSubscribers = subscribers.get();
            if (registeredSubscribers == null) {
                registeredSubscribers = new ArrayList<IDomainEventSubscriber<T>>();
                subscribers.set(registeredSubscribers);
            }
            registeredSubscribers.add(aSubscriber);
        }
    }

    /**
     * Remove a subscriber of the register if existing.
     *
     * @param aSubscriber The mandatory subscriber to remove from register.
     */
    @Override
    public <T> void remove(IDomainEventSubscriber<T> aSubscriber) {
        if (aSubscriber != null) {
            if (publishing.get()) {
                return;
            }
            @SuppressWarnings("unchecked")
            List<IDomainEventSubscriber<T>> registeredSubscribers = subscribers.get();
            if (registeredSubscribers != null) {
                registeredSubscribers.remove(aSubscriber);
            }
        }
    }

    /**
     * Notify an event to all the subscribers registered as interested by a type of
     * event.
     *
     * @param aDomainEvent Event to promote to interested subscribers.
     * @param <T>          Type of event.
     */
    public <T> void publish(final T aDomainEvent) {
        if (publishing.get()) {
            return;
        }
        try {
            // Indicate a current start of publishing status
            publishing.set(Boolean.TRUE);
            // Identify the potential interested subscribers about the published event
            @SuppressWarnings("unchecked")
            List<IDomainEventSubscriber<T>> registeredSubscribers = subscribers.get();

            if (registeredSubscribers != null) {
                Class<?> eventType = aDomainEvent.getClass();
                for (IDomainEventSubscriber<T> subscriber : registeredSubscribers) {
                    Class<?> subscribedTo = subscriber.subscribeToEventType();
                    // Check interest of the subscriber regarding the type of event published
                    if (/* Any event type interest */ subscribedTo == null || subscribedTo == eventType || subscribedTo == DomainEvent.class) {
                        // Notify subscriber about published event
                        subscriber.handleEvent(aDomainEvent);
                    }
                }
            }

        } finally {
            // Notify finalized publishing status
            publishing.set(Boolean.FALSE);
        }
    }

    /**
     * Remove all the registered subscribers from the registry. This cleaning action
     * is only executed when there is none publishing action in progress by this
     * publisher.
     *
     * @return This instance potentially cleaned (if none publishing status was in
     * progress).
     */
    public DomainEventPublisher reset() {
        // Clean only when none publishing action is in progress
        if (!publishing.get()) {
            subscribers.set(null);
        }
        return this;
    }

}
