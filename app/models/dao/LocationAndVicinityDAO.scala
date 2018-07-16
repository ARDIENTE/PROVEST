package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.LocationAndVicinity
import scala.math.BigDecimal._
import utils._

@Singleton
final class LocationAndVicinityDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class LocationAndVicinityTable(tag: Tag) extends
    Table [LocationAndVicinity](tag, "LOCATION_AND_VICINITY") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def projectID = column[UUID]("PROJECT_ID")
      def subProjectID = column[UUID]("SUB_PROJECT_ID")
      def imagePath = column[String]("NAME")
      def description = column[String]("DESCRIPTION")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        projectID,
        subProjectID,
        imagePath,
        description,
        createdAt) <> (
        LocationAndVicinity.tupled, LocationAndVicinity.unapply)
  }

  object Query extends TableQuery(new LocationAndVicinityTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
