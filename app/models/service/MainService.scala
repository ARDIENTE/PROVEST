package models.service

import javax.inject.{ Inject, Singleton }
import java.util.UUID
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain._
import models.repo._

@Singleton
class MainService @Inject()(
    accountRepo: AccountRepo,
    amenitiesAndFacilityRepo: AmenitiesAndFacilityRepo,
    constructionUpdateRepo: ConstructionUpdateRepo,
    contactProjectRepo: ContactProjectRepo,
    emailRepo: EmailRepo,
    locationAndVicinityRepo: LocationAndVicinityRepo,
    overViewRepo: OverViewRepo,
    photoAndVideoGalleryRepo: PhotoAndVideoGalleryRepo,
    perspectiveAndFloorPlanRepo: PerspectiveAndFloorPlanRepo,
    projectRepo: ProjectRepo,
    salesAndMarketingRepo: SalesAndMarketingRepo,
    socialMediaRepo: SocialMediaRepo,
    subProjectRepo: SubProjectRepo,
    val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def deleteSubProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeSubProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeSocialMedia[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeSalesAndMarketing[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removePerspectiveAndFloorPlan[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removePhotoAndVideoGallery[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeOverView[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeLocationAndVicinity[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeEmail[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeContactProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeConstructionUpdate[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeAccount[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeAmenitiesAndFacility[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if(!exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete


  def createSubProject[T <: SubProject](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms.id)

      add <- {
        if(!exists) subProjectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createSocialMedia[T <: SocialMedia](parms: T): Future[Int] =
    for {
      exists <- socialMediaRepo.exists(parms.id)

      add <- {
        if(!exists) socialMediaRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createSalesAndMarketing[T <: SalesAndMarketing](parms: T): Future[Int] =
    for {
      exists <- salesAndMarketingRepo.exists(parms.id)

      add <- {
        if(!exists) salesAndMarketingRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createProject[T <: Project](parms: T): Future[Int] =
    for {
      exists <- projectRepo.exists(parms.id)

      add <- {
        if(!exists) projectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createPerspectiveAndFloorPlan[T <: PerspectiveAndFloorPlan](parms: T): Future[Int] =
    for {
      exists <- perspectiveAndFloorPlanRepo.exists(parms.id)

      add <- {
        if(!exists) perspectiveAndFloorPlanRepo.add(parms)
        else Future.successful(0)
      }
    } yield add


  def createPhotoAndVideoGallery[T <: PhotoAndVideoGallery](parms: T): Future[Int] =
    for {
      exists <- photoAndVideoGalleryRepo.exists(parms.id)

      add <- {
        if(!exists) photoAndVideoGalleryRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createOverView[T <: OverView](parms: T): Future[Int] =
    for {
      exists <- overViewRepo.exists(parms.id)

      add <- {
        if(!exists) overViewRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createLocationAndVicinity[T <: LocationAndVicinity](parms: T): Future[Int] =
    for {
      exists <- locationAndVicinityRepo.exists(parms.id)

      add <- {
        if(!exists) locationAndVicinityRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createEmail[T <: Email](parms: T): Future[Int] =
    for {
      exists <- emailRepo.exists(parms.id)

      add <- {
        if(!exists) emailRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createContactProject[T <: ContactProject](parms: T): Future[Int] =
    for {
      exists <- contactProjectRepo.exists(parms.id)

      add <- {
        if(!exists) contactProjectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createConstructionUpdate[T <: ConstructionUpdate](parms: T): Future[Int] =
    for {
      exists <- constructionUpdateRepo.exists(parms.id)

      add <- {
        if(!exists) constructionUpdateRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createAccount[T <: Account](parms: T): Future[Int] =
    for {
      exists <- accountRepo.exists(parms.accountName)

      add <- {
        if(!exists) accountRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def checkAccount[T <: String](name: T, pass: T): Future[Boolean] =
    accountRepo.checkAccount(name, pass)

  def createAmenitiesAndFacility[T <: AmenitiesAndFacility](parms: T): Future[Int] =
    for {
      exists <- amenitiesAndFacilityRepo.exists(parms.id)

      add <- {
        if(!exists) amenitiesAndFacilityRepo.add(parms)
        else Future.successful(0)
      }
    } yield add
}
