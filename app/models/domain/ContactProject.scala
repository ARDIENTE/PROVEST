package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ContactProject(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    name: String,
    position: String,
    number: String,
    createdAt: Instant)

object ContactProject {
  val tupled = (apply _).tupled

  implicit val contactProjectFormat: Format[ContactProject] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "name").format[String] and
    (JsPath \ "position").format[String] and
    (JsPath \ "number").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(ContactProject.apply, unlift(ContactProject.unapply))
}
