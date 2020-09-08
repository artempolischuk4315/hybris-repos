package org.training.dao.impl;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.daos.ProductDao;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class CustomProductDaoDefaultImplementationIntegrationTest extends ServicelayerTransactionalTest {

    @Resource
    CustomProductDaoDefaultImplementation customProductDao;

    @Resource
    private ModelService modelService;

    @Resource
    private CatalogVersionService catalogVersionService;

    @Resource
    private ProductService productService;

    @Resource
    ProductDao productDao;

    private static final String PRODUCT_CODE = "test";

    @Test
    public void CustomProductDaoIntegrationTest() {

        CatalogVersionModel catalogVersionModel =
                catalogVersionService.getCatalogVersion("Default", "Online");
        catalogVersionService.setSessionCatalogVersion("Default", "Online");

        ProductModel productModel = productDao.findProductsByCode("300441142").get(0);

        ProductModel actual = customProductDao.getProductByExample(productModel.getCode(), productModel.getCatalogVersion());

        assertNotNull(actual);
        assertEquals(productModel, actual);
    }
}
