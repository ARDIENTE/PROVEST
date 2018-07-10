package utils.extension

import java.net.URI
import play.api.mvc.PathBindable
import play.api.mvc.QueryStringBindable


object PathBinders {
  implicit def bindableOption[T: PathBindable] = new PathBindable[Option[T]] {
    def bind(key: String, value: String): Either[String, Option[T]] =
      implicitly[PathBindable[T]]
        .bind(key, value)
        .fold(left => Left(left), right => Right(Some(right)))

    def unbind(key: String, value: Option[T]): String =
      value.map(_.toString).getOrElse("")
  }

  implicit def queryStringBindable(implicit uriBinder: QueryStringBindable[String]) = new QueryStringBindable[URI] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, URI]] = {
      uriBinder.bind(key, params)
        .map({
          case Right(uri) => Right(new URI(uri.toString))
          case _ => Left("Unable to bind a URI")
        })
    }

    override def unbind(key: String, uri: URI): String = {
      uriBinder.unbind(key, uri.toString)
    }
  }
}
