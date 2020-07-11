package com.eunmin.webapp.infra.dto

import java.util.Date

import com.eunmin.webapp.domain.user.entity.{Email, Name, User}
import com.eunmin.webapp.domain.user.error.UserError

case class UserDocument
(
  email: String,
  firstName: String,
  lastName: String,
  createdAt: Date,
) {
  def toDomain(): Either[UserError,User] = for {
    email <- Email(this.email)
    firstName <- Name(this.firstName)
    lastName <- Name(this.lastName)
    createdAt = new Date()
  } yield User(email, firstName, lastName, createdAt)
}

object UserDocument {
  def fromDomain(user: User): UserDocument = {
    UserDocument(
      user.email.value,
      user.firstName.value,
      user.lastName.value,
      user.createdAt
    )
  }
}
