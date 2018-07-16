package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.Email
import scala.math.BigDecimal._
import utils._

@Singleton
final class EmailDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class EmailTable(tag: Tag) extends
    Table[Email](tag, "EMAIL") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def title = column[String]("TITLE")
      def mail = column[String]("EMAIL")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        title,
        mail,
        createdAt) <> (
        Email.tupled, Email.unapply)
  }

  object Query extends TableQuery(new EmailTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
