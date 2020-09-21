package org.training.core.cronJob;

import de.hybris.platform.core.PK;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Utilities;
import org.springframework.beans.factory.annotation.Required;

import java.util.Optional;

public class CustomInvalidateCertainItemCronJob extends AbstractJobPerformable<CronJobModel> {

    private static final String PK_STRING = "cronjob.pk";

    private ModelService modelService;
    private ConfigurationService configurationService;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {

        String pkFromProperties = configurationService.getConfiguration().getString(PK_STRING);

        Optional<PK> pk = Optional.ofNullable(modelService.get(pkFromProperties));

        if (pk.isPresent()) {
            Utilities.invalidateCache(pk.get());
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
    }

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Required
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
