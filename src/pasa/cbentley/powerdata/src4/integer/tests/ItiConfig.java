package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInt;

public class ItiConfig {

   public ItiConfig(String name, IPowerLinkIntToInt c) {
      this.name = name;
      this.c = c;
   }

   IPowerLinkIntToInt c;

   String             name;
}
