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

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.*;

public class UpdateOperationWithCacheFileTaskTest_call
{

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void call_updateFails_fallbackIsNewer_differentVersion() throws IOException
	{
		DataStore fallback = new TestXmlDataStore();

		DataStoreWhichFallsBack store = new DataStoreWhichFallsBack(new XmlDataReader(),
		                                                            NotUpdateableXmlDataStore.DATA_URL_UNREACHABLE, NotUpdateableXmlDataStore.VERSION_URL_UNREACHABLE,
		                                                            DataStore.DEFAULT_CHARSET, fallback);

		UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(store, folder.newFile("cache_file.tmp"));
		assertThat(store.getData()).isSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isFalse();

		// try to update
		task.call();
		assertThat(store.getData()).isNotSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isTrue();

		// retry to update
		task.call();
		assertThat(store.getData()).isNotSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isTrue();
	}

	@Test
	public void call_updateFails_fallbackNotNewer_sameVersion() throws IOException
	{
		DataStore fallback = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL);

		DataStoreWhichFallsBack store = new DataStoreWhichFallsBack(new XmlDataReader(),
		                                                            NotUpdateableXmlDataStore.DATA_URL_UNREACHABLE, NotUpdateableXmlDataStore.VERSION_URL_UNREACHABLE,
		                                                            DataStore.DEFAULT_CHARSET, fallback);

		UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(store, folder.newFile("cache_file.tmp"));
		assertThat(store.getData()).isSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isFalse();

		// try to update
		task.call();
		assertThat(store.getData()).isSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isFalse();

		// retry to update
		task.call();
		assertThat(store.getData()).isSameAs(fallback.getData());
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData())).isFalse();
	}

	private static class DataStoreWhichFallsBack
			extends AbstractRefreshableDataStore
	{

		DataStoreWhichFallsBack(@javax.validation.constraints.NotNull DataReader reader, @javax.validation.constraints.NotNull URL dataUrl, @javax.validation.constraints.NotNull URL versionUrl,
		                        @javax.validation.constraints.NotNull Charset charset, DataStore fallback)
		{
			super(reader, dataUrl, versionUrl, charset, fallback);
		}

		@Override
		public void refresh()
		{
			// bypass non-blocking behavior
			getUpdateOperation().call();
		}

	}

}
