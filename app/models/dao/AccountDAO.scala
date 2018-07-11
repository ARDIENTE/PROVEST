package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain._
import scala.math.BigDecimal._
import utils._

@Singleton
final class AccountDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class AccountTable(tag: Tag) extends Table[Account](tag, "ACCOUNT") {
    def idAccountRef = column[UUID]("ID_ACCOUNT_REF", O.PrimaryKey)
    def accountName = column[String]("ACCOUNT_NAME")
    def password = column[String]("PASSWORD")
    def address = column[String]("ADDRESS")
    def mapURL = column[String]("MAP_URL")
    def imagePath = column[String]("IMAGE_PATH")
    def createdAt = column[Instant]("CREATE_AT")

    def * = (
      idAccountRef,
      accountName,
      password,
      address,
      mapURL,
      imagePath,
      createdAt) <> (
      Account.tupled, Account.unapply)
  }

  object Query extends TableQuery(new AccountTable(_)) {
    @inline def apply(idAccountRef: UUID) = this.withFilter(_.idAccountRef === idAccountRef)
    @inline def apply(accountName: String) = this.withFilter(_.accountName === accountName)
  }
}
