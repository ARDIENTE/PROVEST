package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Contact(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    name: String,
    position: String,
    number: String,
    createdAt: Instant)

object Contact {
  val tupled = (apply _).tupled

  implicit val ContactFormat: Format[Contact] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "name").format[String] and
    (JsPath \ "position").format[String] and
    (JsPath \ "number").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(Contact.apply, unlift(Contact.unapply))
}
