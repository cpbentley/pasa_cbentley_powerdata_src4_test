package pasa.cbentley.powerdata.src4;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.byteobjects.src4.core.interfaces.IBOByteControler;
import pasa.cbentley.byteobjects.src4.core.interfaces.IBOAgentManaged;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.powerdata.src4.base.PowerFactory;
import pasa.cbentley.powerdata.src4.ctx.PDCtx;
import pasa.cbentley.testing.engine.TestCaseBentley;

public class TestPowerDataAbstract extends TestCaseBentley implements IByteObject, IBOByteControler, IBOAgentManaged {

   protected PDCtx        pdc;

   protected BOCtx        boc;

   public void setupAbstract() {
      boc = new BOCtx(uc);
      pdc = new PDCtx(uc, boc);
   }

}
