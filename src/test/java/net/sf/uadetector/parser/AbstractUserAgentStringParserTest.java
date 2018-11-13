package net.sf.uadetector.parser;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.uadetector.DeviceCategory;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.*;
import net.sf.uadetector.internal.util.RegularExpressionConverter;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.*;

public class AbstractUserAgentStringParserTest
{

	@Test
	public void examineAsBrowser_noMatchingSubGroupToGatherVersionNumber()
	{
		// create data structures
		Set<Browser> browsers = new HashSet<>();
		Map<Integer, SortedSet<BrowserPattern>> browserPatterns = Maps.newHashMap();
		Map<Integer, BrowserType> browserTypes = Maps.newHashMap();
		SortedMap<BrowserPattern, Browser> patternToBrowserMap = new TreeMap<>();
		Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings = Sets.newHashSet();
		Set<OperatingSystem> operatingSystems = new HashSet<>();
		Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = Maps.newHashMap();
		SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap = new TreeMap<>();
		List<Robot> robots = new ArrayList<>();
		Set<Device> devices = new HashSet<>(0);
		Map<Integer, SortedSet<DevicePattern>> devicePatterns = new HashMap<>(0);
		SortedMap<DevicePattern, Device> patternToDeviceMap = new TreeMap<>();
		String version = "test-version";

		// fill structures with data
		TreeSet<BrowserPattern> browserPatternSet = Sets.newTreeSet();
		// a pattern without a subgroup definition to gather the version number
		Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern("/^Eudora?/si ");
		BrowserPattern browserPattern = new BrowserPattern(465, pattern, 439);
		browserPatternSet.add(browserPattern);
		browserPatterns.put(1, browserPatternSet);
		BrowserType browserType = new BrowserType(2, "Browser");
		browserTypes.put(browserType.getId(), browserType);
		Browser browser = new Browser(465, UserAgentFamily.EUDORA, "Eudora", new TreeSet<>(), browserType, null,
		                              "eudora.png", "/list-of-ua/browser-detail?browser=Eudora", "Qualcomm Incorporated.", "http://www.qualcomm.com/",
		                              "http://www.eudora.com/archive.html");
		browsers.add(browser);
		patternToBrowserMap.put(browserPattern, browser);

		// create Data instance
		Data data = new Data(browsers, browserPatterns, browserTypes, patternToBrowserMap, browserToOperatingSystemMappings,
		                     operatingSystems, operatingSystemPatterns, patternToOperatingSystemMap, robots, devices, devicePatterns,
		                     patternToDeviceMap, version);

		UserAgentStringParser parser = new UserAgentStringParserImpl<DataStore>(new DataStore()
		{
			@Override
			public Charset getCharset()
			{
				return DataStore.DEFAULT_CHARSET;
			}

			@Override
			public Data getData()
			{
				return data;
			}

			@Override
			public DataReader getDataReader()
			{
				return null;
			}

			@Override
			public URL getDataUrl()
			{
				return null;
			}

			@Override
			public URL getVersionUrl()
			{
				return null;
			}
		});

		ReadableUserAgent ua1 = parser.parse("Eudora");
		assertThat(ua1.getFamily()).isEqualTo(UserAgentFamily.EUDORA);
		assertThat(ua1.getVersionNumber()
		              .toVersionString()).isEqualTo("");
		assertThat(ua1.getDeviceCategory()).isEqualTo(DeviceCategory.EMPTY);

		ReadableUserAgent ua2 = parser.parse("Eudora/1.0");
		assertThat(ua2.getFamily()).isEqualTo(UserAgentFamily.EUDORA);
		assertThat(ua2.getVersionNumber()
		              .toVersionString()).isEqualTo("");
		assertThat(ua2.getDeviceCategory()).isEqualTo(DeviceCategory.EMPTY);
	}

}
