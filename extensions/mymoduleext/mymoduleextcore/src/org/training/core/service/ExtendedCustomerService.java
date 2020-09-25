package org.training.core.service;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.EventService;
import org.springframework.beans.factory.annotation.Required;
import org.training.core.event.CustomerUpdateProfileEvent;

public class ExtendedCustomerService extends DefaultCustomerAccountService {

    private EventService eventService;

    @Override
    public void updateProfile(CustomerModel customerModel, String titleCode, String name, String login) throws DuplicateUidException {
        super.updateProfile(customerModel, titleCode, name, login);
        getEventService().publishEvent(initializeEvent(new CustomerUpdateProfileEvent(name), customerModel));
    }

    @Required
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public EventService getEventService() {
        return eventService;
    }
}
