package com.eunmin.webapp.model.entity

import java.util.Date

case class User
(
  email: Email,
  firstName: Name,
  lastName: Name,
  createdAt: Date
)
