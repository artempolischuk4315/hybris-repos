package org.training.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class CustomProductDaoDefaultImplementationTest extends ServicelayerTransactionalTest {

    @Resource
    CustomProductDaoDefaultImplementation systemUnderTest;

    @Resource
    private ModelService modelService;

    private static final String PRODUCT_CODE = "test";

    @Test
    public void CustomProductDaoIntegrationTest(){
        ProductModel productModel = new ProductModel();
        productModel.setCode(PRODUCT_CODE);
        productModel.setCatalogVersion(new CatalogVersionModel());

        ProductModel actual = systemUnderTest.getProductByExample(productModel);
        assertNull(actual);

        modelService.save(productModel);

        actual = systemUnderTest.getProductByExample(productModel);

        assertNotNull(actual);
        assertEquals(productModel, actual);
    }
}
