package pasa.cbentley.powerdata.src4;

import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.tech.ITechByteControler;
import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;
import pasa.cbentley.byteobjects.src4.tech.ITechObjectManaged;
import pasa.cbentley.powerdata.src4.base.PowerFactory;
import pasa.cbentley.powerdata.src4.ctx.PDCtx;
import pasa.cbentley.testing.engine.TestCaseBentley;

public class TestPowerDataAbstract extends TestCaseBentley implements ITechByteObject, ITechByteControler, ITechObjectManaged {

   protected PDCtx        pdc;

   protected BOCtx        boc;

   public void setupAbstract() {
      boc = new BOCtx(uc);
      pdc = new PDCtx(uc, boc);
   }

}
