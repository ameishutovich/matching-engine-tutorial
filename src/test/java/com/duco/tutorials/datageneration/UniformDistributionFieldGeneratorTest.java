package com.duco.tutorials.datageneration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class UniformDistributionFieldGeneratorTest {

	@Test
	void generatesValuesInRangeOneToNumberOfValues() {
		FieldGenerator generator = new UniformDistributionFieldGenerator(0, 3);
		
		assertThat(generator.generate(0), is(equalTo("1")));
		assertThat(generator.generate(0), is(equalTo("2")));
		assertThat(generator.generate(0), is(equalTo("3")));
		assertThat(generator.generate(0), is(equalTo("1")));
		assertThat(generator.generate(0), is(equalTo("2")));
		assertThat(generator.generate(0), is(equalTo("3")));
		assertThat(generator.generate(0), is(equalTo("1")));
		
	}

}
