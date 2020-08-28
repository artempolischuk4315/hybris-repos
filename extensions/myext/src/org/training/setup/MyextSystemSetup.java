/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package org.training.setup;

import static org.training.constants.MyextConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import org.training.constants.MyextConstants;
import org.training.service.MyextService;


@SystemSetup(extension = MyextConstants.EXTENSIONNAME)
public class MyextSystemSetup
{
	private final MyextService myextService;

	public MyextSystemSetup(final MyextService myextService)
	{
		this.myextService = myextService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		myextService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MyextSystemSetup.class.getResourceAsStream("/myext/sap-hybris-platform.png");
	}
}
