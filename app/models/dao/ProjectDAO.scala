package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.Project
import scala.math.BigDecimal._
import utils._

@Singleton
final class ProjectDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class ProjectTable(tag: Tag) extends 
    Table[Project](tag, "PROJECT") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def name = column[String]("NAME")

      def * = (id, name) <> (Project.tupled, Project.unapply)
  }

  object Query extends TableQuery(new ProjectTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
