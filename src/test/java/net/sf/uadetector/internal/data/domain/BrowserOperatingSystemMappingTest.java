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
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class BrowserOperatingSystemMappingTest
{

	@Test(expected = IllegalNegativeArgumentException.class)
	public void construct_browserId_toSmall()
	{
		int browserId = -1;
		int operatingSystemId = 1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void construct_operatingSystemId_toSmall()
	{
		int browserId = 1;
		int operatingSystemId = -1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test
	public void construct_successful()
	{
		int browserId = 1;
		int operatingSystemId = 1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test
	public void equals_differentBrowserId()
	{
		BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(2, 1);
		assertThat(m1.hashCode() == m2.hashCode()).isFalse();
		assertThat(m1.equals(m2)).isFalse();
	}

	@Test
	public void equals_differentOperatingSystemId()
	{
		BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(1, 2);
		assertThat(m1.hashCode() == m2.hashCode()).isFalse();
		assertThat(m1.equals(m2)).isFalse();
	}

	@Test
	public void equals_identical()
	{
		BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(1, 1);
		assertThat(m1.hashCode() == m2.hashCode()).isTrue();
		assertThat(m1.equals(m2)).isTrue();
	}

	@Test
	public void equals_null()
	{
		BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(1, 1);
		assertThat(m.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(1, 1);
		String otherClass = "";
		assertThat(m.equals(otherClass)).isFalse();
	}

	@Test
	public void equals_same()
	{
		BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(12345, 98765);
		assertThat(m.equals(m)).isTrue();
		assertThat(m.hashCode() == m.hashCode()).isTrue();
	}

	@Test
	public void testGetters()
	{
		int browserId = 12345;
		int operatingSystemId = 98765;
		BrowserOperatingSystemMapping b = new BrowserOperatingSystemMapping(browserId, operatingSystemId);
		assertThat(b.getBrowserId()).isEqualTo(12345);
		assertThat(b.getOperatingSystemId()).isEqualTo(98765);
	}

	@Test
	public void testToString()
	{
		// reduces only some noise in coverage report
		BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(12345, 98765);
		assertThat(m.toString()).isEqualTo("BrowserOperatingSystemMapping [browserId=12345, operatingSystemId=98765]");
	}

}
