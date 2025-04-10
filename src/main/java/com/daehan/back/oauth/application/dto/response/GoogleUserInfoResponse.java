package com.daehan.back.oauth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfoResponse(
        @JsonProperty("sub") String sub,
        @JsonProperty("name") String name,
        @JsonProperty("given_name") String givenName,
        @JsonProperty("family_name") String familyName,
        @JsonProperty("picture") String picture,
        @JsonProperty("email") String email,
        @JsonProperty("email_verified") boolean emailVerified,
        @JsonProperty("locale") String locale
) {
}
