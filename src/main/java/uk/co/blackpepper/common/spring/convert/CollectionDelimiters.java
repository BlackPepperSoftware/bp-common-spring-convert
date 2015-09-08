/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.blackpepper.common.spring.convert;

import org.springframework.core.convert.TypeDescriptor;

final class CollectionDelimiters {

	private CollectionDelimiters() {
		throw new AssertionError();
	}
	
	public static String getDelimiter(TypeDescriptor sourceType) {
		if (sourceType.hasAnnotation(CollectionDelimiter.class)) {
			return sourceType.getAnnotation(CollectionDelimiter.class).value();
		}
		
		try {
			return (String) CollectionDelimiter.class.getMethod("value").getDefaultValue();
		}
		catch (NoSuchMethodException exception) {
			throw new AssertionError("Missing @CollectionDelimiter.value");
		}
	}
}
