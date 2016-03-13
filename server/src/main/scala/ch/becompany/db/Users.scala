package ch.becompany.db

import slick.driver.H2Driver.api._
import ch.becompany.model.User
import ch.becompany.db.Db._
import ch.becompany.dao.Tables.{User => UserDao, UserRow}
import scala.concurrent.{ExecutionContext, Future}

object Users {

  def toUser: UserRow => User = {
    case UserRow(id, email, givenName, familyName) =>
      User(Some(id), email, givenName, familyName)
  }

  def find(id: Long)(implicit ex: ExecutionContext): Future[Option[User]] = {
    db.run(UserDao.filter(_.id === id).result.headOption map (_ map toUser))
  }

  def list()(implicit ex: ExecutionContext): Future[Seq[User]] = {
    db.run(UserDao.result) map (_ map toUser)
  }

  def add(user: User)(implicit ex: ExecutionContext): Future[Long] = {
    db.run((UserDao.map(u => (u.email, u.givenName, u.familyName)) returning
      UserDao.map(_.id)) += (user.email, user.givenName, user.familyName))
  }

  def update(id: Long, user: User)(implicit ex: ExecutionContext) = {
    val query = (for {
      u <- UserDao if u.id === id
    } yield {
      (u.email, u.givenName, u.familyName)
    }).update((user.email, user.givenName, user.familyName))
    db.run(query)
  }
}