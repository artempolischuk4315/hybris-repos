package org.training.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.springframework.beans.factory.annotation.Required;
import org.training.model.process.CustomerUpdateProfileEmailProcessModel;


public class CustomerProfileUpdatedEventListener extends AbstractSiteEventListener<CustomerUpdateProfileEvent> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;
    private KeyGenerator processCodeGenerator;

    @Override
    protected void onSiteEvent(CustomerUpdateProfileEvent event) {
        final CustomerUpdateProfileEmailProcessModel updateProcessModel = (CustomerUpdateProfileEmailProcessModel) getBusinessProcessService()
                .createProcess("CustomerUpdateProfileEmailProcess-" + event.getCustomer().getUid() +
                                "-" + processCodeGenerator.generate().toString(), "customerUpdateProfileEmailProcess");

        updateProcessModel.setSite(event.getSite());
        updateProcessModel.setCustomer(event.getCustomer());
        updateProcessModel.setLanguage(event.getLanguage());
        updateProcessModel.setCurrency(event.getCurrency());
        updateProcessModel.setStore(event.getBaseStore());
        updateProcessModel.setFirstName(event.getFirstName());
        getModelService().save(updateProcessModel);
        getBusinessProcessService().startProcess(updateProcessModel);
    }

    @Override
    protected boolean shouldHandleEvent(CustomerUpdateProfileEvent event) {
        final BaseSiteModel site = event.getSite();
        ServicesUtil.validateParameterNotNullStandardMessage("event.site", site);
        return SiteChannel.B2C.equals(site.getChannel());
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    @Required
    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public KeyGenerator getProcessCodeGenerator() {
        return processCodeGenerator;
    }

    @Required
    public void setProcessCodeGenerator(KeyGenerator processCodeGenerator) {
        this.processCodeGenerator = processCodeGenerator;
    }
}
