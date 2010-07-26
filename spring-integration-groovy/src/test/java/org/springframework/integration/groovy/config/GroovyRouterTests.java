/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.groovy.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.PollableChannel;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.StringMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Fisher
 * @since 2.0
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class GroovyRouterTests {

	@Autowired
	private MessageChannel referencedScriptInput;

	@Autowired
	private MessageChannel inlineScriptInput;

	@Autowired
	private PollableChannel longStrings;

	@Autowired
	private PollableChannel shortStrings;


	@Test
	public void referencedScript() { // long is > 3
		Message<?> message1 = new StringMessage("aardvark");
		Message<?> message2 = new StringMessage("bear");
		Message<?> message3 = new StringMessage("cat");
		Message<?> message4 = new StringMessage("dog");
		Message<?> message5 = new StringMessage("elephant");
		this.referencedScriptInput.send(message1);
		this.referencedScriptInput.send(message2);
		this.referencedScriptInput.send(message3);
		this.referencedScriptInput.send(message4);
		this.referencedScriptInput.send(message5);
		assertEquals("cat", shortStrings.receive(0).getPayload());
		assertEquals("dog", shortStrings.receive(0).getPayload());
		assertEquals("aardvark", longStrings.receive(0).getPayload());
		assertEquals("bear", longStrings.receive(0).getPayload());
		assertEquals("elephant", longStrings.receive(0).getPayload());
		assertNull(shortStrings.receive(0));
		assertNull(longStrings.receive(0));
	}

	@Test
	public void inlineScript() { // long is > 5
		Message<?> message1 = new StringMessage("aardvark");
		Message<?> message2 = new StringMessage("bear");
		Message<?> message3 = new StringMessage("cat");
		Message<?> message4 = new StringMessage("dog");
		Message<?> message5 = new StringMessage("elephant");
		this.inlineScriptInput.send(message1);
		this.inlineScriptInput.send(message2);
		this.inlineScriptInput.send(message3);
		this.inlineScriptInput.send(message4);
		this.inlineScriptInput.send(message5);
		assertEquals("bear", shortStrings.receive(0).getPayload());
		assertEquals("cat", shortStrings.receive(0).getPayload());
		assertEquals("dog", shortStrings.receive(0).getPayload());
		assertEquals("aardvark", longStrings.receive(0).getPayload());
		assertEquals("elephant", longStrings.receive(0).getPayload());
		assertNull(shortStrings.receive(0));
		assertNull(longStrings.receive(0));
	}

}