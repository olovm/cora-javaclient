package se.uu.ub.cora.javaclient;

public interface RestClientFactory {

	RestClient factorUsingAuthToken(String authToken);

}