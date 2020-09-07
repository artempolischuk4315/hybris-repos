package org.training.dao.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.dao.CustomProductDao;

@Component(value = "customProductDao")
public class CustomProductDaoDefaultImplementation implements CustomProductDao {

    @Autowired
    FlexibleSearchService flexibleSearchService;

    @Override
    public ProductModel getProductByExample(ProductModel example) {
       return flexibleSearchService.getModelByExample(example);
    }
}
