package pasa.cbentley.powerdata.src4.integer.tests;

import pasa.cbentley.powerdata.spec.src4.power.integers.IPowerLinkIntToInts;

public class ItisBuildConfig {

   public ItisBuildConfig(String name, IPowerLinkIntToInts c) {
      this.setName(name);
      this.powerLinkIntToInts = c;
   }

   public String getName() {
      return name;
   }

   void setName(String name) {
      this.name = name;
   }

   IPowerLinkIntToInts getPowerLinkIntToInts() {
      return powerLinkIntToInts;
   }

   private IPowerLinkIntToInts powerLinkIntToInts;

   private String              name;
}
