package com.eunmin.webapp.repository

import com.eunmin.webapp.dto.UserDocument
import com.eunmin.webapp.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.stereotype.Repository

@Repository
class UserRepository @Autowired()(mongoTemplate: MongoTemplate) {
  def findOneByEmail(email: String): User = {
    val query = new Query()
    query.addCriteria(Criteria.where("email").is(email))

    val userDocument = mongoTemplate.findOne(query, classOf[UserDocument])
    if (userDocument == null) {
      return null
    }
    userDocument.toDomain()
  }

  def save(user: User): User = {
    val userDocument = UserDocument.fromDomain(user)
    mongoTemplate.save(userDocument).toDomain()
  }
}
