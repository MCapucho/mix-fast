package br.com.postech.mixfast.dataproviders.model.rest.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenApiResponse {

    private Boolean isSuccessful;
    private Integer statusCode;
    private String idToken;
    private String accessToken;
    private String refreshToken;
}
