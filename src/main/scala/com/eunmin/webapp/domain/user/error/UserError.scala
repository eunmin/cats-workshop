package com.eunmin.webapp.domain.user.error

trait UserError
case class EmailAlreadyExistsError() extends UserError
case class InvalidEmailFormatError() extends UserError
case class InvalidNameFormatError() extends UserError
case class UnknownError(message: String) extends UserError
