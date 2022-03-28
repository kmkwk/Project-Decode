package com.ssafy.authsvr.controller;

import com.ssafy.authsvr.payload.request.UserProfileRequest;
import com.ssafy.authsvr.payload.response.UserDetailProfileResponse;
import com.ssafy.authsvr.payload.response.UserProfileResponse;
import com.ssafy.authsvr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RestControllerAdvice(annotations = RestController.class)
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인시 추가 정보 및 프로필 회원정보 변경", notes = "추가 회원정보 업데이트")
    @PutMapping("/recommend")
    public ResponseEntity<String> userRecommendInfoModify(@RequestBody @Valid UserProfileRequest profileRequest) {
        log.info("userRecommendInfoModify");

        if (ObjectUtils.isEmpty(profileRequest)) {
            return ResponseEntity.notFound().build();
        }

        userService.ModifyRecommendInfoUser(profileRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("SUCCESS");
    }

    @ApiOperation(value = "회원의 닉네임 조회", notes = "회원 PK값으로 유저 닉네임 조회")
    @GetMapping("/name/{id}")
    public ResponseEntity<String> userFindNickName(@PathVariable(value = "id") Integer id) {
        log.info("userFindNickName");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findNickNameUser(id));
    }

    @ApiOperation(value = "회원 프로필 정보 조회", notes = "회원 PK값으로 프로필 정보 조회 - 이미지와 닉네임")
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileResponse> userFindProfile(@PathVariable("id") Integer id) {
        log.info("userFindProfile");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findProfileUser(id));
    }

    @ApiOperation(value = "회원 프로필 전체 조회", notes = "회원 PK값으로 프로필 전체 조회")
    @GetMapping("/allProfile/{id}")
    public ResponseEntity<UserDetailProfileResponse> userFindAllProfile(@PathVariable("id") Integer id) {
        log.info("userFindAllProfile");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAllProfileUser(id));
    }

    @ApiOperation(value = "회원 수 조회", notes = "회원 전체 수 조회")
    @GetMapping("/userCount")
    public ResponseEntity<Map<String,Integer>> userFindCount(){
        log.info("userFindCount");

        Map<String,Integer> map = new HashMap<>();
        map.put("userCount", userService.findCountUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }
}