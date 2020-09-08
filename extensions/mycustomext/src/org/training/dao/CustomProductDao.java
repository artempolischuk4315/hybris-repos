package org.training.dao;

import de.hybris.platform.core.model.product.ProductModel;

public interface CustomProductDao {
    ProductModel getProductByExample(ProductModel example);
}
