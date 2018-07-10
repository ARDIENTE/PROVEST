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

// case class Account(
//     idAccountRef: UUID,
//     accountName: String,
//     password: String,
//     email: String,
//     createdAt: Instant) {
//   def toJson: JsObject = Account.Implicits.accountJsonWrites.writes(this).as[JsObject]
// }

// object Account {
//   val tupled = (apply _).tupled
//   // def tupled = (apply: (UUID, String, String, String, Instant) => Account).tupled

//   // def apply(
//   //   accountName: String,
//   //   password: String,
//   //   email: String): Account = apply(UUID.randomUUID, accountName, password, email, Instant.now)

//   object Implicits {
//     implicit val accountJsonReads: Reads[Account] = new Reads[Account] {
//       def reads(json: JsValue): JsResult[Account] = {
//          JsSuccess(apply(
//           (json \ "idAccountRef").as[UUID],
//           (json \ "accountName").as[String],
//           (json \ "password").as[String],
//           (json \ "email").as[String],
//           Instant.ofEpochSecond((json \ "created_at").as[Long])))
//       }
//     }

//     implicit val accountJsonWrites = new Writes[Account] {
//       def writes(account: Account): JsValue = Json.obj(
//         "idAccountRef" -> account.idAccountRef,
//         "accountName" -> account.accountName,
//         "password" -> account.password,
//         "email" -> account.email,
//         "created_at" -> account.createdAt.getEpochSecond)
//     }
//   }
// }
