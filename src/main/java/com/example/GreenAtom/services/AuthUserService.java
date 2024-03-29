package com.example.GreenAtom.services;

import com.example.GreenAtom.models.dto.request.AuthRequest;
import com.example.GreenAtom.models.dto.response.JwtTokenResponse;
import com.example.GreenAtom.models.dto.response.UserResponse;

/**
 * @author Tribushko Danil
 * Сервис для авторизайиии и регистрации пользователя
 */
public interface AuthUserService {
    /**
     * Регистрация пользователя
     * @param request запрос на регистрацию пользователя
     * @return токен пользователя
     */
    UserResponse signUp(AuthRequest request);

    /**
     * Авторизация пользователя
     * @param request запрос на авторизацию пользователя
     * @return токен пользователя
     */
    JwtTokenResponse signIn(AuthRequest request);
}
