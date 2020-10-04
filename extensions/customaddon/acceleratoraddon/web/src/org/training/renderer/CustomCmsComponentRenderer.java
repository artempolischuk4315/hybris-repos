package org.training.renderer;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.yacceleratorcore.model.cms2.components.CustomCMSComponentModel;
import org.springframework.beans.factory.annotation.Required;

import javax.servlet.jsp.PageContext;
import java.util.HashMap;
import java.util.Map;

public class CustomCmsComponentRenderer<C extends CustomCMSComponentModel> extends
        DefaultAddOnCMSComponentRenderer<C> {

    CMSComponentService cmsComponentService;
    ModelService modelService;

    @Required
    public void setCmsComponentService(final CMSComponentService cmsComponentService)
    {
        this.cmsComponentService = cmsComponentService;
    }

    @Required
    public void setModelService(final ModelService modelService)
    {
        this.modelService = modelService;
    }

    protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
    {
        final Map<String, Object> variables = new HashMap<String, Object>();
        for (final String property : cmsComponentService.getEditorProperties(component))
        {
            try
            {
                final Object value = modelService.getAttributeValue(component, property);
                variables.put(property, value);

            }
            catch (final AttributeNotSupportedException ignore)
            {
                // ignore
            }
        }
        return variables;
    }
}
