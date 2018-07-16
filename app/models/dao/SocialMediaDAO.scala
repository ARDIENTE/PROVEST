package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.SocialMedia
import scala.math.BigDecimal._
import utils._

@Singleton
final class SocialMediaDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class SocialMediaTable(tag: Tag) extends 
    Table[SocialMedia](tag, "SOCIAL_MEDIA") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def title = column[String]("TITLE")
      def url = column[String]("URL")

      def * = (
        id,
        title,
        url) <> (
        SocialMedia.tupled, SocialMedia.unapply)
  }

  object Query extends TableQuery(new SocialMediaTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
