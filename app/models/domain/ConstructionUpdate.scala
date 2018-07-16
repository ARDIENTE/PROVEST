package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ConstructionUpdate(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    imagePath: String,
    title: String,
    createdAt: Instant)

object ConstructionUpdate {
  val tupled = (apply _).tupled

  implicit val ConstructionUpdateFormat: Format[ConstructionUpdate] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "image_path").format[String] and
    (JsPath \ "title").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(ConstructionUpdate.apply, unlift(ConstructionUpdate.unapply))
}
