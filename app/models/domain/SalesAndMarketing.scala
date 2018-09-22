package models.domain

import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SalesAndMarketing(
    id: UUID,
    title: String,
    number: String,
    createdAt: Instant)

object SalesAndMarketing {
  val tupled = (apply _).tupled

  implicit val SalesAndMarketingFormat: Format[SalesAndMarketing] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "title").format[String] and
    (JsPath \ "number").format[String] and
    (JsPath \ "created_at").format[Instant]
  )(SalesAndMarketing.apply, unlift(SalesAndMarketing.unapply))
}
