package se.uu.ub.cora.client;

public class AppTokenClientCredentials {
	public final String appTokenVerifierUrl;
	public final String userId;
	public final String appToken;

	public AppTokenClientCredentials(String appTokenVerifierUrl, String userId, String appToken) {
		this.appTokenVerifierUrl = appTokenVerifierUrl;
		this.userId = userId;
		this.appToken = appToken;
	}
}