package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.byteobjects.src4.core.ByteController;
import pasa.cbentley.byteobjects.src4.core.interfaces.IBOByteControler;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.utils.IntUtils;
import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInt;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechIntToInt;
import pasa.cbentley.powerdata.src4.TestPowerDataAbstract;
import pasa.cbentley.powerdata.src4.ctx.PDCtx;

public abstract class TestPowerIntToIntAbstract extends TestPowerDataAbstract implements ITechIntToInt {

   public abstract ItiConfig[] createIntToInt();

   /**
    * Tests the default build
    */
   public void testPutBasic() {
      ItiConfig[] configs = createIntToInt();
      for (int i = 0; i < configs.length; i++) {

         String name = configs[i].name;
         IPowerLinkIntToInt p = configs[i].c;

         assertEquals(name, 10, p.getSize());

         p.setKeyData(5, 6);

         assertEquals(name, 10, p.getSize());
         assertEquals(name, 6, p.getKeyData(5));

         p.setKeyData(7, 7);
         assertEquals(name, 10, p.getSize());
         assertEquals(name, 7, p.getKeyData(7));

         p.setKeyData(7, 8);
         assertEquals(name, 10, p.getSize());
         assertEquals(name, 8, p.getKeyData(7));

         p.setKeyData(50, 50);
         assertEquals(name, 50, p.getSize());
      }
   }

   public void setupAbstract() {
      super.setupAbstract();
      
      IDLogConfig config = uc.toDLog().getDefault().getConfig();
      config.setFlagTag(FLAG_25_PRINT_NULL, true);
   }

   protected IPowerLinkIntToInt getIntToIntFrom(byte[] data) {
      ByteController bc = new ByteController(boc, data);
      bc.setFactory(pdc.getFactory());
      IPowerLinkIntToInt p = (IPowerLinkIntToInt) bc.getAgentRoot();
      return p;
   }

   public void testGrowthTop() {
      ItiConfig[] configs = createIntToInt();
      for (int i = 0; i < configs.length; i++) {

         String name = configs[i].name;
         IPowerLinkIntToInt p = configs[i].c;

         int lastPointer = p.getTech().get4(PS_OFFSET_05_END_POINTER4);

         if (p.getTech().hasFlag(PS_OFFSET_01_FLAG, PS_FLAG_5_GROWTH_TOP)) {

            p.setKeyData(lastPointer + 2, 2);

            assertEquals(lastPointer + 2, p.getTech().get4(PS_OFFSET_05_END_POINTER4));

            assertEquals(2, p.getKeyData(lastPointer + 2));

         }
      }
   }

   public void testGrowthBelow() {
      ItiConfig[] configs = createIntToInt();
      for (int i = 0; i < configs.length; i++) {

         String name = configs[i].name;
         IPowerLinkIntToInt p = configs[i].c;

         int startPointer = p.getTech().get4(PS_OFFSET_04_START_POINTER4);

         if (p.getTech().hasFlag(PS_OFFSET_01_FLAG, PS_FLAG_4_GROWTH_BELOW)) {

            p.setKeyData(startPointer - 2, 2);

            assertEquals(startPointer - 2, p.getTech().get4(PS_OFFSET_04_START_POINTER4));
            assertEquals(2, p.getKeyData(startPointer - 2));

         }
      }
   }

   public void testDefValue() {

      ItiConfig[] configs = createIntToInt();
      for (int i = 0; i < configs.length; i++) {

         String name = configs[i].name;
         try {
            IPowerLinkIntToInt p = configs[i].c;

            if (name.equals("EmptiesDesc")) {
               assertEquals(true, true);
            }

            int defValue = p.getTech().get4(ITI_OFFSET_02_DEF_VALUE4);
            if (p.getTech().hasFlag(PS_OFFSET_01_FLAG, PS_FLAG_4_GROWTH_BELOW)) {
               int startPointer = p.getTech().get4(PS_OFFSET_04_START_POINTER4);
               p.setKeyData(startPointer - 2, 2);
               assertEquals(2, p.getKeyData(startPointer - 2));
               assertEquals(defValue, p.getKeyData(startPointer - 1));
            }

            if (p.getTech().hasFlag(PS_OFFSET_01_FLAG, PS_FLAG_5_GROWTH_TOP)) {
               int lastPointer = p.getTech().get4(PS_OFFSET_05_END_POINTER4);
               p.setKeyData(lastPointer + 2, 2);
               assertEquals(2, p.getKeyData(lastPointer + 2));
               assertEquals(defValue, p.getKeyData(lastPointer + 1));
            }
         } catch (Exception e) {
            throw new RuntimeException(name, e);
         }
      }
   }

   public void testSerializeEmtpy() {
      ItiConfig[] configs = createIntToInt();
      for (int i = 0; i < configs.length; i++) {

         String name = configs[i].name;
         IPowerLinkIntToInt p = configs[i].c;

         int s = p.getSize();

         byte[] d = p.serializePack();

         IPowerLinkIntToInt p2 = getIntToIntFrom(d);

         assertEquals(s, p2.getSize());

         p2.setKeyData(5, 5);
         p.setKeyData(5, 5);
         assertEquals(5, p2.getKeyData(5));
         assertEquals(5, p.getKeyData(5));
      }
   }

   public void testSerializeOne() {
      ItiConfig[] configs = createIntToInt();
      
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].name;
         IPowerLinkIntToInt dataStruc = configs[i].c;

         dataStruc.setKeyData(5, 5);
         assertEquals(name, 5, dataStruc.getKeyData(5));
         int s = dataStruc.getSize();

         byte[] data = dataStruc.serializePack();

         assertEquals(1, IntUtils.readInt24BE(data, IBOByteControler.MEMC_OFFSET_05_NUM_AGENTS3));

         IPowerLinkIntToInt p2 = getIntToIntFrom(data);

         assertEquals(name, s, p2.getSize());
         if (name.equals("EmptiesDesc")) {
            assertEquals(true, true);
            logPrint(dataStruc);
            logPrint(p2);
         }
         assertEquals(name, 5, p2.getKeyData(5));

         assertEquals(name, 5, dataStruc.getKeyData(5));
      }
   }
}
