package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Email(
    id: UUID,
    title: String,
    mail: String,
    createdAt: Instant)

object Email {
  val tupled = (apply _).tupled

  implicit val EmailFormat: Format[Email] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "title").format[String] and
    (JsPath \ "mail").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(Email.apply, unlift(Email.unapply))
}
