package org.cybnity.framework.domain;

import org.cybnity.framework.domain.event.IEventType;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Read operation relative to a projection that can be executed to read status of a data-view projection (e.g according to the supported query language of a graph or database technology).
 */
public interface IProjectionRead {

    /**
     * Type of event type monitored and that is source of read operation over this transaction on read-model projection(s).
     *
     * @return Types of interest source, or null.
     */
    public Set<IEventType> observerOf();

    /**
     * Perform query on this projection to read the current status of the data-view managed scope.
     *
     * @param request        Mandatory query command (CQRS pattern's input element) relative to the projection that shall be performed.
     * @param resultObserver Optional provider of data-view status collected as request results.
     * @throws IllegalArgumentException      When any mandatory parameter is missing.
     * @throws UnsupportedOperationException When request execution generated an issue (e.g query not supported by this projection; or error of request parameter types).
     */
    public void when(Command request, CompletableFuture<IQueryResponse> resultObserver) throws IllegalArgumentException, UnsupportedOperationException;

}
