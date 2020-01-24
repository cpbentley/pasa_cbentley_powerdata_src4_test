package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.byteobjects.src4.core.ByteController;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.core.ByteObjectManaged;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechIntPowerArray;
import pasa.cbentley.powerdata.src4.PowerDataTestCase;
import pasa.cbentley.powerdata.src4.integer.PowerIntArrayBuild;
import pasa.cbentley.powerdata.src4.integer.PowerIntArrayRun;

/**
 * 
 * @author Charles Bentley
 *
 */
public class TestIntPowerArray extends PowerDataTestCase {

   public void testBagBuild() {
      PowerIntArrayBuild ib = new PowerIntArrayBuild(pdc);
      int num = 0;
      for (int i = 0; i <= num; i++) {
         ib.addInt(i);
      }

      int[] values = ib.getInts();
      for (int i = 0; i < values.length; i++) {
         values[i] = i;
      }

   }

   public void testBagBuildVersionConvert() {
      PowerIntArrayBuild bag = new PowerIntArrayBuild(pdc);
      bag.getTech().setVersioning(true);

      assertNull(bag.getMemController());

      bag.addValue(4);
      bag.addValue(5);
      bag.addValue(6);

      assertEquals(3, bag.getVersion());

      bag.addValue(124);
      bag.addValue(125);
      bag.addValue(126);

      assertEquals(6, bag.getVersion());

      int[] in = bag.getValues();
      assertEquals(in.length, 6);
      assertEquals(in[0], 4);
      assertEquals(in[1], 5);
      assertEquals(in[2], 6);
      assertEquals(in[3], 124);
      assertEquals(in[4], 125);
      assertEquals(in[5], 126);

   }

