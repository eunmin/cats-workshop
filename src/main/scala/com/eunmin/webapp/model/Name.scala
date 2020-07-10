package com.eunmin.webapp.model

import com.eunmin.webapp.exception.InvalidNameFormatException

case class Name(value: String)

object Name {
  def apply(value: String) = {
    if (!value.matches("^[a-zA-Z0-9]*$")) {
      throw InvalidNameFormatException()
    }
    new Name(value)
  }
}
