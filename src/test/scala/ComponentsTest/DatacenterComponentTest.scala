package ComponentsTest

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import CloudComponents.DatacenterComponent.getDatacenterConfig
import HelperUtils.Parameters
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.scalatest.matchers.must.Matchers.mustBe

/**
 * this test class is used to test the datacenter
 */
class DatacenterComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Datacenter Component Test"

  it should "Initialize correctly Datacenter types" in {

    implicit val cloudsim: CloudSim = new CloudSim()

    Parameters.datacenterTypes.foreach(componentType => getDatacenterConfig(componentType) mustBe a[Datacenter])

  }

}