   /**
    * Stores 
    */
   public void testIntBagSimpleByte() {

      //we expect the agent length to stay zero.
      ByteObjectManaged tech = pdc.getTechFactory().getPowerIntArrayRunRoot();

      assertEquals(ITechIntPowerArray.ARRAY_INT_BASIC_SIZE, tech.getLength());
      assertEquals(54, tech.getLength());

      //setting the flag here forces
      tech.setVersioning(true);

      assertEquals(56, tech.getLength());

      assertEquals(0, tech.getVersion());

      //tech length is 34
      PowerIntArrayRun bag = new PowerIntArrayRun(pdc, tech);
      
      //the tech stays a simple byte object but the underlying byte array represents a ByteObejctManaged
      assertEquals(0xFFFF, tech.getLength());
      assertEquals(36, bag.getLength());

      System.out.println(bag);

      assertEquals(0, bag.getVersion());

      bag.addValue(4);
      assertEquals(1, bag.getVersion());
      assertEquals(1, bag.get4(ITechIntPowerArray.ARRAY_OFFSET_05_SIZE4));

      System.out.println(bag);
      assertEquals(1, bag.getVersion());
      bag.addValue(5);

      assertEquals(2, bag.get4(ITechIntPowerArray.ARRAY_OFFSET_05_SIZE4));

      bag.addValue(6);

      int[] in = bag.getValues();
      assertEquals(in.length, 3);
      assertEquals(in[0], 4);
      assertEquals(in[1], 5);
      assertEquals(in[2], 6);

      assertEquals(3, bag.getVersion());

      bag.addValue(124);

      System.out.println(bag);

      in = bag.getValues();
      assertEquals(in.length, 4);
      assertEquals(in[0], 4);
      assertEquals(in[1], 5);
      assertEquals(in[2], 6);
      assertEquals(in[3], 124);

      assertEquals(4, bag.get4(ITechIntPowerArray.ARRAY_OFFSET_05_SIZE4));

      bag.addValue(125);
      bag.addValue(126);

      System.out.println(bag);
      in = bag.getValues();
      assertEquals(in.length, 6);
      assertEquals(in[0], 4);
      assertEquals(in[1], 5);
      assertEquals(in[2], 6);
      assertEquals(in[3], 124);
      assertEquals(in[4], 125);
      assertEquals(in[5], 126);

      assertEquals(6, bag.getSize());
      assertEquals(6, bag.get4(ITechIntPowerArray.ARRAY_OFFSET_05_SIZE4));

      assertEquals(Integer.MAX_VALUE, bag.getValueOrderCount(3));
      assertEquals(1, bag.getValueOrderCount(4));
      assertEquals(2, bag.getValueOrderCount(5));
      assertEquals(3, bag.getValueOrderCount(6));
      assertEquals(4, bag.getValueOrderCount(124));
      assertEquals(5, bag.getValueOrderCount(125));
      assertEquals(6, bag.getValueOrderCount(126));
      assertEquals(Integer.MAX_VALUE, bag.getValueOrderCount(127));

      assertEquals(Integer.MAX_VALUE, bag.getValuePosition(3));
      assertEquals(1, bag.getValuePosition(4));
      assertEquals(2, bag.getValuePosition(5));
      assertEquals(3, bag.getValuePosition(6));
      assertEquals(4, bag.getValuePosition(124));
      assertEquals(5, bag.getValuePosition(125));
      assertEquals(6, bag.getValuePosition(126));
      assertEquals(Integer.MAX_VALUE, bag.getValuePosition(127));

      bag.addValue(3);
      in = bag.getValues();
      assertEquals(in.length, 7);
      assertEquals(in[0], 3);
      assertEquals(in[1], 4);
      assertEquals(in[2], 5);
      assertEquals(in[3], 6);
      assertEquals(in[4], 124);

      bag.addValue(7);
      in = bag.getValues();
      assertEquals(in.length, 8);
      assertEquals(in[0], 3);
      assertEquals(in[4], 7);

      //add last
      bag.addValue(127);
      in = bag.getValues();
      assertEquals(in.length, 9);
      assertEquals(in[0], 3);
      assertEquals(in[4], 7);
      assertEquals(in[8], 127);

      //add last
      bag.addValue(129);
      in = bag.getValues();
      assertEquals(in.length, 10);

      bag.addValue(128);
      in = bag.getValues();
      assertEquals(in.length, 11);

      bag.addValue(-16);
      bag.addValue(-15);

      System.out.println(bag);
      assertEquals(bag.getValueOrderCount(-16), 3);
      assertEquals(bag.getValueOrderCount(-15), 4);

      System.out.println(bag.toString());

      in = bag.getValues();
      assertEquals(in.length, 13);
      assertEquals(in[0], -128);
      assertEquals(in[1], -127);
      assertEquals(in[2], -16);
      assertEquals(in[3], -15);
      assertEquals(in[4], 3);
      assertEquals(in[5], 4);
      assertEquals(in[6], 5);
      assertEquals(in[7], 6);
      assertEquals(in[8], 7);
      assertEquals(in[9], 124);
      assertEquals(in[10], 125);
      assertEquals(in[11], 126);
      assertEquals(in[12], 127);

   }

   public void testIntBagByteArrayConstructor() {

      PowerIntArrayRun bag = new PowerIntArrayRun(pdc, pdc.getTechFactory().getPowerIntArrayRunRoot());
      add128(bag);
   }

   public void testIntBagSimpleByteLeastEfficient() {

      PowerIntArrayRun intArrayRun = new PowerIntArrayRun(pdc);

      add128(intArrayRun);

      //gets all the integers
      int[] ints = intArrayRun.getInts();

      assertEquals(ints.length, 128);
      assertEquals(ints[0], -128);
      assertEquals(ints[1], -126);
      assertEquals(ints[2], -124);
      assertEquals(ints[3], -122);

      assertEquals(ints[127] & 0xFF, 126);
      //we have 128 data each taking 2 bytes

      byte[] inefficientData = intArrayRun.toByteArray();

      ByteObject bo = new ByteObject(boc, inefficientData, 0);
      assertEquals(0xFFFF, bo.getShortIntUnSigned(A_OBJECT_OFFSET_3_LENGTH2));
      assertEquals(0xFFFF, bo.getLength());
      ByteObjectManaged bom = new ByteObjectManaged(boc, inefficientData, 0);
      System.out.println(bom);
      assertEquals(0xFFFF, bom.getShortIntUnSigned(A_OBJECT_OFFSET_3_LENGTH2));

      //assertEquals(291, bom.getLength());
      assertEquals(35, bom.getType());
      
      //reuse the data. read it as a single byte array data source
      //byte data cannot be trust... exception i
      ByteController bc = new ByteController(boc, null, inefficientData);
      PowerIntArrayRun newbag = (PowerIntArrayRun) bc.getAgentRoot();

      System.out.println(newbag);
      
      ints = newbag.getInts();
      assertEquals(ints.length, 128);

      //this brideges the gap between 0 and 2 values
      newbag.addInt(1);
      ints = newbag.getInts();

      //test merge
      PowerIntArrayRun ibag = new PowerIntArrayRun(pdc);
      ibag.addInt(1);
      ibag.addInt(3);
      ibag.addInt(2);
      int[] ii = ibag.getInts();
      assertEquals(1, ii[0]);
      assertEquals(2, ii[1]);
      assertEquals(3, ii[2]);
      assertEquals(3, ibag.getSize());

      ibag = new PowerIntArrayRun(pdc);
      ibag.addInt(46);
      ibag.addInt(47);
      ibag.addInt(44);
      ibag.addInt(45);
      ii = ibag.getInts();
      assertEquals(44, ii[0]);
      assertEquals(45, ii[1]);
      assertEquals(46, ii[2]);
      assertEquals(47, ii[3]);
      assertEquals(4, ii.length);

   }

