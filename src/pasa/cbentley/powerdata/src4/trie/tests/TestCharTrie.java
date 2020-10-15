//package mordan.tests.struct.trie;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//
//import com.sun.org.apache.xml.internal.utils.Trie;
//
//import mordan.data.chars.CharCollectorBuild;
//import mordan.data.sequence.IDoubleArray;
//import mordan.data.trie.chars.CharTrie;
//import mordan.data.trie.common.ITrieNodeData;
//import mordan.data.trie.pack.TrieSingleDataBuildPack;
//import mordan.datastruct.spec.ICharsCollector;
//import mordan.interfaces.IUserLink;
//import mordan.memory.IMemAgent;
//import mordan.memory.MemController;
//import mordan.test.uilib.output.MordDataCase;
//import mordan.universal.utils.MUtils;
//import pasa.cbentley.core.src4.io.FileReader;
//import pasa.cbentley.core.src4.io.XString;
//import pasa.cbentley.core.src4.structs.IntToStrings;
//import pasa.cbentley.core.src4.utils.StringUtils;
//import pasa.cbentley.powerdata.spec.src4.guicontrols.SearchSession;
//import pasa.cbentley.powerdata.spec.src4.power.string.IPowerLinkStringToInt;
//import pasa.cbentley.powerdata.src4.TestPowerDataAbstract;
//import pasa.cbentley.powerdata.src4.string.CharCollectorRunLight;
//import pasa.cbentley.powerdata.src4.trie.PowerCharTrie;
//import pasa.cbentley.powerdata.src4.utils.TrieUtils;
//
//public class TestCharTrie extends TestPowerDataAbstract {
//
//   public static void verifyIntegrity(IUserLink ui,CharTrie french, String filename, String encod) {
//      FileReader fr = new FileReader(ui,300);
//      fr.openFile(filename, encod);
//      XString cp = null;
//      //problem with bom, there a lie with nothing
//      fr.readCharLine();
//      int wordcount = 0;
//      while ((cp = fr.readCharLine()) != null) {
//         if (cp.len == 0)
//            continue;
//         if (cp.chars[cp.offset] == '#')
//            continue;
//         String s = new String(cp.chars, cp.offset, cp.len);
//         assertEquals(s, true, french.hasWord(s));
//         wordcount++;
//      }
//
//   }
//
//   private void checkSingle(PowerCharTrie tm) {
//      assertEquals(10, tm.getData("sympa"));
//      assertEquals(7, tm.getData("sept"));
//
//      assertEquals(9, tm.getData("allo"));
//      assertEquals(5, tm.getData("bonjour"));
//      assertEquals(19, tm.getData("alarmant"));
//   }
//
//   private void doFrenchTrie(IPowerLinkStringToInt ct) {
//      System.out.println(ct.debug());
//
//      ct.maxCharsPerNode = 0;
//
//      assertEquals(-1, ct.charco.find("à".toCharArray(), 0, 1));
//      assertEquals(-1, ct.charco.find("à".toCharArray(), 0, 1));
//      //found because alphabet was included?
//      assertEquals(1, ct.charco.find("a".toCharArray(), 0, 1));
//      assertEquals(2, ct.charco.find("b".toCharArray(), 0, 1));
//
//      ct.addString("à");
//      ct.addString("abaissa");
//      ct.addString("abaissable");
//
//      testParentage(ct);
//      //	assertEquals("baissa", new String(ct.getChars(28)));
//      //	assertEquals("ble", new String(ct.getChars(29)));
//      System.out.println(ct.debug());
//      int cp = ct.nodedata.getCharPointer(29);
//      //	assertEquals("ble", new String(ct.getChars(29)));
//      //	assertEquals("ble", new String(ct.charco.getChars(cp)));
//
//      IntToStrings its = ct.getPrefixedStrings("ab");
//      for (int i = 0; i < its.strings.length; i++) {
//         System.out.println(its.strings[i]);
//      }
//      assertEquals(its.nextempty, its.strings.length);
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//
//      ct.addString("abaissables");
//
//      ct.addString("abaissai");
//      ct.addString("abaissaient");
//      ct.addString("abaissais");
//      ct.addString("abaissait");
//      ct.addString("abaissâmes", 33);
//      System.out.println(ct.debug());
//      ct.addString("abaissant", 44);
//
//      //assertEquals(10, ct.countWords(0));
//
//      System.out.println(ct.debug());
//
//      assertEquals(33, ct.getData("abaissâmes"));
//      assertEquals(44, ct.getData("abaissant"));
//
//      its = ct.getPrefixedStrings("ab");
//      for (int i = 0; i < its.strings.length; i++) {
//         System.out.println(its.strings[i]);
//      }
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//      assertEquals(its.strings[2], "abaissables");
//      assertEquals(its.strings[3], "abaissai");
//      assertEquals(its.strings[4], "abaissaient");
//      assertEquals(its.strings[5], "abaissais");
//      assertEquals(its.strings[6], "abaissait");
//      assertEquals(its.strings[7], "abaissant"); // french says  abaissâmes < abaissant
//      assertEquals(its.strings[8], "abaissâmes");
//      testParentage(ct);
//
//      ct.setMode(Trie.MODE_RUNNING);
//
//      its = ct.getPrefixedStrings("ab");
//      for (int i = 0; i < its.strings.length; i++) {
//         System.out.println(its.strings[i]);
//      }
//      System.out.println(ct.debug());
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//      assertEquals(its.strings[2], "abaissables");
//      assertEquals(its.strings[3], "abaissai");
//      assertEquals(its.strings[4], "abaissaient");
//      assertEquals(its.strings[5], "abaissais");
//      assertEquals(its.strings[6], "abaissait");
//      assertEquals(its.strings[7], "abaissant"); // french says  abaissâmes < abaissant
//      assertEquals(its.strings[8], "abaissâmes");
//
//      SearchSession ss = new SearchSession();
//      ss.resetPrefix("aba", 7);
//      its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 7);
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//      assertEquals(its.strings[2], "abaissables");
//      assertEquals(its.strings[3], "abaissai");
//      assertEquals(its.strings[4], "abaissaient");
//      assertEquals(its.strings[5], "abaissais");
//      assertEquals(its.strings[6], "abaissait");
//
//      ss = new SearchSession();
//      ss.resetPrefix("aba", 5);
//      its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 5);
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//      assertEquals(its.strings[2], "abaissables");
//      assertEquals(its.strings[3], "abaissai");
//      assertEquals(its.strings[4], "abaissaient");
//
//      System.out.println(ct.debug());
//      System.out.println(ss.lastVisitedRow);
//      its = ct.getPrefixedStrings(ss, its);
//      System.out.println(its);
//      assertEquals(its.nextempty, 9);
//      assertEquals(its.strings[0], "abaissa");
//      assertEquals(its.strings[1], "abaissable");
//      assertEquals(its.strings[2], "abaissables");
//      assertEquals(its.strings[3], "abaissai");
//      assertEquals(its.strings[4], "abaissaient");
//      assertEquals(its.strings[5], "abaissais");
//      assertEquals(its.strings[6], "abaissait");
//      assertEquals(its.strings[7], "abaissant"); // french says  abaissâmes < abaissant
//      assertEquals(its.strings[8], "abaissâmes");
//
//      ss.resetPrefix("abaissâ");
//      its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 1);
//      assertEquals(its.strings[0], "abaissâmes");
//
//   }
//
//   private void doHardSplit(CharTrie tm) {
//      testParentage(tm);
//
//      tm.addString("amandes", 1000);
//      //assertEquals("amandes", tm.getStringFromNode(0));
//      //assertEquals("amandes", tm.getStringFromNode(1));
//      tm.addString("amandines", 3333);
//      //assertEquals("amand", tm.getStringFromNode(1));
//      //assertEquals("b", tm.getStringFromNode(2));
//
//      assertEquals(3333, tm.getData("amandines"));
//      assertEquals(1000, tm.getData("amandes"));
//      System.out.println(tm.debug(1));
//      tm.addString("ameriques", 4444);
//
//      System.out.println(tm.debug(1));
//
//      assertEquals(1000, tm.getData("amandes"));
//      assertEquals(3333, tm.getData("amandines"));
//      assertEquals(4444, tm.getData("ameriques"));
//
//      testParentage(tm);
//
//      tm.addString("amendes", 1000);
//      tm.addString("amendines", 3333);
//      assertEquals(3333, tm.getData("amendines"));
//      assertEquals(1000, tm.getData("amendes"));
//      System.out.println(tm.debug(1));
//
//      testParentage(tm);
//      //tricky insert. hard split + ariques takes first place 'end' burgeon, shifting it (new node number)
//      tm.addString("amariques", 4444);
//
//      System.out.println(tm.debug(1));
//
//      assertEquals(1000, tm.getData("amendes"));
//      assertEquals(3333, tm.getData("amendines"));
//      assertEquals(4444, tm.getData("amariques"));
//
//      testParentage(tm);
//   }
//
//   private void doMultipleDataTest(CharTrie tm) {
//
//      tm.addString("Bonjour", 4);
//      assertEquals(1, tm.getNode("Bonjour"));
//
//      int[] ar = tm.getDatas(tm.getNode("bonjour"));
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 4);
//
//      assertEquals(4, tm.getData("bonjour"));
//
//      tm.addString("Bonjour", 5);
//      ar = tm.getStringData("bonjour");
//      assertEquals(ar[0], 4);
//      assertEquals(ar[1], 5);
//
//      tm.addString("Vie");
//      ar = tm.getStringData("vie");
//      assertEquals(ar.length, 0);
//
//      System.out.println(tm.debug());
//
//      tm.addString("Vie", 55);
//      ar = tm.getStringData("vie");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 55);
//
//      tm.addString("Manger", 66);
//      System.out.println(tm.debug());
//
//      ar = tm.getStringData("Manger");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 66);
//
//      tm.addString("Manager", 667);
//      tm.addString("Manager", 668);
//
//      System.out.println(tm.debug());
//
//      ar = tm.getStringData("Manger");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 66);
//
//      ar = tm.getStringData("vie");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 55);
//
//      ar = tm.getStringData("Manager");
//      assertEquals(ar[0], 667);
//      assertEquals(ar[1], 668);
//
//      System.out.println(tm.debug());
//
//      multipleVerif(tm);
//
//      assertEquals(6, tm.nodedata.getNodesSize());
//      System.out.println(tm.debug());
//      tm.setMode(Trie.MODE_RUNNING);
//
//      assertEquals(6, tm.nodedata.getNodesSize());
//      System.out.println(tm.getMemController().toString());
//
//      System.out.println(tm.debug());
//
//      multipleVerif(tm);
//
//   }
//
//   private void doSingle(CharTrie tm) {
//
//      tm.addString("Bonjour", 4);
//
//      assertEquals(1, tm.getNode("Bonjour"));
//      assertEquals(4, tm.getData("bonjour"));
//
//      //add the same string
//      tm.addString("Bonjour", 5);
//
//      assertEquals(5, tm.getData("bonjour"));
//
//      tm.addString("Allo", 9);
//      tm.addString("Alarmant", 19);
//
//      tm.addString("Sympa", 10);
//      tm.addString("Sept", 7);
//
//      checkSingle(tm);
//
//      System.out.println(tm.debug());
//      checkSingle(tm);
//
//      tm.setMode(Trie.MODE_RUNNING);
//
//      System.out.println(tm.getMemController().toString());
//      System.out.println(tm.debug());
//      checkSingle(tm);
//
//   }
//
//   private void multipleVerif(CharTrie tm) {
//      int[] ar;
//
//      assertEquals(true, tm.hasString("bonjour"));
//      IntToStrings its = tm.getPrefixedStrings("bonjour");
//      assertEquals(its.ints.length, 1);
//      if (tm.isNodeDataFlagSet(ITrieNodeData.FLAG_PARENT_REF)) {
//         assertEquals("bonjour", tm.getStringFromNode(its.ints[0]));
//      }
//
//      IntToStrings all = tm.getPrefixedStrings("");
//      assertEquals(all.ints.length, 4);
//      assertEquals(all.strings[0], "bonjour");
//      assertEquals(all.strings[1], "manager");
//      assertEquals(all.strings[2], "manger");
//      assertEquals(all.strings[3], "vie");
//
//      ar = tm.getStringData("bonjour");
//      assertEquals(ar.length, 2);
//      assertEquals(ar[0], 4);
//      assertEquals(ar[1], 5);
//
//      ar = tm.getStringData("vie");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 55);
//
//      ar = tm.getStringData("manger");
//      assertEquals(ar.length, 1);
//      assertEquals(ar[0], 66);
//
//      ar = tm.getStringData("manager");
//      assertEquals(ar.length, 2);
//      assertEquals(ar[0], 667);
//      assertEquals(ar[1], 668);
//   }
//
//   public void stestAllFrenchWords() {
//      CharTrie ct = new CharTrie(ui);
//
//      FileReader fr = new FileReader(ui, 300);
//      fr.openFile("/french_all.txt", "UTF-8");
//      XString cp = null;
//      int wordcount = 0;
//      while ((cp = fr.readCharLine()) != null) {
//         String s = new String(cp.chars, cp.offset, cp.len);
//         //System.out.println("Word:"+s);
//         ct.addString(s);
//         wordcount++;
//         if (wordcount % 1000 == 0) {
//            System.out.println("read " + wordcount + " byte read=" + fr.byteread + " free mem=" + Runtime.getRuntime().freeMemory());
//         }
//         if (wordcount == 50000)
//            break;
//      }
//      long time = System.currentTimeMillis();
//      //ct.cleanCharCompressor();
//      System.out.println("Time Cleaning Char Compressor = " + (System.currentTimeMillis() - time) + " ms");
//      System.out.println(ct.debugBuild());
//
//   }
//
//   public void stestEnglishTrieByLetter() {
//      char[] alpha = StringUtils.getLatinChar();
//      CharTrie[] tries = TrieUtils.makeLanguageTrie(ui,alpha, "/english_all.txt", "UTF-8");
//      IntToStrings its = tries[0].getPrefixedStrings("a");
//      assertEquals(its.nextempty, 5583);
//      //System.out.println(its.toString());
//   }
//
//   public void stestRussianDic() {
//      CharTrie ct = new CharTrie(ui);
//
//      System.out.println(ct.debug());
//
//      FileReader fr = new FileReader(ui, 300);
//      fr.openFile("/russian_all.txt", "UTF-8");
//      XString cp = null;
//      int wordcount = 0;
//      //problem with bom, there a lie with nothing
//      fr.readCharLine();
//      while ((cp = fr.readCharLine()) != null) {
//         //String s = new String(cp.chars,cp.offset,cp.len);
//         //System.out.println("Word:"+s);
//         if (cp.len == 0)
//            continue;
//         //	   if(cp.chars[cp.offset] != StringUtils.getCyrillicChar()[0])
//         //		break;
//         ct.addString(cp.chars, cp.offset, cp.len);
//         wordcount++;
//         if (wordcount % 5000 == 0) {
//            System.out.println("read " + wordcount + "\t words read=" + fr.byteread + "\t bytes free mem=" + Runtime.getRuntime().freeMemory());
//         }
//         if (wordcount == 10000)
//            break;
//      }
//      //ct.cleanCharCompressor();
//
//      //System.out.println(ct.debug());
//      System.out.println(ct.debugBuild());
//      System.out.println(ct.nodedata.getFamilySize(0));
//      //	int val = ct._tc.count1FamilySize(0);
//      //	System.out.println(val);
//   }
//
//   public void stestRussianTrieByLetter() {
//      char[] alpha = StringUtils.getCyrillicChar();
//
//      TrieUtils.makeLanguageTrie(ui,alpha, "/russian_all.txt", "UTF-8");
//
//      try {
//         FileOutputStream os = new FileOutputStream("C:\\Java\\workspace\\ME2SETests\\data\\russiantrie_lemma.txt");
//         OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
//         try {
//            osw.write("#BOM KILLER\n");
//            long size = TrieUtils.ccb.getSize();
//            if (os != null) {
//               for (int i = 1; i < size; i++) {
//                  char[] c = TrieUtils.ccb.getChars(i);
//                  osw.write(c, 0, c.length);
//                  osw.append('\n');
//               }
//               osw.flush();
//               osw.close();
//            }
//            os.close();
//         } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException();
//         }
//      } catch (FileNotFoundException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//         throw new RuntimeException();
//      } catch (UnsupportedEncodingException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//
//   }
//
//   public void test100000100001() {
//      CharTrie bt = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//
//      bt.addString("11", 2);
//      bt.addString("1101", 3);
//      bt.addString("10", 2);
//      bt.addString("10", 2);
//      bt.addString("10010001001", 2);
//      bt.addString("100000001001", 67);
//
//      assertEquals(bt.getData("100000001001"), 67);
//
//      bt.addString("100000100001", 5);
//
//      assertEquals(bt.getData("100000100001"), 5);
//
//      assertEquals(bt.getData("100000001001"), 1);
//
//   }
//
//   public void testBigTrie() {
//      char[] alpha = StringUtils.getLatinChar();
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      tm.initAlphabet(alpha);
//      int num = 500; //the number of items
//      int[] offsets = new int[num - 1];
//      char[] alphabet = StringUtils.getLatinChar();
//      char[][] keep = new char[501][];
//
//      for (int i = 1; i <= num; i++) {
//         char[] ar = new char[i];
//         int max = alphabet.length;
//         for (int j = 0; j < ar.length; j++) {
//            ar[j] = alphabet[j % max];
//         }
//         tm.addString(ar, i);
//         keep[i] = ar;
//      }
//      //System.out.println(tm.debug());
//      for (int i = 1; i < keep.length; i++) {
//         int[] addies = tm.getStringData(new String(keep[i]));
//         assertEquals(1, addies.length);
//         assertEquals(i, addies[0]);
//      }
//   }
//
//   public void testBuildToRun() {
//
//   }
//
//   public void testFramedPrefixSearch() {
//      CharTrie ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_MULTIPLE_SPREAD_CONFIG, null);
//      ct.setNodeDataFlag(ITrieNodeData.FLAG_PARENT_REF, true);
//      ct.setCharTrieFlag(CharTrie.FLAG_PERSO_USES, true);
//
//      ct.addString("price");
//      ct.addString("pride");
//      ct.addString("priest");
//      ct.addString("priest");
//
//      ct.addString("primary");
//      ct.addString("prime");
//
//      ct.addString("prime minister");
//      ct.addString("prime number");
//      ct.addString("primitive");
//      ct.addString("princess");
//      ct.addString("principal");
//
//      ct.addString("principle", 4, 0);
//      ct.addString("print");
//      ct.addString("printer");
//      ct.addString("prison");
//      ct.addString("prisoner of war");
//
//      ct.addString("privacy");
//      ct.addString("private");
//      ct.addString("privilege");
//      ct.addString("prize");
//
//      System.out.println(ct.debug());
//      SearchSession ss = new SearchSession();
//      ss.resetPrefix("pri", 5);
//
//      IntToStrings its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 5);
//      System.out.println(its);
//      assertEquals(its.strings[0], "price");
//      assertEquals(its.strings[1], "pride");
//      assertEquals(its.strings[2], "priest");
//      assertEquals(its.strings[3], "primary");
//      assertEquals(its.strings[4], "prime");
//
//      System.out.println(ss.lastVisitedRow);
//
//      //the next five
//      its = ct.getPrefixedStrings(ss);
//
//      assertEquals(its.nextempty, 5);
//      System.out.println(its);
//      assertEquals(its.strings[0], "prime minister");
//      assertEquals(its.strings[1], "prime number");
//      assertEquals(its.strings[2], "primitive");
//      assertEquals(its.strings[3], "princess");
//      assertEquals(its.strings[4], "principal");
//
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.strings[0], "principle");
//      assertEquals(its.strings[1], "print");
//      assertEquals(its.strings[2], "printer");
//      assertEquals(its.strings[3], "prison");
//      assertEquals(its.strings[4], "prisoner of war");
//
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.nextempty, 4);
//      assertEquals(its.strings[0], "privacy");
//      assertEquals(its.strings[1], "private");
//      assertEquals(its.strings[2], "privilege");
//      assertEquals(its.strings[3], "prize");
//
//      ss.lastVisitedRow = null;
//      its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 5);
//      System.out.println(its);
//      assertEquals(its.strings[0], "price");
//      assertEquals(its.strings[1], "pride");
//      assertEquals(its.strings[2], "priest");
//      assertEquals(its.strings[3], "primary");
//      assertEquals(its.strings[4], "prime");
//
//      //keep the old list
//      its = ct.getPrefixedStrings(ss, its);
//      assertEquals(its.nextempty, 10);
//      System.out.println(its);
//      assertEquals(its.strings[0], "price");
//      assertEquals(its.strings[1], "pride");
//      assertEquals(its.strings[2], "priest");
//      assertEquals(its.strings[3], "primary");
//      assertEquals(its.strings[4], "prime");
//      assertEquals(its.strings[5], "prime minister");
//      assertEquals(its.strings[6], "prime number");
//      assertEquals(its.strings[7], "primitive");
//      assertEquals(its.strings[8], "princess");
//      assertEquals(its.strings[9], "principal");
//
//      ss.resetPrefix("prime");
//
//      its = ct.getPrefixedStrings(ss);
//      assertEquals(its.nextempty, 3);
//      System.out.println(its);
//      assertEquals(its.strings[0], "prime");
//      assertEquals(its.strings[1], "prime minister");
//      assertEquals(its.strings[2], "prime number");
//
//      System.out.println(ss.prefixNodes);
//
//      //now simulate that the user selected "prime minister"
//      //the application notifies the Trie and saves the node id of prime minister for all the prefix nodes above
//      ct.tickWord(ss, its.ints[1], 1);
//
//      ss.resetPrefix("pr");
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.nextempty, 5);
//      assertEquals(its.strings[0], "prime minister");
//      assertEquals(its.strings[1], "price");
//      assertEquals(its.strings[2], "pride");
//      assertEquals(its.strings[3], "priest");
//      assertEquals(its.strings[4], "primary");
//
//      ss.resetPrefix("prime");
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.nextempty, 3);
//      assertEquals(its.strings[0], "prime minister");
//      assertEquals(its.strings[1], "prime");
//      assertEquals(its.strings[2], "prime number");
//
//      //now get the results without saved data
//      ss.resetPrefix("pr", 4);
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.strings[0], "prime minister");
//      assertEquals(its.strings[1], "price");
//      assertEquals(its.strings[2], "pride");
//      assertEquals(its.strings[3], "priest");
//
//      System.out.println(ct.getStringFromNode(ss.lastVisitedRow.getLast()));
//      //since we are getting the next frame
//      // now let's get the results without relevant data in front
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.strings[0], "primary");
//      assertEquals(its.strings[1], "prime");
//      assertEquals(its.strings[2], "prime number");
//      assertEquals(its.strings[3], "primitive");
//
//      its = ct.getPrefixedStrings(ss);
//      System.out.println(its);
//      assertEquals(its.strings[0], "princess");
//      assertEquals(its.strings[1], "principal");
//      assertEquals(its.strings[2], "principle");
//      assertEquals(its.strings[3], "print");
//
//   }
//
//   public void testFrenchTriePack() {
//      CharTrie ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      ct.initAlphabet(StringUtils.getLatinChar());
//      ct.setNodeDataFlag(ITrieNodeData.FLAG_PARENT_REF, true);
//      //ct.setCharTrieFlag(CharTrie.FLAG_CHECK_RECURSIVE_CHARTRIE, true);
//   }
//
//   public void testFrenchTrieSpread() {
//      CharTrie ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_SPREAD_CONFIG, null);
//      ct.initAlphabet(StringUtils.getLatinChar());
//      ct.setNodeDataFlag(ITrieNodeData.FLAG_PARENT_REF, true);
//      //ct.setCharTrieFlag(CharTrie.FLAG_CHECK_RECURSIVE_CHARTRIE, true);
//
//      doFrenchTrie(ct);
//   }
//
//   /**
//    * Occurds when for example
//    * amandes amandines
//    * amand es and ines
//    * you add amariques and you have node ama nd es ines
//    */
//   public void testHardSplitPack() {
//
//      char[] alpha = StringUtils.getLatinChar();
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      tm.initAlphabet(alpha);
//      tm.setNodeDataFlag(ITrieNodeData.FLAG_PARENT_REF, true);
//
//      doHardSplit(tm);
//   }
//
//   public void testHardSplitSpread() {
//
//      char[] alpha = StringUtils.getLatinChar();
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_SPREAD_CONFIG, null);
//      tm.initAlphabet(alpha);
//      tm.setNodeDataFlag(ITrieNodeData.FLAG_PARENT_REF, true);
//
//      doHardSplit(tm);
//   }
//
//   public void testIsSameMatch() {
//
//      assertEquals(-1, StringUtils.isSamePrefix("bonjour", 3, "jour"));
//      assertEquals(1, StringUtils.isSamePrefix("r", 0, "rare"));
//      assertEquals(2, StringUtils.isSamePrefix("raw", 0, "rare"));
//      assertEquals(-1, StringUtils.isSamePrefix("rare", 0, "ra"));
//      assertEquals(5, StringUtils.isSamePrefix("deboutonner", 2, "bous"));
//
//   }
//
//   public void testParentage(CharTrie tm) {
//      TrieSingleDataBuildPack ts = (TrieSingleDataBuildPack) tm.nodedata;
//      for (int i = 0; i < ts.getNodesSize(); i++) {
//         int childp = ts.getChildrenPointer(i);
//         System.out.println("Node=" + i + "\t child = " + childp + "\t parent = " + ts.getParentPointer(i) + "\t" + tm.getStringFromNode(i) + "\t" + new String(tm.getCharsAtNode(i)));
//         if (childp != 0) {
//            assertEquals(i, ts.getParentPointer(childp));
//         }
//      }
//   }
//
//   public void testRunToBuild() {
//
//   }
//
//   public void testRussianInflectedTrieByLetter() {
//      //	char[] alpha = StringUtils.getCyrillicChar();
//      //	TrieUtils.makeLanguageTrie(alpha, "/russe-divers.txt", "cp1251");
//
//   }
//
//   public void testRussianTrie() {
//      char[] alpha = StringUtils.getCyrillicChar();
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      tm.initAlphabet(alpha);
//
//      assertEquals(0, tm.countWords(0));
//
//      tm.addString("привет", 11);
//      assertEquals(1, tm.countWords(0));
//
//      System.out.println(tm.debug());
//      assertEquals(11, tm.getData("привет"));
//
//      tm.addString("приветник", 12);
//      assertEquals(2, tm.countWords(0));
//
//      //insert inside the family
//      tm.addString("алло", 13);
//      assertEquals(3, tm.countWords(0));
//
//      System.out.println(tm.debug());
//
//      assertEquals(12, tm.getData("приветник"));
//
//      IntToStrings its = tm.getPrefixedStrings("приветник");
//      assertEquals(1, its.strings.length);
//      assertEquals("приветник", its.strings[0]);
//      assertEquals(12, tm.getDatas(its.ints[0])[0]);
//
//   }
//
//   public void testSecondFrenchG() {
//      CharTrie french = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_SPREAD_CONFIG, null);
//      TrieUtils.populateTrie(ui,french, "/french_test_g.txt", "UTF-8");
//
//      IntToStrings its = french.getPrefixedStrings("");
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      ICharsCollector run = french.charco.runData(null);
//
//      french.setICharsCollector(run);
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      byte[] data = french.serialize();
//
//      MemController mc = new MemController(data);
//
//      CharTrie frun = new CharTrie(ui,mc);
//
//      System.out.println(frun.debug());
//      System.out.println(frun.getPrefixedStrings(""));
//
//      verifyIntegrity(ui,frun, "/french_test_g.txt", "UTF-8");
//
//      MemController mc2 = new MemController();
//
//      french.attachRunData(mc2);
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      System.out.println(mc2);
//
//      CharTrie frenchRun = new CharTrie(ui,mc2);
//      System.out.println(frenchRun.getPrefixedStrings(""));
//      verifyIntegrity(ui,frenchRun, "/french_test_g.txt", "UTF-8");
//
//   }
//
//   public void testSimpleSubLemmeCase() {
//
//      // add amandes
//      // then add amandes
//      char[] alpha = StringUtils.getLatinChar();
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      tm.initAlphabet(alpha);
//
//      tm.addString("amandes", 1000);
//      assertEquals(1000, tm.getData("amandes"));
//      System.out.println(tm.debug());
//
//      tm.addString("amande", 999);
//      assertEquals(999, tm.getData("amande"));
//      assertEquals(1000, tm.getData("amandes"));
//
//   }
//
//   public void testSpecialGFrench() {
//
//      CharTrie french = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_SPREAD_CONFIG, null);
//
//      TrieUtils.populateTrie(ui,french, "/french_test_g.txt", "UTF-8", 100);
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      //System.out.println(french.getPrefixedStrings(""));
//
//      //test the char collector
//
//      ICharsCollector buildRun = french.charco;
//      assertEquals(true, buildRun instanceof CharCollectorBuild);
//      assertEquals(buildRun.getSize(), 978);
//
//      assertEquals(true, (((CharCollectorBuild) buildRun).isAllowDuplicates()));
//      ICharsCollector charsRun = french.charco.runData(null);
//
//      assertEquals(buildRun.getSize(), 978);
//      assertEquals(charsRun.getSize(), 467);
//
//      int size = buildRun.getSize();
//      for (int i = 0; i < size; i++) {
//         char[] cs = buildRun.getChars(i);
//         if (cs == null || cs.length == 0)
//            continue;
//         int p = charsRun.find(cs, 0, cs.length);
//         assertEquals(true, p != ICharsCollector.CHARS_NOT_FOUND);
//      }
//
//      for (int i = 1; i <= charsRun.getSize(); i++) {
//         char[] cs = charsRun.getChars(i);
//         int p = charsRun.getPointer(cs);
//         assertEquals(p == i, true);
//      }
//      assertEquals(true, charsRun instanceof CharCollectorRunLight);
//
//      french.setICharsCollector(buildRun);
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      french.setICharsCollector(charsRun);
//      verifyIntegrity(ui,french, "/french_test_g.txt", "UTF-8");
//
//      byte[] data = french.serialize();
//
//      MemController mc = new MemController(data);
//      System.out.println(mc);
//      CharTrie frun = new CharTrie(ui,mc);
//      verifyIntegrity(ui,frun, "/french_test_g.txt", "UTF-8");
//
//      //test the change of ICharCollector
//   }
//
//   public void testStaticTrie() {
//
//      assertEquals(true, StringUtils.isSame("bonjour".toCharArray(), 3, "jour".toCharArray()));
//      assertEquals(false, StringUtils.isSame("bonjour".toCharArray(), 4, "jour".toCharArray()));
//      assertEquals(false, StringUtils.isSame("rty".toCharArray(), 0, "r".toCharArray()));
//      assertEquals(false, StringUtils.isSame("r".toCharArray(), 0, "rty".toCharArray()));
//      assertEquals(true, StringUtils.isSame("r".toCharArray(), 0, "r".toCharArray()));
//
//   }
//
//   public void testT9Trie() {
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      for (int i = 50; i > 0; i--) {
//         String s = "";
//         s += i;
//         for (int j = 0; j < i; j++) {
//            tm.addString(i + "" + j, i + j);
//         }
//         System.out.println(i);
//      }
//      System.out.println(tm.debug());
//   }
//
//   public void testTrieMobileAdd() {
//      //get the alphabet
//      char[] alpha = StringUtils.getLatinChar();
//
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_PACK_CONFIG, null);
//      ((IDoubleArray) tm.nodedata.getDataStruct(0)).setFlag(IDoubleArray.FLAG_ORDERED, true);
//
//      tm.initAlphabet(alpha);
//
//      tm.addString("bon", 11);
//      System.out.println(tm.debug());
//      assertEquals(11, tm.getData("bon"));
//      tm.addString("bonnet", 12);
//      assertEquals(12, tm.getData("bonnet"));
//      //insert inside the family
//      tm.addString("bonjour", 13);
//      System.out.println(tm.debug());
//      assertEquals(13, tm.getData("bonjour"));
//
//      assertEquals(3, tm.countWords(0));
//      System.out.println(tm.debug());
//
//      TrieSingleDataBuildPack ts = (TrieSingleDataBuildPack) tm.nodedata;
//      for (int i = 0; i < ts.getNodesSize(); i++) {
//         int childp = ts.getChildrenPointer(i);
//         if (childp != 0) {
//            assertEquals(i, ts.getParentPointer(childp));
//         }
//      }
//      tm.addString("adamant", 3);
//      assertEquals(4, tm.countWords(0));
//
//      System.out.println(tm.debug());
//      assertEquals(3, tm.getStringData("adamant")[0]);
//
//      assertEquals(26, tm.nodedata.getFamilySize(0));
//
//      tm.addString("Allo", 5);
//      System.out.println(tm.debug());
//      // difference in behaviour for the get with Capital Letter
//      assertEquals(5, tm.getStringData("Allo")[0]);
//      assertEquals(0, tm.nodedata.getFamilySize(29));
//
//      tm.addString("Allo", 4);
//
//      System.out.println(tm.debug());
//
//      IntToStrings its = tm.getPrefixedStrings("Al");
//      assertEquals(1, its.strings.length);
//      assertEquals("allo", its.strings[0]);
//      //assertEquals(31, its.ints[0]);
//
//      assertEquals(4, tm.getDatas(its.ints[0])[0]);
//      assertEquals(5, tm.getDatas(its.ints[0])[1]);
//
//      //test single letter
//      tm.addString("b", 6);
//      its = tm.getPrefixedStrings("b");
//      assertEquals(4, its.strings.length);
//      assertEquals("b", its.strings[0]);
//      assertEquals("bon", its.strings[1]);
//      assertEquals("bonjour", its.strings[2]);
//      assertEquals("bonnet", its.strings[3]);
//
//      assertEquals(4, its.ints.length);
//      assertEquals(2, its.ints[0]);
//      //imple dependant
//      //	assertEquals(27, its.ints[1]);
//      //	assertEquals(28, its.ints[2]);
//      //	assertEquals(29, its.ints[3]);
//
//      assertEquals(6, tm.getDatas(its.ints[0])[0]);
//      assertEquals(11, tm.getDatas(its.ints[1])[0]);
//      assertEquals(13, tm.getDatas(its.ints[2])[0]);
//      assertEquals(12, tm.getDatas(its.ints[3])[0]);
//
//      // add a word that breaks a previous entry in 2
//      tm.addString("Altitude", 56);
//      System.out.println(tm.debug());
//      its = tm.getPrefixedStrings("alt");
//      assertEquals(1, its.strings.length);
//      assertEquals("altitude", its.strings[0]);
//      assertEquals(56, tm.getDatas(its.ints[0])[0]);
//
//      int[] in = tm.getStringData("al");
//      assertEquals(0, in.length);
//
//      //now shorter
//      its = tm.getPrefixedStrings("al");
//      assertEquals(2, its.ints.length);
//      assertEquals("allo", its.strings[0]);
//      assertEquals("altitude", its.strings[1]);
//
//      its = tm.getPrefixedStrings("all");
//      assertEquals(1, its.ints.length);
//      assertEquals("allo", its.strings[0]);
//
//      //null for bad word
//      its = tm.getPrefixedStrings("alla");
//      assertEquals(null, its);
//
//      its = tm.getPrefixedStrings("a");
//      assertEquals(3, its.ints.length);
//      assertEquals("adamant", its.strings[0]);
//      assertEquals("allo", its.strings[1]);
//      assertEquals("altitude", its.strings[2]);
//
//      //different word to the same address
//      tm.addString("Amande", 77);
//
//      its = tm.getPrefixedStrings("amand");
//      assertEquals(1, its.ints.length);
//      assertEquals("amande", its.strings[0]);
//      assertEquals(77, tm.getDatas(its.ints[0])[0]);
//
//      tm.addString("Amandes", 78);
//      System.out.println(tm.debug(1));
//      its = tm.getPrefixedStrings("amande");
//      assertEquals(2, its.ints.length);
//      assertEquals("amande", its.strings[0]);
//      assertEquals("amandes", its.strings[1]);
//
//      tm.addString("Amanites", 90);
//      System.out.println(tm.debug());
//      its = tm.getPrefixedStrings("amani");
//      assertEquals(1, its.ints.length);
//      assertEquals("amanites", its.strings[0]);
//
//      tm.addString("amant", 91);
//      System.out.println(tm.debug());
//
//      //Test Finding addresses
//      //find the address 5 for prefix of allo
//      its = tm.getPrefixedStrings("Al");
//      assertEquals(2, its.ints.length);
//      assertEquals("allo", its.strings[0]);
//      assertEquals("altitude", its.strings[1]);
//
//      // this prefix won't work
//      its = tm.getPrefixedStrings("Alloo");
//      assertEquals(null, its);
//      its = tm.getPrefixedStrings("Amani");
//      assertEquals(1, its.ints.length);
//
//      assertEquals(90, tm.getDatas(its.ints[0])[0]);
//      assertEquals("amanites", its.strings[0]);
//
//      //test exceptions
//
//      // bad character
//      tm.addString("w-see", 222);
//      tm.addString("w34", 1);
//      tm.addString("authority's", 444);
//
//      tm.addString("wait", -2);
//      int i = tm.nodedata.getNodesSize();
//      tm.addString("324", 3);
//      assertEquals(i + 1, tm.nodedata.getNodesSize());
//      System.out.println(tm.debug());
//
//   }
//
//   public void testTrieMobileMultiplePack() {
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_PACK_CONFIG, null);
//      doMultipleDataTest(tm);
//
//      assertEquals(Trie.MODE_RUNNING, tm.triemode);
//      assertEquals(Trie.TYPE_PACK, tm.trieType);
//
//      byte[] serial = tm.serialize();
//
//      assertEquals(Trie.TYPE_PACK, tm.trieType);
//
//      System.out.println(tm.getMemController().toString());
//
//      //Create a trie from Manager
//      MemController mc = new MemController(IMemAgent.MEMC_EX_POLICY_0_SINGLE, serial);
//
//      CharTrie newCharTrie = new CharTrie(ui,mc);
//
//      System.out.println(newCharTrie.getMemController().toString());
//
//      multipleVerif(newCharTrie);
//   }
//
//   //
//   //   public void testFrenchTrieByLetter() {
//   //	char[] alpha = StringUtils.getLatinChar();
//   //	TrieUtils.makeLanguageTrie(alpha, "/french_all.txt", "UTF-8");
//   //   }
//   //   
//   //   public void testRussianInflectedTrieByLetter() {
//   //	char[] alpha = StringUtils.getCyrillicChar();
//   //	TrieUtils.makeLanguageTrie(alpha, "/russe-divers.txt", "cp1251");
//   //   }
//
//   public void testTrieMobileMultipleSpread() {
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_SPREAD_CONFIG, null);
//
//      doMultipleDataTest(tm);
//
//      assertEquals(Trie.MODE_RUNNING, tm.triemode);
//
//      System.out.println(tm.getMemController().toString());
//
//      multipleVerif(tm);
//
//      //get the run data
//      byte[] serial = tm.serialize();
//
//      assertEquals(IMemAgent.MEMC_MAGIC_WORD, MUtils.intFromByteArray(serial, 0));
//      //Create a trie from Manager
//      MemController mc = new MemController(IMemAgent.MEMC_EX_POLICY_0_SINGLE, serial);
//
//      System.out.println(mc);
//
//      //memory is controlled by a single controller
//      CharTrie newCharTrie = new CharTrie(ui,mc);
//      System.out.println("================================================");
//      System.out.println(newCharTrie.debug());
//
//      System.out.println(newCharTrie.getMemController().toString());
//
//      multipleVerif(newCharTrie);
//   }
//
//   public void testTrieMobileSinglePack() {
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//      doSingle(tm);
//   }
//
//   public void testTrieMobileSingleSpread() {
//      CharTrie tm = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_SPREAD_CONFIG, null);
//      tm.setCharTrieFlag(CharTrie.FLAG_REUSE_CHARS, true);
//      doSingle(tm);
//
//      assertEquals(7, tm.charco.getSize());
//
//   }
//
//   public void testTrieSpread() {
//      CharTrie ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_SPREAD_CONFIG);
//      ct.addString("bonjour");
//      ct.addString("ballotement");
//      ct.addString("binge");
//      ct.setMode(Trie.MODE_RUNNING);
//
//      System.out.println(ct.debug());
//      IntToStrings it = ct.getPrefixedStrings("b");
//      assertNotNull(it);
//      assertEquals(it.nextempty, 3);
//      assertEquals(it.strings[0], "ballotement");
//      assertEquals(it.strings[1], "binge");
//      assertEquals(it.strings[2], "bonjour");
//
//      ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_SPREAD_CONFIG);
//      ct.addString("allo");
//
//      ct.setMode(Trie.MODE_RUNNING);
//
//      System.out.println(ct.debug());
//      it = ct.getPrefixedStrings("a");
//      assertNotNull(it);
//      assertEquals(it.nextempty, 1);
//      assertEquals(it.strings[0], "allo");
//
//      //2
//      ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_MULTIPLE_SPREAD_CONFIG);
//      ct.addString("pyz");
//      ct.addString("prince");
//
//      ct.setMode(Trie.MODE_RUNNING);
//
//      System.out.println(ct.debug());
//      it = ct.getPrefixedStrings("p");
//      assertNotNull(it);
//      assertEquals(it.nextempty, 2);
//      assertEquals(it.strings[0], "prince");
//      assertEquals(it.strings[1], "pyz");
//
//   }
//
//   /**
//    * 
//    *
//    */
//   public void testWierd() {
//
//      CharTrie ct = new CharTrie(ui,Trie.MODE_BUILDING, CharTrie.BASIC_SINGLE_PACK_CONFIG, null);
//
//      //add value with no data
//      ct.addString("manger");
//      System.out.println(ct.debug());
//
//      //add value with data. automatically create a datastruct (SINGLE Type) for that
//      ct.addString("manager", 4);
//      System.out.println(ct.debug());
//      assertEquals(4, ct.getData("manager"));
//      assertEquals(0, ct.getData("manger"));
//
//      ct.setMode(Trie.MODE_RUNNING);
//
//      System.out.println("Debug Wierd");
//      System.out.println(ct.debug());
//
//      IntToStrings its = ct.getPrefixedStrings("m");
//      assertEquals(its.nextempty, 2);
//      assertEquals(its.strings[0], "manager");
//      assertEquals(its.strings[1], "manger");
//
//      assertEquals(4, ct.getData("manager"));
//      assertEquals(0, ct.getData("manger"));
//
//   }
//}
