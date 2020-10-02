package org.training.storefront.form;

import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.RegistrationValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("customRegistrationValidator")
public class CustomRegistrationValidator extends RegistrationValidator {
    @Override
    public void validate(Object object, Errors errors) {
        final ExtendedRegisterForm registerForm = (ExtendedRegisterForm) object;
        final String companyName = registerForm.getCompanyName();
        validateCompanyName(errors, companyName, "companyName", "register.companyName.invalid");

        super.validate(object, errors);
    }

    protected void validateCompanyName(final Errors errors, final String name, final String propertyName, final String property)
    {
        if (StringUtils.length(name) > 255)
        {
            errors.rejectValue(propertyName, property);
        }
    }
}
