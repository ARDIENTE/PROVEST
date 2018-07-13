package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.PerspectiveAndFloorPlan

@Singleton
class PerspectiveAndFloorPlanRepo @Inject()(
    dao: models.dao.PerspectiveAndFloorPlanDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def checkPerspectiveAndFloorPlan(projectID: UUID, subProjectID: UUID): Future[Boolean] =
    db.run(dao.Query.filter( count =>
      count.projectID === projectID &&
      count.subProjectID === subProjectID).exists.result
    )

  def get: Future[Seq[PerspectiveAndFloorPlan]] =
    db.run(dao.Query.result)

  def getByIds(subProjectID: Seq[UUID]): Future[Seq[PerspectiveAndFloorPlan]] =
    db.run(dao.Query.filter(_.subProjectID inSetBind subProjectID).result)

  def find(id: UUID): OptionT[Future, PerspectiveAndFloorPlan] =
    OptionT(db.run(dao.Query(id).result.headOption))

  def add(params: PerspectiveAndFloorPlan): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: PerspectiveAndFloorPlan): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
