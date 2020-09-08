package org.training.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static de.hybris.platform.testframework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomProductDaoDefaultImplementationUnitTest {

    public static final String PRODUCT_CODE = "test";
    @InjectMocks
    CustomProductDaoDefaultImplementation customProductDao;

    @Mock
    FlexibleSearchService flexibleSearchService;

    @Mock
    ProductModel productModel;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final CatalogVersionModel catalogVersionModel = new CatalogVersionModel();

    @Test
    public void getProductByExampleShouldReturnProductModelIfExists(){
        ProductModel productModel = new ProductModel();
        productModel.setCode(PRODUCT_CODE);
        productModel.setCatalogVersion(catalogVersionModel);

        when(flexibleSearchService.getModelByExample(any())).thenReturn(productModel);

        ProductModel actual = customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);

        assertEquals(productModel.getCatalogVersion(), actual.getCatalogVersion());
    }

    @Test
    public void shouldThrowModelNotFoundException() {
        when(flexibleSearchService.getModelByExample(any())).thenThrow(new ModelNotFoundException("Model not found"));

        expectedException.expect(ModelNotFoundException.class);

        customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);
    }

}
