package org.cybnity.application.ui.system.backend;

import org.cybnity.framework.UnoperationalStateException;

import io.vertx.core.AbstractVerticle;

public abstract class SockJSServer extends AbstractVerticle {

    /**
     * Verify the current status of this component as healthy and operable.
     * 
     * @throws UnoperationalStateException When missing required contents (e.g
     *                                     environment variables).
     */
    public abstract void checkHealthyState() throws UnoperationalStateException;
}
