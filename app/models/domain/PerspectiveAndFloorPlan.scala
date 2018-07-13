package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class PerspectiveAndFloorPlan(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    path: String,
    title: String,
    createdAt: Instant)

object PerspectiveAndFloorPlan {
  val tupled = (apply _).tupled

  implicit val perspectiveAndFloorPlanFormat: Format[PerspectiveAndFloorPlan] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "path").format[String] and
    (JsPath \ "title").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(PerspectiveAndFloorPlan.apply, unlift(PerspectiveAndFloorPlan.unapply))
}
