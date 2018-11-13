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

import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class OperatingSystemTest
{

	@Test
	public void construct_successful()
	{
		int id = 1;
		String icon = "icon";
		String infoUrl = "info url";
		String name = "name";
		String url = "url";
		String family = "family";
		String producerUrl = "producer url";
		String producer = "producer";
		SortedSet<OperatingSystemPattern> patterns = new TreeSet<>();
		new OperatingSystem(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
	}

	@Test
	public void copyTo_successful()
	{
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "Linux", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		UserAgent.Builder builder = new UserAgent.Builder();
		os.copyTo(builder);
		assertThat(builder.getOperatingSystem()
		                  .getFamily()).isEqualTo(OperatingSystemFamily.LINUX);
		assertThat(builder.getOperatingSystem()
		                  .getFamilyName()).isEqualTo(os.getFamily());
		assertThat(builder.getOperatingSystem()
		                  .getName()).isEqualTo(os.getName());
		assertThat(builder.getOperatingSystem()
		                  .getProducer()).isEqualTo(os.getProducer());
		assertThat(builder.getOperatingSystem()
		                  .getProducerUrl()).isEqualTo(os.getProducerUrl());
		assertThat(builder.getOperatingSystem()
		                  .getUrl()).isEqualTo(os.getUrl());
	}

	@Test
	public void equals_different_FAMILY()
	{
		OperatingSystem a = new Blueprint().family("family-1")
		                                   .build();
		OperatingSystem b = new Blueprint().family("family-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ICON()
	{
		OperatingSystem a = new Blueprint().icon("icon-1")
		                                   .build();
		OperatingSystem b = new Blueprint().icon("icon-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ID()
	{
		OperatingSystem a = new Blueprint().id(1234)
		                                   .build();
		OperatingSystem b = new Blueprint().id(9876)
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_INFOURL()
	{
		OperatingSystem a = new Blueprint().infoUrl("info-url-1")
		                                   .build();
		OperatingSystem b = new Blueprint().infoUrl("info-url-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_NAME()
	{
		OperatingSystem a = new Blueprint().name("name-1")
		                                   .build();
		OperatingSystem b = new Blueprint().name("name-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERNS()
	{
		SortedSet<OperatingSystemPattern> patterns1 = new TreeSet<>();
		patterns1.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1));
		patterns1.add(new OperatingSystemPattern(2, Pattern.compile("[a-z]+"), 2));
		OperatingSystem a = new Blueprint().patterns(patterns1)
		                                   .build();

		SortedSet<OperatingSystemPattern> patterns2 = new TreeSet<>();
		patterns2.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1));
		OperatingSystem b = new Blueprint().patterns(patterns2)
		                                   .build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCER()
	{
		OperatingSystem a = new Blueprint().producer("prod-1")
		                                   .build();
		OperatingSystem b = new Blueprint().producer("prod-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCERURL()
	{
		OperatingSystem a = new Blueprint().producerUrl("prod-url-1")
		                                   .build();
		OperatingSystem b = new Blueprint().producerUrl("prod-url-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_URL()
	{
		OperatingSystem a = new Blueprint().url("url-1")
		                                   .build();
		OperatingSystem b = new Blueprint().url("url-2")
		                                   .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical()
	{
		OperatingSystem a = new Blueprint().build();
		OperatingSystem b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		OperatingSystem a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		OperatingSystem a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same()
	{
		OperatingSystem a = new Blueprint().build();
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

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME()
	{
		new Blueprint().name(null)
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
		String name = "Programming, Motherfucker";
		String url = "http://user-agent-string.info/";
		String family = "Learn Code The Hard Way";
		String producerUrl = "https://github.com/before";
		String producer = "Our Values";
		SortedSet<OperatingSystemPattern> patterns = new TreeSet<>();
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("1"), 1));
		OperatingSystem b = new OperatingSystem(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
		assertThat(b.getFamily()).isEqualTo("Learn Code The Hard Way");
		assertThat(b.getIcon()).isEqualTo("bunt.png");
		assertThat(b.getId()).isEqualTo(12354);
		assertThat(b.getInfoUrl()).isEqualTo("http://programming-motherfucker.com/");
		assertThat(b.getName()).isEqualTo("Programming, Motherfucker");
		assertThat(b.getPatterns()).isEqualTo(patterns);
		assertThat(b.getProducer()).isEqualTo("Our Values");
		assertThat(b.getProducerUrl()).isEqualTo("https://github.com/before");
		assertThat(b.getUrl()).isEqualTo("http://user-agent-string.info/");
	}

	@Test
	public void testToString()
	{
		// reduces only some noise in coverage report
		SortedSet<OperatingSystemPattern> patterns = new TreeSet<>();
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("1"), 1));
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", patterns, "p1", "pu1", "u1", "i1");
		assertThat(os.toString())
				.isEqualTo(
						"OperatingSystem [id=1, name=n1, family=f1, infoUrl=iu1, patterns=[OperatingSystemPattern [id=1, pattern=1, position=1]], producer=p1, producerUrl=pu1, url=u1, icon=i1]");
	}

	private static final class Blueprint
	{

		private String family = "test-family";

		private String icon = "test-icon";

		private int id = 34567;

		private String infoUrl = "test-info-url";

		private String name = "test-name";

		private SortedSet<OperatingSystemPattern> patterns;

		private String producer = "test-producer";

		private String producerUrl = "test-producer-url";

		private String url = "test-url";

		public Blueprint()
		{
			// default constructor
			patterns = new TreeSet<>();
			patterns.add(new OperatingSystemPattern(1, Pattern.compile("[0-1]+"), 1));
			patterns.add(new OperatingSystemPattern(1, Pattern.compile("[3-5]+"), 1));
			patterns.add(new OperatingSystemPattern(986, Pattern.compile("456"), 243));
		}

		@javax.validation.constraints.NotNull
		public OperatingSystem build()
		{
			return new OperatingSystem(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
		}

		public Blueprint family(String family)
		{
			this.family = family;
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

		public Blueprint name(String name)
		{
			this.name = name;
			return this;
		}

		public Blueprint patterns(SortedSet<OperatingSystemPattern> patterns)
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

		public Blueprint url(String url)
		{
			this.url = url;
			return this;
		}

	}

}
