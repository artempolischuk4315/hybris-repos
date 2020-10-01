package org.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import org.training.model.process.CustomerUpdateProfileEmailProcessModel;

public class CustomerUpdateProfileEmailContext extends CustomerEmailContext {
    private String firstName;

    @Override
    public void init(final StoreFrontCustomerProcessModel processModel, final EmailPageModel emailPageModel)
    {
        super.init(processModel, emailPageModel);
        if (processModel instanceof CustomerUpdateProfileEmailProcessModel)
        {
            setFirstName(((CustomerUpdateProfileEmailProcessModel) processModel).getFirstName());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
