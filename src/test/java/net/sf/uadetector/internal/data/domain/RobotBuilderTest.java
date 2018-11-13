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
import net.sf.uadetector.exception.IllegalEmptyArgumentException;
import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.Robot.Builder;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class RobotBuilderTest
{

	@Test
	public void construct_withCopy()
	{
		Builder builder = new Robot.Builder();
		builder.setFamilyName(UserAgentFamily.GOOGLEBOT.getName());
		builder.setIcon("i1");
		builder.setId(2138);
		builder.setInfoUrl("iu1");
		builder.setName("n1");
		builder.setProducer("p1");
		builder.setProducerUrl("pu1");
		builder.setUserAgentString("uas1");

		Robot r1 = builder.build();
		Robot r2 = new Robot.Builder(r1).build();
		assertThat(r1.equals(r2)).isTrue();
		assertThat(r1.hashCode() == r2.hashCode()).isTrue();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setFamilyName_null()
	{
		new Robot.Builder().setFamilyName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setIcon_null()
	{
		new Robot.Builder().setIcon(null);
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString()
	{
		new Robot.Builder().setId("abc");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setId_emptyString()
	{
		new Robot.Builder().setId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setId_null()
	{
		new Robot.Builder().setId(null);
	}

	@Test
	public void setId_numericString()
	{
		Robot b1 = new Robot.Builder().setId("1")
		                              .setFamilyName(UserAgentFamily.GOOGLEBOT.getName())
		                              .setIcon("i1")
		                              .setInfoUrl("iu1")
		                              .setName("n1")
		                              .setProducer("p1")
		                              .setProducerUrl("pu1")
		                              .setUserAgentString("uas1")
		                              .build();
		Robot b2 = new Robot.Builder().setId(1)
		                              .setFamilyName(UserAgentFamily.GOOGLEBOT.getName())
		                              .setIcon("i1")
		                              .setInfoUrl("iu1")
		                              .setName("n1")
		                              .setProducer("p1")
		                              .setProducerUrl("pu1")
		                              .setUserAgentString("uas1")
		                              .build();
		assertThat(b1.equals(b2)).isTrue();
		assertThat(b1.hashCode() == b2.hashCode()).isTrue();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_toSmall()
	{
		new Robot.Builder().setId(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setInfoUrl_null()
	{
		new Robot.Builder().setInfoUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setName_null()
	{
		new Robot.Builder().setName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducer_null()
	{
		new Robot.Builder().setProducer(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducerUrl_null()
	{
		new Robot.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUserAgentString_null()
	{
		new Robot.Builder().setUserAgentString(null);
	}

}
