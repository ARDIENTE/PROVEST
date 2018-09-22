package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.ConstructionUpdate
import scala.math.BigDecimal._
import utils._

@Singleton
final class ConstructionUpdateDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class ConstructionUpdateTable(tag: Tag) extends 
    Table[ConstructionUpdate](tag, "CONSTRUCTION_UPDATE") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def projectID = column[UUID]("PROJECT_ID")
      def subProjectID = column[UUID]("SUB_PROJECT_ID")
      def imagePath = column[String]("IMAGE_PATH")
      def title = column[String]("TITLE")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        projectID,
        subProjectID,
        imagePath,
        title,
        createdAt) <> (
        ConstructionUpdate.tupled, ConstructionUpdate.unapply)
  }

  object Query extends TableQuery(new ConstructionUpdateTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
