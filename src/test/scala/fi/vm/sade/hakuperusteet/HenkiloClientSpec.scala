package fi.vm.sade.hakuperusteet

import java.net.URLEncoder

import org.http4s.headers.{`Set-Cookie`, Location}
import org.scalatest.{Matchers, FlatSpec}
import org.http4s.Uri
import org.http4s._
import org.http4s.client.Client
import org.http4s.dsl._

import scalaz.concurrent.Task
import scalaz.stream._


class HenkiloClientSpec extends FlatSpec with Matchers {
  val virkailijaUri: Uri = Uri(path = "https://localhost")

  behavior of "HenkiloClient"

  it should "inject a CAS session into a http request as a JSESSIONID cookie" in {
    val casParams = CasParams("/sijoittelu-service", "foo", "bar")
    val casMock = new CasMock(virkailijaUrl = virkailijaUri, params = casParams)
    val mock = new Client {
      override def shutdown(): Task[Unit] = Task.now[Unit] {}
      override def prepare(req: Request): Task[Response] = req match {
        case req@ GET -> Root / "sijoittelu-service" / "resources" / "tila" / "hakukohde" / "1.2.246.562.20.31751481081" =>
          casMock.addStep("valid session")
          Ok("""{"personOid":"1.2.3.4"}""")
        case _ =>
          casMock.addStep("invalid request")
          NotFound()
      }
    }
    val client = new CasAbleClient(new CasClient(virkailijaUri, casMock),
      CasParams("/sijoittelu-service", "foo", "bar"), mock)
    val henkiloClient = new HenkiloClient(virkailijaUri, client)

    val henkilo = henkiloClient .haeHenkilo(LöyhästiTunnistettuHenkilö(email="",henkilötunnus="1.2.246.562.20.31751481081")).run

    henkilo.personOid shouldEqual "1.2.3.4"

    casMock.steps should be (List(
      "created TGT-123",
      "created ST-123",
      "created session foobar-123",
      "valid session"
    ))
  }
}

class CasMock(var ticket: String = "123",
              virkailijaUrl: Uri = Uri(path = "https://localhost"),
              params: CasParams) extends Client {
  var steps: List[String] = List()
  def addStep(step: String) = steps = steps :+ step

  override def shutdown(): Task[Unit] = Task.now[Unit] {}

  override def prepare(req: Request): Task[Response] = req match {
    case req@ POST -> Root / "cas" / "v1" / "tickets" => req.decode[String] {
      case body if body == s"username=${params.username}&password=${params.password}" =>
        addStep(s"created TGT-$ticket")
        Created().withHeaders(Location(Uri(path = s"${virkailijaUrl.toString()}/cas/v1/tickets/TGT-$ticket")))
      case _ =>
        addStep(s"invalid login")
        Unauthorized(Challenge("", ""))
    }

    case req@ POST -> Root / "cas" / "v1" / "tickets" / tgt if tgt == s"TGT-$ticket" => req.decode[String] {
      case service if service == s"service=${URLEncoder.encode(s"${virkailijaUrl.toString()}${params.service}/j_spring_cas_security_check", "UTF8")}" =>
        addStep(s"created ST-$ticket")
        Ok(s"ST-$ticket")
      case _ =>
        addStep("invalid TGT url")
        BadRequest()
    }

    case req@ GET -> Root / service / "j_spring_cas_security_check" if params.service.indexOf(service) > -1 => req.queryString match {
      case s if s == s"ticket=ST-$ticket" =>
        addStep(s"created session foobar-$ticket")
        Ok().withHeaders(`Set-Cookie`(Cookie(name = "JSESSIONID", content = s"foobar-$ticket")))
      case _ =>
        addStep("invalid service ticket")
        Unauthorized(Challenge("", ""))
    }
  }

}