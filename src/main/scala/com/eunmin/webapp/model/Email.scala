package com.eunmin.webapp.model

import com.eunmin.webapp.exception.InvalidEmailFormatException
import javax.persistence.Embeddable

@Embeddable
case class Email(value: String)

object Email {
  def apply(value: String): Either[Exception, Email] =
    if (!"""(\w+)@([\w\.]+)""".r.unapplySeq(value).isDefined) {
      Left(InvalidEmailFormatException())
    } else {
      Right(new Email(value))
    }
}