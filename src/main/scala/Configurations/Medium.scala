package Configurations

import Configurations.Configuration

object Medium {

  private val configName = "medium"

  @main def runConfMedium(): Unit = {

    Configuration.getConfig(configName, false)

  }

}
