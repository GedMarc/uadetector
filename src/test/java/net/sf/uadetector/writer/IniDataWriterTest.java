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
package net.sf.uadetector.writer;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public final class IniDataWriterTest
{

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(IniDataWriterTest.class.toString());

	private static final URL DATA_URL = TestXmlDataStore.class.getClassLoader()
	                                                          .getResource("uas_older.xml");

	private static final URL VERSION_URL = TestXmlDataStore.class.getClassLoader()
	                                                             .getResource("uas_older.version");

	private static final DataStore DATA_STORE = new SimpleXmlDataStore(DATA_URL, VERSION_URL);

	@Test
	public void write() throws Exception
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IniDataWriter.write(DATA_STORE.getData(), outputStream);
		String actual = new String(outputStream.toByteArray(), "UTF-8");
		LOG.finer(actual);
	}

}
