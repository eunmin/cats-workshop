package com.eunmin.webapp.repository

import com.eunmin.webapp.dto.UserDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.stereotype.Repository
import com.eunmin.webapp.model.entity.User

@Repository
class UserRepository @Autowired()(mongoTemplate: MongoTemplate) {
  def findOneByEmail(email: String): Either[Exception,Option[User]]= {
    val query = new Query()
    query.addCriteria(Criteria.where("email").is(email))
    val userDocument = mongoTemplate.findOne(query, classOf[UserDocument])
    if (userDocument == null) {
      Right(None)
    } else {
      userDocument.toDomain().map(Some(_))
    }
  }

  def save(user: User): Either[Exception,User] = {
    val userDocument = UserDocument.fromDomain(user)
    mongoTemplate.save(userDocument).toDomain()
  }
}
