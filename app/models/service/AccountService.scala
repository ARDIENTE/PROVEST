package models.service

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ ExecutionContext, Future }
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.domain._
import models.repo._

@Singleton
class AccountService @Inject()(
    accountRepo: AccountRepo,
    val dbConfigProvider: DatabaseConfigProvider,
    implicit val ec: ExecutionContext
  ) extends HasDatabaseConfigProvider[utils.db.PostgresDriver] {
  import driver.api._

  def checkAccount[T <: String](name: T, pass: T): Future[Boolean] =
    accountRepo.checkAccount(name, pass)

  def createAccount[T <: Account](acc: T): Future[Int] =
    for {
      exists <- accountRepo.exists(acc.accountName)

      add <- {
        if(!exists) accountRepo.add(acc)
        else Future.successful(0)
      }
    } yield add


  def updateAccount[T <: Account](acc: T): Future[Int] =
    accountRepo.update(acc)

  def deleteAccount[T <: String](acc: T): Future[Int] =
    accountRepo.delete(acc)
}
