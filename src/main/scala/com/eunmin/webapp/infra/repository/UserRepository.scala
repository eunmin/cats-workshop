package com.eunmin.webapp.infra.repository

import com.eunmin.webapp.infra.dto.UserDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.stereotype.Repository
import com.eunmin.webapp.domain.user.entity.User
import com.eunmin.webapp.domain.user.error.UserError

@Repository
class UserRepository @Autowired()(mongoTemplate: MongoTemplate) {
  def findOneByEmail(email: String): Either[UserError,Option[User]]= {
    val query = new Query()
    query.addCriteria(Criteria.where("email").is(email))
    val userDocument = mongoTemplate.findOne(query, classOf[UserDocument])
    if (userDocument == null) {
      Right(None)
    } else {
      userDocument.toDomain().map(Some(_))
    }
  }

  def save(user: User): Either[UserError,User] = {
    val userDocument = UserDocument.fromDomain(user)
    mongoTemplate.save(userDocument).toDomain()
  }
}
