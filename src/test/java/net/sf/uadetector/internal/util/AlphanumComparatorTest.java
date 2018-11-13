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
package net.sf.uadetector.internal.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.*;

public class AlphanumComparatorTest
{

	@Test
	public void compare_0_0()
	{
		String charA = "0";
		String charB = "0";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isEqualTo(0);
	}

	@Test
	public void compare_0_0000()
	{
		String charA = "0";
		String charB = "0000";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isLessThan(0);
		assertThat(new NaturalOrderComparator().compare(charB, charA)).isGreaterThan(0);
	}

	@Test
	public void compare_00001_00001()
	{
		String charA = "00001";
		String charB = "00001";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isEqualTo(0);
	}

	@Test
	public void compare_02_2()
	{
		String charA = "02";
		String charB = "2";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isGreaterThan(0);
		assertThat(new NaturalOrderComparator().compare(charB, charA)).isLessThan(0);
	}

	@Test
	public void compare_02_20()
	{
		String charA = "02";
		String charB = "20";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isLessThan(0);
		assertThat(new NaturalOrderComparator().compare(charB, charA)).isGreaterThan(0);
	}

	@Test
	public void compare_0A_0000A()
	{
		String charA = "0A";
		String charB = "0000A";
		assertThat(new NaturalOrderComparator().compare(charA, charB)).isLessThan(0);
		assertThat(new NaturalOrderComparator().compare(charB, charA)).isGreaterThan(0);
	}

	@Test
	public void compare_differentFlags()
	{
		String version1 = "0";
		String version2 = "00";
		assertThat(new NaturalOrderComparator().compare(version1, version2)).isLessThan(0);
		assertThat(new NaturalOrderComparator().compare(version2, version1)).isGreaterThan(0);
	}

	@Test
	public void compare_identical()
	{
		String version1 = "000-12";
		String version2 = "000-12";
		assertThat(new NaturalOrderComparator().compare(version1, version2)).isEqualTo(0);
	}

	@Test
	public void compare_list()
	{
		List<String> src = new ArrayList<>(18);
		src.add("0");
		src.add("1");
		src.add("00");
		src.add("01");
		src.add("02");
		src.add("10");
		src.add("11");
		src.add("12");
		src.add("000");
		src.add("001");
		src.add("002");
		src.add("100");
		src.add("101");
		src.add("102");
		src.add("103");
		src.add("104");
		src.add("0002");
		src.add("1002");
		src.add("1056");

		List<String> scrambled = Arrays.asList(src.toArray(new String[]{}));
		Collections.copy(scrambled, src);
		Collections.shuffle(scrambled);
		Collections.sort(scrambled, new NaturalOrderComparator());

		assertThat(scrambled).isEqualTo(src);
	}

	@Test
	public void compare_null_1()
	{
		new NaturalOrderComparator().compare(null, null);
	}

	@Test
	public void compare_null_2()
	{
		String version = "1";
		new NaturalOrderComparator().compare(null, version);
	}

	@Test
	public void compare_null_3()
	{
		String version = "2";
		new NaturalOrderComparator().compare(version, null);
	}

	@Test
	public void compare_same()
	{
		String version = "4";
		assertThat(new NaturalOrderComparator().compare(version, version)).isEqualTo(0);
	}


}
