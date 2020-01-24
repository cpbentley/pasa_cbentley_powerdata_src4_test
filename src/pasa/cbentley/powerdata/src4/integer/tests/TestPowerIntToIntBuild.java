package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.byteobjects.src4.core.ByteObjectManaged;
import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInt;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechIntToInts;
import pasa.cbentley.powerdata.src4.integer.PowerIntToIntBuild;

public class TestPowerIntToIntBuild extends TestPowerIntToInt implements ITechIntToInts {

   public IPowerLinkIntToInt createInstance() {
      return new PowerIntToIntBuild(pdc, getTestTech());
   }

   public ItiConfig[] createIntToInt() {
      ItiConfig[] cc = new ItiConfig[1];
      cc[0] = new ItiConfig("Def", new PowerIntToIntBuild(pdc));
      return cc;
   }

   public ByteObjectManaged getTestTech() {
      return pdc.getTechFactory().getPowerIntToIntBuildRootTech();
   }

   public void testGrowthBelowAndTop() {
      ByteObjectManaged bo = pdc.getTechFactory().getPowerIntToIntBuildRootTech();
      bo.set4(PS_OFFSET_04_START_POINTER4, -4);
      bo.set4(PS_OFFSET_05_END_POINTER4, 10);
      bo.setFlag(PS_OFFSET_01_FLAG, PS_FLAG_4_GROWTH_BELOW, true);
      bo.setFlag(PS_OFFSET_01_FLAG, PS_FLAG_5_GROWTH_TOP, true);

      PowerIntToIntBuild pit = new PowerIntToIntBuild(pdc, bo);

      pit.setKeyData(-4, -4);

      pit.setKeyData(10, 10);

      assertEquals(-4, pit.getKeyData(-4));
      assertEquals(10, pit.getKeyData(10));

      pit.setKeyData(-100, -100);

      assertEquals(-4, pit.getKeyData(-4));
      assertEquals(10, pit.getKeyData(10));

      assertEquals(-100, pit.getKeyData(-100));

      assertEquals(true, pit.isValidKey(-100));

      assertEquals(false, pit.isValidKey(-101));

   }
}
