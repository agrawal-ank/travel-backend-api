package com.demo.travelbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class Configurations {
	@Value("${mockservice.hostUrl}")
	private String hostUrl;

	@Value("${mockservice.clientId}")
	private String clientId;

	@Value("${mockservice.secret}")
	private String clientSecret;

	@Value("${mockservice.accessTokenUri}")
	private String accessTokenUri;

	@Value("${mockservice.grantType}")
	private String grantType;

	@Bean
	public OAuth2RestTemplate restTemplate() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setAccessTokenUri(hostUrl + accessTokenUri);
		resource.setGrantType(grantType);

		OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext());
		return oauth2RestTemplate;
	}
}
