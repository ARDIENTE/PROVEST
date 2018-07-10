package models.domain

import java.util.{UUID, Date}
import java.sql.Timestamp
import java.time.Instant
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Account(
    idAccountRef: UUID,
    accountName: String,
    password: String,
    email: String,
    createdAt: Instant)

object Account {
  val tupled = (apply _).tupled

  implicit val locationFormat: Format[Account] = (
    (JsPath \ "lat").format[UUID] and
    (JsPath \ "lat").format[String] and
    (JsPath \ "lat").format[String] and
    (JsPath \ "lat").format[String] and
    (JsPath \ "long").format[Instant]
  )(Account.apply, unlift(Account.unapply))
}
