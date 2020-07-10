package com.eunmin.webapp.controller

import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RestController}

@RestController
class UserController @Autowired()(userService: UserService) {
  @PostMapping(path = Array("/users"))
  def create(@RequestBody input: CreateUserDto): UserDto = {
    userService.create(input).fold(ex => throw ex, identity).unsafeRunSync()
  }
}
