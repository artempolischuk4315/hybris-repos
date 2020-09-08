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

import static org.training.constants.MycustomextConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import org.training.constants.MycustomextConstants;
import org.training.service.MycustomextService;


@SystemSetup(extension = MycustomextConstants.EXTENSIONNAME)
public class MycustomextSystemSetup
{
	private final MycustomextService mycustomextService;

	public MycustomextSystemSetup(final MycustomextService mycustomextService)
	{
		this.mycustomextService = mycustomextService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		mycustomextService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return MycustomextSystemSetup.class.getResourceAsStream("/mycustomext/sap-hybris-platform.png");
	}
}
