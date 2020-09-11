package org.training.interceptor;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserAgeTest extends ServicelayerTransactionalTest {

    @Resource
    ModelService modelService;

    @Test
    public void testShouldPass(){
        UserModel userModel = modelService.create(UserModel.class);

        userModel.setName("User");
        userModel.setUid("User");
        userModel.setDateOfBirth(new GregorianCalendar(2008, Calendar.SEPTEMBER, 11).getTime());

        modelService.save(userModel);
    }

    @Test(expected = ModelSavingException.class)
    public void testShouldNotPassBecauseAgeLessThan12(){
        UserModel userModel = modelService.create(UserModel.class);

        userModel.setName("User");
        userModel.setUid("User");
        userModel.setDateOfBirth(new GregorianCalendar(2008, Calendar.SEPTEMBER, 12).getTime());

        modelService.save(userModel);
    }
}
