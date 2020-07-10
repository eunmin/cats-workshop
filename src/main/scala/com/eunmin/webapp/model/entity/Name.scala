package com.eunmin.webapp.model.entity

import com.eunmin.webapp.exception.InvalidNameFormatException

case class Name(value: String)

object Name {
  def apply(value: String): Either[Exception,Name] =
    if (!value.matches("^[a-zA-Z0-9]*$")) {
      Left(InvalidNameFormatException())
    } else {
      Right(new Name(value))
    }
}
