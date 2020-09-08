package org.training.dao.impl;

import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.catalog.model.CatalogVersionModel;
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
    public ProductModel getProductByExample(String productCode, CatalogVersionModel catalogVersionModel) {
        ProductModel productModel = new ProductModel();
        productModel.setCode(productCode);
        productModel.setCatalogVersion(catalogVersionModel);

        return flexibleSearchService.getModelByExample(productModel);
    }
}
