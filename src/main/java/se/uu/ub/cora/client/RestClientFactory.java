package se.uu.ub.cora.client;

public interface RestClientFactory {

	RestClient factorUsingAuthToken(String authToken);

}