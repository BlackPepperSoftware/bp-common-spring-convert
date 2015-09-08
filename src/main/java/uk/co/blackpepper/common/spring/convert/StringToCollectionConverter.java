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
import java.util.List;
import java.util.Set;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.google.common.base.Splitter;

import static java.util.Collections.singleton;

import static org.springframework.core.CollectionFactory.createCollection;

import static com.google.common.base.Preconditions.checkNotNull;

import static uk.co.blackpepper.common.spring.convert.CollectionDelimiters.getDelimiter;

/**
 * Spring converter to convert a string to a collection.
 * <p>
 * The string is split using the configured delimiter and each element is then converted to the target collection's
 * element type. The delimiter is specified by using the {@code @CollectionDelimiter} annotation on the target type
 * which defaults to a comma if not present.
 * <p>
 * This class differs from {@code org.springframework.core.convert.support.StringToCollectionConverter} in that it
 * allows customisation of the delimiter and it does not trim the converted element strings.
 * 
 * @see CollectionDelimiter
 */
public class StringToCollectionConverter implements GenericConverter {

	private final ConversionService conversionService;

	public StringToCollectionConverter(ConversionService conversionService) {
		this.conversionService = checkNotNull(conversionService, "conversionService");
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return singleton(new ConvertiblePair(String.class, Collection.class));
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		String sourceString = (String) source;
		Collection<Object> targetCollection = createCollection(targetType.getType(), 10);
		
		if (sourceString.isEmpty()) {
			return targetCollection;
		}
		
		List<String> sourceStrings = Splitter.on(getDelimiter(targetType)).splitToList(sourceString);
		
		for (String sourceElement : sourceStrings) {
			Object targetElement = conversionService.convert(sourceElement, TypeDescriptor.valueOf(String.class),
				targetType.getElementTypeDescriptor());
			
			targetCollection.add(targetElement);
		}
		
		return targetCollection;
	}
}
