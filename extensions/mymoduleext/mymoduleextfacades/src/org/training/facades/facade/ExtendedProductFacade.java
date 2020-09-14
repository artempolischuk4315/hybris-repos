package org.training.facades.facade;

import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commercefacades.product.impl.DefaultProductVariantFacade;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class ExtendedProductFacade extends DefaultProductVariantFacade {

    public static final String MIN_RATING_LINK = "mymoduleextfacades.min-review-rating";

    private final ConfigurationService configurationService;

    public ExtendedProductFacade(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public ReviewData postReview(String productCode, ReviewData reviewData) {

        double minRating = configurationService.getConfiguration().getDouble(MIN_RATING_LINK);

        if(reviewData.getRating()<minRating){
            return reviewData;
        }
        return super.postReview(productCode, reviewData);
    }
}
