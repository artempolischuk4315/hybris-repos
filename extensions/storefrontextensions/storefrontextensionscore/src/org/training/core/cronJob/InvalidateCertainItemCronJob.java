package org.training.core.cronJob;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.training.model.CertainItemCacheInvalidationCronJobModel;
import de.hybris.platform.util.Utilities;

public class InvalidateCertainItemCronJob extends AbstractJobPerformable<CertainItemCacheInvalidationCronJobModel> {

    @Override
    public PerformResult perform(CertainItemCacheInvalidationCronJobModel cronJobModel) {
        ItemModel itemModel = cronJobModel.getItemToInvalidate();

        if(itemModel != null){
            Utilities.invalidateCache(itemModel.getPk());
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
    }
}
