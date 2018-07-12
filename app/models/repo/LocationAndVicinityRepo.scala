package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.LocationAndVicinity

@Singleton
class LocationAndVicinityRepo @Inject()(
    dao: models.dao.LocationAndVicinityDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def checkLocationAndVicinity(projectID: UUID, subProjectID: UUID): Future[Boolean] =
    db.run(dao.Query.filter( count =>
      count.projectID === projectID &&
      count.subProjectID === subProjectID).exists.result
    )

  def get: Future[Seq[LocationAndVicinity]] =
    db.run(dao.Query.result)

  def getByIds(subProjectID: Seq[UUID]): Future[Seq[LocationAndVicinity]] =
    db.run(dao.Query.filter(_.subProjectID inSetBind subProjectID).result)

  def find(id: UUID): OptionT[Future, LocationAndVicinity] =
    OptionT(db.run(dao.Query(id).result.headOption))

  def add(params: LocationAndVicinity): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: LocationAndVicinity): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
