package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.OverView

@Singleton
class OverViewRepo @Inject()(
    dao: models.dao.OverViewDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def checkOverView(projectID: UUID, subProjectID: UUID): Future[Boolean] =
    db.run(dao.Query.filter( count =>
      count.projectID === projectID &&
      count.subProjectID === subProjectID).exists.result
    )

  def get: Future[Seq[OverView]] =
    db.run(dao.Query.result)

  def getByIDs(projectID: UUID, subProjectID: UUID): Future[Seq[OverView]] =
    db.run(dao.Query.filter(r => r.projectID === projectID && r.subProjectID === subProjectID).result)

  def find(id: UUID): Future[Option[OverView]] =
    db.run(dao.Query(id).result.headOption)

  def add(params: OverView): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: OverView): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
