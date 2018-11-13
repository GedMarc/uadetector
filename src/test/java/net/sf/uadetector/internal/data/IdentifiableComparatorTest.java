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

import net.sf.uadetector.internal.data.domain.BrowserPattern;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class IdentifiableComparatorTest
{

	@Test
	public void compare_differentIds()
	{
		BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+", Pattern.MULTILINE), 1);
		BrowserPattern pattern2 = new BrowserPattern(2, Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE), 1);
		assertThat(IdentifiableComparator.INSTANCE.compare(pattern1, pattern2)).isEqualTo(-1);
		assertThat(IdentifiableComparator.INSTANCE.compare(pattern2, pattern1)).isEqualTo(1);
	}

	@Test
	public void compare_identical()
	{
		BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		BrowserPattern pattern2 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(IdentifiableComparator.INSTANCE.compare(pattern1, pattern2)).isEqualTo(0);
	}

	@Test
	public void compare_null_1()
	{
		IdentifiableComparator.INSTANCE.compare(null, null);
	}

	@Test
	public void compare_null_2()
	{
		BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		IdentifiableComparator.INSTANCE.compare(null, pattern);
	}

	@Test
	public void compare_null_3()
	{
		BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		IdentifiableComparator.INSTANCE.compare(pattern, null);
	}

	@Test
	public void compare_same()
	{
		BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(IdentifiableComparator.INSTANCE.compare(pattern, pattern)).isEqualTo(0);
	}

}
