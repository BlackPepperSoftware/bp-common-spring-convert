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
