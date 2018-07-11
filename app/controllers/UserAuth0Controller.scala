package controllers

import javax.inject.{Inject, Singleton}
import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.streams._
import play.api.i18n.{ I18nSupport, MessagesApi }
import models.domain._
import models.service._
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }

import java.io.File
import java.nio.file.{Files, Path}
import javax.inject._

import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.MultipartFormData.FilePart
import play.core.parsers.Multipart.FileInfo

@Singleton
class UserAuth0Controller @Inject() (
  accountService: AccountService,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {

  private def locAndVacinityForm(path: String) =
    Form(tuple(
      "project_id" -> nonEmptyText,
      "sub_project_id" -> nonEmptyText,
      "description" -> nonEmptyText,
      "image_path" -> default(text, path)
    ))

  def main = SecureUserAction.async { implicit request =>
    Future.successful(Ok(views.html.main(routes.HomeController.logout)))
  }

  def locationVacinity = Action(parse.multipartFormData) { implicit request =>
    request.body.file("fileUpload").map { doc =>
      try {
        locAndVacinityForm(doc.filename)
          .bindFromRequest
          .fold((res => Redirect(routes.UserAuth0Controller.main)),
          { case (a, b, c, d) =>
            // Copy file to new location..
            doc.ref.moveTo(new File("public/images/location-and-vicinity/"+ doc.filename))

            Redirect(routes.UserAuth0Controller.main)
          })
      } catch {
        case e: Exception => Redirect(routes.UserAuth0Controller.main)
      }
    }.getOrElse {
      Redirect(routes.UserAuth0Controller.main)
    }
  }
}
