package org.training.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomProductDaoDefaultImplementationUnitTest {

    private static final String PRODUCT_CODE = "test";

    @InjectMocks
    private CustomProductDaoDefaultImplementation customProductDao;

    @Mock
    private FlexibleSearchService flexibleSearchService;

    @Mock
    private ProductModel productModel;

    @Mock
    private CatalogVersionModel catalogVersionModel;

    @Captor
    private ArgumentCaptor<ProductModel> captor;

    @Test
    public void getProductByExampleShouldReturnProductModelIfExists(){
        when(flexibleSearchService.getModelByExample(captor.capture())).thenReturn(productModel);

        customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);

        verify(flexibleSearchService).getModelByExample(captor.capture());

        ProductModel actual = captor.getValue();
        ProductModel result = customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);

        assertThat(actual.getCode()).isEqualTo(PRODUCT_CODE);
        assertThat(actual.getCatalogVersion()).isEqualTo(catalogVersionModel);
        assertThat(result).isEqualTo(productModel);

    }

    @Test(expected = ModelNotFoundException.class)
    public void shouldThrowModelNotFoundException() {
        when(flexibleSearchService.getModelByExample(any())).thenThrow(new ModelNotFoundException("Model not found"));

        customProductDao.getProductByExample(PRODUCT_CODE, catalogVersionModel);
    }

}
