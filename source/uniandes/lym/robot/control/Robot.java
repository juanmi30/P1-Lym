/* Robot.java */
/* Generated By:JavaCC: Do not edit this line. Robot.java */
package uniandes.lym.robot.control;

import uniandes.lym.robot.kernel.*;
import uniandes.lym.robot.view.Console;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.io.*;
import java.util.Vector;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Robot implements RobotConstants {
        public static Map<String, Integer> variableMap = new HashMap<>();
        public static Map<String, List<String>> functionMap = new HashMap<>();
        public static Map<String, String> paramFunctionMap = new HashMap<>();
        public static String salida = new String();

        private RobotWorldDec world;


        void setWorld(RobotWorld w) {
                world = (RobotWorldDec) w;
        }

/*Variable and Procedure Definitions*/
  final public void varDefinition() throws ParseException {Token variableName;
  int variableValue;
    jj_consume_token(DEFVAR);
    variableName = jj_consume_token(WORD);
    variableValue = value();
    jj_consume_token(RPAREN);
Robot.variableMap.put(variableName.image, variableValue);
}

  final public void procedureDefinition() throws ParseException {Token tokenFunName;
  List<String> paramsList = new ArrayList<>();
    jj_consume_token(DEFUN);
    tokenFunName = jj_consume_token(WORD);
Robot.functionMap.put(tokenFunName.image, paramsList);
    jj_consume_token(LPAREN);
    params(tokenFunName.image);
    jj_consume_token(RPAREN);
    label_1:
    while (true) {
      jj_consume_token(LPAREN);
      instruction();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(RPAREN);
Robot.paramFunctionMap.clear();
}

  final public void params(String funName) throws ParseException {Token tokenParamName;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case WORD:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      tokenParamName = jj_consume_token(WORD);
Robot.functionMap.get(funName).add(tokenParamName.image);
        Robot.paramFunctionMap.put(tokenParamName.image, funName);
    }
}

/*Instructions*/
  final public void instruction() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WORD:{
      procedureCall();
      break;
      }
    case ASSIGN:
    case MOVE:
    case ROBOTSKIP:
    case TURN:
    case FACE:
    case PUT:
    case PICK:
    case MOVEDIR:
    case RUNDIRS:
    case MOVEFACE:
    case NULL:{
      parseCommand();
      break;
      }
    case IF:
    case LOOP:
    case REPEAT:{
      controlStructure();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RPAREN);
}

  final public void block() throws ParseException {
    jj_consume_token(LPAREN);
    label_3:
    while (true) {
      jj_consume_token(LPAREN);
      instruction();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
    }
    jj_consume_token(RPAREN);
}

  final public void procedureCall() throws ParseException {Token funName;
  List<String> paramTokens = new ArrayList<>();
    funName = jj_consume_token(WORD);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CONSTANT:
      case NUM:
      case WORD:{
        ;
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case NUM:{
        jj_consume_token(NUM);
        break;
        }
      case CONSTANT:{
        jj_consume_token(CONSTANT);
        break;
        }
      case WORD:{
        varCall();
paramTokens.add(token.image);
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
if (paramTokens.size() != Robot.functionMap.get(funName.image).size()) {
                {if (true) throw new ParseException("Error: los par\u00c3\u00a1metros de ingreso no coinciden con los esperados.");}
            }
}

  final public void parseCommand() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASSIGN:{
      assign();
      break;
      }
    case MOVE:{
      move();
      break;
      }
    case ROBOTSKIP:{
      skip();
      break;
      }
    case TURN:{
      turn();
      break;
      }
    case FACE:{
      face();
      break;
      }
    case PUT:{
      put();
      break;
      }
    case PICK:{
      pick();
      break;
      }
    case MOVEDIR:{
      moveDir();
      break;
      }
    case RUNDIRS:{
      runDirs();
      break;
      }
    case MOVEFACE:{
      moveFace();
      break;
      }
    case NULL:{
      nullCommand();
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/*Commands*/
  final public void assign() throws ParseException {String variableName;
  int variableValue;
    jj_consume_token(ASSIGN);
    jj_consume_token(WORD);
variableName = token.image;
    if (!Robot.variableMap.containsKey(variableName))
    {
      {if (true) throw new ParseException("Error: La variable " + variableName + " no est\u00c3\u00a1 definida.");}
    }
    variableValue = value();
Robot.variableMap.put(variableName, variableValue);
    Robot.salida = "Command: variable assignment";
}

  final public void move() throws ParseException {int numberSteps;
    jj_consume_token(MOVE);
    numberSteps = value();
if (numberSteps != -1)
    {
      world.moveForward(numberSteps, false);
      Robot.salida = "Command: move";
    }
}

  final public void skip() throws ParseException {int numberValue;
    jj_consume_token(ROBOTSKIP);
    numberValue = value();
world.moveForward(numberValue, true);
    Robot.salida = "Command: skip";
}

  final public void turn() throws ParseException {String direction;
    jj_consume_token(TURN);
    jj_consume_token(DIRECTION);
direction = token.image;
        switch(direction) {
            case ":left":
                world.turnRight();
                world.turnRight();
                world.turnRight();
                break;
            case ":right":
                world.turnRight();
                break;
            case ":around":
                world.turnRight();
                world.turnRight();
                break;
            default:
                {if (true) throw new ParseException("Error: la direcci\u00c3\u00b3n " + token.image + " no es v\u00c3\u00a1lida con el comando turn.");}
        }
        Robot.salida = "Command: turn";
}

  final public void face() throws ParseException {String orientationTokenValue;
int orientationValue = -1;
    jj_consume_token(FACE);
    jj_consume_token(ORIENTATION);
orientationTokenValue = token.image;
    if(orientationTokenValue.equals(":north")) {
      orientationValue = 0;
      } else if (orientationTokenValue.equals(":south")) {
        orientationValue = 1;
      } else if (orientationTokenValue.equals(":east")) {
        orientationValue = 2;
      } else if (orientationTokenValue.equals(":west")) {
                orientationValue = 3;
        }
     while (world.getFacing() != orientationValue) {
       world.turnRight();
       }
       Robot.salida = "Command: face";
}

  final public void put() throws ParseException {Token itemToken;
  int numberItem;
    jj_consume_token(PUT);
    itemToken = jj_consume_token(ITEM);
    numberItem = value();
if (itemToken.image.equals(":balloons")) {
      world.putBalloons(numberItem);
      } else if (itemToken.image.equals(":chips")) {
        world.putChips(numberItem);
        }
    Robot.salida = "Command: put";
}

  final public void pick() throws ParseException {Token itemToken;
  int numberItem;
    jj_consume_token(PICK);
    itemToken = jj_consume_token(ITEM);
    numberItem = value();
if (itemToken.image.equals(":balloons")) {
      world.grabBalloons(numberItem);
      } else if (itemToken.image.equals(":chips")) {
        world.pickChips(numberItem);
        }
    Robot.salida = "Command: pick";
}

  final public void moveDir() throws ParseException {Token itemToken;
  int initialOrientation;
  int numberItem;
    jj_consume_token(MOVEDIR);
    numberItem = value();
    itemToken = direction();
//guardar orientacion inicial
    initialOrientation = world.getFacing();

    if (itemToken.image.equals(":right")) {
      world.turnRight();
      world.moveForward(numberItem, false);
      } else if (itemToken.image.equals(":left")) {
        world.turnRight();
        world.turnRight();
        world.turnRight();
        world.moveForward(numberItem, false);
       } else if (itemToken.image.equals(":front")) {
         world.moveForward(numberItem, false);
         } else if (itemToken.image.equals(":back")) {
                 world.turnRight();
                 world.turnRight();
         world.moveForward(numberItem, false);
         }
     //Volver a orientacion de inicio
     while (world.getFacing() != initialOrientation) {
       world.turnRight();
       }
    Robot.salida = "Command: move-dir";
}

  final public void runDirs() throws ParseException {List<String> directionsList = new ArrayList<>();
  Token directionReturn;
  int initialOrientation;
    jj_consume_token(RUNDIRS);
    label_5:
    while (true) {
      directionReturn = direction();
directionsList.add(directionReturn.image);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DIRECTION:{
        ;
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        break label_5;
      }
    }
for (String direction : directionsList) {

      initialOrientation = world.getFacing();

     if (direction.equals(":right")) {
        world.turnRight();
        world.moveForward(1, false);
      } else if (direction.equals(":left")) {
        world.turnRight();
        world.turnRight();
        world.turnRight();
        world.moveForward(1, false);
       } else if (direction.equals(":front")) {
         world.moveForward(1, false);
         } else if (direction.equals(":back")) {
                 world.turnRight();
                 world.turnRight();
         world.moveForward(1, false);
         }
     //Volver a orientacion de inicio
     while (world.getFacing() != initialOrientation) {
       world.turnRight();
       }
      }

      Robot.salida = "Command: rund dirs";
}

  final public void moveFace() throws ParseException {Token orientationToken;
  int moveValue;
  String orientationTokenValue;
  int orientationValue = -1;
    jj_consume_token(MOVEFACE);
    moveValue = value();
    orientationToken = jj_consume_token(ORIENTATION);
orientationTokenValue = orientationToken.image;
    if(orientationTokenValue.equals(":north")) {
      orientationValue = 0;
      } else if (orientationTokenValue.equals(":south")) {
        orientationValue = 1;
      } else if (orientationTokenValue.equals(":east")) {
        orientationValue = 2;
      } else if (orientationTokenValue.equals(":west")) {
                orientationValue = 3;
        }
     while (world.getFacing() != orientationValue) {
       world.turnRight();
       }
       world.moveForward(moveValue, false);
       Robot.salida = "Command: move-face";
}

  final public void nullCommand() throws ParseException {
    jj_consume_token(NULL);
}

/*Control Structures*/
  final public void controlStructure() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IF:{
      conditional();
      break;
      }
    case LOOP:{
      repeat();
      break;
      }
    case REPEAT:{
      repeatTimes();
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void repeat() throws ParseException {
    jj_consume_token(LOOP);
    condition();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASSIGN:
    case MOVE:
    case ROBOTSKIP:
    case TURN:
    case FACE:
    case PUT:
    case PICK:
    case MOVEDIR:
    case RUNDIRS:
    case MOVEFACE:
    case NULL:{
      parseCommand();
      break;
      }
    case LPAREN:{
      block();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void repeatTimes() throws ParseException {
    jj_consume_token(REPEAT);
    value();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASSIGN:
    case MOVE:
    case ROBOTSKIP:
    case TURN:
    case FACE:
    case PUT:
    case PICK:
    case MOVEDIR:
    case RUNDIRS:
    case MOVEFACE:
    case NULL:{
      parseCommand();
      break;
      }
    case LPAREN:{
      block();
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/*Conditionals*/
  final public void conditional() throws ParseException {boolean conditionReturn;
    jj_consume_token(IF);
    conditionReturn = condition();
    block();
    block();
}

  final public boolean condition() throws ParseException {boolean valueReturn;
    jj_consume_token(LPAREN);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case FACING:{
      valueReturn = facingCondition();
      break;
      }
    case BLOCKED:{
      valueReturn = blockedCondition();
      break;
      }
    case CANPUT:{
      valueReturn = canputCondition();
      break;
      }
    case CANPICK:{
      valueReturn = canpickCondition();
      break;
      }
    case CANMOVE:{
      valueReturn = canmoveCondition();
      break;
      }
    case ISZERO:{
      valueReturn = iszeroCondition();
      break;
      }
    case NOT:{
      valueReturn = notCondition();
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RPAREN);
}

  final public boolean facingCondition() throws ParseException {Token orientationToken;
  String orientationTokenValue;
  int orientationValue = -1;
  boolean returnValue;
    jj_consume_token(FACING);
    orientationToken = jj_consume_token(ORIENTATION);
orientationTokenValue = orientationToken.image;
    if(orientationTokenValue.equals(":north")) {
      orientationValue = 0;
      } else if (orientationTokenValue.equals(":south")) {
        orientationValue = 1;
      } else if (orientationTokenValue.equals(":east")) {
        orientationValue = 2;
      } else if (orientationTokenValue.equals(":west")) {
                orientationValue = 3;
        }
     if (orientationValue == world.getFacing()) {
       returnValue = true;
        } else {
        returnValue = false;
          }
     {if ("" != null) return returnValue;}
    throw new Error("Missing return statement in function");
}

  final public void blockedCondition() throws ParseException {
    jj_consume_token(BLOCKED);
}

  final public void canputCondition() throws ParseException {
    jj_consume_token(CANPUT);
    jj_consume_token(ITEM);
    value();
}

  final public void canpickCondition() throws ParseException {
    jj_consume_token(CANPICK);
    jj_consume_token(ITEM);
    value();
}

  final public void canmoveCondition() throws ParseException {
    jj_consume_token(CANMOVE);
    jj_consume_token(ORIENTATION);
}

  final public void iszeroCondition() throws ParseException {
    jj_consume_token(ISZERO);
    value();
}

  final public void notCondition() throws ParseException {
    jj_consume_token(NOT);
    condition();
}

/* Auxiliary Functions*/
  final public int varCall() throws ParseException {int value = -1;
    jj_consume_token(WORD);
if (Robot.variableMap.containsKey(token.image))
    {
      try
      {
        value = Robot.variableMap.get(token.image);
      }
      catch (NumberFormatException ee)
      {
        {if (true) throw new Error("Hay overflow con el valor: "+value);}
      }

      {if ("" != null) return value;}
    }
    if (!Robot.variableMap.containsKey(token.image)) {
                if (!Robot.paramFunctionMap.containsKey(token.image)) {
                        {if (true) throw new ParseException("Error: La variable " + token.image + " no est\u00c3\u00a1 definida.");}
        }
    }

    {if ("" != null) return value;} //Si se retorna -1 significa que el varCall() corresponde al parámetros de una función

    throw new Error("Missing return statement in function");
}

  final public int value() throws ParseException, Error {int value = -1;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NUM:{
      jj_consume_token(NUM);
try
    {
      value = Integer.parseInt(token.image);
    }
        catch (NumberFormatException ee)
                {
                        {if (true) throw new Error("Hay overflow con el valor: "+token.image);}
                }
        {if ("" != null) return value;}
      break;
      }
    case CONSTANT:{
      jj_consume_token(CONSTANT);
String image = token.image;
        if (image.equals("dim")) { value = world.getN(); }
        else if (image.equals("myxpos")) { value = (int) world.getPosition().getX(); }
        else if (image.equals("myypos")) { value = (int) world.getPosition().getY(); }
        else if (image.equals("mychips")) { value = world.getMyChips(); }
        else if (image.equals("myballons")) { value = world.getMyBalloons(); }
        else if (image.equals("ballonshere")) { value = world.countBalloons(); }
        else if (image.equals("chipshere")) { value = world.chipsToPick(); }
        else if (image.equals("spaces")) { value = world.freeSpacesForChips(); }

        {if ("" != null) return value;}
      break;
      }
    case WORD:{
      value = varCall();
{if ("" != null) return value;}
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

//value() retorna el valor numérico para usarlo con los métodos de world
  final public 
Token direction() throws ParseException {Token directionToken;
    directionToken = jj_consume_token(DIRECTION);
if (!(token.image.equals(":front") || token.image.equals(":right") || token.image.equals(":left") || token.image.equals(":back"))) {
                {if (true) throw new ParseException("Error: la direcci\u00c3\u00b3n " + token.image + " no es v\u00c3\u00a1lida con el comando move-dir o run-dirs.");}
    }
    {if ("" != null) return directionToken;}
    throw new Error("Missing return statement in function");
}

//Initial Rule
  final public boolean command(Console sistema) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:{
      label_6:
      while (true) {
        jj_consume_token(LPAREN);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case DEFVAR:{
          varDefinition();
          break;
          }
        case DEFUN:{
          procedureDefinition();
          break;
          }
        case ASSIGN:
        case MOVE:
        case ROBOTSKIP:
        case TURN:
        case FACE:
        case PUT:
        case PICK:
        case MOVEDIR:
        case RUNDIRS:
        case MOVEFACE:
        case NULL:
        case IF:
        case LOOP:
        case REPEAT:
        case WORD:{
          instruction();
          break;
          }
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
try {
                         Thread.sleep(900);
            } catch (InterruptedException e) {
                                System.err.format("IOException: %s%n", e);
                    }

                sistema.printOutput(salida);
                {if ("" != null) return true;}
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case LPAREN:{
          ;
          break;
          }
        default:
          jj_la1[14] = jj_gen;
          break label_6;
        }
      }
      break;
      }
    case 0:{
      jj_consume_token(0);
{if ("" != null) return false;}
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  /** Generated Token Manager. */
  public RobotTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0xe1ff60,0x0,0x80100000,0x80100000,0x1ff60,0x40000,0xe00000,0x1ff60,0x1ff60,0x7f000000,0x80100000,0xe1ff60,0x0,0x1,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x1,0x10,0x10,0x1,0x10,0x10,0x0,0x0,0x0,0x1,0x1,0x0,0x10,0x1c,0x1,0x1,};
	}

  /** Constructor with InputStream. */
  public Robot(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Robot(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new RobotTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Robot(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new RobotTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new RobotTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Robot(RobotTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(RobotTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
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
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[38];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 16; i++) {
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
	 for (int i = 0; i < 38; i++) {
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

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
