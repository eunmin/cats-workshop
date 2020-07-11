package com.eunmin.webapp.infra.dto

import com.eunmin.webapp.domain.user.entity.User

import scala.beans.BeanProperty

case class UserDto
(
  @BeanProperty email: String,
  @BeanProperty firstName: String,
  @BeanProperty lastName: String,
  @BeanProperty createdAt: Long
)

object UserDto {
  def fromDomain(user: User): UserDto = {
    UserDto(
      user.email.value,
      user.firstName.value,
      user.lastName.value,
      user.createdAt.getTime
    )
  }
}