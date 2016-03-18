package ch.becompany.db

import ch.becompany.dao.Tables.{Role => RoleDao, RoleRow}
import ch.becompany.db.Db._
import ch.becompany.model.Role
import slick.driver.H2Driver.api._

import scala.concurrent.{ExecutionContext, Future}

object Roles {

  def toRole(role: RoleRow): Role = Role.parse(role.id)

  def list()(implicit ex: ExecutionContext): Future[Seq[Role]] = {
    db.run(RoleDao.result) map (_ map toRole)
  }

}