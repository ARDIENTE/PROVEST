package models.domain

import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Project(id: UUID, name: String)

object Project {
  val tupled = (apply _).tupled

  implicit val projectFormat: Format[Project] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "name").format[String]
  )(Project.apply, unlift(Project.unapply))
}
