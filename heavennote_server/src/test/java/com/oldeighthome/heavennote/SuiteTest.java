package com.oldeighthome.heavennote;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({Test.class,ParameterTest.class})
public class SuiteTest {
}
