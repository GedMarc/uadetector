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

import com.google.common.base.Charsets;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.util.UrlUtil;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

public final class XmlDataWriterTest
{

	@Test
	public void test() throws Exception
	{
		URL resource = getClass().getClassLoader()
		                         .getResource("uas_older.xml");
		String expected = formatSimilar(UrlUtil.read(resource, Charsets.UTF_8)).replaceAll("/si </regstring>", "/si</regstring>");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XmlDataWriter.write(new TestXmlDataStore().getData(), outputStream);
		String actual = new String(outputStream.toByteArray(), "UTF-8");

		//		assertThat(formatSimilar(actual)).isEqualTo(expected);
	}

	public String formatSimilar(String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		return format(removeIndentation(xml));
	}

	public static String format(String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		StringWriter stringWriter = new StringWriter();
		Document document = XmlDataWriter.newDocumentBuilder()
		                                 .parse(new InputSource(new StringReader(xml)));
		StreamResult xmlOutput = new StreamResult(stringWriter);
		XmlDataWriter.transform(new DOMSource(document.getDocumentElement()), xmlOutput);
		return xmlOutput.getWriter()
		                .toString();
	}

	private static String removeIndentation(@javax.validation.constraints.NotNull String xml)
	{
		return xml.replaceAll("(?m)^\\s+", "");
	}

}
