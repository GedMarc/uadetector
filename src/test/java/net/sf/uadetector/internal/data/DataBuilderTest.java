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

import net.sf.uadetector.ReadableDeviceCategory;
import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import net.sf.uadetector.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.data.domain.*;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class DataBuilderTest
{

	@Test
	public void appendBrowser()
	{
		DataBuilder b = new DataBuilder();
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		BrowserType browserType = new BrowserType(1, "Browser");
		Browser browser = new Browser(4256, UserAgentFamily.FIREBIRD, UserAgentFamily.FIREBIRD.getName(),
		                              new TreeSet<>(), browserType, os, "icn", "iu1", "p1", "pu1", "u1");
		assertThat(b.appendBrowser(browser)).isSameAs(b);
		assertThat(b.appendBrowser(browser)).isSameAs(b); // testing to add same, one more time
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowser_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowser(null);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendBrowserBuilder_addSameOneMoreTime()
	{
		DataBuilder b = new DataBuilder();
		Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		builder.setType(new BrowserType(1, "Browser"));
		assertThat(b.appendBrowserBuilder(builder)).isSameAs(b);
		assertThat(b.appendBrowserBuilder(builder)).isSameAs(b); // testing to add same one more time
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void appendBrowserBuilder_id_toSmall()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowserBuilder(new Browser.Builder());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserBuilder_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowserBuilder(null);
	}

	@Test
	public void appendBrowserBuilder_successful_testCopyFunction()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		builder.setType(new BrowserType(1, "Browser"));
		assertThat(d.appendBrowserBuilder(builder)).isSameAs(d);
		builder.setId(2);
		builder.setFamilyName(UserAgentFamily.CHROME.getName());
		builder.setType(new BrowserType(1, "Browser"));
		assertThat(d.appendBrowserBuilder(builder)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getBrowsers()).hasSize(2);
	}

	@Test
	public void appendBrowserBuilder_successful_withoutPattern()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		Browser.Builder b1 = new Browser.Builder();
		b1.setId(1);
		b1.setFamilyName(UserAgentFamily.FIREFOX.getName());
		b1.setType(new BrowserType(1, "Browser"));
		assertThat(d.appendBrowserBuilder(b1)).isSameAs(d);
		Browser.Builder b2 = new Browser.Builder();
		b2.setId(2);
		b2.setFamilyName(UserAgentFamily.CHROME.getName());
		b2.setType(new BrowserType(1, "Browser"));
		assertThat(d.appendBrowserBuilder(b2)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getBrowsers()).hasSize(2);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendBrowserBuilder_withoutType()
	{
		DataBuilder b = new DataBuilder();
		Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		b.appendBrowserBuilder(builder);
	}

	@Test
	public void appendBrowserBuilder_withTypeId()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		BrowserType type = new BrowserType(2, "Email client");
		d.appendBrowserType(type);
		Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		builder.setTypeId(2);
		assertThat(d.appendBrowserBuilder(builder)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getBrowsers()).hasSize(1);
		assertThat(data.getBrowsers()
		               .iterator()
		               .next()
		               .getType()).isEqualTo(type);
	}

	@Test
	public void appendBrowserBuilder_withUnknownTypeId()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		builder.setTypeId(1); // type does not exist, a log message occur
		assertThat(d.appendBrowserBuilder(builder)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getBrowsers()).isEmpty();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserOperatingSystemMapping_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowserOperatingSystemMapping(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserPattern_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowserPattern(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserType()
	{
		DataBuilder b = new DataBuilder();
		b.appendBrowserType(null);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendDeviceBuilder_addSameOneMoreTime()
	{
		DataBuilder b = new DataBuilder();
		Device.Builder builder = new Device.Builder();
		builder.setId(1);
		builder.setName(ReadableDeviceCategory.Category.TABLET.getName());
		assertThat(b.appendDeviceBuilder(builder)).isSameAs(b);
		assertThat(b.appendDeviceBuilder(builder)).isSameAs(b); // testing to add same one more time
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void appendDeviceBuilder_id_toSmall()
	{
		DataBuilder b = new DataBuilder();
		b.appendDeviceBuilder(new Device.Builder());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendDeviceBuilder_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendDeviceBuilder(null);
	}

	@Test
	public void appendDeviceBuilder_successful_testCopyFunction()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		Device.Builder builder = new Device.Builder();
		builder.setId(1);
		builder.setName(Category.TABLET.getName());
		assertThat(d.appendDeviceBuilder(builder)).isSameAs(d);
		builder.setId(2);
		builder.setName(ReadableDeviceCategory.Category.SMARTPHONE.getName());
		assertThat(d.appendDeviceBuilder(builder)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getDevices()).hasSize(2);
	}

	@Test
	public void appendDeviceBuilder_successful_withoutPattern()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		Device.Builder b1 = new Device.Builder();
		b1.setId(1);
		b1.setName(ReadableDeviceCategory.Category.TABLET.getName());
		assertThat(d.appendDeviceBuilder(b1)).isSameAs(d);
		Device.Builder b2 = new Device.Builder();
		b2.setId(2);
		b2.setName(ReadableDeviceCategory.Category.SMARTPHONE.getName());
		assertThat(d.appendDeviceBuilder(b2)).isSameAs(d);
		Data data = d.build();
		assertThat(data.getDevices()).hasSize(2);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystem_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendOperatingSystem(null);
	}

	@Test
	public void appendOperatingSystem_successful()
	{
		SortedSet<OperatingSystemPattern> patterns = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", patterns, "p1", "pu1", "u1", "i1");
		DataBuilder b = new DataBuilder();
		assertThat(b.appendOperatingSystem(os)).isSameAs(b);
		b.appendOperatingSystem(os); // testing to add same one more time
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendOperatingSystemBuilder_addSameOneMoreTime()
	{
		OperatingSystem.Builder builder = new OperatingSystem.Builder().setId("1")
		                                                               .setFamily("f1")
		                                                               .setIcon("i1")
		                                                               .setInfoUrl("iu1")
		                                                               .setProducer("p1")
		                                                               .setProducerUrl("pu1")
		                                                               .setUrl("u1");
		DataBuilder b = new DataBuilder();
		assertThat(b.appendOperatingSystemBuilder(builder)).isSameAs(b);
		b.appendOperatingSystemBuilder(builder); // testing to add same one more time fails
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void appendOperatingSystemBuilder_id_toSmall()
	{
		DataBuilder b = new DataBuilder();
		b.appendOperatingSystemBuilder(new OperatingSystem.Builder());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystemBuilder_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendOperatingSystemBuilder(null);
	}

	@Test
	public void appendOperatingSystemBuilder_onlyWithIdAndPattern()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		d.appendOperatingSystemPattern(new OperatingSystemPattern(101, Pattern.compile("[0-9]"), 1));

		OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setId(101);
		d.appendOperatingSystemBuilder(builder);

		Data data = d.build();
		assertThat(data.getOperatingSystems()).hasSize(1);
		OperatingSystem os = data.getOperatingSystems()
		                         .iterator()
		                         .next();
		assertThat(os.getName()).isEqualTo("");
	}

	@Test
	public void appendOperatingSystemBuilder_withoutPatternSet()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");

		// no matching pattern for the following OS, a log message occur
		d.appendOperatingSystemPattern(new OperatingSystemPattern(200, Pattern.compile("[0-9]"), 1));

		OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setFamily("f1");
		builder.setIcon("i1");
		builder.setId(100);
		builder.setInfoUrl("iu1");
		builder.setName("n1");
		builder.setProducer("p1");
		builder.setProducerUrl("pu1");
		builder.setUrl("u1");
		d.appendOperatingSystemBuilder(builder);

		Data data = d.build();
		assertThat(data.getOperatingSystems()).hasSize(1);
	}

	@Test
	public void appendOperatingSystemPattern_addIdenticalPatternMoreTimes()
	{
		DataBuilder b = new DataBuilder();
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystemPattern_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendOperatingSystemPattern(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendRobot_null()
	{
		DataBuilder b = new DataBuilder();
		b.appendRobot(null);
	}

	@Test
	public void appendRobot_successful()
	{
		Robot robot = new Robot(12, "Majestic-12", UserAgentFamily.MJ12BOT, "Majestic-12 bot", "http://majestic12.co.uk/bot.php",
		                        "Majestic-12", "http://www.majestic12.co.uk/", "MJ12bot/v1.4.3", "mj12.png");
		DataBuilder b = new DataBuilder();
		assertThat(b.appendRobot(robot)).isSameAs(b);

		// test to add same robot one more time
		b.appendRobot(robot);
	}

	@Test
	public void build_successful_onlyWithVersionInfo()
	{
		new DataBuilder().setVersion("empty test version")
		                 .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void build_withoutData()
	{
		new DataBuilder().build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setVersion_null()
	{
		DataBuilder b = new DataBuilder();
		b.setVersion(null);
	}

	@Test
	public void testOperatingSystemToBrowserMapping_withMatchingEntries()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");
		d.appendOperatingSystemPattern(new OperatingSystemPattern(303, Pattern.compile("[0-9]"), 1));

		// browser builder entry
		Browser.Builder browserBuilder = new Browser.Builder();
		browserBuilder.setId(1);
		browserBuilder.setFamilyName(UserAgentFamily.FIREFOX.getName());
		browserBuilder.setType(new BrowserType(1, "Browser"));
		d.appendBrowserBuilder(browserBuilder);
		browserBuilder.setId(2);
		browserBuilder.setFamilyName(UserAgentFamily.CHROME.getName());
		browserBuilder.setType(new BrowserType(1, "Browser"));
		d.appendBrowserBuilder(browserBuilder);

		// operating system entry
		OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setId(303);
		builder.setName("MyOS");
		d.appendOperatingSystemBuilder(builder);

		// add mapping
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(1, 303));
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(2, 909));

		Data data = d.build();
		assertThat(data.getBrowsers()).hasSize(2);
		assertThat(data.getOperatingSystems()).hasSize(1);
		OperatingSystem os = data.getOperatingSystems()
		                         .iterator()
		                         .next();
		assertThat(os.getName()).isEqualTo("MyOS");
	}

	@Test
	public void testOperatingSystemToBrowserMapping_withoutMatchingEntries()
	{
		DataBuilder d = new DataBuilder().setVersion("test version");

		// add mapping
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(909, 303));

		Data data = d.build();
		assertThat(data).isNotNull();
		assertThat(data.getOperatingSystems()).isEmpty();
	}

}
