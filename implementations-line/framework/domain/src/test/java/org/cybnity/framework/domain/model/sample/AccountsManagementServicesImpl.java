package org.cybnity.framework.domain.model.sample;

import java.security.InvalidParameterException;

import javax.management.modelmbean.InvalidTargetObjectTypeException;

import org.cybnity.framework.domain.Command;
import org.cybnity.framework.domain.ProcessManager;
import org.cybnity.framework.domain.application.ApplicationService;
import org.cybnity.framework.domain.application.sample.UserAccountManagementProcessesImpl;

/**
 * Example of application service component that define an applicative perimeter
 * (as componenf of a write model boundary) managing the handling of commands
 * (e.g domain changes requests) on write model's store(s).
 * 
 * @author olivier
 *
 */
public class AccountsManagementServicesImpl extends ApplicationService {

    private ProcessManager boundaryAPI;

    /**
     * Default constructor using a delegation for processes management regarding the
     * domain boundary.
     * 
     * @throws InvalidTargetObjectTypeException When none managed handlers are
     *                                          identified (non sens of
     *                                          instantiation regarding this
     *                                          component without any delegation
     *                                          defined) or some eligible handler
     *                                          instances are not valid.
     */
    public AccountsManagementServicesImpl() throws InvalidTargetObjectTypeException {
	boundaryAPI = new UserAccountManagementProcessesImpl();
    }

    @Override
    public void handle(Command command) throws IllegalArgumentException, InvalidParameterException {
	// Delegate the command realization to the handling service
	boundaryAPI.handle(command);
    }

}
