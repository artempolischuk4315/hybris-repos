package org.training.cronJob;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.*;
import java.util.Collections;
import java.util.List;

public class CustomExportCronJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(CustomExportCronJob.class.getName());
    private static final String FILE_WRITING_EXCEPTION = "FILE WRITING EXCEPTION";
    private static final String FILENAME = "C:\\mentoring\\extensions\\myext\\resources\\file\\export.csv";
    private static final String ENCODING = "UTF-8";
    private static final String DELIMITER = ";";
    private static final String CURRENT_DATE_PLUS_FIVE_DAYS = "currentDatePlusFive";

    public static final String QUERY_STRING = "SELECT {p:" + ProductModel.CODE + "}" +
            " FROM {" + ProductModel._TYPECODE + " AS p }" +
            " WHERE " + "{p:" + ProductModel.OFFLINEDATE + "}<?currentDatePlusFive";

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        FileWriterWithEncoding writer;

        List<String> codes = getLessThanFiveDaysOfflineProducts();

        String stringToWrite = String.join(DELIMITER, codes);

        try {
            writer = new FileWriterWithEncoding(FILENAME, ENCODING);
            writer.write(stringToWrite);
            writer.close();
        } catch (IOException e) {
            LOG.error(FILE_WRITING_EXCEPTION, e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private List<String> getLessThanFiveDaysOfflineProducts() {
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(QUERY_STRING);

        flexibleSearchQuery.addQueryParameter(CURRENT_DATE_PLUS_FIVE_DAYS, ZonedDateTime.now().plusDays(5));
        flexibleSearchQuery.setResultClassList(Collections.singletonList(String.class));

        return flexibleSearchService.<String>search(flexibleSearchQuery).getResult();
    }
}
