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

import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class OperatingSystemTest
{

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_family_null()
	{
		new OperatingSystem(null, "family", "icon", "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_familyName_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, null, "icon", "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_icon_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", null, "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", null, "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producer_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", null, "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producerUrl_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", null, "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_url_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", null, new VersionNumber("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_version_null()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url", null);
	}

	@Test
	public void empty()
	{
		OperatingSystem os = OperatingSystem.EMPTY;
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.UNKNOWN);
		assertThat(os.getFamilyName()).isEqualTo("unknown");
		assertThat(os.getIcon()).isEqualTo("unknown.png");
		assertThat(os.getName()).isEqualTo("unknown");
		assertThat(os.getProducer()).isEqualTo("");
		assertThat(os.getProducerUrl()).isEqualTo("");
		assertThat(os.getUrl()).isEqualTo("");
	}

	@Test
	public void equals_differentFamily()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystemFamily aix = OperatingSystemFamily.AIX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(aix, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentFamilyName()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family1", "icon", "name", "prod", "prodUrl", "url", new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family2", "icon", "name", "prod", "prodUrl", "url", new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentIcon()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon1", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon2", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentName()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name1", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name2", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentProducer()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer1", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer2", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentProducerUrl()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url 1", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url 2", "url",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentUrl()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url1",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url2",
		                                          new VersionNumber("1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentVersionNumber()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1", "0"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1", "1"));
		assertThat(os1.equals(os2)).isFalse();
		assertThat(os1.hashCode() == os2.hashCode()).isFalse();
	}

	@Test
	public void equals_EMPTY()
	{
		assertThat(OperatingSystem.EMPTY).isEqualTo(OperatingSystem.EMPTY);
		assertThat(OperatingSystem.EMPTY).isSameAs(OperatingSystem.EMPTY);
	}

	@Test
	public void equals_identical()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os2).isEqualTo(os1);
	}

	@Test
	public void equals_null()
	{
		assertThat(OperatingSystem.EMPTY.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                         new VersionNumber("1"));
		assertThat(os.equals(123456)).isFalse();
	}

	@Test
	public void testGetters()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                         new VersionNumber("1"));
		assertThat(os.getFamily()).isEqualTo(linux);
		assertThat(os.getFamilyName()).isEqualTo("family");
		assertThat(os.getIcon()).isEqualTo("icon");
		assertThat(os.getName()).isEqualTo("name");
		assertThat(os.getProducer()).isEqualTo("producer");
		assertThat(os.getProducerUrl()).isEqualTo("producer url");
		assertThat(os.getUrl()).isEqualTo("url");
	}

	@Test
	public void testHashCode()
	{
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
		                                          new VersionNumber("1"));
		assertThat(os2.hashCode()).isEqualTo(os1.hashCode());
	}

	@Test
	public void testHashCode_EMPTY()
	{
		assertThat(OperatingSystem.EMPTY.hashCode()).isEqualTo(OperatingSystem.EMPTY.hashCode());
	}

	@Test
	public void testToString()
	{
		// reduces only some noise in coverage report
		OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		OperatingSystem ua = new OperatingSystem(linux, "f1", "i1", "n1", "p1", "pu1", "u1", new VersionNumber("1", "0", "0",
		                                                                                                       "-stable"));
		assertThat(ua.toString())
				.isEqualTo(
						"OperatingSystem [family=LINUX, familyName=f1, icon=i1, name=n1, producer=p1, producerUrl=pu1, url=u1, versionNumber=VersionNumber [groups=[1, 0, 0], extension=-stable]]");
	}

}
