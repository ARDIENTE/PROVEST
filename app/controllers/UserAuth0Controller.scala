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
import models.service.MainService
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }

@Singleton
class UserAuth0Controller @Inject() (
  service: MainService,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {
  private def addLocAndVacinityForm = Form(tuple(
      "id"              ->  ignored(UUID.randomUUID),
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "description"     ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
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

  def addSocialMedia = SecureUserAction(parse.multipartFormData) { implicit request =>
    addSocialMediaForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c) =>
        for {
          _ <- uploadImage("social-media", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addSalesAndMarketing = SecureUserAction(parse.multipartFormData) { implicit request =>
    addSalesAndMarketingForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c, d) =>
        for {
          _ <- uploadImage("sales-and-marketing", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }


  def addSubProject = SecureUserAction(parse.multipartFormData) { implicit request =>
    addSubProjectForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b) =>
        for {
          _ <- uploadImage("sub-project", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addProject = SecureUserAction(parse.multipartFormData) { implicit request =>
    addProjectForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b) =>
        for {
          _ <- uploadImage("project", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addPrespectiveFloorPlan = SecureUserAction(parse.multipartFormData) { implicit request =>
    addPrespectiveFloorPlanForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c, d, e) =>
        for {
          _ <- uploadImage("prespective-floor-plan", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addPhotoVideoGallery = SecureUserAction(parse.multipartFormData) { implicit request =>
    addPhotoVideoGalleryForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c, d, e, f) =>
        for {
          _ <- uploadImage("photo-video-gallery", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addOverView = SecureUserAction(parse.multipartFormData) { implicit request =>
    addOverViewForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c, d, e, f, g, h, i) =>
        for {
          _ <- uploadImage("overview", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addEmail = SecureUserAction(parse.multipartFormData) { implicit request =>
    addEmailForm.bindFromRequest.fold(
      badRequest => Redirect(routes.UserAuth0Controller.main),
      { case (a, b, c, d) =>
        for {
          _ <- uploadImage("email", request)
          // Add User to Database....
        } yield ()

        Redirect(routes.UserAuth0Controller.main)
      })
  }

  def addContactProject = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addContactProjectForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case form@(a, b, c, d, e, f, g) =>
        service
          .createContactProject((ContactProject.apply _) tupled form)
          .map { result =>
            if (result == 0) 
              Redirect(routes.UserAuth0Controller.main)
                .flashing("error" -> "Error or Some files has failed.")
            else
              Redirect(routes.UserAuth0Controller.main)
                .flashing("info" -> "Done uploading.")
          }
      })
  }

  def addConstructionUpdate = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addConstructionUpdateForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e) =>
        val validate = {
          for {
            path <- uploadImage("construction-update", request)
            
            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createConstructionUpdate(ConstructionUpdate(a, b, c, url, d, e))
                else
                  Future.successful(0)
              }}
          } yield (add)
        }

        validate.map { result =>
          if (result.contains(0)) 
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  def addLocationAndVicinity = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addLocAndVacinityForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e) => 

        val validate = {
          for {
            path <- uploadImage("location-and-vicinity", request)
            
            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createLocationAndVicinity(LocationAndVicinity(a, b, c, url, d, e))
                else
                  Future.successful(0)
              }}
          } yield (add)
        }

        validate.map { result =>
          if (result.contains(0)) 
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  def addAmenitiesAndFacility = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addAmenitiesAndFacilityForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f) =>
        val validate = {
          for {
            path <- uploadImage("amenities-and-facility", request)
            
            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createAmenitiesAndFacility(AmenitiesAndFacility(a, b, c, url, d, e, f))
                else
                  Future.successful(0)
              }}
          } yield (add)
        }

        validate.map { result =>
          if (result.contains(0)) 
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  private def uploadImage[A >: String]
    ( folder: A,
      request: Request[MultipartFormData[TemporaryFile]]
    ): Future[Seq[(A, A)]] = {
    Future.successful {
      if (!request.body.files.isEmpty)
        request.body.files.map { file =>
          val newPath = s"public/images/${folder}/" + Random.nextString(5) + file.filename
          try {
            file.ref.moveTo(new File(newPath))
            ("Done", newPath)
          } catch {
            case _: Exception => ("Error", file.filename)
          }
        }
      else Seq.empty[(A, A)]
    }
  }
}