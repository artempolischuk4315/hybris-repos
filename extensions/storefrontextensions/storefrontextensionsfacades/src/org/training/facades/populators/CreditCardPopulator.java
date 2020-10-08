package org.training.facades.populators;

import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class CreditCardPopulator implements Populator<CreditCardPaymentInfoModel, CCPaymentInfoData> {
    @Override
    public void populate(CreditCardPaymentInfoModel creditCardPaymentInfoModel, CCPaymentInfoData ccPaymentInfoData) throws ConversionException {
        ccPaymentInfoData.setCardOwner(creditCardPaymentInfoModel.getCardOwner());
    }
}
