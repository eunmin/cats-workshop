package com.eunmin.webapp.model.usecase

import cats.data.EitherT
import cats.effect.IO
import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.exception.EmailAlreadyExsistsException
import com.eunmin.webapp.model.entity.User
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import cats.free.Free
import cats.free.Free.{liftF, pure}

sealed trait UserDependencyA[A] {}
case class FindOneByEmail(email: String) extends UserDependencyA[IO[Either[Exception,Option[User]]]]
case class Save(user: User) extends UserDependencyA[IO[Either[Exception,User]]]

object UserUseCase {
  type UserDependency[A] = Free[UserDependencyA, A]

  def findOneByEmail(email: String): UserDependency[IO[Either[Exception,Option[User]]]] =
    liftF[UserDependencyA, IO[Either[Exception,Option[User]]]](FindOneByEmail(email))
  def save(user: User): UserDependency[IO[Either[Exception,User]]] =
    liftF[UserDependencyA, IO[Either[Exception,User]]](Save(user))

  def create(input: CreateUserDto): EitherT[UserDependency,Exception,User] = for {
    user <- EitherT(pure[UserDependencyA, Either[Exception,User]](input.toDomain()))
    createdUser <- EitherT(save(user))
  } yield createdUser

//  def create(input: CreateUserDto): EitherT[IO,Exception,UserDto] = ???
//  def create2(input: CreateUserDto): EitherT[IO,Exception,UserDto] = for {
//    user        <- EitherT(IO(input.toDomain()))
//    findedUser  <- EitherT(userRepository.findOneByEmail(user.email.value))
//    _           <- EitherT(IO(findedUser.fold[Either[Exception,Unit]](
//                      Right(())
//                    )( _ =>
//                      Left(EmailAlreadyExsistsException())
//                    )))
//    createdUser <- EitherT(userRepository.save(user))
//  } yield UserDto.fromDomain(createdUser)
}