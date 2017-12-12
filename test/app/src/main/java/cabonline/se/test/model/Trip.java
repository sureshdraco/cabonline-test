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

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Trip extends RealmObject {
	@PrimaryKey
	private int id;
	private String amount, paymentType, destination;
	private Date time;
	
	public Trip() {
	}
	
	public Trip(int id, String amount, String paymentType, String destination, Date time) {
		this.id = id;
		this.amount = amount;
		this.paymentType = paymentType;
		this.destination = destination;
		this.time = time;
	}
	
	public int getId() {
		return id;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getDisplayTime() {
		return time == null ? "" : time.toString();
	}
	
	public String getAmount() {
		return amount;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public String getDestination() {
		return destination;
	}
}
