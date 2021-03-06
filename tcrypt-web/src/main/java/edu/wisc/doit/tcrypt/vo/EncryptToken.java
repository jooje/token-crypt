/**
 * Copyright 2012, Board of Regents of the University of
 * Wisconsin System. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Board of Regents of the University of Wisconsin
 * System licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.wisc.doit.tcrypt.vo;

public class EncryptToken {
	
	//variables
	private String serviceKeyName;
	private String unencryptedText;
	private String encryptedText;
	private String errorMessage;
	
	//getters/setters
	public String getServiceKeyName() {
		return serviceKeyName;
	}
	public void setServiceKeyName(String serviceKeyName) {
		this.serviceKeyName = serviceKeyName;
	}
	public String getUnencryptedText() {
		return unencryptedText;
	}
	public void setUnencryptedText(String unencryptedText) {
		this.unencryptedText = unencryptedText;
	}
	public String getEncryptedText() {
		return encryptedText;
	}
	public void setEncryptedText(String encryptedText) {
		this.encryptedText = encryptedText;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
