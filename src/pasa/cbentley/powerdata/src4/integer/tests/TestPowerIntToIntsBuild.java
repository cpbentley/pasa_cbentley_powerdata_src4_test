package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.byteobjects.src4.core.ByteObjectManaged;
import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInts;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechIntToIntsBuild;
import pasa.cbentley.powerdata.src4.integer.PowerIntToIntsBuild;

public class TestPowerIntToIntsBuild extends TestPowerIntToIntsAbstract implements ITechIntToIntsBuild {

   public ItisBuildConfig[] createInstance() {
      ItisBuildConfig[] configs = new ItisBuildConfig[16];

      configs[0] = new ItisBuildConfig("Default", new PowerIntToIntsBuild(pdc));
      configs[1] = new ItisBuildConfig("DefAsc", getTech(ITIS_ARRAY_ORDER_2_ASC, false));
      configs[2] = new ItisBuildConfig("DefDesc", getTech(ITIS_ARRAY_ORDER_3_DESC, false));
      configs[3] = new ItisBuildConfig("DefPrefix", getTech(ITIS_ARRAY_ORDER_1_APPEND_PREFIX, false));

      configs[4] = new ItisBuildConfig("EmptiesDesc", getTech(ITIS_ARRAY_ORDER_3_DESC, true));
      configs[5] = new ItisBuildConfig("EmptiesAsc", getTech(ITIS_ARRAY_ORDER_2_ASC, true));
      configs[6] = new ItisBuildConfig("EmptiesPref", getTech(ITIS_ARRAY_ORDER_1_APPEND_PREFIX, true));
      configs[7] = new ItisBuildConfig("EmptiesSuf", getTech(ITIS_ARRAY_ORDER_0_APPEND_SUFFIX, true));

      configs[10] = new ItisBuildConfig("DefDescDuplicates", getTechD(ITIS_ARRAY_ORDER_3_DESC, false));
      configs[9] = new ItisBuildConfig("DefAscDuplicates", getTechD(ITIS_ARRAY_ORDER_2_ASC, false));
      configs[11] = new ItisBuildConfig("DefPrefixDuplicates", getTechD(ITIS_ARRAY_ORDER_1_APPEND_PREFIX, false));
      configs[8] = new ItisBuildConfig("SufDuplicates", getTechD(ITIS_ARRAY_ORDER_0_APPEND_SUFFIX, false));

      configs[12] = new ItisBuildConfig("EmptiesDescDuplicates", getTechD(ITIS_ARRAY_ORDER_3_DESC, true));
      configs[13] = new ItisBuildConfig("EmptiesAscDuplicates", getTechD(ITIS_ARRAY_ORDER_2_ASC, true));
      configs[14] = new ItisBuildConfig("EmptiesPrefDuplicates", getTechD(ITIS_ARRAY_ORDER_1_APPEND_PREFIX, true));
      configs[15] = new ItisBuildConfig("EmptiesSufDuplicates", getTechD(ITIS_ARRAY_ORDER_0_APPEND_SUFFIX, true));

      return configs;
   }

   public ItiConfig[] createIntToInt() {
      ItisBuildConfig[] ob = createInstance();
      ItiConfig[] cc = new ItiConfig[ob.length];
      for (int i = 0; i < cc.length; i++) {
         cc[i] = new ItiConfig(ob[i].getName(), ob[i].getPowerLinkIntToInts());
      }
      return cc;
   }

   public IPowerLinkIntToInts getTech(int sort, boolean empties, boolean duplicates) {
      ByteObjectManaged bo = pdc.getTechFactory().getPowerIntToIntsBuildRootTech();
      bo.set1(ITIS_OFFSET_02_ORDER_TYPE1, sort);
      bo.setFlag(ITIS_BUILD_OFFSET_01_FLAG1, ITIS_BUILD_FLAG_1_EMPTIES, empties);
      bo.setFlag(ITIS_OFFSET_01_FLAG, ITIS_FLAG_2_DUPLICATES, duplicates);
      return new PowerIntToIntsBuild(pdc, bo);
   }

   public IPowerLinkIntToInts getTechD(int sort, boolean empties) {
      return getTech(sort, empties, true);
   }

   public IPowerLinkIntToInts getTech(int sort, boolean empties) {
      return getTech(sort, empties, false);
   }

   public void setupAbstract() {
      super.setupAbstract();
   }


}
