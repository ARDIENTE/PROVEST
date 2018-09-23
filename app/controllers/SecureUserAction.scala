package controllers

import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc._
import scala.concurrent._

object SecureUserAction extends ActionBuilder[Request] {
  private val logger = play.api.Logger(this.getClass)

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val maybeUsername = request.session.get(utils.UserAuth.SESSION_USERNAME_KEY)
    maybeUsername match {
      case None => {
        // Future.successful(Forbidden("Dude, you’re not logged in."))
        Future.successful(Redirect(routes.UserAuth0Controller.forbidden))
      }
      
      case Some(u) => {
        block(request)
      }
    }
  }

}
