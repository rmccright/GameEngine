package gameEngine.Core;

import gameEngine.Utilities.Vector2f;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input 
{
   
        public static final int NUM_KEYCODES = 256;
        public static final int NUM_MOUSEBUTTONS = 5;
        static Map<Integer, String> charMap = new HashMap<>();
        static Map<Integer, String> charMapUpper = new HashMap<>();
        //All these constants come from LWJGL's Keyboard class
        public static final int KEY_NONE            = 0x00;
        public static final int KEY_ESCAPE          = 0x01;
        public static final int KEY_1               = 0x02;
        public static final int KEY_2               = 0x03;
        public static final int KEY_3               = 0x04;
        public static final int KEY_4               = 0x05;
        public static final int KEY_5               = 0x06;
        public static final int KEY_6               = 0x07;
        public static final int KEY_7               = 0x08;
        public static final int KEY_8               = 0x09;
        public static final int KEY_9               = 0x0A;
        public static final int KEY_0               = 0x0B;
        public static final int KEY_MINUS           = 0x0C; /* - on main keyboard */
        public static final int KEY_EQUALS          = 0x0D;
        public static final int KEY_BACK            = 0x0E; /* backspace */
        public static final int KEY_TAB             = 0x0F;
        public static final int KEY_Q               = 0x10;
        public static final int KEY_W               = 0x11;
        public static final int KEY_E               = 0x12;
        public static final int KEY_R               = 0x13;
        public static final int KEY_T               = 0x14;
        public static final int KEY_Y               = 0x15;
        public static final int KEY_U               = 0x16;
        public static final int KEY_I               = 0x17;
        public static final int KEY_O               = 0x18;
        public static final int KEY_P               = 0x19;
        public static final int KEY_LBRACKET        = 0x1A;
        public static final int KEY_RBRACKET        = 0x1B;
        public static final int KEY_RETURN          = 0x1C; /* Enter on main keyboard */
        public static final int KEY_LCONTROL        = 0x1D;
        public static final int KEY_A               = 0x1E;
        public static final int KEY_S               = 0x1F;
        public static final int KEY_D               = 0x20;
        public static final int KEY_F               = 0x21;
        public static final int KEY_G               = 0x22;
        public static final int KEY_H               = 0x23;
        public static final int KEY_J               = 0x24;
        public static final int KEY_K               = 0x25;
        public static final int KEY_L               = 0x26;
        public static final int KEY_SEMICOLON       = 0x27;
        public static final int KEY_APOSTROPHE      = 0x28;
        public static final int KEY_GRAVE           = 0x29; /* accent grave */
        public static final int KEY_LSHIFT          = 0x2A;
        public static final int KEY_BACKSLASH       = 0x2B;
        public static final int KEY_Z               = 0x2C;
        public static final int KEY_X               = 0x2D;
        public static final int KEY_C               = 0x2E;
        public static final int KEY_V               = 0x2F;
        public static final int KEY_B               = 0x30;
        public static final int KEY_N               = 0x31;
        public static final int KEY_M               = 0x32;
        public static final int KEY_COMMA           = 0x33;
        public static final int KEY_PERIOD          = 0x34; /* . on main keyboard */
        public static final int KEY_SLASH           = 0x35; /* / on main keyboard */
        public static final int KEY_RSHIFT          = 0x36;
        public static final int KEY_MULTIPLY        = 0x37; /* * on numeric keypad */
        public static final int KEY_LMENU           = 0x38; /* left Alt */
        public static final int KEY_LALT            = KEY_LMENU; /* left Alt */
        public static final int KEY_SPACE           = 0x39;
        public static final int KEY_CAPITAL         = 0x3A;
        public static final int KEY_F1              = 0x3B;
        public static final int KEY_F2              = 0x3C;
        public static final int KEY_F3              = 0x3D;
        public static final int KEY_F4              = 0x3E;
        public static final int KEY_F5              = 0x3F;
        public static final int KEY_F6              = 0x40;
        public static final int KEY_F7              = 0x41;
        public static final int KEY_F8              = 0x42;
        public static final int KEY_F9              = 0x43;
        public static final int KEY_F10             = 0x44;
        public static final int KEY_NUMLOCK         = 0x45;
        public static final int KEY_SCROLL          = 0x46; /* Scroll Lock */
        public static final int KEY_NUMPAD7         = 0x47;
        public static final int KEY_NUMPAD8         = 0x48;
        public static final int KEY_NUMPAD9         = 0x49;
        public static final int KEY_SUBTRACT        = 0x4A; /* - on numeric keypad */
        public static final int KEY_NUMPAD4         = 0x4B;
        public static final int KEY_NUMPAD5         = 0x4C;
        public static final int KEY_NUMPAD6         = 0x4D;
        public static final int KEY_ADD             = 0x4E; /* + on numeric keypad */
        public static final int KEY_NUMPAD1         = 0x4F;
        public static final int KEY_NUMPAD2         = 0x50;
        public static final int KEY_NUMPAD3         = 0x51;
        public static final int KEY_NUMPAD0         = 0x52;
        public static final int KEY_DECIMAL         = 0x53; /* . on numeric keypad */
        public static final int KEY_F11             = 0x57;
        public static final int KEY_F12             = 0x58;
        public static final int KEY_F13             = 0x64; /*                     (NEC PC98) */
        public static final int KEY_F14             = 0x65; /*                     (NEC PC98) */
        public static final int KEY_F15             = 0x66; /*                     (NEC PC98) */
        public static final int KEY_KANA            = 0x70; /* (Japanese keyboard)            */
        public static final int KEY_CONVERT         = 0x79; /* (Japanese keyboard)            */
        public static final int KEY_NOCONVERT       = 0x7B; /* (Japanese keyboard)            */
        public static final int KEY_YEN             = 0x7D; /* (Japanese keyboard)            */
        public static final int KEY_NUMPADEQUALS    = 0x8D; /* = on numeric keypad (NEC PC98) */
        public static final int KEY_CIRCUMFLEX      = 0x90; /* (Japanese keyboard)            */
        public static final int KEY_AT              = 0x91; /*                     (NEC PC98) */
        public static final int KEY_COLON           = 0x92; /*                     (NEC PC98) */
        public static final int KEY_UNDERLINE       = 0x93; /*                     (NEC PC98) */
        public static final int KEY_KANJI           = 0x94; /* (Japanese keyboard)            */
        public static final int KEY_STOP            = 0x95; /*                     (NEC PC98) */
        public static final int KEY_AX              = 0x96; /*                     (Japan AX) */
        public static final int KEY_UNLABELED       = 0x97; /*                        (J3100) */
        public static final int KEY_NUMPADENTER     = 0x9C; /* Enter on numeric keypad */
        public static final int KEY_RCONTROL        = 0x9D;
        public static final int KEY_NUMPADCOMMA     = 0xB3; /* , on numeric keypad (NEC PC98) */
        public static final int KEY_DIVIDE          = 0xB5; /* / on numeric keypad */
        public static final int KEY_SYSRQ           = 0xB7;
        public static final int KEY_RMENU           = 0xB8; /* right Alt */
        public static final int KEY_RALT            = KEY_RMENU; /* right Alt */
        public static final int KEY_PAUSE           = 0xC5; /* Pause */
        public static final int KEY_HOME            = 0xC7; /* Home on arrow keypad */
        public static final int KEY_UP              = 0xC8; /* UpArrow on arrow keypad */
        public static final int KEY_PRIOR           = 0xC9; /* PgUp on arrow keypad */
        public static final int KEY_LEFT            = 0xCB; /* LeftArrow on arrow keypad */
        public static final int KEY_RIGHT           = 0xCD; /* RightArrow on arrow keypad */
        public static final int KEY_END             = 0xCF; /* End on arrow keypad */
        public static final int KEY_DOWN            = 0xD0; /* DownArrow on arrow keypad */
        public static final int KEY_NEXT            = 0xD1; /* PgDn on arrow keypad */
        public static final int KEY_INSERT          = 0xD2; /* Insert on arrow keypad */
        public static final int KEY_DELETE          = 0xD3; /* Delete on arrow keypad */
        public static final int KEY_LMETA           = 0xDB; /* Left Windows/Option key */
        public static final int KEY_LWIN            = KEY_LMETA; /* Left Windows key */
        public static final int KEY_RMETA           = 0xDC; /* Right Windows/Option key */
        public static final int KEY_RWIN            = KEY_RMETA; /* Right Windows key */
        public static final int KEY_APPS            = 0xDD; /* AppMenu key */
        public static final int KEY_POWER           = 0xDE;
        public static final int KEY_SLEEP           = 0xDF;
        
        private static boolean[] lastKeys = new boolean[NUM_KEYCODES];
        private static boolean[] lastMouse = new boolean[NUM_MOUSEBUTTONS];
        private static boolean[] newKeys = new boolean[NUM_KEYCODES];
        private static boolean[] newMouse = new boolean[NUM_MOUSEBUTTONS];
        private static boolean[] keysPressed = new boolean[NUM_KEYCODES];
        private static boolean[] mousePressed = new boolean[NUM_MOUSEBUTTONS];
        private static boolean[] keysHeld = new boolean[NUM_KEYCODES];
        private static boolean[] mouseHeld = new boolean[NUM_MOUSEBUTTONS];
        private static int[] timeHeldKeys = new int[NUM_KEYCODES];
       
        public static void update(){
            populateNewKeys();
            populateNewMouse();
            
             
            determineKeyStatus();
            
            populateLastKeys();
            populateLastMouse();
        }
        ///need to know if an on was turrned off and prevent more keys to be pressed optionally, what if i didnt know all the ways id want to do it.. then what? To PROVIDE! Information to be exact kaka;p
     
        public static void determineKeyStatus(){
            for(int a = 0; a < NUM_KEYCODES; a++){
                //keys
             
                if(lastKeys[a] == true && newKeys[a] == false){
                    keysPressed[a] = true;
                    
                }else keysPressed[a] = false;
                
                if(lastKeys[a] == true && newKeys[a] == true){
                    keysHeld[a] = true;
                    timeHeldKeys[a] ++;
                }else {keysHeld[a] = false; timeHeldKeys[a] = 0; }               
            }  
            for(int a = 0; a < NUM_MOUSEBUTTONS; a++){
             //mouse
                 if(lastMouse[a] == true && newMouse[a] == false){
                    mousePressed[a] = true;
                    
                }else mousePressed[a] = false;
                
                if(lastMouse[a] == true && newMouse[a] == true){
                    mouseHeld[a] = true;
                }else mouseHeld[a] = false;   
        }}
        
        public static boolean getKeyPressed(int keyCode)
        { 
                return keysPressed[keyCode];
        }
        
        public static boolean getMousePressed(int keyCode)
        { 
                return mousePressed[keyCode];
        }
        
        public static boolean getKeyHeld(int keyCode)
        { 
                return keysHeld[keyCode];
        }
        
        public static int timeHeld(int keyCode){
            return timeHeldKeys[keyCode];
        }
        
        public static boolean getMouseHeld(int keyCode)
        { 
                return mouseHeld[keyCode];
        }
        
        public static void populateNewKeys(){
            for(int i = 0; i < NUM_KEYCODES; i++)
                newKeys[i] = getKey(i);
       }
        
        public static void populateNewMouse(){
            for(int i = 0; i < NUM_MOUSEBUTTONS; i++)
               newMouse[i] = getMouse(i);
        }
        
        public static void populateLastKeys(){
            System.arraycopy(newKeys, 0, lastKeys, 0, lastKeys.length);
        }
        
        public static void populateLastMouse(){
            System.arraycopy(newMouse, 0, lastMouse, 0, lastMouse.length);
        }
        
        public static boolean getKey(int keyCode)
        { 
                return Keyboard.isKeyDown(keyCode);
        }
        
        public static boolean getKeyDown(int keyCode)
        {
                return getKey(keyCode) && !lastKeys[keyCode];
        }
        
        public static boolean getKeyUp(int keyCode)
        {
                return !getKey(keyCode) && lastKeys[keyCode];
        }
        
        public static boolean getMouse(int mouseButton)
        {
                return Mouse.isButtonDown(mouseButton);
        }
        
        public static boolean getMouseDown(int mouseButton)
        {
                return getMouse(mouseButton) && !lastMouse[mouseButton];
        }
        
        public static boolean getMouseUp(int mouseButton)
        {
                return !getMouse(mouseButton) && lastMouse[mouseButton];
        }
        
        public static Vector2f getMousePosition()
        {
                return new Vector2f(Mouse.getX(), Mouse.getY());
        }
        
        public static void setMousePosition(Vector2f pos)
        {
                Mouse.setCursorPosition((int)pos.GetX(), (int)pos.GetY());
        }
        
        public static void setCursor(boolean enabled)
        {
                Mouse.setGrabbed(!enabled);
        }
        
        static String theString = "";
        public static String getKeys(){
            
            
            for(int i = 0; i < NUM_KEYCODES; i++){
                if(Keyboard.isKeyDown(i) == true && (!getKeyHeld(i)| timeHeld(i) > 5)){
                    
                    if(i == Keyboard.KEY_BACK){
                         if(theString.length() >0)
                            theString = theString.substring(0, theString.length()-1);
                   
                    }else{
                        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) | Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) | Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
                            theString += charMapUpper.get(i);
                        else theString += charMap.get(i);
                    }
                    
                       
            }  }
            
            return theString;
        }
        
        public static void loadHash(){
            charMap.put(1,"");
            charMap.put(2,"1");
            charMap.put(3,"2");
            charMap.put(4,"3");
            charMap.put(5,"4");
            charMap.put(6,"5");
            charMap.put(7,"6");
            charMap.put(8,"7");
            charMap.put(9,"8");
            charMap.put(10,"9");
            charMap.put(11,"0");
            charMap.put(12,"-");
            charMap.put(13,"=");
            charMap.put(14,"");
            charMap.put(15,"");
            charMap.put(16,"q");
            charMap.put(17,"w");
            charMap.put(18,"e");
            charMap.put(19,"r");
            charMap.put(20,"t"); 
            charMap.put(21,"y");
            charMap.put(22,"u");
            charMap.put(23,"i");
            charMap.put(24,"o");
            charMap.put(25,"p");
            charMap.put(26,"[");
            charMap.put(27,"]");
            charMap.put(28,"");
            charMap.put(29,"");
            charMap.put(30,"a");
            charMap.put(31,"s");
            charMap.put(32,"d");
            charMap.put(33,"f");
            charMap.put(34,"g");
            charMap.put(35,"h");
            charMap.put(36,"j");
            charMap.put(37,"k");
            charMap.put(38,"l");
            charMap.put(39,";");
            charMap.put(40,"\'");
            charMap.put(41,"`");
            charMap.put(42,""); //leftshift
            charMap.put(43,"\\");
            charMap.put(44,"z");
            charMap.put(45,"x");
            charMap.put(46,"c");
            charMap.put(47,"v");
            charMap.put(48,"b");
            charMap.put(49,"n");
            charMap.put(50,"m");
            charMap.put(51,",");
            charMap.put(52,".");
            charMap.put(53,"/");
            charMap.put(54,"");
            charMap.put(55,"*");
            charMap.put(56,"");
            charMap.put(57," ");
            charMap.put(58,"");
            charMap.put(59,"");
            charMap.put(60,""); 
            charMap.put(61,"");
            charMap.put(62,"");
            charMap.put(63,"");
            charMap.put(64,"");
            charMap.put(65,"");
            charMap.put(66,"");
            charMap.put(67,"");
            charMap.put(68,"");
            charMap.put(69,"");
            charMap.put(70,"");
            charMap.put(71,"7");
            charMap.put(72,"8");
            charMap.put(73,"9");
            charMap.put(74,"-");
            charMap.put(75,"4");
            charMap.put(76,"5");
            charMap.put(77,"6");
            charMap.put(78,"+");
            charMap.put(79,"1");
            charMap.put(80,"2");
            charMap.put(81,"3");
            charMap.put(82,"0");
            charMap.put(83,".");
            charMap.put(84,"");
            charMap.put(85,"");
            charMap.put(86,"");
            charMap.put(87,"");
            charMap.put(88,"");
            charMap.put(89,"");
            charMap.put(90,"");
            charMap.put(91,"");
            charMap.put(92,"");
            charMap.put(93,"");
            charMap.put(94,"");
            charMap.put(95,"");
            charMap.put(96,"");
            charMap.put(97,"");
            charMap.put(98,"");
            charMap.put(99,"");
            charMap.put(100,""); 
            charMap.put(101,"");
            charMap.put(102,"");
            charMap.put(103,"");
            charMap.put(104,"");
            charMap.put(105,"");
            charMap.put(106,"");
            charMap.put(107,"");
            charMap.put(108,"");
            charMap.put(109,"");
            charMap.put(110,"");
            charMap.put(111,"");
            charMap.put(112,"");
            charMap.put(113,"");
            charMap.put(114,"");
            charMap.put(115,"");
            charMap.put(116,"");
            charMap.put(117,"");
            charMap.put(118,"");
            charMap.put(119,"");
            charMap.put(120,"");  
            charMap.put(121,"");
            charMap.put(122,"");
            charMap.put(123,"");
            charMap.put(124,"");
            charMap.put(125,"");
            charMap.put(126,"");
            charMap.put(127,"");
            charMap.put(128,"");
            charMap.put(129,"");
            charMap.put(130,"");
            charMap.put(131,"");
            charMap.put(132,"");
            charMap.put(133,"");
            charMap.put(134,"");
            charMap.put(135,"");
            charMap.put(136,"");
            charMap.put(137,"");
            charMap.put(138,"");
            charMap.put(139,"");
            charMap.put(140,""); 
            charMap.put(141,"");
            charMap.put(142,"");
            charMap.put(143,"");
            charMap.put(144,"");
            charMap.put(145,"@");
            charMap.put(146,":");
            charMap.put(147,"_");
            charMap.put(148,"");
            charMap.put(149,"");
            charMap.put(150,"");
            charMap.put(151,"");
            charMap.put(152,"");
            charMap.put(153,"");
            charMap.put(154,"");
            charMap.put(155,"");
            charMap.put(156,"");
            charMap.put(157,"");
            charMap.put(158,"");
            charMap.put(159,"");
            charMap.put(160,"");
            charMap.put(161,"");
            charMap.put(162,"");
            charMap.put(163,"");
            charMap.put(164,"");
            charMap.put(165,"");
            charMap.put(166,"");
            charMap.put(167,"");
            charMap.put(168,"");
            charMap.put(169,"");
            charMap.put(170,"");
            charMap.put(171,"");
            charMap.put(172,"");
            charMap.put(173,"");
            charMap.put(174,"");
            charMap.put(175,"");
            charMap.put(176,"");
            charMap.put(177,"");
            charMap.put(178,"");
            charMap.put(179,"");
            charMap.put(180,"");
            charMap.put(181,"/"); 
            charMap.put(182,"");
            charMap.put(183,"");
            charMap.put(184,"");
            charMap.put(185,"");
            charMap.put(186,"");
            charMap.put(187,"");
            charMap.put(188,"");
            charMap.put(189,"");
            charMap.put(190,"");
            charMap.put(191,"");
            charMap.put(192,"");
            charMap.put(193,"");
            charMap.put(194,"");
            charMap.put(195,"");
            charMap.put(196,"");
            charMap.put(197,"");
            charMap.put(198,"");
            charMap.put(199,"");
            charMap.put(200,"");
            charMap.put(201,"");
            charMap.put(202,"");
            charMap.put(203,"");
            charMap.put(204,"");
            charMap.put(205,"");
            charMap.put(206,"");
            charMap.put(207,"");
            charMap.put(208,"");
            charMap.put(209,"");
            charMap.put(210,"");
            charMap.put(211,"");
            charMap.put(212,"");
            charMap.put(213,"");
            charMap.put(214,"");
            charMap.put(215,"");
            charMap.put(216,"");
            charMap.put(217,"");
            charMap.put(218,"");
            charMap.put(219,"");
            charMap.put(220,"");
            charMap.put(221,"");
            charMap.put(222,"");
            charMap.put(223,""); 
            charMap.put(224,"");
            charMap.put(225,"");
            charMap.put(226,"");
            charMap.put(227,"");
            charMap.put(228,"");
            charMap.put(229,"");
            charMap.put(230,"");
            charMap.put(231,"");
            charMap.put(232,"");
            charMap.put(233,"");
            charMap.put(234,"");
            charMap.put(235,"");
            charMap.put(236,"");
            charMap.put(237,"");
            charMap.put(238,"");
            charMap.put(239,"");
            charMap.put(240,"");
            charMap.put(241,"");
            charMap.put(242,"");
            charMap.put(243,"");
            charMap.put(244,"");
            charMap.put(245,"");
            charMap.put(246,"");
            charMap.put(247,"");
            charMap.put(248,"");
            charMap.put(249,"");
            charMap.put(250,"");
            charMap.put(251,"");
            charMap.put(252,"");
            charMap.put(253,"");
            charMap.put(254,"");
            charMap.put(255,"");
            charMap.put(256,"");
           
            
            
            charMapUpper.put(1,"");
            charMapUpper.put(2,"!");
            charMapUpper.put(3,"@");
            charMapUpper.put(4,"#");
            charMapUpper.put(5,"$");
            charMapUpper.put(6,"%");
            charMapUpper.put(7,"^");
            charMapUpper.put(8,"&");
            charMapUpper.put(9,"*");
            charMapUpper.put(10,"(");
            charMapUpper.put(11,")");
            charMapUpper.put(12,"_");
            charMapUpper.put(13,"+");
            charMapUpper.put(14,"");
            charMapUpper.put(15,"");
            charMapUpper.put(16,"Q");
            charMapUpper.put(17,"W");
            charMapUpper.put(18,"E");
            charMapUpper.put(19,"R");
            charMapUpper.put(20,"T"); 
            charMapUpper.put(21,"Y");
            charMapUpper.put(22,"U");
            charMapUpper.put(23,"I");
            charMapUpper.put(24,"O");
            charMapUpper.put(25,"P");
            charMapUpper.put(26,"{");
            charMapUpper.put(27,"}");
            charMapUpper.put(28,"");
            charMapUpper.put(29,"");
            charMapUpper.put(30,"A");
            charMapUpper.put(31,"S");
            charMapUpper.put(32,"D");
            charMapUpper.put(33,"F");
            charMapUpper.put(34,"G");
            charMapUpper.put(35,"H");
            charMapUpper.put(36,"J");
            charMapUpper.put(37,"K");
            charMapUpper.put(38,"L");
            charMapUpper.put(39,":");
            charMapUpper.put(40,"\"");
            charMapUpper.put(41,"~");
            charMapUpper.put(42,""); //leftshift
            charMapUpper.put(43,"|");
            charMapUpper.put(44,"Z");
            charMapUpper.put(45,"X");
            charMapUpper.put(46,"C");
            charMapUpper.put(47,"V");
            charMapUpper.put(48,"B");
            charMapUpper.put(49,"N");
            charMapUpper.put(50,"M");
            charMapUpper.put(51,"<");
            charMapUpper.put(52,">");
            charMapUpper.put(53,"?");
            charMapUpper.put(54,"");
            charMapUpper.put(55,"*");
            charMapUpper.put(56,"");
            charMapUpper.put(57," ");
            charMapUpper.put(58,"");
            charMapUpper.put(59,"");
            charMapUpper.put(60,""); 
            charMapUpper.put(61,"");
            charMapUpper.put(62,"");
            charMapUpper.put(63,"");
            charMapUpper.put(64,"");
            charMapUpper.put(65,"");
            charMapUpper.put(66,"");
            charMapUpper.put(67,"");
            charMapUpper.put(68,"");
            charMapUpper.put(69,"");
            charMapUpper.put(70,"");
            charMapUpper.put(71,"7");
            charMapUpper.put(72,"8");
            charMapUpper.put(73,"9");
            charMapUpper.put(74,"-");
            charMapUpper.put(75,"4");
            charMapUpper.put(76,"5");
            charMapUpper.put(77,"6");
            charMapUpper.put(78,"+");
            charMapUpper.put(79,"1");
            charMapUpper.put(80,"2");
            charMapUpper.put(81,"3");
            charMapUpper.put(82,"0");
            charMapUpper.put(83,".");
            charMapUpper.put(84,"");
            charMapUpper.put(85,"");
            charMapUpper.put(86,"");
            charMapUpper.put(87,"");
            charMapUpper.put(88,"");
            charMapUpper.put(89,"");
            charMapUpper.put(90,"");
            charMapUpper.put(91,"");
            charMapUpper.put(92,"");
            charMapUpper.put(93,"");
            charMapUpper.put(94,"");
            charMapUpper.put(95,"");
            charMapUpper.put(96,"");
            charMapUpper.put(97,"");
            charMapUpper.put(98,"");
            charMapUpper.put(99,"");
            charMapUpper.put(100,""); 
            charMapUpper.put(101,"");
            charMapUpper.put(102,"");
            charMapUpper.put(103,"");
            charMapUpper.put(104,"");
            charMapUpper.put(105,"");
            charMapUpper.put(106,"");
            charMapUpper.put(107,"");
            charMapUpper.put(108,"");
            charMapUpper.put(109,"");
            charMapUpper.put(110,"");
            charMapUpper.put(111,"");
            charMapUpper.put(112,"");
            charMapUpper.put(113,"");
            charMapUpper.put(114,"");
            charMapUpper.put(115,"");
            charMapUpper.put(116,"");
            charMapUpper.put(117,"");
            charMapUpper.put(118,"");
            charMapUpper.put(119,"");
            charMapUpper.put(120,"");  
            charMapUpper.put(121,"");
            charMapUpper.put(122,"");
            charMapUpper.put(123,"");
            charMapUpper.put(124,"");
            charMapUpper.put(125,"");
            charMapUpper.put(126,"");
            charMapUpper.put(127,"");
            charMapUpper.put(128,"");
            charMapUpper.put(129,"");
            charMapUpper.put(130,"");
            charMapUpper.put(131,"");
            charMapUpper.put(132,"");
            charMapUpper.put(133,"");
            charMapUpper.put(134,"");
            charMapUpper.put(135,"");
            charMapUpper.put(136,"");
            charMapUpper.put(137,"");
            charMapUpper.put(138,"");
            charMapUpper.put(139,"");
            charMapUpper.put(140,""); 
            charMapUpper.put(141,"");
            charMapUpper.put(142,"");
            charMapUpper.put(143,"");
            charMapUpper.put(144,"");
            charMapUpper.put(145,"@");
            charMapUpper.put(146,":");
            charMapUpper.put(147,"_");
            charMapUpper.put(148,"");
            charMapUpper.put(149,"");
            charMapUpper.put(150,"");
            charMapUpper.put(151,"");
            charMapUpper.put(152,"");
            charMapUpper.put(153,"");
            charMapUpper.put(154,"");
            charMapUpper.put(155,"");
            charMapUpper.put(156,"");
            charMapUpper.put(157,"");
            charMapUpper.put(158,"");
            charMapUpper.put(159,"");
            charMapUpper.put(160,"");
            charMapUpper.put(161,"");
            charMapUpper.put(162,"");
            charMapUpper.put(163,"");
            charMapUpper.put(164,"");
            charMapUpper.put(165,"");
            charMapUpper.put(166,"");
            charMapUpper.put(167,"");
            charMapUpper.put(168,"");
            charMapUpper.put(169,"");
            charMapUpper.put(170,"");
            charMapUpper.put(171,"");
            charMapUpper.put(172,"");
            charMapUpper.put(173,"");
            charMapUpper.put(174,"");
            charMapUpper.put(175,"");
            charMapUpper.put(176,"");
            charMapUpper.put(177,"");
            charMapUpper.put(178,"");
            charMapUpper.put(179,"");
            charMapUpper.put(180,"");
            charMapUpper.put(181,"/"); 
            charMapUpper.put(182,"");
            charMapUpper.put(183,"");
            charMapUpper.put(184,"");
            charMapUpper.put(185,"");
            charMapUpper.put(186,"");
            charMapUpper.put(187,"");
            charMapUpper.put(188,"");
            charMapUpper.put(189,"");
            charMapUpper.put(190,"");
            charMapUpper.put(191,"");
            charMapUpper.put(192,"");
            charMapUpper.put(193,"");
            charMapUpper.put(194,"");
            charMapUpper.put(195,"");
            charMapUpper.put(196,"");
            charMapUpper.put(197,"");
            charMapUpper.put(198,"");
            charMapUpper.put(199,"");
            charMapUpper.put(200,"");
            charMapUpper.put(201,"");
            charMapUpper.put(202,"");
            charMapUpper.put(203,"");
            charMapUpper.put(204,"");
            charMapUpper.put(205,"");
            charMapUpper.put(206,"");
            charMapUpper.put(207,"");
            charMapUpper.put(208,"");
            charMapUpper.put(209,"");
            charMapUpper.put(210,"");
            charMapUpper.put(211,"");
            charMapUpper.put(212,"");
            charMapUpper.put(213,"");
            charMapUpper.put(214,"");
            charMapUpper.put(215,"");
            charMapUpper.put(216,"");
            charMapUpper.put(217,"");
            charMapUpper.put(218,"");
            charMapUpper.put(219,"");
            charMapUpper.put(220,"");
            charMapUpper.put(221,"");
            charMapUpper.put(222,"");
            charMapUpper.put(223,""); 
            charMapUpper.put(224,"");
            charMapUpper.put(225,"");
            charMapUpper.put(226,"");
            charMapUpper.put(227,"");
            charMapUpper.put(228,"");
            charMapUpper.put(229,"");
            charMapUpper.put(230,"");
            charMapUpper.put(231,"");
            charMapUpper.put(232,"");
            charMapUpper.put(233,"");
            charMapUpper.put(234,"");
            charMapUpper.put(235,"");
            charMapUpper.put(236,"");
            charMapUpper.put(237,"");
            charMapUpper.put(238,"");
            charMapUpper.put(239,"");
            charMapUpper.put(240,"");
            charMapUpper.put(241,"");
            charMapUpper.put(242,"");
            charMapUpper.put(243,"");
            charMapUpper.put(244,"");
            charMapUpper.put(245,"");
            charMapUpper.put(246,"");
            charMapUpper.put(247,"");
            charMapUpper.put(248,"");
            charMapUpper.put(249,"");
            charMapUpper.put(250,"");
            charMapUpper.put(251,"");
            charMapUpper.put(252,"");
            charMapUpper.put(253,"");
            charMapUpper.put(254,"");
            charMapUpper.put(255,"");
            charMapUpper.put(256,"");
        }
        
      
        
}
