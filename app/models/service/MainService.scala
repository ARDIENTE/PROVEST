package models.service

import javax.inject.{ Inject, Singleton }
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
    prespectiveAndFloorPlanRepo: PrespectiveAndFloorPlanRepo,
    projectRepo: ProjectRepo,
    salesAndMarketingRepo: SalesAndMarketingRepo,
    socialMediaRepo: SocialMediaRepo,
    subProjectRepo: SubProjectRepo,
    val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

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

  def createPrespectiveAndFloorPlan[T <: PrespectiveAndFloorPlan](parms: T): Future[Int] =
    for {
      exists <- prespectiveAndFloorPlanRepo.exists(parms.id)

      add <- {
        if(!exists) prespectiveAndFloorPlanRepo.add(parms)
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
