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

import net.sf.uadetector.exception.IllegalNegativeArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.BrowserType.Builder;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class BrowserTypeBuilderTest
{

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_toSmall()
	{
		Builder b = new BrowserType.Builder();
		b.setId(-1);
	}

	@Test
	public void setId_byString_valid()
	{
		Builder b = new BrowserType.Builder();
		b.setId("1");
	}

	@Test(expected = NumberFormatException.class)
	public void setId_byString_invalid()
	{
		Builder b = new BrowserType.Builder();
		b.setId("abc");
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_byString_toSmall()
	{
		Builder b = new BrowserType.Builder();
		b.setId("-1");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setId_null()
	{
		Builder b = new BrowserType.Builder();
		b.setId(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setName_null()
	{
		Builder b = new BrowserType.Builder();
		b.setName(null);
	}

	@Test
	public void testSettersAndGetters()
	{
		Builder b = new BrowserType.Builder();
		b.setId(1);
		b.setName("Test");
		BrowserType i = b.build();
		assertThat(i.getId()).isEqualTo(1);
		assertThat(i.getName()).isEqualTo("Test");
	}

}
