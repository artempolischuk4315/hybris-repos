package org.training.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MyProductService extends DefaultProductService {

    private ModelService modelService;

    @Transactional
    public void updateManufacturerNameForProducts(String manufacturerName, List<ProductModel> products) {
        products.forEach(productModel -> {
                    productModel.setManufacturerName(manufacturerName);
                    modelService.save(productModel);
        });
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
