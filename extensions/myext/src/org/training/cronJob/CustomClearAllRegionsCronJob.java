package org.training.core.cronJob;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.regioncache.CacheController;
import de.hybris.platform.regioncache.region.CacheRegion;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;

public class CustomClearAllRegionsCronJob extends AbstractJobPerformable<CronJobModel> {
    private CacheController cacheController;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {

        Collection<CacheRegion> regionList = cacheController.getRegions();

        for(CacheRegion region : regionList)
            cacheController.clearCache(region);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    @Required
    public void setCacheController(CacheController cacheController) {
        this.cacheController = cacheController;
    }
}
