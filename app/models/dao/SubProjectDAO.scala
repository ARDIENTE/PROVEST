package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.SubProject
import scala.math.BigDecimal._
import utils._

@Singleton
final class SubProjectDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class SubProjectTable(tag: Tag) extends 
    Table[SubProject](tag, "SUB_PROJECT") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def name = column[String]("NAME")

      def * = (id, name) <> (SubProject.tupled, SubProject.unapply)
  }

  object Query extends TableQuery(new SubProjectTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
