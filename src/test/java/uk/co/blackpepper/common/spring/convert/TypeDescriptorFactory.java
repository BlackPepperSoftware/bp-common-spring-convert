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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.core.convert.TypeDescriptor;

final class TypeDescriptorFactory {
	
	@CollectionDelimiter("|")
	private Collection<Integer> pipeAnnotatedIntegerCollectionType;
	
	@CollectionDelimiter
	private Integer annotatedIntegerType;

	private TypeDescriptorFactory() {
	}
	
	public static TypeDescriptor collectionTypeOf(Class<?> elementType) {
		return TypeDescriptor.collection(Collection.class, TypeDescriptor.valueOf(elementType));
	}

	public static TypeDescriptor setTypeOf(Class<?> elementType) {
		return TypeDescriptor.collection(Set.class, TypeDescriptor.valueOf(elementType));
	}

	public static TypeDescriptor sortedSetTypeOf(Class<?> elementType) {
		return TypeDescriptor.collection(SortedSet.class, TypeDescriptor.valueOf(elementType));
	}
	
	public static TypeDescriptor listTypeOf(Class<?> elementType) {
		return TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(elementType));
	}
	
	public static TypeDescriptor pipeAnnotatedIntegerCollectionType() throws NoSuchFieldException, SecurityException {
		Field field = TypeDescriptorFactory.class.getDeclaredField("pipeAnnotatedIntegerCollectionType");
		return new TypeDescriptor(field);
	}

	public static TypeDescriptor annotatedIntegerType() throws NoSuchFieldException, SecurityException {
		Field field = TypeDescriptorFactory.class.getDeclaredField("annotatedIntegerType");
		return new TypeDescriptor(field);
	}
}
