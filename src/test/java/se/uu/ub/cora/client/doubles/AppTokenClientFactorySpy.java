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
package se.uu.ub.cora.client.doubles;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.client.AppTokenClient;
import se.uu.ub.cora.client.AppTokenClientFactory;

public class AppTokenClientFactorySpy implements AppTokenClientFactory {

	public List<AppTokenClientSpy> factored = new ArrayList<>();
	public List<String> usedUserId = new ArrayList<>();
	public List<String> usedAppToken = new ArrayList<>();

	@Override
	public AppTokenClient factor(String userId, String appToken) {
		usedUserId.add(userId);
		usedAppToken.add(appToken);
		AppTokenClientSpy appTokenClientSpy = new AppTokenClientSpy();
		factored.add(appTokenClientSpy);
		return appTokenClientSpy;
	}

}
