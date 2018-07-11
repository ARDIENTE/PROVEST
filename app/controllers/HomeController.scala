package controllers

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor._
import akka.stream._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.mvc.WebSocket._
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.streams._
import play.api.i18n.{ I18nSupport, MessagesApi }
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import actors._
import models.domain._
import models.service._

@Singleton
class HomeController @Inject() (
  accountService: AccountService,
  implicit val system: ActorSystem,
  implicit val materializer: Materializer,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {
  import utils.UserAuth0

  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[JsValue, JsValue]
  private val verifyShortText = "Must be 3 to 20 characters allowed."
  private val verifyLongText = "Above 20 characters not allowed."

  private def accountForm = Form(mapping(
      "id_account_ref" -> ignored(java.util.UUID.randomUUID),
      "account_name" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
      "created_at" -> ignored(java.time.Instant.now))
  (Account.apply)(Account.unapply))

  private val loginForm = Form(mapping(
    "account_name" -> nonEmptyText
      .verifying(verifyShortText,   char => lengthIsGreaterThanNCharacters(char, 2))
      .verifying(verifyLongText,    char => lengthIsLessThanNCharacters(char, 20)),
    "password" -> nonEmptyText
      .verifying(verifyShortText,   char => lengthIsGreaterThanNCharacters(char, 2))
      .verifying(verifyLongText,    char => lengthIsLessThanNCharacters(char, 30))
  )(UserAuth0.apply)(UserAuth0.unapply))

  def ws = WebSocket.accept[JsValue, JsValue] { implicit request =>
    ActorFlow.actorRef(out => ClientManagerActor.props(out))
  }

  def index = Action { implicit request =>
    Ok(views.html.dashboard())
  }

  def auth = Action { implicit request =>
    Ok(views.html.auth(loginForm, routes.HomeController.loginUser))
  }

  def logout =  SecureUserAction.async { implicit request =>
    Future.successful(
      Redirect(routes.HomeController.auth)
      .flashing("info" -> "You are logged out.")
      .withNewSession
    )
  }

  def createUser = Action.async { implicit request =>
    accountForm.bindFromRequest.fold(
      formWithErrors => Future.successful(Redirect(routes.HomeController.auth())),
      account => {
        accountService
          .createAccount(account)
          .map(_ => Redirect(routes.HomeController.auth()))
    })
  }

  def loginUser = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors =>
        Future.successful(
          Ok(views.html.auth(loginForm, routes.HomeController.loginUser)
        )),
      login =>
        accountService
          .checkAccount(login.accountName, login.password)
          .map(
            if(_)
              Redirect(routes.UserAuth0Controller.main())
                .flashing("info" -> "You are logged in.")
                .withSession(utils.UserAuth.SESSION_USERNAME_KEY -> login.accountName)
            else
              Redirect(routes.HomeController.auth())
                .flashing("error" -> "Invalid username or password."))
    )
  }

  private def lengthIsGreaterThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length > n) true else false
  }

  private def lengthIsLessThanNCharacters(s: String, n: Int): Boolean = {
    if (s.length < n) true else false
  }
}
