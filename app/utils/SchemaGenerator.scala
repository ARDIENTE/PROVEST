package utils

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.dao._

@Singleton
class SchemaGenerator @Inject()(
    accountDao: AccountDAO,
    amenitiesAndFacilityDao: AmenitiesAndFacilityDAO,
    constructionUpdateDao: ConstructionUpdateDAO,
    contactProjectDao: ContactProjectDAO,
    emailDao: EmailDAO,
    locationAndVicinityDao: LocationAndVicinityDAO,
    overViewDao: OverViewDAO,
    photoAndVideoGalleryDao: PhotoAndVideoGalleryDAO,
    prespectiveAndFloorPlanDao: PrespectiveAndFloorPlanDAO,
    projectDao: ProjectDAO,
    salesAndMarketingDao: SalesAndMarketingDAO,
    socialMediaDao: SocialMediaDAO,
    subProjectDao: ProjectDAO,
    val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def createDDLScript() = {
    val schemas =
      accountDao.Query.schema ++
      accountDao.Query.schema ++
      amenitiesAndFacilityDao.Query.schema ++
      constructionUpdateDao.Query.schema ++
      contactProjectDao.Query.schema ++
      emailDao.Query.schema ++
      locationAndVicinityDao.Query.schema ++
      overViewDao.Query.schema ++
      photoAndVideoGalleryDao.Query.schema ++
      prespectiveAndFloorPlanDao.Query.schema ++
      projectDao.Query.schema ++
      salesAndMarketingDao.Query.schema ++
      socialMediaDao.Query.schema ++
      subProjectDao.Query.schema

    val writer = new java.io.PrintWriter("target/schema.sql")
    writer.write("# --- !Ups\n\n")
    schemas.createStatements.foreach { s => writer.write(s + ";\n\n") }

    writer.write("\n\n# --- !Downs\n\n")
    schemas.dropStatements.foreach { s => writer.write(s + ";\n") }

    println("Schema definitions are written")

    writer.close()
  }

  createDDLScript()
}
