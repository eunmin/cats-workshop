package com.eunmin.webapp.domain.user.value

import java.util.Date

import com.eunmin.webapp.domain.user.entity.{Email, Name, User}
import com.eunmin.webapp.domain.user.error.UserError

case class CreateUserInput(email: String, firstName: String, lastName: String) {
  def toUser(): Either[UserError,User] = for {
    email <- Email(this.email)
    firstName <- Name(this.firstName)
    lastName <- Name(this.lastName)
    createdAt = new Date()
  } yield User(email, firstName, lastName, createdAt)
}

