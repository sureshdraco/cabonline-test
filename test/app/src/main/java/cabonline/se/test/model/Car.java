/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cabonline.se.test.model;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Car implements RealmModel {
	@PrimaryKey
	private int id;
	private String name, transmission;
	private int noOfSeats;

	public Car() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}
}
