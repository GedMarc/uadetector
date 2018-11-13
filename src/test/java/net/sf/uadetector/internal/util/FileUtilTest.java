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

import net.sf.uadetector.datastore.DataStore;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BufferedReader.class, FileUtil.class})
public class FileUtilTest
{

	/**
	 * The character set to read the contents of an URL
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = FileUtilTest.class.getClassLoader()
	                                                         .getResource("uas_newer.version");

	@Test
	public void isEmpty_withEmptyFile() throws Exception
	{
		File tempEmptyFile = File.createTempFile("testfile", ".tmp");
		tempEmptyFile.deleteOnExit();
		boolean isEmpty = FileUtil.isEmpty(tempEmptyFile, CHARSET);
		PowerMock.verifyAll();
		assertThat(isEmpty).isTrue();
		tempEmptyFile.delete();
	}

	@Test
	public void isEmpty_withFilledFile() throws Exception
	{
		File tempEmptyFile = File.createTempFile("testfile", ".tmp");
		tempEmptyFile.deleteOnExit();
		FileOutputStream writer = new FileOutputStream(tempEmptyFile);
		writer.write("test".getBytes());
		writer.close();
		boolean isEmpty = FileUtil.isEmpty(tempEmptyFile, CHARSET);
		PowerMock.verifyAll();
		assertThat(isEmpty).isFalse();
		tempEmptyFile.delete();
	}

	@Test(expected = IOException.class)
	public void testNullCheckBeforeClosing() throws Exception
	{
		PowerMock.expectNiceNew(BufferedReader.class, EasyMock.anyObject(Reader.class))
		         .andThrow(new IOException());
		PowerMock.replayAll();
		FileUtil.isEmpty(new File(VERSION_URL.toURI()), CHARSET);
		PowerMock.verifyAll();
	}

}
