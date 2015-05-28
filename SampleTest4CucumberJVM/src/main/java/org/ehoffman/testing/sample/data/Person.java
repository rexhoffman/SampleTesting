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

package org.ehoffman.testing.sample.data;

public class Person {

    public boolean locked = false;
    
    public Person() {
    }
    
    public Person(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;        
    }

    private String firstName;
    
    private String lastName;

    public void throwIfLocked() {
        if (locked) {
            throw new RuntimeException("Trying to manipulate a locked (immutable) object");
        }
    }
    
    public String getFirstName() {
        locked = true;
        return firstName;
    }

    public void setFirstName(String firstName) {
        throwIfLocked();
        this.firstName = firstName;
    }

    public String getLastName() {
        locked = true;
        return lastName;
    }

    public void setLastName(String lastName) {
        throwIfLocked();
        this.lastName = lastName;
    }
    
    public void lock() {
        locked = true;
    }
}
