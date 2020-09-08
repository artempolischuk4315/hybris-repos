package org.training.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;

import java.util.List;

public interface CustomLeafCategoryDao {
    List<CategoryModel> findAllLeafCategoriesByCatalogVersion(CatalogVersionModel catalogVersionModel);
}
