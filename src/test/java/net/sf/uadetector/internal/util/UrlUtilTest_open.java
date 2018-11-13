/*******************************************************************************
 * Copyright 2013 André Rouél
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.internal.util;

import net.sf.uadetector.exception.CanNotOpenStreamException;
import org.easymock.EasyMock;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UrlUtil.class)
@PowerMockIgnore({"org.slf4j.*"})
public class UrlUtilTest_open
{

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(UrlUtilTest_open.class.toString());

	@Before
	public void doNotRunOnLinux()
	{
		// this assumption currently does not work
		boolean isLinux = OperatingSystemDetector.isLinux();
		Assume.assumeTrue(isLinux);
		if (isLinux)
		{
			LOG.info("This unit test will be ignored due to a bug in EasyMock <= 3.1, in the class mocking feature under GNU/Linux.");
		}
	}

	@Test
	// (expected = CanNotOpenStreamException.class)
	public void open_withIOException() throws IOException
	{
		if (!OperatingSystemDetector.isLinux())
		{
			try
			{
				// The following line makes a bug in EasyMock 3.1 visible. For example on OS X the output will be
				// 'EasyMock.DISABLE_CLASS_MOCKING: false' and on Linux it is 'EasyMock.DISABLE_CLASS_MOCKING: true'.
				// The behavior of EasyMock on Linux breaks this test.
				EasyMock.setEasyMockProperty(EasyMock.DISABLE_CLASS_MOCKING, Boolean.FALSE.toString());
				System.out.println("EasyMock.DISABLE_CLASS_MOCKING: " + EasyMock.getEasyMockProperty(EasyMock.DISABLE_CLASS_MOCKING));

				URL url = PowerMock.createMock(URL.class);
				EasyMock.expect(url.openStream())
				        .andThrow(new IOException());
				PowerMock.replayAll();
				UrlUtil.open(url);
				PowerMock.verifyAll();
			}
			catch (CanNotOpenStreamException e)
			{
				// all things okay
			}
		}
	}

}
