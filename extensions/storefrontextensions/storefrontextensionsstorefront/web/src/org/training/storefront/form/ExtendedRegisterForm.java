package org.training.storefront.form;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;

public class ExtendedRegisterForm extends RegisterForm {
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
