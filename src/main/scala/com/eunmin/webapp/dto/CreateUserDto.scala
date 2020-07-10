package com.eunmin.webapp.dto

import java.util.Date

import com.eunmin.webapp.model.entity.{Email, Name, User}

import scala.beans.BeanProperty

case class CreateUserDto
(
  @BeanProperty email: String,
  @BeanProperty firstName: String,
  @BeanProperty lastName: String
) {
  def toDomain(): Either[Exception,User] = for {
    email <- Email(this.email)
    firstName <- Name(this.firstName)
    lastName <- Name(this.lastName)
    createdAt = new Date()
  } yield User(email, firstName, lastName, createdAt)
}

object CreateUserDto {}