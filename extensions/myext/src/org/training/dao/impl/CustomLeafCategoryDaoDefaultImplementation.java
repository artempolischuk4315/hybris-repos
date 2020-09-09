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

        final StringBuilder query = new StringBuilder("SELECT {cat." + CategoryModel.PK + "} ");
        query.append("FROM {" + CategoryModel._TYPECODE + " AS cat} ");
        query.append("WHERE NOT EXISTS ({{");
        query.append("SELECT {pk} FROM {" + CategoryModel._CATEGORYCATEGORYRELATION + " AS rel ");
        query.append("JOIN " + CategoryModel._TYPECODE + " AS sub ON {rel.target}={sub.PK} } ");
        query.append("WHERE {rel:source}={cat.pk} ");
        query.append("}}) ");
        query.append("AND {cat:" + CategoryModel.CATALOGVERSION + "}=?catalogVersion");

        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        flexibleSearchQuery.addQueryParameter("catalogVersion", catalogVersionModel);

        return flexibleSearchService.<CategoryModel> search(flexibleSearchQuery).getResult();
    }
}
