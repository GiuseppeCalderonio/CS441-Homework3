package HelperUtilsTest

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import HelperUtils.Parameters.*

class ParametersTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Parameters Test"

  // cloudlets tests

  it should "Never throw an exception when calling the method checkType with correct values for cloudlets" in {

    cloudletTypes.foreach( typeName => checkType("cloudlet", typeName , cloudletTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for cloudlets" in {

    an [RuntimeException] should be thrownBy checkType("cloudlet", "wrong value", cloudletTypes)
  }

  // datacenter tests

  it should "Never throw an exception when calling the method checkType with correct values for datacenters" in {

    datacenterTypes.foreach(typeName => checkType("datacenter", typeName, datacenterTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for datacenters" in {

    an[RuntimeException] should be thrownBy checkType("datacenter", "wrong value", datacenterTypes)
  }

  // host tests

  it should "Never throw an exception when calling the method checkType with correct values for hosts" in {

    hostsTypes.foreach(typeName => checkType("host", typeName, hostsTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for hosts" in {

    an[RuntimeException] should be thrownBy checkType("host", "wrong value", hostsTypes)
  }

  // horizontal scaling tests

  it should "Never throw an exception when calling the method checkType with correct values for horizontal scaling" in {

    horizontalScalingTypes.foreach(typeName => checkType("horizontal scaling", typeName, horizontalScalingTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for horizontal scaling" in {

    an[RuntimeException] should be thrownBy checkType("horizontal scaling", "wrong value", horizontalScalingTypes)
  }

  // vertical scaling tests

  it should "Never throw an exception when calling the method checkType with correct values for vertical scaling" in {

    verticalScalingTypes.foreach(typeName => checkType("vertical scaling", typeName, verticalScalingTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for vertical scaling" in {

    an[RuntimeException] should be thrownBy checkType("vertical scaling", "wrong value", verticalScalingTypes)
  }

  // vm tests

  it should "Never throw an exception when calling the method checkType with correct values for vms" in {

    vmTypes.foreach(typeName => checkType("vm", typeName, vmTypes) should be(true))
  }

  it should "Throw an exception when calling the method checkType with incorrect values for vms" in {

    an[RuntimeException] should be thrownBy checkType("vm", "wrong value", vmTypes)
  }



}
