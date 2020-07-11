package com.eunmin.webapp.infra.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
case class EmailAlreadyExsistsException() extends RuntimeException("Email already exsists")
