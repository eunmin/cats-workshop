package com.eunmin.webapp.dto

import java.util.Date
import com.eunmin.webapp.model.{Email, Name, User}
import scala.beans.BeanProperty

case class CreateUserDto
(
  @BeanProperty email: String,
  @BeanProperty firstName: String,
  @BeanProperty lastName: String
) {
  def toDomain(): User = {
    val email = Email(this.email)
    val firstName = Name(this.firstName)
    val lastName = Name(this.lastName)
    val createdAt = new Date()

    User(email, firstName, lastName, createdAt)
  }
}

object CreateUserDto {}