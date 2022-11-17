package example

import Configurations.Configuration

object Large {

  private val configName = "large"

  @main def runConfLarge(): Unit = {

    Configuration.getConfig(configName, false)

  }

}
