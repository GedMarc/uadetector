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
package net.sf.uadetector.internal.util;

import net.sf.uadetector.exception.CannotCloseException;
import org.easymock.EasyMock;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.Fail.*;

public class CloseablesTest
{

	private Closeable mockCloseable;

	@Test
	public void close_withClosable_butSwallowIOException_closeSuccessful() throws IOException
	{
		boolean swallowException = true;
		boolean expectThrown = false;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	private void setupCloseableMock(boolean throwIOException) throws IOException
	{
		mockCloseable = EasyMock.createStrictMock(Closeable.class);
		mockCloseable.close();
		if (throwIOException)
		{
			EasyMock.expectLastCall()
			        .andThrow(new IOException("I/O test error (this is not an issue)"));
		}
		EasyMock.replay(mockCloseable);
	}

	private void doClose(Closeable closeable, boolean swallowException, boolean expectThrown)
	{
		try
		{
			Closeables.close(closeable, swallowException);
			if (expectThrown && !swallowException)
			{
				fail("An IOException must be thrown.");
			}
		}
		catch (IOException e)
		{
			if (!expectThrown)
			{
				fail("An IOException must be thrown.");
			}
		}
		EasyMock.verify(closeable);
	}

	@Test
	public void close_withClosable_butSwallowIOException_throwIOException() throws IOException
	{
		boolean swallowException = true;
		boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withClosable_dontSwallowIOException_closeSuccessful() throws IOException
	{
		boolean swallowException = false;
		boolean expectThrown = false;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withClosable_dontSwallowIOException_throwIOException() throws IOException
	{
		boolean swallowException = false;
		boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withNull_swallow() throws IOException
	{
		Closeables.close(null, true);
	}

	@Test
	public void close_withNull_swallowNot() throws IOException
	{
		Closeables.close(null, false);
	}

	@Test
	public void closeAndConvert_withClosable_dontSwallowIOException_eatCheckedException() throws IOException
	{
		boolean swallowException = false;
		boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doCloseAndConvert(mockCloseable, swallowException, expectThrown);
	}

	private void doCloseAndConvert(Closeable closeable, boolean swallowException, boolean expectThrown)
	{
		try
		{
			Closeables.closeAndConvert(closeable, swallowException);
			if (expectThrown && !swallowException)
			{
				fail("A CannotCloseException must be thrown.");
			}
		}
		catch (CannotCloseException e)
		{
			if (!expectThrown)
			{
				fail("A CannotCloseException must be thrown.");
			}
			else
			{
				assertThat(e.getCause()).isNotNull();
			}
		}
		EasyMock.verify(closeable);
	}

	@Test
	public void closeAndConvert_withClosable_swallowIOException_closeSuccessful() throws IOException
	{
		boolean swallowException = true;
		boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doCloseAndConvert(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void closeAndConvert_withNull_swallow()
	{
		Closeables.closeAndConvert(null, true);
	}

	@Test
	public void closeAndConvert_withNull_swallowNot()
	{
		Closeables.closeAndConvert(null, false);
	}

}
