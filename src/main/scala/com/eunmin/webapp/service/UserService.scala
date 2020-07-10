package com.eunmin.webapp.service

import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.exception.EmailAlreadyExsistsException
import com.eunmin.webapp.model.User
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(userRepository: UserRepository) {
  def create(input: CreateUserDto): Either[Exception,UserDto] = for {
    user <- input.toDomain()
    findedUser <- userRepository.findOneByEmail(user.email.value)
    _ <- findedUser.fold[Either[Exception,Unit]](
      Right(())
    )( _ =>
      Left(EmailAlreadyExsistsException())
    )
    createdUser <- userRepository.save(user)
  } yield UserDto.fromDomain(createdUser)
}