   protected void add128(PowerIntArrayRun bag) {
      int count = 0;

      for (int i = 0; i < 256; i = i + 2) {
         bag.addInt(i);
         count++;
      }

      assertEquals(count, 128);
   }

   public void testIntBagSimpleByteNegative() {
      PowerIntArrayRun bag = new PowerIntArrayRun(pdc);
      for (int i = -30; i < 30; i++) {
         bag.addInt(i);
      }
      System.out.println(bag.toString());
      assertEquals(bag.getSize(), 60);
      int[] ints = bag.getInts();
      assertEquals(ints.length, 60);
      assertEquals(ints[0], -30);
      assertEquals(ints[59], 29);
      bag = new PowerIntArrayRun(pdc);
      for (int i = 1; i <= 127; i++) {
         bag.addInt(i);
      }
      assertEquals(bag.getSize(), 127);
      ints = bag.getInts();
      assertEquals(ints.length, 127);
      assertEquals(ints[0], 1);
      assertEquals(ints[126], 127);
      bag.addInt(128); //this is -128
      assertEquals(bag.getSize(), 128);
      ints = bag.getInts();
      assertEquals(ints[0], -128);
      assertEquals(ints[127], 127);
      bag.addInt(0);
      assertEquals(bag.getSize(), 129);
      bag.addInt(-1);
      assertEquals(bag.getSize(), 130);
      ints = bag.getInts();
      assertEquals(ints[1], -1);
      assertEquals(ints[2], 0);
      assertEquals(ints[0], -128);
      System.out.println(bag.toString());
      bag.addInt(254);
      assertEquals(bag.getSize(), 131);
      System.out.println(bag.toString());
      bag.addInt(-10);
      assertEquals(bag.getSize(), 132);
      ints = bag.getInts();
      System.out.println(bag.toString());

      assertEquals(bag.getValuePosition(-128), 1);
      assertEquals(bag.getValuePosition(128), 1);
      assertEquals(bag.getValuePosition(-10), 2);
      assertEquals(bag.getValuePosition(-2), 3);
      assertEquals(bag.getValuePosition(-1), 4);
      assertEquals(bag.getValuePosition(0), 5);

      assertEquals(bag.getValueFromPosition(1), -128);
      assertEquals(bag.getValueFromPosition(2), -10);
      assertEquals(bag.getValueFromPosition(3), -2);
      assertEquals(bag.getValueFromPosition(4), -1);
      assertEquals(bag.getValueFromPosition(5), 0);

      assertFalse(-23 == (('é' >>> 0) & 0xFF));
      assertEquals(-23, (byte) (('é' >>> 0) & 0xFF));
      assertEquals(233, (-23 & 0xFF));

      //value to position
      int[] mapping = bag.getPositionMapping();
      assertEquals(mapping[-128 & 0xFF], 1);
      assertEquals(mapping[-10 & 0xFF], 2);
      assertEquals(mapping[-2 & 0xFF], 3);
      assertEquals(mapping[-1 & 0xFF], 4);
      assertEquals(mapping[0], 5);
      assertEquals(mapping[1], 6);
      assertEquals(mapping[33], 38);

   }
}
