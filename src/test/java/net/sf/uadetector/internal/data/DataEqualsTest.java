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

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.*;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class DataEqualsTest
{

	@Test
	public void equals_different_browserPatterns()
	{
		Browser browser = createBrowser(1);
		BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		BrowserPattern pattern2 = new BrowserPattern(2, Pattern.compile("[0-9]+"), 1);
		BrowserPattern pattern3 = new BrowserPattern(3, Pattern.compile("[0-9]+"), 3);
		BrowserPattern pattern4 = new BrowserPattern(3, Pattern.compile("[0-9]+"), 3);
		TreeMap<BrowserPattern, Browser> patterns1 = new TreeMap<>();
		patterns1.put(pattern1, browser);
		patterns1.put(pattern2, browser);
		TreeMap<BrowserPattern, Browser> patterns2 = new TreeMap<>();
		patterns2.put(pattern1, browser);
		TreeMap<BrowserPattern, Browser> patterns3 = new TreeMap<>();
		patterns3.put(pattern3, browser);
		TreeMap<BrowserPattern, Browser> patterns4 = new TreeMap<>();
		patterns4.put(pattern4, browser);

		Data data1 = new DataBlueprint().patternToBrowserMap(patterns1)
		                                .build();
		Data data2 = new DataBlueprint().patternToBrowserMap(patterns2)
		                                .build();
		Data data3 = new DataBlueprint().patternToBrowserMap(patterns3)
		                                .build();
		Data data4 = new DataBlueprint().patternToBrowserMap(patterns4)
		                                .build();

		assertThat(data1.equals(data2)).isFalse();
		assertThat(data1.hashCode() == data2.hashCode()).isFalse();
		assertThat(data2.equals(data1)).isFalse();

		assertThat(data1.equals(data3)).isFalse();
		assertThat(data1.hashCode() == data3.hashCode()).isFalse();
		assertThat(data3.equals(data1)).isFalse();

		assertThat(data3.equals(data4)).isTrue();
		assertThat(data3.hashCode() == data4.hashCode()).isTrue();
		assertThat(data4.equals(data3)).isTrue();

		assertThat(data2.equals(data3)).isFalse();
		assertThat(data2.hashCode() == data3.hashCode()).isFalse();

		assertThat(data1.equals(data4)).isFalse();
		assertThat(data1.hashCode() == data4.hashCode()).isFalse();
	}

	private static Browser createBrowser(int id)
	{
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		BrowserType browserType = new BrowserType(1, "Browser");
		return new Browser(id, UserAgentFamily.FIREBIRD, UserAgentFamily.FIREBIRD.getName(), new TreeSet<>(), browserType,
		                   os, "icn", "iu1", "p1", "pu1", "u1");
	}

	@Test
	public void equals_different_browsers()
	{
		Browser browser1 = createBrowser(1);
		Browser browser2 = createBrowser(2);
		Browser browser3 = createBrowser(3);
		Set<Browser> browsers1 = new HashSet<>();
		browsers1.add(browser1);
		browsers1.add(browser2);
		Set<Browser> browsers2 = new HashSet<>();
		browsers2.add(browser3);
		browsers2.add(browser2);
		browsers2.add(browser1);
		Data data1 = new DataBlueprint().browsers(browsers1)
		                                .build();
		Data data2 = new DataBlueprint().browsers(browsers2)
		                                .build();
		assertThat(data1.equals(data2)).isFalse();
	}

	@Test
	public void equals_different_operatingSystemPatterns()
	{
		OperatingSystem operatingSystem = createOperatingSystem(1);
		OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		OperatingSystemPattern pattern2 = new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 1);
		OperatingSystemPattern pattern3 = new OperatingSystemPattern(3, Pattern.compile("[0-9]+"), 3);
		OperatingSystemPattern pattern4 = new OperatingSystemPattern(3, Pattern.compile("[0-9]+"), 3);
		TreeMap<OperatingSystemPattern, OperatingSystem> patterns1 = new TreeMap<>();
		patterns1.put(pattern1, operatingSystem);
		patterns1.put(pattern2, operatingSystem);
		TreeMap<OperatingSystemPattern, OperatingSystem> patterns2 = new TreeMap<>();
		patterns2.put(pattern1, operatingSystem);
		TreeMap<OperatingSystemPattern, OperatingSystem> patterns3 = new TreeMap<>();
		patterns3.put(pattern3, operatingSystem);
		TreeMap<OperatingSystemPattern, OperatingSystem> patterns4 = new TreeMap<>();
		patterns4.put(pattern4, operatingSystem);

		Data data1 = new DataBlueprint().patternToOperatingSystemMap(patterns1)
		                                .build();
		Data data2 = new DataBlueprint().patternToOperatingSystemMap(patterns2)
		                                .build();
		Data data3 = new DataBlueprint().patternToOperatingSystemMap(patterns3)
		                                .build();
		Data data4 = new DataBlueprint().patternToOperatingSystemMap(patterns4)
		                                .build();

		assertThat(data1.equals(data2)).isFalse();
		assertThat(data1.hashCode() == data2.hashCode()).isFalse();
		assertThat(data2.equals(data1)).isFalse();

		assertThat(data1.equals(data3)).isFalse();
		assertThat(data1.hashCode() == data3.hashCode()).isFalse();
		assertThat(data3.equals(data1)).isFalse();

		assertThat(data3.equals(data4)).isTrue();
		assertThat(data3.hashCode() == data4.hashCode()).isTrue();
		assertThat(data4.equals(data3)).isTrue();

		assertThat(data2.equals(data3)).isFalse();
		assertThat(data2.hashCode() == data3.hashCode()).isFalse();

		assertThat(data1.equals(data4)).isFalse();
		assertThat(data1.hashCode() == data4.hashCode()).isFalse();
	}

	private static OperatingSystem createOperatingSystem(int id)
	{
		SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<>();
		return new OperatingSystem(id, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
	}

	@Test
	public void equals_different_operatingSystems()
	{
		OperatingSystem os1 = createOperatingSystem(1);
		OperatingSystem os2 = createOperatingSystem(2);
		OperatingSystem os3 = createOperatingSystem(3);
		Set<OperatingSystem> operatingSystems1 = new HashSet<>();
		operatingSystems1.add(os1);
		operatingSystems1.add(os2);
		Set<OperatingSystem> operatingSystems2 = new HashSet<>();
		operatingSystems2.add(os1);
		operatingSystems2.add(os3);
		Data data1 = new DataBlueprint().operatingSystems(operatingSystems1)
		                                .build();
		Data data2 = new DataBlueprint().operatingSystems(operatingSystems2)
		                                .build();
		assertThat(data1.equals(data2)).isFalse();
		assertThat(data1.hashCode() == data2.hashCode()).isFalse();
	}

	@Test
	public void equals_different_robots()
	{
		Robot robot1 = createRobot(1);
		Robot robot2 = createRobot(2);
		List<Robot> robots1 = new ArrayList<>();
		robots1.add(robot1);
		robots1.add(robot2);
		List<Robot> robots2 = new ArrayList<>();
		robots2.add(robot1);
		Data data1 = new DataBlueprint().robots(robots1)
		                                .build();
		Data data2 = new DataBlueprint().robots(robots2)
		                                .build();
		assertThat(data1.equals(data2)).isFalse();
		assertThat(data1.hashCode() == data2.hashCode()).isFalse();
	}

	private static final Robot createRobot(int id)
	{
		return new Robot(id, "Majestic-12", UserAgentFamily.MJ12BOT, "Majestic-12 bot", "http://majestic12.co.uk/bot.php", "Majestic-12",
		                 "http://www.majestic12.co.uk/", "MJ12bot/v1.4.3", "mj12.png");
	}

	@Test
	public void equals_different_version()
	{
		Data data1 = new DataBlueprint().version("v1")
		                                .build();
		Data data2 = new DataBlueprint().version("v2")
		                                .build();
		assertThat(data1.equals(data2)).isFalse();
		assertThat(data1.hashCode() == data2.hashCode()).isFalse();
	}

	@Test
	public void equals_identical()
	{
		Data data1 = new DataBlueprint().version("identical")
		                                .build();
		Data data2 = new DataBlueprint().version("identical")
		                                .build();
		assertThat(data1.equals(data2)).isTrue();
		assertThat(data1.hashCode() == data2.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		Data data = new DataBlueprint().build();
		assertThat(data.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		Data data = new DataBlueprint().build();
		String otherClass = "";
		assertThat(data.equals(otherClass)).isFalse();
	}

	@Test
	public void equals_same()
	{
		Data data = new DataBlueprint().version("same")
		                               .build();
		assertThat(data.equals(data)).isTrue();
		assertThat(data.hashCode() == data.hashCode()).isTrue();
	}

}
