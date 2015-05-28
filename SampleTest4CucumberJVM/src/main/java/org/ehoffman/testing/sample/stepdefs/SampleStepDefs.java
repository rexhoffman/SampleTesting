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

package org.ehoffman.testing.sample.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import org.ehoffman.testing.sample.data.Person;
import org.ehoffman.testing.sample.model.ConcatonatorModel;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SampleStepDefs {

    @Autowired
    private ConcatonatorModel model;

    @Autowired
    private Person person;

    @Given("^I am ([^ ]*) (.*)$")
    public void stateThyName(final String firstName, final String lastName) throws Throwable {
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.lock();
    }

    @When("^I enter my data$")
    public void doTheThing() throws Throwable {
        model.concatonate();
    }

    @Then("^my name is stated for the record$")
    public void itShouldRecord() throws Throwable {
        assertThat(model.result()).isEqualTo(person.getFirstName() + " " + person.getLastName());
    }
}
