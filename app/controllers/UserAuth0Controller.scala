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
import play.api.libs.json._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.core.parsers.Multipart.FileInfo
import models.domain._
import models.service.MainService

@Singleton
class UserAuth0Controller @Inject() (
  service: MainService,
  val messagesApi: MessagesApi,
  implicit val wja: WebJarAssets
) extends Controller with I18nSupport {

  def main = SecureUserAction.async { implicit request =>
    Future.successful(Ok(views.html.main(routes.HomeController.logout)))
  }

  def getSocialMedia = Action.async { implicit request =>
    service.getSocialMedia.map(count => Ok(Json.toJson(count)))
  }

  def getSalesAndMarketing = Action.async { implicit request =>
    service.getSalesAndMarketing.map(count => Ok(Json.toJson(count)))
  }

  def getProject = Action.async { implicit request =>
    service.getProject.map(count => Ok(Json.toJson(count)))
  }

  def getSubProject = Action.async { implicit request =>
    service.getSubProject.map(count => Ok(Json.toJson(count)))
  }

  def getPerspectiveAndFloorPlan = Action.async { implicit request =>
    service.getPerspectiveAndFloorPlan.map(count => Ok(Json.toJson(count)))
  }

  def getPerspectiveAndFloorPlanByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getPerspectiveAndFloorPlanByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }


  def getPhotoAndVideoGallery = Action.async { implicit request =>
    service.getPhotoAndVideoGallery.map(count => Ok(Json.toJson(count)))
  }

  def getPhotoVideoGalleryByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getPhotoVideoGalleryByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def getOverView = Action.async { implicit request =>
    service.getOverView.map(count => Ok(Json.toJson(count)))
  }

  def getOverViewByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getOverViewByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def getLocationAndVicinity = Action.async { implicit request =>
    service.getLocationAndVicinity.map(count => Ok(Json.toJson(count)))
  }

  def getLocationAndVicinityByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getLocationAndVicinityByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def getEmail = Action.async { implicit request =>
    service.getEmail.map(count => Ok(Json.toJson(count)))
  }

  def getContactProject = Action.async { implicit request =>
    service.getContactProject.map(count => Ok(Json.toJson(count)))
  }

  def getContactProjectByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getContactProjectByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def getConstructionUpdate = Action.async { implicit request =>
    service.getConstructionUpdate.map(count => Ok(Json.toJson(count)))
  }

  def getConstructionUpdateByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getConstructionUpdateByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def getAmenitiesAndFacility = Action.async { implicit request =>
    service.getAmenitiesAndFacility.map(count => Ok(Json.toJson(count)))
  }

  def getAmenitiesAndFacilityByIDs[T >: UUID](proID: UUID, subID: UUID) = Action.async
    { implicit request =>
      service.getAmenitiesAndFacilityByIDs(proID, subID).map(count => Ok(Json.toJson(count)))
    }

  def addSocialMedia = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    FormValidations.addSocialMediaForm.bindFromRequest.fold(
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
    FormValidations.addSalesAndMarketingForm.bindFromRequest.fold(
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
    FormValidations.addSubProjectForm.bindFromRequest.fold(
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
    FormValidations.addProjectForm.bindFromRequest.fold(
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
    FormValidations.addPerspectiveAndFloorPlanForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("perspective-floor-plan", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createPerspectiveAndFloorPlan(
                    PerspectiveAndFloorPlan(
                      UUID.randomUUID, 
                      UUID.fromString(a), 
                      UUID.fromString(b), 
                      url, 
                      c, 
                      d))
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
    FormValidations.addPhotoGalleryForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e) =>
        (for {
            path <- uploadImage("photo-and-video-gallery", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createPhotoAndVideoGallery(
                    PhotoAndVideoGallery(
                      UUID.randomUUID, 
                      UUID.fromString(a), 
                      UUID.fromString(b), 
                      c, 
                      url, 
                      d, 
                      e))
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
    FormValidations.addVideoGalleryForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f) =>
        service
          .createPhotoAndVideoGallery(PhotoAndVideoGallery(
            UUID.randomUUID, 
            UUID.fromString(a), 
            UUID.fromString(b), 
            c, 
            d, 
            e, 
            f
          )).map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addOverView = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    FormValidations.addOverViewForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f, g, h) =>
        service
          .createOverView(OverView(
            UUID.randomUUID, 
            UUID.fromString(a), 
            UUID.fromString(b), 
            c, 
            d, 
            e, 
            f, 
            g, 
            h
          )).map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addEmail = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    FormValidations.addEmailForm.bindFromRequest.fold(
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
    FormValidations.addContactProjectForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e, f) =>
        service
          .createContactProject(ContactProject(
            UUID.randomUUID, 
            UUID.fromString(a), 
            UUID.fromString(b), 
            c, 
            d, 
            e, 
            f
          )).map { result =>
            if (result == 0)
              Redirect(routes.UserAuth0Controller.main)
            else
              Redirect(routes.UserAuth0Controller.main)
          }
      })
  }

  def addConstructionUpdate = SecureUserAction.async(parse.multipartFormData) { implicit request =>
    FormValidations.addConstructionUpdateForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("construction-update", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (stat, url) =>
                if (stat.contains("Done"))
                  service.createConstructionUpdate(
                    ConstructionUpdate(
                      UUID.randomUUID, 
                      UUID.fromString(a), 
                      UUID.fromString(b), 
                      url, 
                      c, 
                      d))
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
    FormValidations.addLocAndVacinityForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d) =>
        (for {
            path <- uploadImage("location-and-vicinity", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (status, url) =>
                if (status.contains("Done"))
                  service.createLocationAndVicinity(
                    LocationAndVicinity(
                      UUID.randomUUID,
                      UUID.fromString(a),
                      UUID.fromString(b),
                      url,
                      c,
                      d))
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
    FormValidations.addAmenitiesAndFacilityForm.bindFromRequest.fold(
      badRequest => Future.successful(Redirect(routes.UserAuth0Controller.main)),
      { case (a, b, c, d, e) =>
        (for {
            path <- uploadImage("amenities-and-facility", request)

            add <- Future.sequence { // Add User to Database....
              path.map { case (status, url) =>
                if (status.contains("Done"))
                  service.createAmenitiesAndFacility(
                      AmenitiesAndFacility(
                        UUID.randomUUID, 
                        UUID.fromString(a), 
                        UUID.fromString(b), 
                        url, 
                        c, 
                        d, 
                        e))
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

  def removeSocialMedia(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeSocialMedia(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeSalesAndMarketing(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeSalesAndMarketing(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeSubProject(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeSubProject(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeProject(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeProject(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removePerspectiveAndFloorPlan(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removePerspectiveAndFloorPlan(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removePhotoAndVideoGallery(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removePhotoAndVideoGallery(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeOverView(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeOverView(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeEmail(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeEmail(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeContactProject(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeContactProject(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeConstructionUpdate(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeConstructionUpdate(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeLocationAndVicinity(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeLocationAndVicinity(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def removeAmenitiesAndFacility(id: UUID) = SecureUserAction.async { implicit request =>
    service
      .removeAmenitiesAndFacility(id)
      .map { count =>
        if (count == 0)
          Redirect(routes.UserAuth0Controller.main)
              .flashing("error" -> "Something went wrong.")
        else
          Redirect(routes.UserAuth0Controller.main)
              .flashing("info" -> "Success.")
      }
  }

  def forbidden = Action.async { implicit request =>
    Future.successful(Ok(views.html.error()))
  }

  private def uploadImage[A >: String](
      folder: A,
      request: Request[MultipartFormData[TemporaryFile]]):
    Future[Seq[(A, A)]] = {
      Future.successful {
        if (!request.body.files.isEmpty)
          request.body.files.map { file =>
            val random = Random.alphanumeric.take(5).mkString("")
            val diskPath = s"public/images/${folder}/${random + file.filename}"
            val publicPath = s"../assets/images/${folder}/${random + file.filename}"
            try {
              file.ref.moveTo(new File(diskPath))
              ("Done", publicPath)
            } catch {
              case _: Exception => ("Error", file.filename)
            }
          }
        else Seq.empty[(A, A)]
      }
    }
}
