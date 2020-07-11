package com.eunmin.webapp.domain.user.entity

import java.util.Date

case class User(email: Email, firstName: Name, lastName: Name, createdAt: Date)
