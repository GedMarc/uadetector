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

import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class XmlDataHandlerTest
{

	@Test(expected = IllegalNullArgumentException.class)
	public void constructor_null()
	{
		new XmlDataHandler(null);
	}

	/**
	 * This test reduces only some coverage noise.
	 */
	@Test
	public void logParsingIssue()
	{
		String message = "msg";
		String publicId = "public id";
		String systemId = "system id";
		int lineNumber = 123;
		int columnNumber = 345;
		IllegalArgumentException iae = new IllegalArgumentException();
		SAXParseException e = new SAXParseException(message, publicId, systemId, lineNumber, columnNumber, iae);
		XmlDataHandler.logParsingIssue("Warning", e);
	}

	@Test
	public void resolveEntity_knownUrl() throws IOException, SAXException
	{
		XmlDataHandler handler = new XmlDataHandler(new DataBuilder());
		InputSource input = handler.resolveEntity("publicId is irrelevant", XmlDataHandler.UASDATA_DEF_URL);
		assertThat(input).isNotNull();
	}

	@Test(expected = SAXException.class)
	public void resolveEntity_unknownUrl() throws IOException, SAXException
	{
		XmlDataHandler handler = new XmlDataHandler(new DataBuilder());
		handler.resolveEntity("publicId unknown", "systemId unknown");
	}

}
