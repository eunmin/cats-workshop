package com.eunmin.webapp.model

import java.util.Date

case class User
(
  email: Email,
  firstName: Name,
  lastName: Name,
  createdAt: Date
)