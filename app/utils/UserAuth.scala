package utils

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global

case class UserAuth0(accountName: String, password: String)
object UserAuth0 {
  val tupled = (apply _).tupled

  implicit val locationFormat: Format[UserAuth0] = (
    (JsPath \ "account_name").format[String] and
    (JsPath \ "password").format[String]
  )(UserAuth0.apply, unlift(UserAuth0.unapply))
}

sealed trait UserAuth[A] {
  def SESSION_USERNAME_KEY: A
}

@javax.inject.Singleton
object UserAuth {
  def SESSION_USERNAME_KEY[A](implicit userAuth: UserAuth[A]) = userAuth.SESSION_USERNAME_KEY

  implicit def GET_USER_SESSION[A >: String]: UserAuth[A] = new UserAuth[A] {
    def SESSION_USERNAME_KEY: A = "Authenticated User"
  }
}
