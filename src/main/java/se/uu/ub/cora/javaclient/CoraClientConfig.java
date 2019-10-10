/*
 * Copyright 2018 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.javaclient;

public class CoraClientConfig {

	public final String userId;
	public final String appToken;
	public final String appTokenVerifierUrl;
	public final String coraUrl;

	public CoraClientConfig(String userId, String appToken, String appTokenVerifierUrl,
			String coraUrl) {
		this.userId = userId;
		this.appToken = appToken;
		this.appTokenVerifierUrl = appTokenVerifierUrl;
		this.coraUrl = coraUrl;
	}

}
