package models.domain

import java.util.{UUID, Date}
import java.sql.Timestamp
import java.time.Instant
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Account(
    id: UUID,
    accountName: String,
    password: String,
    address: String,
    mapURL: String,
    imagePath: String,
    createdAt: Instant)

object Account {
  val tupled = (apply _).tupled

  implicit val locationFormat: Format[Account] = (
    (JsPath \ "id").format[UUID] and
    (JsPath \ "account_name").format[String] and
    (JsPath \ "password").format[String] and
    (JsPath \ "address").format[String] and
    (JsPath \ "map_URL").format[String] and
    (JsPath \ "image_path").format[String] and
    (JsPath \ "createdAt").format[Instant]
  )(Account.apply, unlift(Account.unapply))
}
