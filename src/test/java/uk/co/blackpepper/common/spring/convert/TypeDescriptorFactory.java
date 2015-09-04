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
