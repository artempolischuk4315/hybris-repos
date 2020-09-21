package org.training.core.workflow;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;
import org.apache.log4j.Logger;

public class SupervisorRegistrationConfirmationActionJob extends AbstractAdvancedUserRegistrationActionJob {
    private static final Logger LOG = Logger.getLogger(SupervisorRegistrationConfirmationActionJob.class);

    @Override
    public WorkflowDecisionModel perform(final WorkflowActionModel action)
    {
        final CustomerModel customer = getAttachedCustomer(action);

        LOG.info("Customer " + customer.getUid() + " confirmed by supervisor!");

        customer.setLoginDisabled(false);
        ModelService modelService = getModelService();
        modelService.save(customer);

        for (final WorkflowDecisionModel decision : action.getDecisions())
        {
            return decision;
        }
        return null;
    }
}
