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
    (JsPath \ "id_account_ref").format[UUID] and
    (JsPath \ "account_name").format[String] and
    (JsPath \ "password").format[String] and
    (JsPath \ "email").format[String] and
    (JsPath \ "createdAt").format[Instant]
  )(Account.apply, unlift(Account.unapply))
}
