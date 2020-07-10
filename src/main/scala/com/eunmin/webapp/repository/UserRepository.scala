package com.eunmin.webapp.repository

import com.eunmin.webapp.dto.UserDocument
import com.eunmin.webapp.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.stereotype.Repository
import cats.effect.IO
import cats.data.EitherT

@Repository
class UserRepository @Autowired()(mongoTemplate: MongoTemplate) {
  private def findOne(query: Query): IO[UserDocument] = IO {
    mongoTemplate.findOne(query, classOf[UserDocument])
  }

  private def save(userDocument: UserDocument): IO[UserDocument] = IO {
    mongoTemplate.save(userDocument)
  }

  def findOneByEmail(email: String): IO[Either[Exception,Option[User]]]= {
    val query = new Query()
    query.addCriteria(Criteria.where("email").is(email))
    findOne(query).map { userDocument =>
      if (userDocument == null) {
        Right(None)
      } else {
        userDocument.toDomain().map(Some(_))
      }
    }
  }

  def save(user: User): IO[Either[Exception,User]] = {
    val userDocument = UserDocument.fromDomain(user)
    save(userDocument).map(_.toDomain())
  }
}
