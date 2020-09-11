package org.training.interceptor;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class UserAgeValidationInterceptor implements ValidateInterceptor {

    @Override
    public void onValidate(Object o, InterceptorContext interceptorContext) throws InterceptorException {
        if (o instanceof UserModel) {
            UserModel user = (UserModel) o;

            LocalDate dateOfBirth = convertToLocalDateViaInstant(user.getDateOfBirth());

            int age = calculateAge(dateOfBirth, LocalDate.now());

            if (age < 12) {
                throw new InterceptorException("User age cannot be < 12");
            }
        }
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
