package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.SalesAndMarketing
import scala.math.BigDecimal._
import utils._

@Singleton
final class SalesAndMarketingDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class SalesAndMarketingTable(tag: Tag) extends 
    Table [SalesAndMarketing](tag, "SALES_AND_MARKETING") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def title = column[String]("TITLE")
      def number = column[String]("NUMBER")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        title,
        number,
        createdAt) <> (
        SalesAndMarketing.tupled, SalesAndMarketing.unapply)
  }

  object Query extends TableQuery(new SalesAndMarketingTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
