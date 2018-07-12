package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.Email

@Singleton
class EmailRepo @Inject()(
    dao: models.dao.EmailDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def get: Future[Seq[Email]] =
    db.run(dao.Query.result)

  def getByIds(id: Seq[UUID]): Future[Seq[Email]] =
    db.run(dao.Query.filter(_.id inSetBind id).result)

  def find(id: UUID): OptionT[Future, Email] =
    OptionT(db.run(dao.Query(id).result.headOption))

  def add(params: Email): Future[Int] =
    db.run(dao.Query += params)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def update(params: Email): Future[Int] =
    db.run(dao.Query(params.id).update(params))
}
