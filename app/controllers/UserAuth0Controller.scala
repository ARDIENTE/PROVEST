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
import ejisan.play.libs.{ PageMetaSupport, PageMetaApi }
import models.domain._
import models.service.MainService

@Singleton
class UserAuth0Controller @Inject() (
  service: MainService,
  val messagesApi: MessagesApi,
  val pageMetaApi: PageMetaApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport with PageMetaSupport {
  private def addLocAndVacinityForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "description"     ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addAmenitiesAndFacilityForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"           ->  nonEmptyText,
      "description"     ->  text,
      "created_at"      ->  ignored(Instant.now)))
  private def addConstructionUpdateForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"     ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addContactProjectForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "name"            ->  nonEmptyText,
      "position"        ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addEmailForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "mail"            ->  email,
      "created_at"      ->  ignored(Instant.now)
    )(Email.apply)(Email.unapply))
  private def addOverViewForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "total_land_area" ->  of[Double],
      "phase"           ->  number,
      "status"          ->  nonEmptyText,
      "address"         ->  nonEmptyText,
      "map_URL"         ->  text,
      "created_at"      ->  ignored(Instant.now)))
  private def addPhotoGalleryForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "is_video"        ->  ignored(false),
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addVideoGalleryForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "is_video"        ->  ignored(false),
      "URL"             ->  nonEmptyText,
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addPerspectiveAndFloorPlanForm = Form(tuple(
      "project_id"      ->  of[UUID],
      "sub_project_id"  ->  of[UUID],
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  private def addProjectForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText
    )(Project.apply)(Project.unapply))
  private def addSubProjectForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText
    )(SubProject.apply)(SubProject.unapply))
  private def addSalesAndMarketingForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)
    )(SalesAndMarketing.apply)(SalesAndMarketing.unapply))
  private def addSocialMediaForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "URL"             ->  text,
      "title"           ->  nonEmptyText
    )(SocialMedia.apply)(SocialMedia.unapply))

  def main = SecureUserAction.async { implicit request =>
    Future.successful(Ok(views.html.main(routes.HomeController.logout)))
  }

  def addSocialMedia = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addSocialMediaForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { result =>
        service
          .createSocialMedia(result)
          .map { result =>
            if (result == 0 )
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addSalesAndMarketing = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addSalesAndMarketingForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { result =>
        service
          .createSalesAndMarketing(result)
          .map { result =>
            if (result == 0 )
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }


  def addSubProject = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addSubProjectForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      result => {
        service
          .createSubProject(result)
          .map { result =>
            if (result == 0 )
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addProject = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addProjectForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      result => {
        service
          .createProject(result)
          .map { result =>
            if (result == 0 )
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addPerspectiveAndFloorPlan = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addPerspectiveAndFloorPlanForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("perspective-floor-plan", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createPerspectiveAndFloorPlan(
                    PerspectiveAndFloorPlan(UUID.randomUUID, a, b, url, c, d))
                else
                  Future.successful(0)
              }}
          } yield (add)
        ).map { result =>
          if (result.contains(0))
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  def addPhotoGallery = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addPhotoGalleryForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e) =>
        (for {
            path <- uploadImage("photo-and-video-gallery", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createPhotoAndVideoGallery(
                    PhotoAndVideoGallery(UUID.randomUUID, a, b, c, url, d, e))
                else
                  Future.successful(0)
              }}
          } yield (add)
        ).map { result =>
          if (result.contains(0))
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  def addVideoGallery = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addVideoGalleryForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f) =>
        service
          .createPhotoAndVideoGallery(PhotoAndVideoGallery(UUID.randomUUID, a, b, c, d, e, f))
          .map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addOverView = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addOverViewForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f, g, h) =>
        service
          .createOverView(OverView(UUID.randomUUID, a, b, c, d, e, f, g, h))
          .map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addEmail = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addEmailForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { result =>
        service
          .createEmail(result)
          .map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addContactProject = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addContactProjectForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f) =>
        service
          .createContactProject(ContactProject(UUID.randomUUID, a, b, c, d, e, f))
          .map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addConstructionUpdate = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    addConstructionUpdateForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("construction-update", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createConstructionUpdate(
                    ConstructionUpdate(UUID.randomUUID, a, b, url, c, d))
                else
                  Future.successful(0)
              }}
          } yield (add)
        ).map { result =>
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
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("location-and-vicinity", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (status, url) =>
                if (status.contains("Done"))
                  service.createLocationAndVicinity(
                    LocationAndVicinity(UUID.randomUUID,a, b, url, c, d))
                else
                  Future.successful(0)
              }}
          } yield (add)
        ).map { result =>
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
      { case (a, b, c, d, e) =>
        (for {
            path <- uploadImage("amenities-and-facility", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (status, url) =>
                if (status.contains("Done"))
                  service.createAmenitiesAndFacility(
                      AmenitiesAndFacility(UUID.randomUUID, a, b, url, c, d, e))
                else
                  Future.successful(0)
              }}
          } yield (add)
        ).map { result =>
          if (result.contains(0))
            Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Error or Some files has failed.")
          else
            Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Done uploading.")
        }
      })
  }

  private def uploadImage[A >: String](
      folder: A,
      request: Request[MultipartFormData[TemporaryFile]]):
    Future[Seq[(A, A)]] = {
      Future.successful {
        if (!request.body.files.isEmpty)
          request.body.files.map { file =>
            val random = Random.alphanumeric.take(5).mkString("")
            val newPath = s"public/images/${folder}/${random + file.filename}"
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
