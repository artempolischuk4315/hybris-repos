package org.training.dao.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CustomLeafCategoryDaoDefaultImplementationIntegrationTest extends ServicelayerTransactionalTest {
    private static final String CATALOG_VERSION = "Staged";
    private static final String CATALOG_ID = "100123131";
    private static final String CATALOG_NAME = "test";
    private static final String SECOND_CATALOG_VERSION = "Online";
    private static final String FIRST_CODE = "first";
    private static final String SECOND_CODE = "second";
    private static final String THIRD_CODE = "third";

    private CatalogVersionModel catalogVersionModel;
    private CatalogVersionModel secondCatalogVersionModel;
    private CategoryModel firstCategory;
    private CategoryModel secondCategory;
    private CategoryModel thirdCategory;

    @Resource(name = "customLeafCategoryDao")
    private CustomLeafCategoryDaoDefaultImplementation categoryDao;

    @Resource
    private ModelService modelService;

    @Resource
    private CatalogVersionService catalogVersionService;

    @Before
    public void setup() {
        CatalogModel catalogModel = modelService.create(CatalogModel.class);
        catalogModel.setId(CATALOG_ID);
        catalogModel.setName(CATALOG_NAME);

        modelService.save(catalogModel);

        catalogVersionModel = modelService.create(CatalogVersionModel.class);
        catalogVersionModel.setCatalog(catalogModel);
        catalogVersionModel.setVersion(CATALOG_VERSION);
        modelService.save(catalogVersionModel);

        secondCatalogVersionModel = modelService.create(CatalogVersionModel.class);
        secondCatalogVersionModel.setCatalog(catalogModel);
        secondCatalogVersionModel.setVersion(SECOND_CATALOG_VERSION);
        modelService.save(secondCatalogVersionModel);

        catalogVersionService.setSessionCatalogVersion(CATALOG_ID, CATALOG_VERSION);

        firstCategory = modelService.create(CategoryModel.class);
        firstCategory.setCode(FIRST_CODE);
        firstCategory.setCatalogVersion(catalogVersionModel);

        secondCategory = modelService.create(CategoryModel.class);
        secondCategory.setCode(SECOND_CODE);
        secondCategory.setCatalogVersion(catalogVersionModel);
        secondCategory.setSupercategories(Collections.singletonList(firstCategory));

        thirdCategory = modelService.create(CategoryModel.class);
        thirdCategory.setCode(THIRD_CODE);
        thirdCategory.setCatalogVersion(catalogVersionModel);
        thirdCategory.setSupercategories(Collections.singletonList(secondCategory));

        modelService.save(firstCategory);
        modelService.save(secondCategory);
        modelService.save(thirdCategory);

    }

    @Test
    public void daoShouldReturnOnlyLeafCategories() {
        List<CategoryModel> expected = new ArrayList<>();

        expected.add(thirdCategory);

        List<CategoryModel> actual = categoryDao.findAllLeafCategoriesByCatalogVersion(catalogVersionModel);

        //assertThat(actual.get(0).getCode()).isEqualTo("leaf");
        assertThat(actual).containsExactly(thirdCategory);
        //assertThat(actual.get(0).getCode()).isEqualTo("leaf");
 //       assertThat(actual.get(0).getCode()).isEqualTo("another");
    }
}