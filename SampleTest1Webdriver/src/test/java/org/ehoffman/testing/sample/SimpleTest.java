/*
 * #%L
 * JunitAopAllianceAdviceFixtures
 * %%
 * Copyright (C) 2015 Rex Hoffman
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.ehoffman.testing.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class SimpleTest {

    private static String PART1 = "John";
    private static String PART2 = "Doe";

    @Test
    public void simpleWebDriverTest() throws InterruptedException, URISyntaxException {
        /*
         * this data, aka the url is coupled to this test method, do to be co-located with it. Maybe other tests need it as well,
         * another reason for a global context.
         */
        String url = Thread.currentThread().getContextClassLoader().getResource("index.html").toURI().toString();

        /*
         * not the lack of variability in starting the browser, clearly a method is need. Note as well that there is also global
         * state around how many browser happen to be open in a multi-threaded run. this can be achieved in a number of ways,
         * naively relying on the test runner threading model, or explicitly with a pool managed in the context of a global test
         * run.
         */
        String browser = System.getProperty("BROWSER");
        final WebDriver driver = "PHANTOMJS".equals(browser) ? new PhantomJSDriver() : new ChromeDriver();

        driver.navigate().to(url);

        driver.findElement(By.name("part1")).sendKeys(PART1);
        driver.findElement(By.name("part2")).sendKeys(PART2);

        /*
         * A little experience will show you that this below line can fail, it is in a race condition with the javascript + any
         * server side it might call. Web driver waits are the answer.
         */
        assertThat(driver.findElement(By.name("output")).getAttribute("value")).isEqualTo(PART1 + " " + PART2);

        /*
         * Notice that without control flow any exception may "leak" and entire browser -- try catch, much like @Before/@After are
         * not really your friend here.
         */
        driver.close();
    }

}
