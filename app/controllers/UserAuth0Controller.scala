package controllers

import javax.inject.{Inject, Singleton}
import java.time.Instant
import java.io.File
import java.util.UUID
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.Files.TemporaryFile
import play.api.data.validation._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.core.parsers.Multipart.FileInfo
import models.domain._
import models.service._
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }

@Singleton
class UserAuth0Controller @Inject() (
  accountService: AccountService,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {
  private def locAndVacinityForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "description"     ->  nonEmptyText))
  private def addAmenitiesAndFacilityForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"           ->  nonEmptyText,
      "description"     ->  text,
      "created_at"      ->  ignored(Instant.now)))
  private def addConstructionUpdateForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"     ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addContactProjectForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "name"            ->  nonEmptyText,
      "position"        ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addEmailForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "mail"            ->  email,
      "created_at"      ->  ignored(Instant.now)))
  private def addOverViewForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "total_land_area" ->  of[Double],
      "phase"           ->  number,
      "status"          ->  nonEmptyText,
      "address"         ->  nonEmptyText,
      "map_URL"         ->  text,
      "created_at"      ->  ignored(Instant.now)))
  private def addPhotoVideoGalleryForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "is_video"        ->  of[Boolean],
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addPrespectiveFloorPlanForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addProjectForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText))
  private def addSubProjectForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText))
  private def addSalesAndMarketingForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addSocialMediaForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "URL"             ->  text,
      "title"           ->  nonEmptyText))

  def main = SecureUserAction.async { implicit request =>
    Future.successful(Ok(views.html.main(routes.HomeController.logout)))
  }

  def addConstructionUpdate = SecureUserAction(parse.multipartFormData) { implicit request =>
    ???
  }

  def addLocationVacinity = SecureUserAction(parse.multipartFormData) { implicit request =>
    locAndVacinityForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      {case (a, b, c) =>
        for {
          _ <- uploadImage("location-and-vicinity", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addAmenitiesAndFacility = SecureUserAction(parse.multipartFormData) { implicit request =>
    addAmenitiesAndFacilityForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      {case (a, b, c, d, e, f) =>
        for {
          _ <- uploadImage("amenities-and-facility", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  private def uploadImage[A >: String]
    ( folder: A,
      request: Request[MultipartFormData[TemporaryFile]]
    ): Future[Seq[A]] =
      Future.successful {
        request.body.files.map { file =>
          val newPath = s"public/images/${folder}/" + Random.nextString(5) + file.filename
          file.ref.moveTo(new File(newPath))
          newPath
        }
      }
}
