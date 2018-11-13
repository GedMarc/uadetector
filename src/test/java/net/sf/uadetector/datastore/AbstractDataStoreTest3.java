/*******************************************************************************
 * Copyright 2012 André Rouél
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

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import java.net.MalformedURLException;
import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.*;

public class AbstractDataStoreTest3
{

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;
	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final String DATA_URL = AbstractDataStoreTest2.class.getClassLoader()
	                                                                   .getResource("uas_newer.xml")
	                                                                   .toExternalForm();
	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final String VERSION_URL = AbstractDataStoreTest2.class.getClassLoader()
	                                                                      .getResource("uas_newer.version")
	                                                                      .toString();

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_charset_null() throws MalformedURLException
	{
		new TestDataStore(new XmlDataReader(), DATA_URL, VERSION_URL, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataReader_null() throws MalformedURLException
	{
		new TestDataStore(null, DATA_URL, VERSION_URL, CHARSET);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException
	{
		new TestDataStore(new XmlDataReader(), null, VERSION_URL, CHARSET);
	}

	@Test
	public void construct_successful()
	{
		DataReader reader = new XmlDataReader();
		DataStore store = new TestDataStore(reader, DATA_URL, VERSION_URL, CHARSET);

		assertThat(!store.getData()
		                 .getVersion()
		                 .isEmpty()).isTrue();
		assertThat(store.getDataReader()).isEqualTo(reader);
		assertThat(store.getDataUrl()
		                .toExternalForm()).isEqualTo(DATA_URL);
		assertThat(store.getVersionUrl()
		                .toExternalForm()).isEqualTo(VERSION_URL);
	}

	@Test(expected = IllegalStateException.class)
	public void construct_unreachable_url()
	{
		DataReader reader = new XmlDataReader();
		String unreachable = "http://unreachable.local";
		new TestDataStore(reader, unreachable, unreachable, CHARSET);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException
	{
		new TestDataStore(new XmlDataReader(), DATA_URL, null, CHARSET);
	}

	private static class TestDataStore
			extends AbstractDataStore
	{

		protected TestDataStore(DataReader reader, String dataUrl, String versionUrl, Charset charset)
		{
			super(reader, dataUrl, versionUrl, charset);
		}

	}

}
