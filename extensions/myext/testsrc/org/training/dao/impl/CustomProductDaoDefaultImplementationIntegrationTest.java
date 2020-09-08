package org.training.dao.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class CustomProductDaoDefaultImplementationIntegrationTest extends ServicelayerTransactionalTest {

    private static final String PRODUCT_CODE = "121221212";
    private static final String CATALOG_VERSION = "Staged";

    private CatalogVersionModel catalogVersionModel;

    @Resource(name = "customProductDao")
    private CustomProductDaoDefaultImplementation productDao;

    @Resource
    private ModelService modelService;

    @Resource
    private CatalogVersionService catalogVersionService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        CatalogModel catalogModel = modelService.create(CatalogModel.class);
        catalogModel.setId("100123131");
        catalogModel.setName("test");

        modelService.save(catalogModel);

        catalogVersionModel = modelService.create(CatalogVersionModel.class);
        catalogVersionModel.setCatalog(catalogModel);
        catalogVersionModel.setVersion(CATALOG_VERSION);

        modelService.save(catalogVersionModel);
        catalogVersionService.setSessionCatalogVersion(catalogVersionModel.getCatalog().getId(), CATALOG_VERSION);
    }

    @Test
    public void shouldReturnProductModelForCodeAndVersion() {
        ProductModel productModel = getProductModel();
        modelService.save(productModel);

        ProductModel actual = productDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);

        assertEquals(productModel, actual);
    }

    @Test
    public void shouldThrowModelNotFoundException() {
        expectedException.expect(ModelNotFoundException.class);

        productDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);
    }

    private ProductModel getProductModel() {
        ProductModel productModel = modelService.create(ProductModel.class);

        productModel.setCode(PRODUCT_CODE);
        productModel.setCatalogVersion(catalogVersionModel);
        productModel.setApprovalStatus(ArticleApprovalStatus.APPROVED);

        return productModel;
    }
}
