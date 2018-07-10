package models.repo

import java.util.UUID
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import cats.data.{ EitherT, OptionT }
import cats.implicits._
import models.domain.Account

@Singleton
class AccountRepo @Inject()(
    dao: models.dao.AccountDAO,
    protected val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def exists(accountName: String): Future[Boolean] =
    db.run(dao.Query(accountName).exists.result)

  def checkAccount[T >: String](accountName: T, password: T): Future[Boolean] =
    db.run(dao.Query.map(r => r.accountName == accountName && r.password == password).exists.result)

  def get: Future[Seq[Account]] =
    db.run(dao.Query.result)

  def getByIds(accountName: Seq[String]): Future[Seq[Account]] =
    db.run(dao.Query.filter(_.accountName inSetBind accountName).result)

  def find(idAccountRef: UUID): OptionT[Future, Account] =
    OptionT(db.run(dao.Query(idAccountRef).result.headOption))

  def add[T <: Account](acc: T): Future[Int] =
    db.run(dao.Query += acc)

  def delete(accountName: String): Future[Int] =
    db.run(dao.Query(accountName).delete)

  def update[T <: Account](acc: T): Future[Int] =
    db.run(dao.Query(acc.idAccountRef).update(acc))
}
