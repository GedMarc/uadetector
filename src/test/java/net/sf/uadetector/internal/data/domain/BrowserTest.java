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
package net.sf.uadetector.internal.data.domain;

import com.google.common.collect.Sets;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgent.Builder;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class BrowserTest
{

	@Test
	public void copyTo_withOperatingSystem()
	{
		SortedSet<OperatingSystemPattern> patternSet = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", patternSet, "p1", "pu1", "u1", "i1");
		Browser b = new Blueprint().operatingSystem(os)
		                           .build();
		Builder builder = new UserAgent.Builder();
		b.copyTo(builder);
		assertThat(builder.getFamily()).isEqualTo(b.getFamily());
		assertThat(builder.getProducer()).isEqualTo(b.getProducer());
		assertThat(builder.getProducerUrl()).isEqualTo(b.getProducerUrl());
		assertThat(builder.getTypeName()).isEqualTo(b.getType()
		                                             .getName());
		assertThat(builder.getUrl()).isEqualTo(b.getUrl());
		assertThat(builder.getOperatingSystem()).isNotNull();
	}

	@Test
	public void copyTo_withoutOperatingSystem()
	{
		Browser b = new Blueprint().operatingSystem(null)
		                           .build();
		Builder builder = new UserAgent.Builder();
		b.copyTo(builder);
		assertThat(builder.getFamily()).isEqualTo(b.getFamily());
		assertThat(builder.getProducer()).isEqualTo(b.getProducer());
		assertThat(builder.getProducerUrl()).isEqualTo(b.getProducerUrl());
		assertThat(builder.getTypeName()).isEqualTo(b.getType()
		                                             .getName());
		assertThat(builder.getUrl()).isEqualTo(b.getUrl());
		assertThat(builder.getOperatingSystem()).isSameAs(net.sf.uadetector.OperatingSystem.EMPTY);
	}

	@Test
	public void equals_different_FAMILY()
	{
		Browser a = new Blueprint().family(UserAgentFamily.FIREBIRD)
		                           .build();
		Browser b = new Blueprint().family(UserAgentFamily.FIREFOX)
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_FAMILYNAME()
	{
		Browser a = new Blueprint().familyName("name1")
		                           .build();
		Browser b = new Blueprint().familyName("name2")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ICON()
	{
		Browser a = new Blueprint().icon("icon.png")
		                           .build();
		Browser b = new Blueprint().icon("icon.gif")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ID()
	{
		Browser a = new Blueprint().id(725)
		                           .build();
		Browser b = new Blueprint().id(289)
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_INFOURL()
	{
		Browser a = new Blueprint().infoUrl("info-1")
		                           .build();
		Browser b = new Blueprint().infoUrl("info-2")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_OPERATINGSYSTEM()
	{
		OperatingSystem os1 = new OperatingSystem(141, "Gentoo", "Linux", "iu1", new TreeSet<>(),
		                                          "Gentoo project", "http://gentoo.org", "http://gentoo.org", "tux.png");
		Browser a = new Blueprint().operatingSystem(os1)
		                           .build();

		OperatingSystem os2 = new OperatingSystem(734, "SUSE Linux", "Linux", "iu1", new TreeSet<>(),
		                                          "SUSE Linux GmbH", "https://www.suse.com", "https://www.suse.com", "tux.png");
		Browser b = new Blueprint().operatingSystem(os2)
		                           .build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_OPERATINGSYSTEM_canBeNull()
	{
		Browser a = new Blueprint().operatingSystem(null)
		                           .build();

		OperatingSystem os2 = new OperatingSystem(734, "SUSE Linux", "Linux", "iu1", new TreeSet<>(),
		                                          "SUSE Linux GmbH", "https://www.suse.com", "https://www.suse.com", "tux.png");
		Browser b = new Blueprint().operatingSystem(os2)
		                           .build();

		assertThat(a.equals(b)).isFalse();
		assertThat(b.equals(a)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERNS()
	{
		SortedSet<BrowserPattern> patterns1 = Sets.newTreeSet();
		patterns1.add(new BrowserPattern(7654, Pattern.compile("d*[a-f]+"), 46));
		Browser a = new Blueprint().patterns(patterns1)
		                           .build();

		SortedSet<BrowserPattern> patterns2 = Sets.newTreeSet();
		patterns2.add(new BrowserPattern(1932, Pattern.compile("f*[1-4]+"), 733));
		Browser b = new Blueprint().patterns(patterns2)
		                           .build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCER()
	{
		Browser a = new Blueprint().producer("prod-1")
		                           .build();
		Browser b = new Blueprint().producer("prod-2")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCERURL()
	{
		Browser a = new Blueprint().producerUrl("prod-url-1")
		                           .build();
		Browser b = new Blueprint().producerUrl("prod-url-2")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_TYPE()
	{
		Browser a = new Blueprint().type(new BrowserType(1, "Type 1"))
		                           .build();
		Browser b = new Blueprint().type(new BrowserType(2, "Type 2"))
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_URL()
	{
		Browser a = new Blueprint().url("url.net")
		                           .build();
		Browser b = new Blueprint().url("url.org")
		                           .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical()
	{
		Browser a = new Blueprint().build();
		Browser b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		Browser a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		Browser a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same()
	{
		Browser a = new Blueprint().build();
		assertThat(a).isEqualTo(a);
		assertThat(a).isSameAs(a);
		assertThat(a.hashCode() == a.hashCode()).isTrue();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FAMILY()
	{
		new Blueprint().family(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FAMILYNAME()
	{
		new Blueprint().familyName(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ICON()
	{
		new Blueprint().icon(null)
		               .build();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_ID()
	{
		new Blueprint().id(-1)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INFOURL()
	{
		new Blueprint().infoUrl(null)
		               .build();
	}

	@Test
	public void precondition_OPERATINGSYSTEM_canBeNull()
	{
		new Blueprint().operatingSystem(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERNS()
	{
		new Blueprint().patterns(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PRODUCER()
	{
		new Blueprint().producer(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PRODUCERURL()
	{
		new Blueprint().producerUrl(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_TYPE()
	{
		new Blueprint().type(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_URL()
	{
		new Blueprint().url(null)
		               .build();
	}

	@Test
	public void testGetters()
	{
		int id = 12354;
		String icon = "bunt.png";
		String infoUrl = "http://programming-motherfucker.com/";
		String url = "http://user-agent-string.info/";
		UserAgentFamily family = UserAgentFamily.FIREFOX;
		String producerUrl = "https://github.com/before";
		String producer = "Our Values";
		BrowserType type = new BrowserType(1, "Browser");
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		SortedSet<BrowserPattern> patterns = new TreeSet<>();
		Browser b = new Browser(id, family, family.getName(), patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl,
		                        url);
		assertThat(b.getFamily()).isEqualTo(family);
		assertThat(b.getIcon()).isEqualTo("bunt.png");
		assertThat(b.getId()).isEqualTo(12354);
		assertThat(b.getInfoUrl()).isEqualTo("http://programming-motherfucker.com/");
		assertThat(b.getOperatingSystem()).isEqualTo(operatingSystem);
		assertThat(b.getPatterns()).isEqualTo(patterns);
		assertThat(b.getProducer()).isEqualTo("Our Values");
		assertThat(b.getProducerUrl()).isEqualTo("https://github.com/before");
		assertThat(b.getType()).isEqualTo(type);
		assertThat(b.getUrl()).isEqualTo("http://user-agent-string.info/");
	}

	@Test
	public void testToString()
	{
		// reduces only some noise in coverage report
		int id = 12354;
		String icon = "bunt.png";
		String infoUrl = "http://programming-motherfucker.com/";
		String url = "http://user-agent-string.info/";
		UserAgentFamily family = UserAgentFamily.FIREFOX;
		String producerUrl = "https://github.com/before";
		String producer = "Our Values";
		BrowserType type = new BrowserType(1, "Browser");
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		SortedSet<BrowserPattern> patterns = new TreeSet<>();
		Browser b = new Browser(id, family, family.getName(), patterns, type, os, icon, infoUrl, producer, producerUrl, url);

		assertThat(b.toString())
				.isEqualTo(
						"Browser [id=12354, family=FIREFOX, familyName=Firefox, patterns=[], type=BrowserType [id=1, name=Browser], operatingSystem=OperatingSystem [id=1, name=n1, family=f1, infoUrl=iu1, patterns=[], producer=p1, producerUrl=pu1, url=u1, icon=i1], icon=bunt.png, infoUrl=http://programming-motherfucker.com/, producer=Our Values, producerUrl=https://github.com/before, url=http://user-agent-string.info/]");
	}

	private static final class Blueprint
	{

		private UserAgentFamily family = UserAgentFamily.NCSA_MOSAIC;

		private String familyName = "test-family-name";

		private String icon = "test-icon.png";

		private int id = 9876543;

		private String infoUrl = "test-info-url";

		private OperatingSystem operatingSystem;

		private SortedSet<BrowserPattern> patterns = Sets.newTreeSet();

		private String producer = "test-producer";

		private String producerUrl = "test-producer-url";

		private BrowserType type = new BrowserType(9876, "test-type");

		private String url = "test-url";

		public Blueprint()
		{
			patterns.add(new BrowserPattern(9876, Pattern.compile("[a-z]+"), 1));
			patterns.add(new BrowserPattern(7652, Pattern.compile("[0-9]+"), 2));
			SortedSet<OperatingSystemPattern> osPatterns = Sets.newTreeSet();
			osPatterns.add(new OperatingSystemPattern(3265, Pattern.compile(".*"), 1));
			operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatterns, "p1", "pu1", "u1", "i1");
		}

		@javax.validation.constraints.NotNull
		public Browser build()
		{
			return new Browser(id, family, familyName, patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
		}

		public Blueprint family(UserAgentFamily family)
		{
			this.family = family;
			return this;
		}

		public Blueprint familyName(String familyName)
		{
			this.familyName = familyName;
			return this;
		}

		public Blueprint icon(String icon)
		{
			this.icon = icon;
			return this;
		}

		public Blueprint id(int id)
		{
			this.id = id;
			return this;
		}

		public Blueprint infoUrl(String infoUrl)
		{
			this.infoUrl = infoUrl;
			return this;
		}

		public Blueprint operatingSystem(OperatingSystem operatingSystem)
		{
			this.operatingSystem = operatingSystem;
			return this;
		}

		public Blueprint patterns(SortedSet<BrowserPattern> patterns)
		{
			this.patterns = patterns;
			return this;
		}

		public Blueprint producer(String producer)
		{
			this.producer = producer;
			return this;
		}

		public Blueprint producerUrl(String producerUrl)
		{
			this.producerUrl = producerUrl;
			return this;
		}

		public Blueprint type(BrowserType type)
		{
			this.type = type;
			return this;
		}

		public Blueprint url(String url)
		{
			this.url = url;
			return this;
		}

	}

}
