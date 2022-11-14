package Components

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import CloudletComponent.*
import HelperUtils.Parameters
import org.scalatest.matchers.must.Matchers.mustBe

class CoudletComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Cloudlet Component Test"

  it should "Initialize correctly cloudlet types" in {

    Parameters.cloudletTypes.foreach(componentType => getCloudletConfig(componentType) mustBe a[Cloudlet])

  }


}
