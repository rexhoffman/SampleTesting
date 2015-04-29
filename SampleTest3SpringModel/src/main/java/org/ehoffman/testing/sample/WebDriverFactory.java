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

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {

    public enum Driver {
        CHROME, FIREFOX
    }

    /**
     * Returns a {@link WebDriver} instance built to the input specifications.
     * 
     * @param type the type of {@link WebDriver}.
     * @return a {@link WebDriver} instance ready for use.
     */
    public WebDriver buildWebdriver(final Driver type, final String url) {
        WebDriver driver = null;
        switch (type) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
            default:
                driver = new FirefoxDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.NANOSECONDS);
        driver.navigate().to(url);
        return driver;
    }

}
