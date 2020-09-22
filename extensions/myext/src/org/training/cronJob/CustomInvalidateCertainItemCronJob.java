package org.training.cronJob;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Utilities;
import org.springframework.beans.factory.annotation.Required;

public class CustomInvalidateCertainItemCronJob extends AbstractJobPerformable<CronJobModel> {
    private static final String CODE_KEY = "cronjob.code";
    private static final String CATALOG_ID_KEY = "cronjob.catalog-id";
    private static final String CATALOG_VERSION_KEY = "cronjob.catalog-version";

    private ProductService productService;
    private CatalogVersionService catalogVersionService;
    private ConfigurationService configurationService;

    public CustomInvalidateCertainItemCronJob() {
    }

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        String code = configurationService.getConfiguration().getString(CODE_KEY);
        String id = configurationService.getConfiguration().getString(CATALOG_ID_KEY);
        String version = configurationService.getConfiguration().getString(CATALOG_VERSION_KEY);

        CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(id, version);
        ProductModel productModel = productService.getProductForCode(catalogVersion, code);

        if (productModel != null) {
            Utilities.invalidateCache(productModel.getPk());
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
    }


    @Required
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Required
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Required
    public void setCatalogVersionService(CatalogVersionService catalogVersionService) {
        this.catalogVersionService = catalogVersionService;
    }
}
