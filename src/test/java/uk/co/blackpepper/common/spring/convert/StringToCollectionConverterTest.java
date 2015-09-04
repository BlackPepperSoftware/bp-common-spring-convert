package uk.co.blackpepper.common.spring.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.annotatedIntegerType;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.collectionTypeOf;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.listTypeOf;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.pipeAnnotatedIntegerCollectionType;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.setTypeOf;
import static uk.co.blackpepper.common.spring.convert.TypeDescriptorFactory.sortedSetTypeOf;

public class StringToCollectionConverterTest {
	
	private ConversionService conversionService;
	
	private StringToCollectionConverter converter;
	
	@Before
	public void setUp() {
		conversionService = mock(ConversionService.class);
		converter = new StringToCollectionConverter(conversionService);
	}
	
	@Test
	public void getConvertibleTypesReturnsStringToCollection() {
		assertThat(converter.getConvertibleTypes(), contains(new ConvertiblePair(String.class, Collection.class)));
	}
	
	@Test
	public void convertWithEmptyStringToCollectionReturnsEmptyCollection() {
		Object actual = converter.convert("", valueOf(String.class), collectionTypeOf(Integer.class));
		
		assertThat(new ArrayList<>((Collection<?>) actual), is(empty()));
	}
	
	@Test
	public void convertWithStringToCollectionReturnsCollection() {
		when(conversionService.convert("x", valueOf(String.class), valueOf(Integer.class))).thenReturn(1);
		when(conversionService.convert("y", valueOf(String.class), valueOf(Integer.class))).thenReturn(2);
		
		Object actual = converter.convert("x,y", valueOf(String.class), collectionTypeOf(Integer.class));
		
		assertThat(new ArrayList<>((Collection<?>) actual), contains((Object) 1, 2));
	}
	
	@Test
	public void convertWithStringToCollectionDoesNotTrimElements() {
		when(conversionService.convert(" x ", valueOf(String.class), valueOf(Integer.class))).thenReturn(1);
		
		Object actual = converter.convert(" x ", valueOf(String.class), collectionTypeOf(Integer.class));
		
		assertThat(new ArrayList<>((Collection<?>) actual), contains((Object) 1));
	}
	
	@Test
	public void convertWithStringToListReturnsList() {
		Object actual = converter.convert("x", valueOf(String.class), listTypeOf(Integer.class));
		
		assertThat(actual, is(instanceOf(List.class)));
	}
	
	@Test
	public void convertWithStringToSetReturnsSet() {
		Object actual = converter.convert("x", valueOf(String.class), setTypeOf(Integer.class));
		
		assertThat(actual, is(instanceOf(Set.class)));
	}
	
	@Test
	public void convertWithStringToSortedSetReturnsSet() {
		when(conversionService.convert("x", valueOf(String.class), valueOf(Integer.class))).thenReturn(1);
		
		Object actual = converter.convert("x", valueOf(String.class), sortedSetTypeOf(Integer.class));
		
		assertThat(actual, is(instanceOf(SortedSet.class)));
	}
	
	@Test
	public void convertWithStringToAnnotatedCollectionReturnsCollection() throws Exception {
		when(conversionService.convert("x", valueOf(String.class), annotatedIntegerType())).thenReturn(1);
		when(conversionService.convert("y", valueOf(String.class), annotatedIntegerType())).thenReturn(2);
		
		Object actual = converter.convert("x|y", valueOf(String.class), pipeAnnotatedIntegerCollectionType());
		
		assertThat(new ArrayList<>((Collection<?>) actual), contains((Object) 1, 2));
	}
}
