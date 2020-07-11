package com.eunmin.webapp.domain.user.entity

import com.eunmin.webapp.domain.user.error.{InvalidNameFormatError, UserError}

case class Name(value: String)

object Name {
  def apply(value: String): Either[UserError,Name] =
    if (!value.matches("^[a-zA-Z0-9]*$")) {
      Left(InvalidNameFormatError())
    } else {
      Right(new Name(value))
    }
}
