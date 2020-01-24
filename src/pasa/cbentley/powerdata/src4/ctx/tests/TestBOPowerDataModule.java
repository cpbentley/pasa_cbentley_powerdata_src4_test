package pasa.cbentley.powerdata.src4.ctx.tests;

import pasa.cbentley.byteobjects.src4.interfaces.IJavaObjectFactory;
import pasa.cbentley.powerdata.src4.PowerDataTestCase;
import pasa.cbentley.powerdata.src4.ctx.BOPowerDataModule;

public class TestBOPowerDataModule extends PowerDataTestCase {

   public void setUpMord() {

   }

   public void testCreate() {

      assertEquals(2, boc.getBOModuleManager().getModulesCount());

      //we create a new module
      BOPowerDataModule modulePowerData = new BOPowerDataModule(pdc);

      assertEquals(2, boc.getBOModuleManager().getModulesCount());

      IJavaObjectFactory fac = modulePowerData.getDefaultFactory();

      assertEquals(fac, pdc.getPowerFactory());

      modulePowerData.subToStringType(0);

   }

}
