package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class AmenitiesAndFacility(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    imagePath: String,
    title: String,
    description: String,
    createdAt: Instant)

object AmenitiesAndFacility {
  val tupled = (apply _).tupled

  implicit val AmenitiesAndFacilityFormat: Format[AmenitiesAndFacility] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "image_path").format[String] and
    (JsPath \ "title").format[String] and
    (JsPath \ "description").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(AmenitiesAndFacility.apply, unlift(AmenitiesAndFacility.unapply))
}
