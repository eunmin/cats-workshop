package com.eunmin.webapp.infra.controller

import cats.{Id, ~>}
import com.eunmin.webapp.domain.user.error.{EmailAlreadyExistsError, InvalidEmailFormatError, InvalidNameFormatError, UnknownError}
import com.eunmin.webapp.infra.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.domain.user.usecase.{FindOneByEmail, Save, UserDependencyA, UserUseCase}
import com.eunmin.webapp.infra.exception.{EmailAlreadyExsistsException, InvalidEmailFormatException}
import com.eunmin.webapp.infra.repository.UserRepository
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
    UserUseCase.create(input.toInput()).foldMap(dependencies).fold(error => error match {
      case EmailAlreadyExistsError() => throw EmailAlreadyExsistsException()
      case InvalidEmailFormatError() => throw InvalidEmailFormatException()
      case InvalidNameFormatError() => throw InvalidEmailFormatException()
      case UnknownError(message) => throw new RuntimeException(message)
    }, user => UserDto.fromDomain(user))
  }
}
