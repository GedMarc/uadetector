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
package net.sf.uadetector;

import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class UserAgentTest
{

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_deviceCategory_null()
	{
		new UserAgent(null, UserAgentFamily.SAFARI, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_family_null()
	{
		new UserAgent(DeviceCategory.EMPTY, null, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
		              "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_icon_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, null, "name", OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", null, OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_os_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", null, "producer", "producer url",
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producer_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, null, "producer url",
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producerUrl_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", null,
		              UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_type_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
		              null, "type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_typeName_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, null, "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_url_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, "type", null, VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_version_null()
	{
		new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
		              UserAgentType.BROWSER, "type", "url", null);
	}

	@Test
	public void empty()
	{
		UserAgent ua = UserAgent.EMPTY;
		assertThat(ua.getFamily()).isEqualTo(UserAgentFamily.UNKNOWN);
		assertThat(ua.getName()).isEqualTo("unknown");
		assertThat(ua.getProducer()).isEqualTo("");
		assertThat(ua.getProducerUrl()).isEqualTo("");
		assertThat(ua.getUrl()).isEqualTo("");
	}

	@Test
	public void equals_differentFamily()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROME, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROME_MOBILE, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentIcon()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon1", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon2", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentName()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name1", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name2", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentOperatingSystem()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "Gentoo", "icon", "name1", "p", "pUrl", "url", new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "Gentoo", "icon", "name2", "p", "pUrl", "url", new VersionNumber("1"));
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", os1, "producer",
		                              "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", os2, "producer",
		                              "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentProducer()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer1", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer2", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentProducerUrl()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url 1", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url 2", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentType()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.LIBRARY, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.ROBOT, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentTypeName()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type1", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type2", "url", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentUrl()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url1", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url2", VersionParser.parseVersion("1"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentVersionNumber()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("2"));
		assertThat(ua1.equals(ua2)).isFalse();
		assertThat(ua1.hashCode() == ua2.hashCode()).isFalse();
	}

	@Test
	public void equals_EMPTY()
	{
		assertThat(UserAgent.EMPTY).isEqualTo(UserAgent.EMPTY);
		assertThat(UserAgent.EMPTY.hashCode() == UserAgent.EMPTY.hashCode()).isTrue();
		assertThat(UserAgent.EMPTY).isSameAs(UserAgent.EMPTY);
	}

	@Test
	public void equals_identical()
	{
		UserAgent ua1 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		UserAgent ua2 = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                              "producer", "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua2).isEqualTo(ua1);
		assertThat(ua1.hashCode() == ua2.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		assertThat(UserAgent.EMPTY.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		UserAgent ua = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY,
		                             "producer", "producer url", UserAgentType.MOBILE_BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua.equals(OperatingSystem.EMPTY)).isFalse();
	}

	@Test
	public void testGetters()
	{
		DeviceCategory category = new DeviceCategory(Category.SMARTPHONE, "", "", "My Nifty Phone");
		UserAgent ua = new UserAgent(category, UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer",
		                             "producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		assertThat(ua.getDeviceCategory()).isEqualTo(category);
		assertThat(ua.getFamily()).isEqualTo(UserAgentFamily.CHROMIUM);
		assertThat(ua.getIcon()).isEqualTo("icon");
		assertThat(ua.getName()).isEqualTo("name");
		assertThat(ua.getOperatingSystem()).isEqualTo(OperatingSystem.EMPTY);
		assertThat(ua.getProducer()).isEqualTo("producer");
		assertThat(ua.getProducerUrl()).isEqualTo("producer url");
		assertThat(ua.getTypeName()).isEqualTo("type");
		assertThat(ua.getUrl()).isEqualTo("url");
		assertThat(ua.getVersionNumber()
		             .toVersionString()).isEqualTo("1");
	}

	@Test
	public void testHashCode()
	{
		UserAgent ua1 = new UserAgent(
				//
				new DeviceCategory(Category.SMARTPHONE, "", "", "My Nifty Phone"),

				UserAgentFamily.CHROMIUM,

				"icon",

				"name",

				new OperatingSystem(OperatingSystemFamily.LINUX, OperatingSystemFamily.LINUX.getName(), "", "My Linux", "", "", "",
				                    VersionNumber.parseVersion("test")),

				"producer",

				"producer url",

				UserAgentType.BROWSER,

				"type",

				"url",

				VersionParser.parseVersion("1.0"));

		UserAgent ua2 = new UserAgent(
				//
				new DeviceCategory(Category.SMARTPHONE, "", "", "My Nifty Phone"),

				UserAgentFamily.CHROMIUM,

				"icon",

				"name",

				new OperatingSystem(OperatingSystemFamily.LINUX, OperatingSystemFamily.LINUX.getName(), "", "My Linux", "", "", "",
				                    VersionNumber.parseVersion("test")),

				"producer",

				"producer url",

				UserAgentType.BROWSER,

				"type",

				"url",

				VersionParser.parseVersion("1.0"));

		assertThat(ua2.hashCode()).isEqualTo(ua1.hashCode());
	}

	@Test
	public void testHashCode_EMPTY()
	{
		assertThat(UserAgent.EMPTY.hashCode()).isEqualTo(UserAgent.EMPTY.hashCode());
	}

	@Test
	public void testToString()
	{
		// reduces only some noise in coverage report
		UserAgent ua = new UserAgent(DeviceCategory.EMPTY, UserAgentFamily.SAFARI, "i1", "n1", OperatingSystem.EMPTY, "p1", "pu1",
		                             UserAgentType.USERAGENT_ANONYMIZER, "t1", "u1", VersionParser.parseVersion("1"));
		assertThat(ua.toString())
				.isEqualTo(
						"UserAgent [deviceCategory="
						+ DeviceCategory.EMPTY.toString()
						+ ", family=SAFARI, icon=i1, name=n1, operatingSystem="
						+ OperatingSystem.EMPTY.toString()
						+ ", producer=p1, producerUrl=pu1, type=USERAGENT_ANONYMIZER, typeName=t1, url=u1, versionNumber=VersionNumber [groups=[1, , ], extension=]]");
	}
}
