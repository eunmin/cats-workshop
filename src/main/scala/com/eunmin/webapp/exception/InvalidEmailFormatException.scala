package com.eunmin.webapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
case class InvalidEmailFormatException() extends RuntimeException
