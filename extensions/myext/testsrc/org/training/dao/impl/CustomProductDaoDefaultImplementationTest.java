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
    CustomProductDaoDefaultImplementation customProductDao;

    @Resource
    private ModelService modelService;

    private static final String PRODUCT_CODE = "test";

    @Test
    public void CustomProductDaoIntegrationTest(){
        CatalogVersionModel catalogVersionModel = new CatalogVersionModel();

        ProductModel productModel = new ProductModel();
        productModel.setCode(PRODUCT_CODE);
        productModel.setCatalogVersion(catalogVersionModel);

        ProductModel actual = customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);
        assertNull(actual);

        modelService.save(productModel);

        actual = customProductDao.getProductByExample(PRODUCT_CODE, new CatalogVersionModel());

        assertNotNull(actual);
        assertEquals(productModel, actual);
    }
}
