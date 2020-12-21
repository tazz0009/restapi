package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.UserJpaRepo;
import com.example.demo.advice.exception.CUserNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.model.response.CommonResult;
import com.example.demo.model.response.ListResult;
import com.example.demo.model.response.SingleResult;
import com.example.demo.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

	private final UserJpaRepo userJpaRepo;
	private final ResponseService responseService;

	@ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
	@GetMapping(value = "/user")
	public ListResult<User> findAllUser() {
		return responseService.getListResult(userJpaRepo.findAll());
	}
	
	@ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다.")
	@GetMapping(value = "/user/{msrl}")
	public SingleResult<User> findUserById(
			@ApiParam(value = "회원ID",  required = true) @PathVariable long msrl,
            @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) throws Exception {
		return responseService.getSingResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
	}
	
	@ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
	@PostMapping(value = "/user") 
	public SingleResult<User> save(
				@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
				@ApiParam(value = "회원이름", required = true) @RequestParam String name
			) {
		User user = User.builder()
				.uid(uid)
				.name(name)
				.build();
		return responseService.getSingResult(userJpaRepo.save(user));
	}

	@ApiOperation(value = "회원 수정", notes = "회원정보을 수정한다.")
	@PutMapping(value = "/user") 
	public SingleResult<User> modify(
			@ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
			@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name
			) {
		User user = User.builder()
				.msrl(msrl)
				.uid(uid)
				.name(name)
				.build();
		return responseService.getSingResult(userJpaRepo.save(user));
	}

	@ApiOperation(value = "회원 삭제", notes = "회원정보을 삭제한다.")
	@DeleteMapping(value = "/user/{msrl}") 
	public CommonResult delete(
			@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
		userJpaRepo.deleteById(msrl);
		return responseService.getSuccessResult();
	}
	
}
