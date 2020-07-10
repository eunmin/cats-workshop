package com.eunmin.webapp.service

import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.exception.EmailAlreadyExsistsException
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(userRepository: UserRepository) {
  def create(input: CreateUserDto): UserDto = {
    val user = input.toDomain()
    if(userRepository.findOneByEmail(user.email.value) != null) {
      throw EmailAlreadyExsistsException()
    }
    val createdUser = userRepository.save(user)
    UserDto.fromDomain(createdUser)
  }
}
