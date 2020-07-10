package com.eunmin.webapp.service

import cats.{Id, ~>}
import com.eunmin.webapp.model.entity.User
import com.eunmin.webapp.model.usecase.{Save, UserDependencyA}
import com.eunmin.webapp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(userRepository: UserRepository) {
  def create(user: User): UserDependencyA ~> Id = new (UserDependencyA ~> Id) {
    override def apply[A](fa: UserDependencyA[A]): Id[A] =
      fa match {
        case Save(user) =>
          userRepository.save(user)
      }
  }
}
