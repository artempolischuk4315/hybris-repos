package org.training.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.*;
import de.hybris.platform.genericsearch.GenericSearchService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.training.dao.CustomLeafCategoryDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component(value = "customLeafCategoryDaoGenericSearch")
public class CustomLeafCategoryDaoGenericSearchImplementation implements CustomLeafCategoryDao {

    @Autowired
    private GenericSearchService genericSearchService;

    @Override
    public List<CategoryModel> findAllLeafCategoriesByCatalogVersion(CatalogVersionModel catalogVersionModel) {
        GenericQuery query = new GenericQuery(CategoryModel._TYPECODE);

        GenericSearchField categoryPK =
                new GenericSearchField(CategoryModel._TYPECODE, CategoryModel.PK);
        GenericSearchField categoryCatalogVersion =
                new GenericSearchField(CategoryModel._TYPECODE, CategoryModel.CATALOGVERSION);
        GenericSearchField relationFieldSource =
                new GenericSearchField(CategoryModel._CATEGORYCATEGORYRELATION, "source");
        GenericSearchField relationFieldTarget =
                new GenericSearchField(CategoryModel._CATEGORYCATEGORYRELATION, "target");

        GenericCondition joinCondition =
                GenericCondition.createConditionForFieldComparison(categoryPK, Operator.EQUAL, relationFieldSource);

        GenericCondition whereConditionOnCatalogVersion =
                GenericCondition.createConditionForValueComparison
                        (categoryCatalogVersion, Operator.EQUAL, catalogVersionModel);

        GenericCondition whereConditionOnTarget =
                GenericCondition.createIsNullCondition(relationFieldTarget);

        query.addOuterJoin(CategoryModel._CATEGORYCATEGORYRELATION, joinCondition);
        query.addCondition(whereConditionOnCatalogVersion);
        query.addCondition(whereConditionOnTarget);

        Collection result = genericSearchService.search(query).getResult();

        return new ArrayList<CategoryModel>(result);
    }
}