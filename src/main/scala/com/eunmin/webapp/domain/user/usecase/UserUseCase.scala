package com.eunmin.webapp.domain.user.usecase

import cats.data.EitherT
import com.eunmin.webapp.domain.user.entity.User
import cats.free.Free
import cats.free.Free.{liftF, pure}
import com.eunmin.webapp.domain.user.error.{EmailAlreadyExistsError, UserError}
import com.eunmin.webapp.domain.user.value.CreateUserInput

sealed trait UserDependencyA[A] {}
case class FindOneByEmail(email: String) extends UserDependencyA[Either[UserError,Option[User]]]
case class Save(user: User) extends UserDependencyA[Either[UserError,User]]

object UserUseCase {
  type UserDependency[A] = Free[UserDependencyA, A]

  def findOneByEmail(email: String): UserDependency[Either[UserError,Option[User]]] =
    liftF[UserDependencyA, Either[UserError,Option[User]]](FindOneByEmail(email))
  def save(user: User): UserDependency[Either[UserError,User]] =
    liftF[UserDependencyA, Either[UserError,User]](Save(user))

  def toFree[A](a: A): Free[UserDependencyA, A] = pure(a)

  def create(input: CreateUserInput): UserDependency[Either[UserError,User]] = (for {
    user <- EitherT(toFree[Either[UserError,User]](input.toUser()))
    findedUser  <- EitherT(findOneByEmail(user.email.value))
    _ = println(findedUser)
    _           <- EitherT(toFree[Either[UserError,Unit]](findedUser.fold[Either[UserError,Unit]](
                      Right(())
                    )( _ =>
                      Left(EmailAlreadyExistsError())
                    )))
    createdUser <- EitherT(save(user))
  } yield createdUser).value
}