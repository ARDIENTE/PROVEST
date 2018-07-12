package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.OverView
import scala.math.BigDecimal._
import utils._

@Singleton
final class OverViewDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class OverViewTable(tag: Tag) extends 
    Table[OverView](tag, "OVERVIEW") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def projectID = column[UUID]("PROJECT_ID")
      def subProjectID = column[UUID]("SUB_PROJECT_ID")
      def totalLandArea = column[Double]("TOTAL_LAND_AREA")
      def phase = column[Int]("PHASE")
      def status = column[String]("STATUS")
      def address = column[String]("ADDRESS")
      def mapURL = column[String]("MAP_URL")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        projectID,
        subProjectID,
        totalLandArea,
        phase,
        status,
        address,
        mapURL,
        createdAt) <> (
        OverView.tupled, OverView.unapply)
  }

  object Query extends TableQuery(new OverViewTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
