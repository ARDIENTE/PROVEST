package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.ConstructionUpdate

@Singleton
class ConstructionUpdateRepo @Inject()(
    dao: models.dao.ConstructionUpdateDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def checkConstructionUpdate(projectID: UUID, subProjectID: UUID): Future[Boolean] =
    db.run(dao.Query.filter( count =>
      count.projectID === projectID &&
      count.subProjectID === subProjectID).exists.result
    )

  def get: Future[Seq[ConstructionUpdate]] =
    db.run(dao.Query.result)

  def getByIDs(projectID: UUID, subProjectID: UUID): Future[Seq[ConstructionUpdate]] =
    db.run(dao.Query.filter(r => r.projectID === projectID && r.subProjectID === subProjectID).result)

  def find(id: UUID): Future[Option[ConstructionUpdate]] =
    db.run(dao.Query(id).result.headOption)

  def add(params: ConstructionUpdate): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: ConstructionUpdate): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
