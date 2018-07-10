package controllers

import scala.concurrent.Future
import play.api.mvc._

object LoggingAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)

    // val maybeUsername = request.session.get(models.domain.UserAuth.SESSION_USERNAME_KEY)
    // val maybeUsername = request.session.get(models.domain.UserAuth.SESSION_USERNAME_KEY)
    //   maybeUsername match {
    //       case None => {
    //           Future.successful(Forbidden("Dude, you’re not logged in."))
    //       }
    //       case Some(u) => {
    //           val res: Future[Result] = block(request)
    //           res
    //       }
    //   }
  }
}
