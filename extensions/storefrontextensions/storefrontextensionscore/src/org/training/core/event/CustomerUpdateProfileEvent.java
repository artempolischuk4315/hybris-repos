package org.training.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

public class CustomerUpdateProfileEvent extends AbstractCommerceUserEvent<BaseSiteModel> {
    private String firstName;

    public CustomerUpdateProfileEvent() {
        super();
    }

    public CustomerUpdateProfileEvent(String firstName) {
        super();
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
