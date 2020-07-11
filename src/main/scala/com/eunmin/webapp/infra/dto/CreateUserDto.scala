package com.eunmin.webapp.infra.dto

import com.eunmin.webapp.domain.user.value.CreateUserInput

import scala.beans.BeanProperty

case class CreateUserDto
(
  @BeanProperty email: String,
  @BeanProperty firstName: String,
  @BeanProperty lastName: String
) {
  def toInput(): CreateUserInput = CreateUserInput(email, firstName, lastName)
}

object CreateUserDto {}