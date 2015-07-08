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

package org.ehoffman.testing.sample.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class SimplePage {

    private static final String OUTPUT_ID = "output";

    @FindBy(name = "part1")
    private WebElement part1;

    @FindBy(name = "part2")
    private WebElement part2;

    @FindBy(name = OUTPUT_ID)
    private WebElement output;

    private final WebDriver driver;

    /*
     * Please note we can also make partial page objects, they would have a by passed in, or a webelement.
     * That would be useful to allow every widget to come with a ready to go partial page object.
     * Use of an ElementLocatorFactory in the PageFactory.initElements(...) makes this possible. 
     */
    public SimplePage(final WebDriver driver) {
        this.driver = driver;
        /*
         * Here we wait to ensure the page is fully loaded, given ajax, there is no universal way to do this. 
         */
        new WebDriverWait(driver, 10).pollingEvery(20, TimeUnit.MILLISECONDS).until(
                        ExpectedConditions.presenceOfElementLocated(By.id(OUTPUT_ID)));
        PageFactory.initElements(driver, this);
    }

    public SimplePage setInput(final String part1String, final String part2String) {
        part1.sendKeys(part1String);
        part2.sendKeys(part2String);
        return this;
    }

    /**
     * 
     * @return the updated field that results from setting the input.
     */
    public String getOutput() {
        /*
         * Here we wait to ensure the page has been updated by our actions, it's as loose as possible
         * to ensure that something has happened, but does not verify the expected state more than it must.
         */
        new WebDriverWait(driver, 10).pollingEvery(20, TimeUnit.MILLISECONDS).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return !input.findElement(By.id(OUTPUT_ID)).getAttribute("value").isEmpty();
            }
        });
        return output.getAttribute("value");
    }

}
