/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.exception;

import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class CanNotOpenStreamExceptionTest
{

	@Test
	public void construct_cause_null()
	{
		new CanNotOpenStreamException((Throwable) null);
	}

	@Test
	public void construct_cause_successful()
	{
		IOException tunneled = new IOException();
		CanNotOpenStreamException e = new CanNotOpenStreamException(tunneled);
		String expected = CanNotOpenStreamException.DEFAULT_MESSAGE;
		assertThat(e.getMessage()).isEqualTo(expected);
		assertThat(e.getCause()).isSameAs(tunneled);
	}

	@Test
	public void construct_default_successful()
	{
		new CanNotOpenStreamException();
	}

	@Test
	public void construct_url_cause_null()
	{
		new CanNotOpenStreamException((String) null, null);
	}

	@Test
	public void construct_url_cause_successful()
	{
		String url = "http://localhost";
		IOException tunneled = new IOException();
		CanNotOpenStreamException e = new CanNotOpenStreamException(url, tunneled);
		String expected = String.format(CanNotOpenStreamException.MESSAGE_WITH_URL, url);
		assertThat(e.getMessage()).isEqualTo(expected);
		assertThat(e.getCause()).isSameAs(tunneled);
	}

	@Test
	public void construct_url_null()
	{
		new CanNotOpenStreamException((String) null);
	}

	@Test
	public void construct_url_successful()
	{
		String url = "http://localhost";
		CanNotOpenStreamException e = new CanNotOpenStreamException(url);
		String expected = String.format(CanNotOpenStreamException.MESSAGE_WITH_URL, url);
		assertThat(e.getMessage()).isEqualTo(expected);
	}

}
