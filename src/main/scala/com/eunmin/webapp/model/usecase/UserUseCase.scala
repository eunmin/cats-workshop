package com.eunmin.webapp.model.usecase

import cats.data.EitherT
import com.eunmin.webapp.dto.{CreateUserDto, UserDto}
import com.eunmin.webapp.exception.EmailAlreadyExsistsException
import com.eunmin.webapp.model.entity.User
import cats.free.Free
import cats.free.Free.{liftF, pure}

sealed trait UserDependencyA[A] {}
case class FindOneByEmail(email: String) extends UserDependencyA[Either[Exception,Option[User]]]
case class Save(user: User) extends UserDependencyA[Either[Exception,User]]

object UserUseCase {
  type UserDependency[A] = Free[UserDependencyA, A]

  def findOneByEmail(email: String): UserDependency[Either[Exception,Option[User]]] =
    liftF[UserDependencyA, Either[Exception,Option[User]]](FindOneByEmail(email))
  def save(user: User): UserDependency[Either[Exception,User]] =
    liftF[UserDependencyA, Either[Exception,User]](Save(user))

  def toFree[A](a: A): Free[UserDependencyA, A] = pure(a)

  def create(input: CreateUserDto): UserDependency[Either[Exception,UserDto]] = (for {
    user <- EitherT(toFree[Either[Exception,User]](input.toDomain()))
    findedUser  <- EitherT(findOneByEmail(user.email.value))
    _ = println(findedUser)
    _           <- EitherT(toFree[Either[Exception,Unit]](findedUser.fold[Either[Exception,Unit]](
                      Right(())
                    )( _ =>
                      Left(EmailAlreadyExsistsException())
                    )))
    createdUser <- EitherT(save(user))
    userDto <- EitherT(toFree[Either[Exception,UserDto]](Right(UserDto.fromDomain(createdUser))))
  } yield userDto).value
}