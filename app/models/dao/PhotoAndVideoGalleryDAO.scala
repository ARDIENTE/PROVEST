package models.dao

import java.util.UUID
import java.time.Instant
import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain.PhotoAndVideoGallery
import scala.math.BigDecimal._
import utils._

@Singleton
final class PhotoAndVideoGalleryDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  protected class PhotoAndVideoGalleryTable(tag: Tag) extends 
    Table[PhotoAndVideoGallery](tag, "PHOTO_AND_VIDEO_GALLERY") {
      def id = column[UUID]("ID", O.PrimaryKey)
      def projectID = column[UUID]("PROJECT_ID")
      def subProjectID = column[UUID]("SUB_PROJECT_ID")
      def isVideo = column[Boolean]("IS_VIDEO")
      def imagePath = column[String]("IMAGE_PATH")
      def title = column[String]("TITLE")
      def createdAt = column[Instant]("CREATED_AT")

      def * = (
        id,
        projectID,
        subProjectID,
        isVideo,
        imagePath,
        title,
        createdAt) <> (
        PhotoAndVideoGallery.tupled, PhotoAndVideoGallery.unapply)
  }

  object Query extends TableQuery(new PhotoAndVideoGalleryTable(_)) {
    @inline def apply(id: UUID) = this.withFilter(_.id === id)
  }
}
