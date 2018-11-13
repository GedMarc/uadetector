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

import org.easymock.EasyMock;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

@RunWith(PowerMockRunner.class)
@PrepareForTest({File.class, URI.class})
@PowerMockIgnore({"org.slf4j.*"})
public class UrlUtilTest_toUrl
{

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(UrlUtilTest_toUrl.class.toString());

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
	// (expected = IllegalStateException.class)
	public void read() throws Exception
	{
		if (!OperatingSystemDetector.isLinux())
		{
			try
			{
				URI uri = PowerMock.createPartialMock(URI.class, "toURL");
				EasyMock.expect(uri.toURL())
				        .andThrow(new MalformedURLException());
				File file = PowerMock.createMock(File.class);
				EasyMock.expect(file.toURI())
				        .andReturn(uri);
				PowerMock.replayAll();
				UrlUtil.toUrl(file);
				PowerMock.verifyAll();
			}
			catch (IllegalStateException e)
			{
				// all things okay
			}
		}
	}

}
