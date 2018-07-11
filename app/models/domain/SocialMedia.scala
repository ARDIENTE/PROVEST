package models.domain

import java.util.UUID
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SocialMedia(
    id: UUID,
    title: String,
    url: String)

object SocialMedia {
  val tupled = (apply _).tupled

  implicit val SocialMediaFormat: Format[SocialMedia] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "title").format[String] and
    (JsPath \ "url").format[String]
  )(SocialMedia.apply, unlift(SocialMedia.unapply))
}
