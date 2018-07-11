package models.domain

import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SubProject(id: UUID, name: String)

object SubProject {
  val tupled = (apply _).tupled

  implicit val subProjectFormat: Format[SubProject] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "name").format[String]
  )(SubProject.apply, unlift(SubProject.unapply))
}
