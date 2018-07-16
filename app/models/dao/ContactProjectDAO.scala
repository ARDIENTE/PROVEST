package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.ContactProject
import scala.math.BigDecimal._
import utils._

@Singleton
final class ContactProjectDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class ContactProjectTable(tag: Tag) extends 
    Table [ContactProject](tag, "CONTACT_PROJECT") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def projectID = column[UUID]("PROJECT_ID")
      def subProjectID = column[UUID]("SUB_PROJECT_ID")
      def name = column[String]("NAME")
      def position = column[String]("POSITION")
      def number = column[String]("NUMBER")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        projectID,
        subProjectID,
        name,
        position,
        number,
        createdAt) <> (
        ContactProject.tupled, ContactProject.unapply)
  }

  object Query extends TableQuery(new ContactProjectTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
