package org.training.core.workflow;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractAdvancedUserRegistrationActionJob implements AutomatedWorkflowTemplateJob {
    @Autowired
    private ModelService modelService;


    protected CustomerModel getAttachedCustomer(final WorkflowActionModel action)
    {
        final List<ItemModel> attachments = action.getAttachmentItems();
        if (attachments != null)
        {
            for (final ItemModel item : attachments)
            {
                if (item instanceof CustomerModel)
                {
                    return (CustomerModel) item;
                }
            }
        }
        return null;
    }

    public void setModelService(final ModelService modelService)
    {
        this.modelService = modelService;
    }

    protected ModelService getModelService()
    {
        return this.modelService;
    }

}
