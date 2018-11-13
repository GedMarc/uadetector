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
package net.sf.uadetector.util;

import org.junit.After;
import org.junit.Test;

import java.util.logging.Logger;

import static org.fest.assertions.Assertions.*;

public class FinalStaticFieldValueChangerTest
{

	private static final Logger LOG = Logger.getLogger(FinalStaticFieldValueChangerTest.class.toString());

	@After
	public void tearDown() throws SecurityException, NoSuchFieldException, Exception
	{
		FinalStaticFieldValueChanger.setFinalStatic(StaticFinalField.class.getField("FALSE"), false);
	}

	@Test
	public void test() throws SecurityException, NoSuchFieldException, Exception
	{
		assertThat(false).isEqualTo(StaticFinalField.FALSE);
		FinalStaticFieldValueChanger.setFinalStatic(StaticFinalField.class.getField("FALSE"), true);
		assertThat(true).isEqualTo(StaticFinalField.FALSE);
		LOG.info(String.format("%s: Everything is %s", this.getClass()
		                                                   .getSimpleName(), false)); // "Everything is true"
	}

	private static final class StaticFinalField
	{
		public static final Boolean FALSE = new Boolean(false);
	}

}
