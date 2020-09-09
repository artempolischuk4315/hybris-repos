package org.training.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.dao.CustomLeafCategoryDao;

import java.util.List;

@Component(value = "customLeafCategoryDao")
public class CustomLeafCategoryDaoDefaultImplementation implements CustomLeafCategoryDao {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<CategoryModel> findAllLeafCategoriesByCatalogVersion(CatalogVersionModel catalogVersionModel) {

        final String queryString =
                "SELECT {c:" + CategoryModel.PK + "}" +
                        "FROM {" + CategoryModel._TYPECODE + " AS c " +
                        "LEFT JOIN "+ CategoryModel._CATEGORYCATEGORYRELATION +" as rel ON {c:pk} = {rel:source} } " +
                        "WHERE " + "{c:" + CategoryModel.CATALOGVERSION + "}=?catalogVersion AND {rel:target} IS NULL";

        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(queryString);
        flexibleSearchQuery.addQueryParameter("catalogVersion", catalogVersionModel);

        return flexibleSearchService.<CategoryModel> search(flexibleSearchQuery).getResult();
    }
}
