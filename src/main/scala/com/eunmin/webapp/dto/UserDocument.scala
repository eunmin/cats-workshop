package com.eunmin.webapp.dto

import java.util.Date

import com.eunmin.webapp.model.{Email, Name, User}

case class UserDocument
(
  email: String,
  firstName: String,
  lastName: String,
  createdAt: Date,
) {
  def toDomain(): User = {
    val email = Email(this.email)
    val firstName = Name(this.firstName)
    val lastName = Name(this.lastName)
    val createdAt = new Date()

    User(email, firstName, lastName, createdAt)
  }
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
