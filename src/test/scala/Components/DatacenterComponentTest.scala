package Components

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import HelperUtils.Parameters
import Components.*
import Components.DatacenterComponent.getDatacenterConfig
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.scalatest.matchers.must.Matchers.mustBe

class DatacenterComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Datacenter Component Test"

  it should "Initialize correctly Datacenter types" in {

    implicit val cloudsim = new CloudSim()

    Parameters.datacenterTypes.foreach(componentType => getDatacenterConfig(componentType) mustBe a[Datacenter])

  }

}
