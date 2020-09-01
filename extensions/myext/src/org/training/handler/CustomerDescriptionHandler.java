package org.training.handler;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

public class CustomerDescriptionHandler implements DynamicAttributeHandler<String, CustomerModel> {
    public static final String COLON= ":";
    public static final String SPACE= " ";
    public static final String QUANTITY_IS = ". Order quantity is";
    public static final String ERROR_MESSAGE = "Item model is required";


    @Override
    public String get(final CustomerModel item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return item.getName() + COLON + item.getEmail() + QUANTITY_IS + SPACE + item.getOrders().size() ;
    }

    @Override
    public void set(final CustomerModel item, final String value)
    {
        throw new UnsupportedOperationException();
    }
}
