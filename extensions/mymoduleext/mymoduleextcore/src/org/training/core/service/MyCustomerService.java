package org.training.core.service;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.workflow.WorkflowProcessingService;
import de.hybris.platform.workflow.WorkflowService;
import de.hybris.platform.workflow.WorkflowTemplateService;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.model.WorkflowTemplateModel;
import org.springframework.beans.factory.annotation.Required;

public class MyCustomerService extends DefaultCustomerAccountService {

    private static final String WORKFLOW_NAME= "AdvancedUserRegistration";

    private WorkflowService workflowService;
    private WorkflowTemplateService workflowTemplateService;
    private WorkflowProcessingService workflowProcessingService;

    @Override
    public void register(CustomerModel customerModel, String password) throws DuplicateUidException {
        customerModel.setLoginDisabled(true);
        super.register(customerModel, password);

        startWorkflow(customerModel);
    }

    private void startWorkflow(CustomerModel customerModel) {
        WorkflowTemplateModel workflowTemplateModel = workflowTemplateService
                .getWorkflowTemplateForCode(WORKFLOW_NAME);
        WorkflowModel workflowModel = workflowService
                .createWorkflow(workflowTemplateModel, customerModel, getUserService().getAdminUser());

        getModelService().save(workflowModel);

        workflowModel.getActions().forEach(a -> getModelService().save(a));

        workflowProcessingService.startWorkflow(workflowModel);
    }

    @Required
    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @Required
    public void setWorkflowTemplateService(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    @Required
    public void setWorkflowProcessingService(WorkflowProcessingService workflowProcessingService) {
        this.workflowProcessingService = workflowProcessingService;
    }
}

