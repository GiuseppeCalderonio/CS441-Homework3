package ComponentsTest

import Components.VmComponent.getVmConfig
import HelperUtils.Parameters
import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.cloudbus.cloudsim.vms.Vm
import org.scalatest.matchers.must.Matchers.mustBe


class VmComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Vm Component Test"

  it should "Initialize correctly vm types" in {

    Parameters.vmTypes.foreach(componentType => getVmConfig(componentType) mustBe a[Vm])

  }

}
