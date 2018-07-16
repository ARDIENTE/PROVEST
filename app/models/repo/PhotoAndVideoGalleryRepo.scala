package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.PhotoAndVideoGallery

@Singleton
class PhotoAndVideoGalleryRepo @Inject()(
    dao: models.dao.PhotoAndVideoGalleryDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def checkPhotoAndVideoGallery(projectID: UUID, subProjectID: UUID): Future[Boolean] =
    db.run(dao.Query.filter( count =>
      count.projectID === projectID &&
      count.subProjectID === subProjectID).exists.result
    )

  def get: Future[Seq[PhotoAndVideoGallery]] =
    db.run(dao.Query.result)

  def getByIDs(projectID: UUID, subProjectID: UUID): Future[Seq[PhotoAndVideoGallery]] =
    db.run(dao.Query.filter(r => r.projectID === projectID && r.subProjectID === subProjectID).result)

  def find(id: UUID): OptionT[Future, PhotoAndVideoGallery] =
    OptionT(db.run(dao.Query(id).result.headOption))

  def add(params: PhotoAndVideoGallery): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: PhotoAndVideoGallery): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
