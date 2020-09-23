package org.training.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.impl.DefaultProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.Transaction;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class MyProductService extends DefaultProductService {

    private ModelService modelService;


    public void updateManufacturerNameForProducts(String manufacturerName, List<ProductModel> products) {
        Transaction tx = Transaction.current();
        tx.begin();
        boolean success = false;
        try {
            products.forEach(productModel -> {
                productModel.setManufacturerName(manufacturerName);
                modelService.save(productModel);
            });
            success = true;
        }finally {
            if(success)
                tx.commit();
            else
                tx.rollback();
        }
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
