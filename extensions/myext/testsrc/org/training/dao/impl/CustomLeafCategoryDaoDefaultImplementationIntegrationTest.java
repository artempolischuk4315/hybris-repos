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

    private CatalogVersionModel catalogVersionModel;
    private CatalogVersionModel secondCatalogVersionModel;
    private CategoryModel notLeafCategory;
    private CategoryModel leafCategory;
    private CategoryModel categoryWithAnotherCatalogVersion;

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
        secondCatalogVersionModel.setVersion("Online");
        modelService.save(secondCatalogVersionModel);

        catalogVersionService.setSessionCatalogVersion(CATALOG_ID, CATALOG_VERSION);

        categoryWithAnotherCatalogVersion = modelService.create(CategoryModel.class);
        categoryWithAnotherCatalogVersion.setCode("another");
        categoryWithAnotherCatalogVersion.setCatalogVersion(secondCatalogVersionModel);
        modelService.save(categoryWithAnotherCatalogVersion);

        notLeafCategory = modelService.create(CategoryModel.class);
        notLeafCategory.setCode("not-leaf");
        notLeafCategory.setCatalogVersion(catalogVersionModel);
        notLeafCategory.setSupercategories(Collections.singletonList(categoryWithAnotherCatalogVersion));
        modelService.save(notLeafCategory);

        leafCategory = modelService.create(CategoryModel.class);
        leafCategory.setCode("leaf");
        leafCategory.setCatalogVersion(catalogVersionModel);
        leafCategory.setSupercategories(Collections.singletonList(notLeafCategory));
        modelService.save(leafCategory);

    }

    @Test
    public void daoShouldReturnOnlyLeafCategories(){
        List<CategoryModel> expected = new ArrayList<>();

        expected.add(categoryWithAnotherCatalogVersion);
        expected.add(notLeafCategory);
        expected.add(leafCategory);

        List<CategoryModel> actual = categoryDao.findAllLeafCategoriesByCatalogVersion(catalogVersionModel);

        assertThat(actual).isEqualTo(expected);
    }
}
