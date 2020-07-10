package com.eunmin.webapp.service

import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.exception.EmailAlreadyExsistsException
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import cats.data.EitherT
import cats.effect.IO

@Service
class UserService @Autowired()(userRepository: UserRepository) {
  def create(input: CreateUserDto): EitherT[IO,Exception,UserDto] = for {
    user        <- EitherT(IO(input.toDomain()))
    findedUser  <- EitherT(userRepository.findOneByEmail(user.email.value))
    _           <- EitherT(IO(findedUser.fold[Either[Exception,Unit]](
                      Right(())
                    )( _ =>
                      Left(EmailAlreadyExsistsException())
                    )))
    createdUser <- EitherT(userRepository.save(user))
  } yield UserDto.fromDomain(createdUser)
}