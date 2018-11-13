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
package net.sf.uadetector.internal.data;

import org.junit.Test;

import java.io.*;
import java.net.URL;

import static org.fest.assertions.Assertions.*;

public class ActualityOfDtdTest
{

	@Test
	public void checkDtdActuality() throws IOException
	{
		String currentDefinition = XmlDataHandler.UASDATA_DEF;
		URL definitionUrl = new URL(XmlDataHandler.UASDATA_DEF_URL);

		// read DTD online
		InputStream onlineStream = definitionUrl.openStream();
		InputStreamReader onlineReader = new InputStreamReader(onlineStream);
		String onlineDtd = read(onlineReader);
		onlineReader.close();
		onlineStream.close();

		// read DTD local
		InputStreamReader localReader = new InputStreamReader(this.getClass()
		                                                          .getClassLoader()
		                                                          .getResourceAsStream(currentDefinition));
		String localDtd = read(localReader);
		localReader.close();

		assertThat(localDtd).isEqualTo(onlineDtd);
	}

	private String read(Reader reader) throws IOException
	{
		StringBuilder buffer = new StringBuilder();
		String nextLine = null;
		BufferedReader bufferedReader = new BufferedReader(reader);
		do
		{
			nextLine = bufferedReader.readLine();
			if (nextLine != null)
			{
				buffer.append(nextLine);
			}
		}
		while (nextLine != null);
		bufferedReader.close();
		return buffer.toString();
	}

}
