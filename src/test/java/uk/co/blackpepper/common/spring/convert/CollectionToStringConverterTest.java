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

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

import static java.util.Arrays.asList;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.annotatedIntegerType;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.collectionTypeOf;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.pipeAnnotatedIntegerCollectionType;

public class CollectionToStringConverterTest {
	
	private ConversionService conversionService;
	
	private CollectionToStringConverter converter;
	
	@Before
	public void setUp() {
		conversionService = mock(ConversionService.class);
		converter = new CollectionToStringConverter(conversionService);
	}
	
	@Test
	public void getConvertibleTypesReturnsCollectionToString() {
		assertThat(converter.getConvertibleTypes(), contains(new ConvertiblePair(Collection.class, String.class)));
	}
	
	@Test
	public void convertWithCollectionToStringReturnsString() {
		when(conversionService.convert(1, valueOf(Integer.class), valueOf(String.class))).thenReturn("x");
		when(conversionService.convert(2, valueOf(Integer.class), valueOf(String.class))).thenReturn("y");
		
		Object actual = converter.convert(asList(1, 2), collectionTypeOf(Integer.class), valueOf(String.class));
		
		assertThat((String) actual, is("x,y"));
	}
	
	@Test
	public void convertWithAnnotatedCollectionToStringReturnsString() throws Exception {
		when(conversionService.convert(1, annotatedIntegerType(), valueOf(String.class))).thenReturn("x");
		when(conversionService.convert(2, annotatedIntegerType(), valueOf(String.class))).thenReturn("y");
		
		Object actual = converter.convert(asList(1, 2), pipeAnnotatedIntegerCollectionType(), valueOf(String.class));
		
		assertThat((String) actual, is("x|y"));
	}
}
