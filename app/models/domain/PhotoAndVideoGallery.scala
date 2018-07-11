package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class PhotoAndVideoGallery(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    isVideo: Boolean,
    path: String,
    title: String,
    createdAt: Instant)

object PhotoAndVideoGallery {
  val tupled = (apply _).tupled

  implicit val PhotoAndVideoGalleryFormat: Format[PhotoAndVideoGallery] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "is_video").format[Boolean] and
    (JsPath \ "path").format[String] and
    (JsPath \ "title").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(PhotoAndVideoGallery.apply, unlift(PhotoAndVideoGallery.unapply))
}
