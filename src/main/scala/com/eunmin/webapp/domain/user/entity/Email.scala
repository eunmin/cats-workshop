package com.eunmin.webapp.domain.user.entity

import com.eunmin.webapp.domain.user.error.{InvalidEmailFormatError, UserError}

case class Email(value: String)

object Email {
  def apply(value: String): Either[UserError, Email] =
    if (!"""(\w+)@([\w\.]+)""".r.unapplySeq(value).isDefined) {
      Left(InvalidEmailFormatError())
    } else {
      Right(new Email(value))
    }
}