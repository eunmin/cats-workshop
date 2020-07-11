package com.eunmin.webapp.controller

import cats.{Id, ~>}
import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.model.usecase.{FindOneByEmail, Save, UserDependencyA, UserUseCase}
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RestController}

@RestController
class UserController @Autowired()(userRepository: UserRepository) {

  val dependencies: UserDependencyA ~> Id = new (UserDependencyA ~> Id) {
    override def apply[A](fa: UserDependencyA[A]): Id[A] =
      fa match {
        case Save(user) =>
          userRepository.save(user)
        case FindOneByEmail(email) =>
          userRepository.findOneByEmail(email)
      }
  }

  @PostMapping(path = Array("/users"))
  def create(@RequestBody input: CreateUserDto): UserDto = {
    UserUseCase.create(input).foldMap(dependencies).fold(ex => throw ex, identity)
  }
}
