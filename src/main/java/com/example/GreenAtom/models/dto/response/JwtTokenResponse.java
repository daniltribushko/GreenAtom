package com.example.GreenAtom.models.dto.response;

/**
 * @author Tribushko Danil
 * Класс овета для запроса на получение токена
 */
public class JwtTokenResponse {
    private String token;

    public JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
