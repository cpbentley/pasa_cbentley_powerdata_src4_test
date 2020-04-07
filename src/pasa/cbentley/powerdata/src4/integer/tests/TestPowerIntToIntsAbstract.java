package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.byteobjects.src4.core.ByteController;
import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInts;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechIntToInts;

public abstract class TestPowerIntToIntsAbstract extends TestPowerIntToIntAbstract implements ITechIntToInts {

   public void setupAbstract() {
      super.setupAbstract();
   }

   
   public void testBasics() {

      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         c.addValueToKey(450, 45);

         int[] a = c.getKeyValues(45);
         assertEquals(name, 1, a.length);
         assertEquals(name, 450, a[0]);

         assertEquals(name, 450, c.getKeyValue(45, 0));
      }
   }

   public void test150Add() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         logPrint(c);
         if(name.startsWith("Empties")) {
            assertTrue(true);
         }
         for (int j = 0; j < 150; j++) {
            c.addValueToKey(j, j);
         }
         logPrint(c);
         for (int j = 0; j < 150; j++) {
            assertEquals(name, j, c.getKeyData(j));
            assertEquals(name, 1, c.getNumValuesFromKey(j));
            assertEquals(name, j, c.getKeyValues(j)[0]);
         }

      }
   }

   public void testGetIndexOfValueFromKey() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         int index = c.getIndexOfValueFromKey(4, 4);
         assertEquals(name, -1, index);

         c.addValueToKey(4, 4);

         assertEquals(null, c.getKeyValues(2));

         index = c.getIndexOfValueFromKey(4, 4);
         assertEquals(name, 0, index); //should be the first
         assertEquals(true, c.isValueBelongToKey(4, 4));

         c.addValueToKey(10, 4);
         assertEquals(true, c.isValueBelongToKey(10, 4));
         
      }
   }
   
   protected IPowerLinkIntToInts getIntToIntsFrom(byte[] data) {
      ByteController bc = new ByteController(boc, data);
      IPowerLinkIntToInts p = (IPowerLinkIntToInts) bc.getAgentRoot();
      return p;
   }

   public void testSerialize() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();
         if (name.equals("DefAsc")) {
            assertEquals(true, true);
         }
         c.addValueToKey(4, 45);
         c.addValuesToKey(new int[] { 47, 45, 89 }, 6);

         c.addValueToKey(4, 45);
         c.addValueToKey(4, 44);

         byte[] d = c.serializePack();
         
         IPowerLinkIntToInts p2 = getIntToIntsFrom(d);

         assertEquals(3, p2.getNumValuesFromKey(6));
         assertEquals(4, p2.getKeyData(44));
      }
   }

   public void testAddValueToArray() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();
         if (name.equals("DefAsc")) {
            assertEquals(true, true);
         }
         c.addValueToKey(4, 45);
         assertEquals(name, 1, c.getNumValuesFromKey(45));
         assertEquals(name, 4, c.getKeyData(45));
         assertEquals(name, 4, c.getKeyValue(45, 0));
         int[] v = c.getKeyValues(45);
         assertNotNull(v);
         assertEquals(1, v.length);
         assertEquals(name, 4, v[0]);

      }
   }

   public void testIsValidKey() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         assertEquals(name, false, c.isValidKey(45));
         c.addValueToKey(45, 450);
         assertEquals(name, true, c.isValidKey(45));
      }
   }

   public void testAddValuesToKey() {

      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         int[] ar = new int[] { 4, 6, 7, 8 };
         c.addValuesToKey(ar, 4);

         assertEquals(name, 4, c.getNumValuesFromKey(4));

         int[] v = c.getKeyValues(4);

         int sortType = c.getTech().get1(ITIS_OFFSET_02_ORDER_TYPE1);
         if (sortType == ITIS_ARRAY_ORDER_0_APPEND_SUFFIX) {
            int offset = 0;
            assertEquals(name, 4, v[offset++]);
            assertEquals(name, 6, v[offset++]);
            assertEquals(name, 7, v[offset++]);
            assertEquals(name, 8, v[offset++]);
         } else if (sortType == ITIS_ARRAY_ORDER_1_APPEND_PREFIX) {

         } else if (sortType == ITIS_ARRAY_ORDER_2_ASC) {

         } else if (sortType == ITIS_ARRAY_ORDER_3_DESC) {

         }

         c.addValuesToKey(new int[] { 0, 5, 45, 100 }, 4);
         v = c.getKeyValues(4);
         if (sortType == ITIS_ARRAY_ORDER_0_APPEND_SUFFIX) {
            int offset = 0;
            assertEquals(name, 4, v[offset++]);
            assertEquals(name, 6, v[offset++]);
            assertEquals(name, 7, v[offset++]);
            assertEquals(name, 8, v[offset++]);
            assertEquals(name, 0, v[offset++]);
            assertEquals(name, 5, v[offset++]);
            assertEquals(name, 45, v[offset++]);
            assertEquals(name, 100, v[offset++]);
         } else if (sortType == ITIS_ARRAY_ORDER_1_APPEND_PREFIX) {

         } else if (sortType == ITIS_ARRAY_ORDER_2_ASC) {
            int offset = 0;
            assertEquals(name, 0, v[offset++]);
            assertEquals(name, 4, v[offset++]);
            assertEquals(name, 5, v[offset++]);
            assertEquals(name, 6, v[offset++]);
            assertEquals(name, 7, v[offset++]);
            assertEquals(name, 8, v[offset++]);
            assertEquals(name, 45, v[offset++]);
            assertEquals(name, 100, v[offset++]);
         } else if (sortType == ITIS_ARRAY_ORDER_3_DESC) {

         }
      }
   }

   public void testRemoveKey() {

      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         if (name.equals("EmptiesDesc")) {
            assertEquals(true, true);
         }

         c.addValueToKey(5, 5);
         int v1 = c.removeKeyValues(5);

         assertEquals(name, 1, v1);

         assertEquals(0, c.getNumValuesFromKey(5));

         logPrint(c);

         int[] vf1 = c.getKeyValues(5);

         assertNull(name, vf1);

         int[] ar = new int[] { 4, 6, 7, 8 };
         c.addValuesToKey(ar, 4);

         int v = c.removeKeyValues(4);

         assertEquals(name, 4, v);

         int[] vf = c.getKeyValues(4);
         assertNull(vf);
      }
   }

   public void testSetValuesForKey() {
      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         c.addValueToKey(5, 5);
         assertEquals(5, c.getKeyData(5));

         c.setValuesForKey(new int[] { 6 }, 5);

         assertEquals(6, c.getKeyData(5));
      }

   }

   public void testRemoveValueFromKey() {

      ItisBuildConfig[] configs = createInstance();
      for (int i = 0; i < configs.length; i++) {
         String name = configs[i].getName();
         IPowerLinkIntToInts c = configs[i].getPowerLinkIntToInts();

         int[] ar = new int[] { 4, 6 };
         c.addValuesToKey(ar, 4);

         c.removeValueFromKey(6, 4);

         logPrint(c);

         assertEquals(1, c.getNumValuesFromKey(4));
         int[] v = c.getKeyValues(4);

         assertEquals(name, 4, v[0]);

         c.removeValueFromKey(4, 4);
         assertEquals(0, c.getNumValuesFromKey(4));
         int[] vv = c.getKeyValues(4);
         assertNotNull(vv);
         assertEquals(0, vv.length);
      }
   }

   public abstract ItisBuildConfig[] createInstance();
}
