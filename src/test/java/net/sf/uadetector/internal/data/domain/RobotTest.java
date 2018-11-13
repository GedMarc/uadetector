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

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class RobotTest
{

	@Test
	public void equals_different_FAMILY()
	{
		Robot a = new Blueprint().family(UserAgentFamily.GOOGLEBOT)
		                         .build();
		Robot b = new Blueprint().family(UserAgentFamily.YAHOO)
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_FAMILYNAME()
	{
		Robot a = new Blueprint().familyName("f1")
		                         .build();
		Robot b = new Blueprint().familyName("f2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ICON()
	{
		Robot a = new Blueprint().icon("icon-name-1")
		                         .build();
		Robot b = new Blueprint().icon("icon-name-2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ID()
	{
		Robot a = new Blueprint().id(123)
		                         .build();
		Robot b = new Blueprint().id(987)
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_INFOURL()
	{
		Robot a = new Blueprint().infoUrl("info-url-1")
		                         .build();
		Robot b = new Blueprint().infoUrl("info-url-2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_NAME()
	{
		Robot a = new Blueprint().name("name-1")
		                         .build();
		Robot b = new Blueprint().name("name-2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCER()
	{
		Robot a = new Blueprint().producer("p1")
		                         .build();
		Robot b = new Blueprint().producer("p2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PRODUCERURL()
	{
		Robot a = new Blueprint().producerUrl("pu1")
		                         .build();
		Robot b = new Blueprint().producerUrl("pu2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_USERAGENTSTRING()
	{
		Robot a = new Blueprint().userAgentString("uas.1")
		                         .build();
		Robot b = new Blueprint().userAgentString("uas.2")
		                         .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical()
	{
		Robot a = new Blueprint().build();
		Robot b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		Robot a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		Robot a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same()
	{
		Robot a = new Blueprint().build();
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

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME()
	{
		new Blueprint().name(null)
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
	public void precondition_USERAGENTSTRING()
	{
		new Blueprint().userAgentString(null)
		               .build();
	}

	@Test
	public void testGetters()
	{
		int id = 12354;
		String icon = "bunt.png";
		String infoUrl = "http://programming-motherfucker.com/";
		String name = "Programming, Motherfucker";
		UserAgentFamily family = UserAgentFamily.GOOGLEBOT;
		String familyName = UserAgentFamily.GOOGLEBOT.getName();
		String producerUrl = "https://github.com/before";
		String producer = "Our Values";
		String userAgentString = "I'm a robot";
		Robot r = new Robot(id, name, family, familyName, infoUrl, producer, producerUrl, userAgentString, icon);

		assertThat(r.getFamily()).isEqualTo(family);
		assertThat(r.getFamilyName()).isEqualTo(familyName);
		assertThat(r.getIcon()).isEqualTo("bunt.png");
		assertThat(r.getId()).isEqualTo(12354);
		assertThat(r.getInfoUrl()).isEqualTo("http://programming-motherfucker.com/");
		assertThat(r.getName()).isEqualTo("Programming, Motherfucker");
		assertThat(r.getProducer()).isEqualTo("Our Values");
		assertThat(r.getProducerUrl()).isEqualTo("https://github.com/before");
		assertThat(r.getUserAgentString()).isEqualTo("I'm a robot");
	}

	@Test
	public void testToString()
	{
		// reduces some noise in coverage report
		int id = 12354;
		String icon = "bunt.png";
		String infoUrl = "http://programming-motherfucker.com/";
		String name = "Programming, Motherfucker";
		UserAgentFamily family = UserAgentFamily.MJ12BOT;
		String familyName = "Majestic-12";
		String producerUrl = "https://github.com/before";
		String producer = "Our Values";
		String userAgentString = "I'm a robot";
		Robot r = new Robot(id, name, family, familyName, infoUrl, producer, producerUrl, userAgentString, icon);
		assertThat(r.toString())
				.isEqualTo(
						"ReadableRobot [id=12354, name=Programming, Motherfucker, family=MJ12BOT, familyName=Majestic-12, infoUrl=http://programming-motherfucker.com/, producer=Our Values, producerUrl=https://github.com/before, userAgentString=I'm a robot, icon=bunt.png]");
	}

	private static final class Blueprint
	{

		private UserAgentFamily family = UserAgentFamily.MJ12BOT;

		private String familyName = "test-family-name";

		private String icon = "test-icon-name";

		private int id = 1234;

		private String infoUrl = "test-info.url";

		private String name = "test-name";

		private String producer = "test-producer";

		private String producerUrl = "test-producer-url";

		private String userAgentString = "test-uas";

		@javax.validation.constraints.NotNull
		public Robot build()
		{
			return new Robot(id, name, family, familyName, infoUrl, producer, producerUrl, userAgentString, icon);
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

		public Blueprint name(String name)
		{
			this.name = name;
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

		public Blueprint userAgentString(String userAgentString)
		{
			this.userAgentString = userAgentString;
			return this;
		}

	}

}
