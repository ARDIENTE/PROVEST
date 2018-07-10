package controllers

import javax.inject._
import java.time.Instant
import java.util.UUID
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
import actors._
import models.domain._
import models.service._

import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (
  accountService: AccountService,
  implicit val system: ActorSystem,
  implicit val materializer: Materializer,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {
  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[JsValue, JsValue]
  /**
   * Create an Action to render an HTML page.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  private def accountForm = Form(mapping(
      "id_account_ref" -> ignored(UUID.randomUUID),
      "account_name" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
      "created_at" -> ignored(Instant.now))
  (Account.apply)(Account.unapply))

  private def loginForm = Form(tuple(
      "account_name" -> nonEmptyText,
      "password" -> nonEmptyText))

  def ws = WebSocket.accept[JsValue, JsValue] { implicit request =>
    ActorFlow.actorRef(out => ClientManagerActor.props(out))
  }

  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.dashboard()))
  }

  def auth = Action.async { implicit request =>
    Future.successful(Ok(views.html.auth()))
  }

  def main = Action.async { implicit request =>
    Future.successful(Ok(views.html.main()))
  }

  def createUser = Action.async { implicit request =>
    accountForm.bindFromRequest.fold(
      formWithErrors => {
        // Future.successful(BadRequest(formWithErrors.errorsAsJson))},
        Future.successful(Redirect(routes.HomeController.auth()))},
      account => {
        accountService
          .createAccount(account)
          .map(_ => Redirect(routes.HomeController.auth()))
      })
  }

  def loginUser = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(Redirect(routes.HomeController.auth())),
      user => {
        accountService.checkAccount(user._1, user._2)
          .map {
            if(_) Redirect(routes.HomeController.main())
            else Redirect(routes.HomeController.auth())
          }
      })
  }
}
