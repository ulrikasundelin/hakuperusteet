package fi.vm.sade.hakuperusteet

import com.typesafe.config.Config
import org.joda.time.DateTime
import org.scalatra.ScalatraServlet

class VetumaServlet(config: Config) extends ScalatraServlet {

  get("/openvetuma") {
    val language = "fi"
    val ref = "1234561"
    val orderNro = Vetuma.generateOrderNumber

    val v = VetumaUrl(
      config.getString("vetuma.host"),
      DateTime.now,
      language,
      config.getString("vetuma.success.url"),
      config.getString("vetuma.cancel.url"),
      config.getString("vetuma.error.url"),
      config.getString("vetuma.app.name"),
      config.getString("vetuma.amount"),
      ref,
      orderNro,
      config.getString("vetuma.msg.bueyr"),
      config.getString("vetuma.msg.seller"),
      config.getString("vetuma.msg.form")
    )
    v.toUrl
  }

}
