package models.service

import javax.inject.{ Inject, Singleton }
import java.io.File
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

  def getSocialMedia: Future[Seq[SocialMedia]] = {
    socialMediaRepo.get
  }

  def getSalesAndMarketing: Future[Seq[SalesAndMarketing]] = {
    salesAndMarketingRepo.get
  }

  def getProject: Future[Seq[Project]] = {
    projectRepo.get
  }

  def getSubProject: Future[Seq[SubProject]] = {
    subProjectRepo.get
  }

  def getPerspectiveAndFloorPlan: Future[Seq[PerspectiveAndFloorPlan]] = {
    perspectiveAndFloorPlanRepo.get
  }

  def getPerspectiveAndFloorPlanByIDs[T <: UUID](a: T, b: T): Future[Seq[PerspectiveAndFloorPlan]] = {
    perspectiveAndFloorPlanRepo.getByIDs(a, b)
  }

  def getPhotoAndVideoGallery: Future[Seq[PhotoAndVideoGallery]] = {
    photoAndVideoGalleryRepo.get
  }

  def getPhotoVideoGalleryByIDs[T <: UUID](a: T, b: T): Future[Seq[PhotoAndVideoGallery]] = {
    photoAndVideoGalleryRepo.getByIDs(a, b)
  }

  def getOverView: Future[Seq[OverView]] = {
    overViewRepo.get
  }

  def getOverViewByIDs[T <: UUID](a: T, b: T): Future[Seq[OverView]] = {
    overViewRepo.getByIDs(a, b)
  }

  def getLocationAndVicinity: Future[Seq[LocationAndVicinity]] = {
    locationAndVicinityRepo.get
  }

  def getLocationAndVicinityByIDs[T <: UUID](a: T, b: T): Future[Seq[LocationAndVicinity]] = {
    locationAndVicinityRepo.getByIDs(a, b)
  }

  def getEmail: Future[Seq[Email]] = {
    emailRepo.get
  }

  def getContactProject: Future[Seq[ContactProject]] = {
    contactProjectRepo.get
  }

  def getContactProjectByIDs[T <: UUID](a: T, b: T): Future[Seq[ContactProject]] = {
    contactProjectRepo.getByIDs(a, b)
  }

  def getConstructionUpdate: Future[Seq[ConstructionUpdate]] = {
    constructionUpdateRepo.get
  }

  def getConstructionUpdateByIDs[T <: UUID](a: T, b: T): Future[Seq[ConstructionUpdate]] = {
    constructionUpdateRepo.getByIDs(a, b)
  }

  def getAmenitiesAndFacility: Future[Seq[AmenitiesAndFacility]] = {
    amenitiesAndFacilityRepo.get
  }

  def getAmenitiesAndFacilityByIDs[T <: UUID](a: T, b: T): Future[Seq[AmenitiesAndFacility]] = {
    amenitiesAndFacilityRepo.getByIDs(a, b)
  }

  def removeSubProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if (exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeSocialMedia[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- socialMediaRepo.exists(parms)

      delete <- {
        if (exists) socialMediaRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeSalesAndMarketing[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- salesAndMarketingRepo.exists(parms)

      delete <- {
        if (exists) salesAndMarketingRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if (exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removePerspectiveAndFloorPlan[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- perspectiveAndFloorPlanRepo.exists(parms)

      delete <- {
        if (exists) perspectiveAndFloorPlanRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removePhotoAndVideoGallery[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- photoAndVideoGalleryRepo.exists(parms)

      delete <- {
        if (exists) photoAndVideoGalleryRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeOverView[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- overViewRepo.exists(parms)

      delete <- {
        if (exists) overViewRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeLocationAndVicinity[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- locationAndVicinityRepo.exists(parms)

      getDetails <- {
        if (exists) {
          locationAndVicinityRepo
            .find(parms)
            .map {
              case data: Option[LocationAndVicinity] =>
                val path: String = data.map(_.imagePath).getOrElse(null)

                deleteFilePath(path)

              case _ => false
            }
        }
        else Future.successful(false)
      }

      delete <- {
        if (getDetails) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeEmail[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms)

      delete <- {
        if (exists) subProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeContactProject[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- contactProjectRepo.exists(parms)

      delete <- {
        if (exists) contactProjectRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeConstructionUpdate[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- constructionUpdateRepo.exists(parms)

      getDetails <- {
        if (exists) {
          constructionUpdateRepo
            .find(parms)
            .map {
              case data: Option[ConstructionUpdate] =>
                val path: String = data.map(_.imagePath).getOrElse(null)
                deleteFilePath(path)

              case _ => false
            }
        }
        else Future.successful(false)
      }

      delete <- {
        if (getDetails) constructionUpdateRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeAccount[T <: String](parms: T): Future[Int] =
    for {
      exists <- accountRepo.exists(parms)

      delete <- {
        if (exists) accountRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete

  def removeAmenitiesAndFacility[T <: UUID](parms: T): Future[Int] =
    for {
      exists <- amenitiesAndFacilityRepo.exists(parms)

      delete <- {
        if (exists) amenitiesAndFacilityRepo.delete(parms)
        else Future.successful(0)
      }
    } yield delete


  def createSubProject[T <: SubProject](parms: T): Future[Int] =
    for {
      exists <- subProjectRepo.exists(parms.id)

      add <- {
        if (!exists) subProjectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createSocialMedia[T <: SocialMedia](parms: T): Future[Int] =
    for {
      exists <- socialMediaRepo.exists(parms.id)

      add <- {
        if (!exists) socialMediaRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createSalesAndMarketing[T <: SalesAndMarketing](parms: T): Future[Int] =
    for {
      exists <- salesAndMarketingRepo.exists(parms.id)

      add <- {
        if (!exists) salesAndMarketingRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createProject[T <: Project](parms: T): Future[Int] =
    for {
      exists <- projectRepo.exists(parms.id)

      add <- {
        if (!exists) projectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createPerspectiveAndFloorPlan[T <: PerspectiveAndFloorPlan](parms: T): Future[Int] =
    for {
      exists <- perspectiveAndFloorPlanRepo.exists(parms.id)

      add <- {
        if (!exists) perspectiveAndFloorPlanRepo.add(parms)
        else Future.successful(0)
      }
    } yield add


  def createPhotoAndVideoGallery[T <: PhotoAndVideoGallery](parms: T): Future[Int] =
    for {
      exists <- photoAndVideoGalleryRepo.exists(parms.id)

      add <- {
        if (!exists) photoAndVideoGalleryRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createOverView[T <: OverView](parms: T): Future[Int] =
    for {
      exists <- overViewRepo.exists(parms.id)

      add <- {
        if (!exists) overViewRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createLocationAndVicinity[T <: LocationAndVicinity](parms: T): Future[Int] =
    for {
      exists <- locationAndVicinityRepo.exists(parms.id)

      add <- {
        if (!exists) locationAndVicinityRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createEmail[T <: Email](parms: T): Future[Int] =
    for {
      exists <- emailRepo.exists(parms.id)

      add <- {
        if (!exists) emailRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createContactProject[T <: ContactProject](parms: T): Future[Int] =
    for {
      exists <- contactProjectRepo.exists(parms.id)

      add <- {
        if (!exists) contactProjectRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createConstructionUpdate[T <: ConstructionUpdate](parms: T): Future[Int] =
    for {
      exists <- constructionUpdateRepo.exists(parms.id)

      add <- {
        if (!exists) constructionUpdateRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def createAccount[T <: Account](parms: T): Future[Int] =
    for {
      exists <- accountRepo.exists(parms.accountName)

      add <- {
        if (!exists) accountRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def checkAccount[T <: String](name: T, pass: T): Future[Boolean] =
    accountRepo.checkAccount(name, pass)

  def createAmenitiesAndFacility[T <: AmenitiesAndFacility](parms: T): Future[Int] =
    for {
      exists <- amenitiesAndFacilityRepo.exists(parms.id)

      add <- {
        if (!exists) amenitiesAndFacilityRepo.add(parms)
        else Future.successful(0)
      }
    } yield add

  def findLocationAndVicinity(id: UUID): Future[Option[LocationAndVicinity]] = 
    locationAndVicinityRepo.find(id)

  def deleteFilePath(url: String): Boolean = {
    try {
      if (url == null)  
        false

      else 
        new File(url).delete  
    } catch {
      case _: Exception => false
    }
  }

  def updateSocialMedia(params: SocialMedia): Future[Int] = 
    for {
      exists <- socialMediaRepo.exists(params.id)

      update <- {
        if (!exists) socialMediaRepo.update(params)
        else Future.successful(0)
      }
    } yield update

  def updateSalesAndMarketing(params: SalesAndMarketing): Future[Int] = 
      for {
        exists <- salesAndMarketingRepo.exists(params.id)

        update <- {
          if (!exists) salesAndMarketingRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateSubProject(params: SubProject): Future[Int] = 
      for {
        exists <- subProjectRepo.exists(params.id)

        update <- {
          if (!exists) subProjectRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateProject(params: Project): Future[Int] = 
      for {
        exists <- projectRepo.exists(params.id)

        update <- {
          if (!exists) projectRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updatePerspectiveAndFloorPlan(params: PerspectiveAndFloorPlan): Future[Int] = 
      for {
        exists <- perspectiveAndFloorPlanRepo.exists(params.id)

        update <- {
          if (!exists) perspectiveAndFloorPlanRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updatePhotoAndVideoGallery(params: PhotoAndVideoGallery): Future[Int] = 
      for {
        exists <- photoAndVideoGalleryRepo.exists(params.id)

        update <- {
          if (!exists) photoAndVideoGalleryRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateOverView(params: OverView): Future[Int] = 
      for {
        exists <- overViewRepo.exists(params.id)

        update <- {
          if (!exists) overViewRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateEmail(params: Email): Future[Int] = 
      for {
        exists <- emailRepo.exists(params.id)

        update <- {
          if (!exists) emailRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateContactProject(params: ContactProject): Future[Int] = 
      for {
        exists <- contactProjectRepo.exists(params.id)

        update <- {
          if (!exists) contactProjectRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateConstructionUpdate(params: ConstructionUpdate): Future[Int] = 
      for {
        exists <- constructionUpdateRepo.exists(params.id)

        update <- {
          if (!exists) constructionUpdateRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateLocationAndVicinity(params: LocationAndVicinity): Future[Int] = 
      for {
        exists <- locationAndVicinityRepo.exists(params.id)

        update <- {
          if (!exists) locationAndVicinityRepo.update(params)
          else Future.successful(0)
        }
      } yield update

  def updateAmenitiesAndFacility(params: AmenitiesAndFacility): Future[Int] = 
      for {
        exists <- amenitiesAndFacilityRepo.exists(params.id)

        update <- {
          if (!exists) amenitiesAndFacilityRepo.update(params)
          else Future.successful(0)
        }
      } yield update

}
