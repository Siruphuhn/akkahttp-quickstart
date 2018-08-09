import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}

class TodoRouter(todoRepository: TodoRepository) extends Router with Directives with TodoDirectives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = pathPrefix("todos") {
    pathEndOrSingleSlash {
      get {
        handleWithDefault(todoRepository.all()) { todos =>
          complete(todos)
        }
      }
    } ~ path("done") {
      get {
        handleWithDefault(todoRepository.done()) { todos =>
          complete(todos)
        }
      }
    } ~ path("pending") {
      get {
        handleWithDefault(todoRepository.pending()) { todos =>
          complete(todos)
        }
      }
    }
  }
}
