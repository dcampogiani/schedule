/* Generated By:JavaCC: Do not edit this line. ScheduleParser.java */
package parser;

import parser.visitor.*;
import parser.syntaxtree.*;


public class ScheduleParser implements ScheduleParserConstants {

  static final public Scope Scope() throws ParseException {
  // --- JTB generated node declarations ---
  Declarations n0 = null;
  Body n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
    n0 = Declarations();
    n1 = Body();
    n3 = jj_consume_token(0);
    n3.beginColumn++;
    n3.endColumn++;
    { n2 = JTBToolkit.makeNodeToken(n3); }
    {if (true) return new Scope(n0, n1, n2);}
    throw new Error("Missing return statement in function");
  }

  static final public Declarations Declarations() throws ParseException {
  // --- JTB generated node declarations ---
  NodeOptional n0 = new NodeOptional();
  TimeZoneDeclaration n1 = null;
  NodeListOptional n2 = new NodeListOptional();
  VariableDeclaration n3 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 5:
      n1 = TimeZoneDeclaration();
      n0.addNode(n1);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 8:
      case 9:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      n3 = VariableDeclaration();
      n2.addNode(n3);
    }
    n2.nodes.trimToSize();
    {if (true) return new Declarations(n0, n2);}
    throw new Error("Missing return statement in function");
  }

  static final public TimeZoneDeclaration TimeZoneDeclaration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
  NodeToken n6 = null;
  Token n7 = null;
    n1 = jj_consume_token(5);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(6);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(TIMEZONE);
    n4 = JTBToolkit.makeNodeToken(n5);
    n7 = jj_consume_token(7);
    n6 = JTBToolkit.makeNodeToken(n7);
    {if (true) return new TimeZoneDeclaration(n0, n2, n4, n6);}
    throw new Error("Missing return statement in function");
  }

  static final public VariableDeclaration VariableDeclaration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeChoice n0 = null;
  PersonDeclaration n1 = null;
  LocationDeclaration n2 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 8:
      n1 = PersonDeclaration();
      n0 = new NodeChoice(n1, 0, 2);
      break;
    case 9:
      n2 = LocationDeclaration();
      n0 = new NodeChoice(n2, 1, 2);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new VariableDeclaration(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public PersonDeclaration PersonDeclaration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
  NodeToken n6 = null;
  Token n7 = null;
  NodeToken n8 = null;
  Token n9 = null;
    n1 = jj_consume_token(8);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(ID);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(6);
    n4 = JTBToolkit.makeNodeToken(n5);
    n7 = jj_consume_token(MAIL);
    n6 = JTBToolkit.makeNodeToken(n7);
    n9 = jj_consume_token(7);
    n8 = JTBToolkit.makeNodeToken(n9);
    {if (true) return new PersonDeclaration(n0, n2, n4, n6, n8);}
    throw new Error("Missing return statement in function");
  }

  static final public LocationDeclaration LocationDeclaration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
  NodeToken n6 = null;
  Token n7 = null;
  NodeToken n8 = null;
  Token n9 = null;
    n1 = jj_consume_token(9);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(ID);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(6);
    n4 = JTBToolkit.makeNodeToken(n5);
    n7 = jj_consume_token(STRING);
    n6 = JTBToolkit.makeNodeToken(n7);
    n9 = jj_consume_token(7);
    n8 = JTBToolkit.makeNodeToken(n9);
    {if (true) return new LocationDeclaration(n0, n2, n4, n6, n8);}
    throw new Error("Missing return statement in function");
  }

  static final public Body Body() throws ParseException {
  // --- JTB generated node declarations ---
  NodeListOptional n0 = new NodeListOptional();
  Day n1 = null;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 10:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      n1 = Day();
      n0.addNode(n1);
    }
    n0.nodes.trimToSize();
    {if (true) return new Body(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public Day Day() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  DayDate n2 = null;
  NodeToken n3 = null;
  Token n4 = null;
  NodeToken n5 = null;
  Token n6 = null;
  Duration n7 = null;
  Doing n8 = null;
  NodeOptional n9 = new NodeOptional();
  Partecipants n10 = null;
  NodeOptional n11 = new NodeOptional();
  Location n12 = null;
  NodeOptional n13 = new NodeOptional();
  Repeating n14 = null;
  NodeToken n15 = null;
  Token n16 = null;
    n1 = jj_consume_token(10);
    n0 = JTBToolkit.makeNodeToken(n1);
    n2 = DayDate();
    n4 = jj_consume_token(11);
    n3 = JTBToolkit.makeNodeToken(n4);
    n6 = jj_consume_token(12);
    n5 = JTBToolkit.makeNodeToken(n6);
    n7 = Duration();
    n8 = Doing();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 19:
      n10 = Partecipants();
      n9.addNode(n10);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 21:
      n12 = Location();
      n11.addNode(n12);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
      n14 = Repeating();
      n13.addNode(n14);
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    n16 = jj_consume_token(13);
    n15 = JTBToolkit.makeNodeToken(n16);
    {if (true) return new Day(n0, n2, n3, n5, n7, n8, n9, n11, n13, n15);}
    throw new Error("Missing return statement in function");
  }

  static final public DayDate DayDate() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
  NodeToken n6 = null;
  Token n7 = null;
  NodeToken n8 = null;
  Token n9 = null;
    n1 = jj_consume_token(INTEGER);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(14);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(INTEGER);
    n4 = JTBToolkit.makeNodeToken(n5);
    n7 = jj_consume_token(14);
    n6 = JTBToolkit.makeNodeToken(n7);
    n9 = jj_consume_token(INTEGER);
    n8 = JTBToolkit.makeNodeToken(n9);
    {if (true) return new DayDate(n0, n2, n4, n6, n8);}
    throw new Error("Missing return statement in function");
  }

  static final public Duration Duration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeChoice n0 = null;
  AllDayDuration n1 = null;
  FromToDuration n2 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 15:
      n1 = AllDayDuration();
      n0 = new NodeChoice(n1, 0, 2);
      break;
    case 16:
      n2 = FromToDuration();
      n0 = new NodeChoice(n2, 1, 2);
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new Duration(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public AllDayDuration AllDayDuration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
    n1 = jj_consume_token(15);
    n0 = JTBToolkit.makeNodeToken(n1);
    {if (true) return new AllDayDuration(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public FromToDuration FromToDuration() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  TimeEvent n2 = null;
  NodeToken n3 = null;
  Token n4 = null;
  TimeEvent n5 = null;
    n1 = jj_consume_token(16);
    n0 = JTBToolkit.makeNodeToken(n1);
    n2 = TimeEvent();
    n4 = jj_consume_token(17);
    n3 = JTBToolkit.makeNodeToken(n4);
    n5 = TimeEvent();
    {if (true) return new FromToDuration(n0, n2, n3, n5);}
    throw new Error("Missing return statement in function");
  }

  static final public TimeEvent TimeEvent() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
  NodeToken n6 = null;
  Token n7 = null;
  NodeToken n8 = null;
  Token n9 = null;
    n1 = jj_consume_token(DIGIT);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(DIGIT);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(11);
    n4 = JTBToolkit.makeNodeToken(n5);
    n7 = jj_consume_token(DIGIT);
    n6 = JTBToolkit.makeNodeToken(n7);
    n9 = jj_consume_token(DIGIT);
    n8 = JTBToolkit.makeNodeToken(n9);
    {if (true) return new TimeEvent(n0, n2, n4, n6, n8);}
    throw new Error("Missing return statement in function");
  }

  static final public Doing Doing() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
    n1 = jj_consume_token(18);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(11);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(STRING);
    n4 = JTBToolkit.makeNodeToken(n5);
    {if (true) return new Doing(n0, n2, n4);}
    throw new Error("Missing return statement in function");
  }

  static final public Partecipants Partecipants() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  Partecipant n4 = null;
  NodeListOptional n5 = new NodeListOptional();
  OthersPartecipants n6 = null;
    n1 = jj_consume_token(19);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(11);
    n2 = JTBToolkit.makeNodeToken(n3);
    n4 = Partecipant();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 20:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_3;
      }
      n6 = OthersPartecipants();
      n5.addNode(n6);
    }
    n5.nodes.trimToSize();
    {if (true) return new Partecipants(n0, n2, n4, n5);}
    throw new Error("Missing return statement in function");
  }

  static final public OthersPartecipants OthersPartecipants() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  Partecipant n2 = null;
    n1 = jj_consume_token(20);
    n0 = JTBToolkit.makeNodeToken(n1);
    n2 = Partecipant();
    {if (true) return new OthersPartecipants(n0, n2);}
    throw new Error("Missing return statement in function");
  }

  static final public Partecipant Partecipant() throws ParseException {
  // --- JTB generated node declarations ---
  NodeChoice n0 = null;
  NodeToken n1 = null;
  Token n2 = null;
  NodeToken n3 = null;
  Token n4 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      n2 = jj_consume_token(ID);
      n1 = JTBToolkit.makeNodeToken(n2);
      n0 = new NodeChoice(n1, 0, 2);
      break;
    case MAIL:
      n4 = jj_consume_token(MAIL);
      n3 = JTBToolkit.makeNodeToken(n4);
      n0 = new NodeChoice(n3, 1, 2);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new Partecipant(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public Location Location() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  Place n4 = null;
    n1 = jj_consume_token(21);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(11);
    n2 = JTBToolkit.makeNodeToken(n3);
    n4 = Place();
    {if (true) return new Location(n0, n2, n4);}
    throw new Error("Missing return statement in function");
  }

  static final public Place Place() throws ParseException {
  // --- JTB generated node declarations ---
  NodeChoice n0 = null;
  NodeToken n1 = null;
  Token n2 = null;
  NodeToken n3 = null;
  Token n4 = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      n2 = jj_consume_token(ID);
      n1 = JTBToolkit.makeNodeToken(n2);
      n0 = new NodeChoice(n1, 0, 2);
      break;
    case STRING:
      n4 = jj_consume_token(STRING);
      n3 = JTBToolkit.makeNodeToken(n4);
      n0 = new NodeChoice(n3, 1, 2);
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new Place(n0);}
    throw new Error("Missing return statement in function");
  }

  static final public Repeating Repeating() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  RepeatingTime n4 = null;
  RepeatingStop n5 = null;
    n1 = jj_consume_token(22);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(11);
    n2 = JTBToolkit.makeNodeToken(n3);
    n4 = RepeatingTime();
    n5 = RepeatingStop();
    {if (true) return new Repeating(n0, n2, n4, n5);}
    throw new Error("Missing return statement in function");
  }

  static final public RepeatingTime RepeatingTime() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  NodeToken n2 = null;
  Token n3 = null;
  NodeToken n4 = null;
  Token n5 = null;
    n1 = jj_consume_token(23);
    n0 = JTBToolkit.makeNodeToken(n1);
    n3 = jj_consume_token(INTEGER);
    n2 = JTBToolkit.makeNodeToken(n3);
    n5 = jj_consume_token(24);
    n4 = JTBToolkit.makeNodeToken(n5);
    {if (true) return new RepeatingTime(n0, n2, n4);}
    throw new Error("Missing return statement in function");
  }

  static final public RepeatingStop RepeatingStop() throws ParseException {
  // --- JTB generated node declarations ---
  NodeToken n0 = null;
  Token n1 = null;
  DayDate n2 = null;
    n1 = jj_consume_token(25);
    n0 = JTBToolkit.makeNodeToken(n1);
    n2 = DayDate();
    {if (true) return new RepeatingStop(n0, n2);}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ScheduleParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x20,0x300,0x300,0x400,0x80000,0x200000,0x400000,0x18000,0x100000,0x20000000,0xa0000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x2,0x0,};
   }

  /** Constructor with InputStream. */
  public ScheduleParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ScheduleParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ScheduleParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ScheduleParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ScheduleParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ScheduleParser(ScheduleParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ScheduleParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[34];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 34; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

                             }

class JTBToolkit {

  static NodeToken makeNodeToken(final Token t) {
    return new NodeToken(t.image.intern(), t.kind, t.beginLine, t.beginColumn, t.endLine, t.endColumn);
  }
}
