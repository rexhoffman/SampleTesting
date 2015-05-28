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

import org.ehoffman.testing.sample.WebDriverFactory.Driver;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class SimpleTest {

    private static String PART1 = "John";
    private static String PART2 = "Doe";

    @Test
    public void simpleWebDriverTest() throws InterruptedException, URISyntaxException {
        /*
         * this data, a.k.a. the URL is coupled to this test method, due to being co-located with it. Maybe other tests need it as
         * well, another reason for a global context.
         */
        String url = Thread.currentThread().getContextClassLoader().getResource("index.html").toURI().toString();

        /*
         * Better we have a parameterized factory and ChromeDriver is not imported,
         * but the factory details are still visible as an import, so still coupled.
         */
        Driver driverType = Driver.CHROME; 
        String name = System.getProperty("BROWSER");
        if (name != null) {
            driverType = Driver.valueOf(System.getProperty("BROWSER"));
        }
        final WebDriver driver = new WebDriverFactory().buildWebdriver(driverType, url);

        assertThat(new SimplePage(driver).setInput(PART1, PART2).getOutput()).isEqualTo(PART1 + " " + PART2);

        /*
         * Notice that without control flow any exception may "leak" and entire browser -- try catch, much like @Before/@After are
         * not really your friend here.
         */
        driver.close();
    }

}
