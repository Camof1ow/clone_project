package com.project.cloneproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.cloneproject.exceptionHandler.CustomException;
import com.project.cloneproject.exceptionHandler.ErrorCode;
import com.project.cloneproject.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class SocialLoginController {

    private final KakaoUserService kakaoUserService;

    //카카오 로그인
    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity kakaoLogin(@PathVariable String code, HttpServletResponse response) throws JsonProcessingException {

        try { // 회원가입 진행 성공시

            kakaoUserService.kakaoLogin(code, response);
            return new ResponseEntity("카카오 로그인 성공", HttpStatus.OK);
        } catch (Exception e) { // 에러나면 false
            throw new CustomException(ErrorCode.INVALID_KAKAO_LOGIN_ATTEMPT);
        }
    }

//    //네이버 로그인
//    @GetMapping("/oauth/naver/callback")
//    public ResponseEntity naverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonProcessingException {
//        try { // 회원가입 진행 성공시
//            naverUserService.naverLogin(code, state, response);
//            return new ResponseEntity("네이버 로그인 성공", HttpStatus.OK);
//        } catch (Exception e) { // 에러나면 false
//            throw new CustomException(ErrorCode.INVALID_NAVER_LOGIN_ATTEMPT);
//        }
//    }
//
//    //구글 로그인
//    @GetMapping("/oauth/google/callback")
//    public ResponseEntity googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        try { // 회원가입 진행 성공시
//            googleUserService.googleLogin(code, response);
//            return new ResponseEntity("구글 로그인 성공", HttpStatus.OK);
//        } catch (Exception e) { // 에러나면 false
//            throw new CustomException(ErrorCode.INVALID_GOOGLE_LOGIN_ATTEMPT);
//        }
//    }
}
