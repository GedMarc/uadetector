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
package net.sf.uadetector;


import net.sf.uadetector.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.util.NaturalOrderComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The {@code VersionNumber} class represents the version number of an operating system or User-Agent.<br>
 * <br>
 * A {@code VersionNumber} object is immutable, their values cannot be changed after creation.
 *
 * @author André Rouél
 */
public final class VersionNumber
		implements ReadableVersionNumber, Serializable
{

	/**
	 * Empty extension or addition of a version number
	 */
	public static final String EMPTY_EXTENSION = "";

	/**
	 * Empty group or category of a version number
	 */
	public static final String EMPTY_GROUP = "";
	/**
	 * Defines an empty or not set version number
	 */
	public static final VersionNumber UNKNOWN = new VersionNumber(EMPTY_GROUP);
	/**
	 * Minimum number of numeric group a version number
	 */
	private static final int MIN_GROUP_SIZE = 3;
	/**
	 * Regular expression to find only numerical values ​​in strings
	 */
	private static final Pattern NUMERIC = Pattern.compile("\\d+");
	/**
	 * Separator between numeric groups of a version number
	 */
	private static final char SEPARATOR = '.';
	/**
	 * Serialization version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Extension or suffix of the version number consisting of alphanumeric and special characters
	 */
	@javax.validation.constraints.NotNull
	private final String extension;
	/**
	 * Groups, segments or categories of the version number
	 */
	@javax.validation.constraints.NotNull
	private final List<String> groups;

	/**
	 * Constructs a {@code VersionNumber} with the given numeric groups, such as major, minor and bugfix number.
	 *
	 * @param groups
	 * 		list of numbers of a version number
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if one of the segments of the version number is smaller than 0 and not empty
	 */
	public VersionNumber(@javax.validation.constraints.NotNull List<String> groups)
	{
		this(groups, EMPTY_EXTENSION);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given numeric groups, such as major, minor and bugfix number and
	 * extension.
	 *
	 * @param groups
	 * 		list of numbers of a version number
	 * @param extension
	 * 		extension of a version number
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if one of the groups of the version number is not empty or a positive number
	 */
	public VersionNumber(@javax.validation.constraints.NotNull List<String> groups, @javax.validation.constraints.NotNull String extension)
	{
		Check.notNull(groups, "groups");
		Check.notNull(extension, "extension");

		List<String> segments = replaceNullValueWithEmptyGroup(groups);
		int i = 0;
		for (String segment : segments)
		{
			if (!EMPTY_GROUP.equals(segment) && !isNumeric(segment))
			{
				throw new IllegalStateOfArgumentException("The segment on position " + i + " (" + segment + ") must be a number.");
			}
			i++;
		}

		this.groups = segments;
		this.extension = extension;
	}

	/**
	 * Replaces all {@code null} values in the given list of groups with {@code VersionNumber#EMPTY_GROUP}.
	 *
	 * @param groups
	 * 		list of numbers of a version number
	 *
	 * @return a new list of groups without {@code null} values
	 */
	public static List<String> replaceNullValueWithEmptyGroup(@javax.validation.constraints.NotNull List<String> groups)
	{
		Check.notNull(groups, "groups");

		List<String> result = new ArrayList<>(groups.size());
		for (String group : groups)
		{
			if (group == null)
			{
				result.add(EMPTY_GROUP);
			}
			else
			{
				result.add(group);
			}
		}
		for (int i = result.size(); i < MIN_GROUP_SIZE; i++)
		{
			result.add(EMPTY_GROUP);
		}
		return result;
	}

	/**
	 * Checks a string that only numerical values ​​are present. Negative numbers are not included.
	 *
	 * @param text
	 * 		string to be tested
	 *
	 * @return {@code true} if only numeric characters are present, otherwise {@code false}
	 */
	private static boolean isNumeric(String text)
	{
		return NUMERIC.matcher(text)
		              .matches();
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major number and without a minor and bugfix number.
	 *
	 * @param major
	 * 		major group of the version number
	 * 		<p>
	 * 		<p>
	 * 		if the given argument is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the major segment is smaller than 0 and not empty
	 */
	public VersionNumber(@javax.validation.constraints.NotNull String major)
	{
		this(Check.notNull(major, "major"), EMPTY_GROUP);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major, minor number and without a bugfix number.
	 *
	 * @param major
	 * 		major group of the version number
	 * @param minor
	 * 		minor group of the version number
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the major or minor segment is smaller than 0 and not empty
	 */
	public VersionNumber(@javax.validation.constraints.NotNull String major, @javax.validation.constraints.NotNull String minor)
	{
		this(Check.notNull(major, "major"), Check.notNull(minor, "minor"), EMPTY_GROUP);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major, minor and bugfix number.
	 *
	 * @param major
	 * 		major group of the version number
	 * @param minor
	 * 		minor group of the version number
	 * @param bugfix
	 * 		bugfix group of the version number
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the major, minor or bugfix segment is smaller than 0 and not empty
	 */
	public VersionNumber(@javax.validation.constraints.NotNull String major, @javax.validation.constraints.NotNull String minor, @javax.validation.constraints.NotNull String bugfix)
	{
		this(Check.notNull(major, "major"), Check.notNull(minor, "minor"), Check.notNull(bugfix, "bugfix"), EMPTY_EXTENSION);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major, minor and bugfix number and extension.
	 *
	 * @param major
	 * 		major group of the version number
	 * @param minor
	 * 		minor group of the version number
	 * @param bugfix
	 * 		bugfix group of the version number
	 * @param extension
	 * 		extension of a version number
	 * 		<p>
	 * 		<p>
	 * 		if one of the given arguments is {@code null}
	 *
	 * @throws net.sf.uadetector.exception.IllegalStateOfArgumentException
	 * 		if the major, minor or bugfix segment is smaller than 0 and not empty
	 */
	public VersionNumber(@javax.validation.constraints.NotNull String major, @javax.validation.constraints.NotNull String minor, @javax.validation.constraints.NotNull String bugfix,
	                     @javax.validation.constraints.NotNull String extension)
	{
		this(Arrays.asList(Check.notNull(major, "major"), Check.notNull(minor, "minor"), Check.notNull(bugfix, "bugfix")), Check.notNull(
				extension, "extension"));
	}

	/**
	 * Interprets a string with version information. The last version number in the string will be searched and
	 * processed.
	 *
	 * @param text
	 * 		string with version information
	 *
	 * @return an object of {@code VersionNumber}, never {@code null}
	 */
	public static VersionNumber parseLastVersionNumber(@javax.validation.constraints.NotNull String text)
	{
		return VersionParser.parseLastVersionNumber(Check.notNull(text, "text"));
	}

	/**
	 * Try to determine the version number of the operating system by parsing the user agent string.
	 *
	 * @param family
	 * 		family of the operating system
	 * @param userAgent
	 * 		user agent string
	 *
	 * @return extracted version number
	 */
	public static VersionNumber parseOperatingSystemVersion(@javax.validation.constraints.NotNull OperatingSystemFamily family, @javax.validation.constraints.NotNull String userAgent)
	{
		Check.notNull(family, "family");
		Check.notNull(userAgent, "userAgent");
		return VersionParser.parseOperatingSystemVersion(family, userAgent);
	}

	/**
	 * Interprets a string with version information. The first found group will be taken and processed.
	 *
	 * @param version
	 * 		version as string
	 *
	 * @return an object of {@code VersionNumber}, never {@code null}
	 */
	public static VersionNumber parseVersion(@javax.validation.constraints.NotNull String version)
	{
		return VersionParser.parseVersion(Check.notNull(version, "version"));
	}

	/**
	 * Compares this version number with the specified version number for order. Returns a negative integer, zero, or a
	 * positive integer as this version number is less than, equal to, or greater than the specified version number.
	 *
	 * @return a negative integer, zero, or a positive integer as this version number is less than, equal to, or greater
	 * 		than the specified version number.
	 */
	@Override
	public int compareTo(ReadableVersionNumber other)
	{
		int result = 0;
		if (other == null)
		{
			result = -1;
		}
		else
		{
			Check.notNull(other.getGroups(), "other.getGroups()");
			int length = groups.size() < other.getGroups()
			                                  .size()
			             ? groups.size()
			             : other.getGroups()
			                    .size();
			NaturalOrderComparator comparator = new NaturalOrderComparator();
			result = comparator.compare(toVersionString(groups.subList(0, length)), toVersionString(other.getGroups()
			                                                                                             .subList(0, length)));
			if (result == 0)
			{
				result = Integer.compare(groups.size(), other.getGroups()
				                                             .size());
			}
			if (result == 0)
			{
				result = extension.compareTo(other.getExtension());
			}
			if (result == 0)
			{
				result = comparator.compare(toVersionString(), other.toVersionString());
			}
		}
		return result;
	}

	/**
	 * Converts the given list of numbers in a version string. The groups of the version number will be separated by a
	 * dot.
	 *
	 * @param groups
	 * 		list of numbers of a version number
	 *
	 * @return a formated version string
	 */
	private static String toVersionString(@javax.validation.constraints.NotNull List<String> groups)
	{
		StringBuilder builder = new StringBuilder(6);
		int count = 0;
		for (String segment : groups)
		{
			if (EMPTY_GROUP.equals(segment))
			{
				break;
			}
			else
			{
				if (count > 0)
				{
					builder.append(SEPARATOR);
				}
				builder.append(segment);
			}
			count++;
		}
		return builder.toString();
	}

	/**
	 * Gets the bugfix category of the version number.
	 */
	@Override
	public String getBugfix()
	{
		return groups.get(2);
	}

	/**
	 * Gets the addition or extension of the version number.
	 *
	 * @return extension of the version number
	 */
	@Override
	public String getExtension()
	{
		return extension;
	}

	/**
	 * Get all groups (or categories) of this version number. The first element in the list is the major category,
	 * followed by the minor and bugfix segment of the version number.<br>
	 * <br>
	 * The returned list of the version number segments is immutable.
	 *
	 * @return an unmodifiable view of the of the version number groups
	 */
	@Override
	public List<String> getGroups()
	{
		return Collections.unmodifiableList(groups);
	}

	/**
	 * Gets the major category of the version number.
	 */
	@Override
	public String getMajor()
	{
		return groups.get(0);
	}

	/**
	 * Gets the major category of the version number.
	 */
	@Override
	public String getMinor()
	{
		return groups.get(1);
	}

	/**
	 * Gets this version number as string.
	 *
	 * @return numeric groups as dot separated version number string
	 */
	@javax.validation.constraints.NotNull
	@Override
	public String toVersionString()
	{
		return toVersionString(groups) + extension;
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + groups.hashCode();
		result = prime * result + extension.hashCode();
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this version number.
	 *
	 * @return {@code true} if the given version number is equal to this one
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		VersionNumber other = (VersionNumber) obj;
		if (!groups.equals(other.groups))
		{
			return false;
		}
		if (!extension.equals(other.extension))
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns a string representation of the version number.
	 *
	 * @return a string representation of this version number
	 */
	@javax.validation.constraints.NotNull
	@Override
	public String toString()
	{
		return "VersionNumber [groups=" + groups + ", extension=" + extension + "]";
	}

}
