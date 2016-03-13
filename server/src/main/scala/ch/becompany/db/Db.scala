package ch.becompany.db

import slick.driver.H2Driver.api._

object Db {
  
  val db = Database.forURL("jdbc:h2:./target/db");

}