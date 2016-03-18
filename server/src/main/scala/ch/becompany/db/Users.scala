package ch.becompany.db

import slick.driver.H2Driver.api._
import ch.becompany.model.{Role, User}
import ch.becompany.db.Db._
import ch.becompany.dao.Tables.{User => UserDao, UserRow}
import scala.concurrent.{ExecutionContext, Future}

object Users {

  def toUser(row: UserRow): User =
      User(Some(row.id), row.email, row.givenName, row.familyName, Role.parse(row.role))

  def find(id: Long)(implicit ex: ExecutionContext): Future[Option[User]] = {
    db.run(UserDao.filter(_.id === id).result.headOption map (_ map toUser))
  }

  def findByEmail(email: String)(implicit ex: ExecutionContext): Future[Option[User]] = {
    db.run(UserDao.filter(_.email === email).result.headOption map (_ map toUser))
  }

  def list()(implicit ex: ExecutionContext): Future[Seq[User]] = {
    db.run(UserDao.result) map (_ map toUser)
  }

  def add(user: User)(implicit ex: ExecutionContext): Future[Long] = {
    db.run((UserDao.map(u => (u.email, u.givenName, u.familyName, u.role)) returning
      UserDao.map(_.id)) += (user.email, user.givenName, user.familyName, user.role.toString))
  }

  def update(id: Long, user: User)(implicit ex: ExecutionContext) = {
    val query = (for {
      u <- UserDao if u.id === id
    } yield {
      (u.email, u.givenName, u.familyName, u.role)
    }).update((user.email, user.givenName, user.familyName, user.role.toString))
    db.run(query)
  }
}