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
package net.sf.uadetector.datastore;

import com.google.common.io.Files;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class CachingXmlDataStoreTest
{

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = CachingXmlDataStoreTest.class.getClassLoader()
	                                                                 .getResource("uas_older.xml");

	/**
	 * Corresponding default logger of this class
	 */
	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(CachingXmlDataStoreTest.class.toString());

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = CachingXmlDataStoreTest.class.getClassLoader()
	                                                                    .getResource("uas_older.version");
	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();
	private DataStore fallback;

	@Before
	public void before()
	{
		fallback = new TestXmlDataStore();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_charset_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, VERSION_URL, null, fallback);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_dataUrl_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), null, VERSION_URL, CHARSET, fallback);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_fallback1_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, VERSION_URL, CHARSET, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_fallback2_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(DATA_URL, VERSION_URL, CHARSET, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_fallback3_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_file2_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(null, DATA_URL, VERSION_URL, CHARSET, fallback);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_file3_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(null, fallback);
	}

	@Test
	public void createCachingXmlDataStore_provokeFallback() throws IOException, InterruptedException
	{
		// create temp file
		File cache = folder.newFile("uas_temp.xml");
		assertEquals(readFile(cache), "");

		// create fallback data store
		String version = "fallback-data-version";
		DataStore fallback = new DataStore()
		{

			@Override
			public Charset getCharset()
			{
				return null;
			}

			@Override
			public Data getData()
			{
				return new DataBlueprint().version(version)
				                          .build();
			}

			@Override
			public DataReader getDataReader()
			{
				return null;
			}

			@Override
			public URL getDataUrl()
			{
				return null;
			}

			@Override
			public URL getVersionUrl()
			{
				return null;
			}
		};

		// create caching data store
		DataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, CHARSET, fallback);
		assertEquals(store.getData()
		                  .getVersion(), version);
	}

	private static String readFile(File file) throws IOException
	{
		byte[] bytes = Files.toByteArray(file);
		return new String(bytes, Charset.defaultCharset());
	}

	@Test
	public void createCachingXmlDataStore_successful() throws IOException, InterruptedException
	{
		// create temp file
		File cache = folder.newFile("uas_temp.xml");
		assertEquals(readFile(cache), "");

		// create caching data store
		CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL, VERSION_URL, CHARSET, fallback);
		UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);

		Thread.sleep(1000l);
		assertTrue(readFile(cache).length() >= 721915);

		long firstLastUpdateCheck = store.getUpdateOperation()
		                                 .getLastUpdateCheck();
		LOG.finer("LastUpdateCheck at: " + firstLastUpdateCheck);
		long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.finer("Reducing the update interval during the test.");
		parser.setUpdateInterval(1000l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(3000l);
		long currentLastUpdateCheck = store.getUpdateOperation()
		                                   .getLastUpdateCheck();
		LOG.finer("LastUpdateCheck at: " + currentLastUpdateCheck);
		assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

		parser.setUpdateInterval(originalInterval);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createCachingXmlDataStore_versionUrl_null() throws IOException
	{
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, null, CHARSET, fallback);
	}

	@Test
	public void findOrCreateCacheFile()
	{
		CachingXmlDataStore.findOrCreateCacheFile()
		                   .delete(); // delete if exists
		File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file does not exist
		assertTrue(temp.exists());
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		assertTrue(temp.exists());
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		assertTrue(temp.exists());
		temp.delete();
		assertFalse(temp.exists());
	}

}
