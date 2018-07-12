package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class OverView(
    id: UUID,
    projectID: UUID,
    subProjectID: UUID,
    totalLandArea: Double,
    phase: Int,
    status: String,
    address: String,
    mapURL: String,
    createdAt: Instant)

object OverView {
  val tupled = (apply _).tupled

  implicit val overViewFormat: Format[OverView] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "project_id").format[UUID] and
    (JsPath \ "sub_project_id").format[UUID] and
    (JsPath \ "total_land_area").format[Double] and
    (JsPath \ "phase").format[Int] and
    (JsPath \ "status").format[String] and
    (JsPath \ "address").format[String] and
    (JsPath \ "map_URL").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(OverView.apply, unlift(OverView.unapply))
}
