package uk.co.blackpepper.common.spring.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the delimiter to use for converting a collection to a string and vice-versa.
 * 
 * @see CollectionToStringConverter
 * @see StringToCollectionConverter
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionDelimiter {

	/**
	 * Returns the delimiter to use for conversion.
	 * 
	 * @return the delimiter
	 */
	String value() default ",";
}
