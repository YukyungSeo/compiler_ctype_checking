// Generated from C:/Users/YukyungSeo/git/compiler_ctype_checking/BytecodeGenerator/src\MiniC.g4 by ANTLR 4.7.2
 
package generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, VOID=20, INT=21, FLOAT=22, INTARRAY=23, FLOATARRAY=24, 
		WHILE=25, IF=26, ELSE=27, RETURN=28, OR=29, AND=30, LE=31, GE=32, EQ=33, 
		NE=34, IDENT=35, LITERAL=36, DecimalConstant=37, OctalConstant=38, HexadecimalConstant=39, 
		FloatConstant=40, WS=41;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "VOID", "INT", "FLOAT", "INTARRAY", "FLOATARRAY", "WHILE", 
			"IF", "ELSE", "RETURN", "OR", "AND", "LE", "GE", "EQ", "NE", "IDENT", 
			"LITERAL", "DecimalConstant", "OctalConstant", "HexadecimalConstant", 
			"FloatConstant", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'='", "'['", "']'", "'('", "')'", "','", "'{'", "'}'", 
			"'-'", "'+'", "'--'", "'++'", "'*'", "'/'", "'%'", "'<'", "'>'", "'!'", 
			"'void'", "'int'", "'float'", "'int[]'", "'float[]'", "'while'", "'if'", 
			"'else'", "'return'", "'or'", "'and'", "'<='", "'>='", "'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "VOID", "INT", "FLOAT", 
			"INTARRAY", "FLOATARRAY", "WHILE", "IF", "ELSE", "RETURN", "OR", "AND", 
			"LE", "GE", "EQ", "NE", "IDENT", "LITERAL", "DecimalConstant", "OctalConstant", 
			"HexadecimalConstant", "FloatConstant", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MiniCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u00fb\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3"+
		"#\3#\3$\3$\7$\u00c5\n$\f$\16$\u00c8\13$\3%\3%\3%\3%\5%\u00ce\n%\3&\3&"+
		"\3&\7&\u00d3\n&\f&\16&\u00d6\13&\5&\u00d8\n&\3\'\3\'\7\'\u00dc\n\'\f\'"+
		"\16\'\u00df\13\'\3(\3(\3(\6(\u00e4\n(\r(\16(\u00e5\3)\3)\7)\u00ea\n)\f"+
		")\16)\u00ed\13)\3)\3)\6)\u00f1\n)\r)\16)\u00f2\3*\6*\u00f6\n*\r*\16*\u00f7"+
		"\3*\3*\2\2+\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+\3\2\n\5\2C\\aac|\6\2\62;C\\aac|"+
		"\3\2\63;\3\2\62;\3\2\629\4\2ZZzz\5\2\62;CHch\5\2\13\f\17\17\"\"\2\u0105"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\3"+
		"U\3\2\2\2\5W\3\2\2\2\7Y\3\2\2\2\t[\3\2\2\2\13]\3\2\2\2\r_\3\2\2\2\17a"+
		"\3\2\2\2\21c\3\2\2\2\23e\3\2\2\2\25g\3\2\2\2\27i\3\2\2\2\31k\3\2\2\2\33"+
		"n\3\2\2\2\35q\3\2\2\2\37s\3\2\2\2!u\3\2\2\2#w\3\2\2\2%y\3\2\2\2\'{\3\2"+
		"\2\2)}\3\2\2\2+\u0082\3\2\2\2-\u0086\3\2\2\2/\u008c\3\2\2\2\61\u0092\3"+
		"\2\2\2\63\u009a\3\2\2\2\65\u00a0\3\2\2\2\67\u00a3\3\2\2\29\u00a8\3\2\2"+
		"\2;\u00af\3\2\2\2=\u00b2\3\2\2\2?\u00b6\3\2\2\2A\u00b9\3\2\2\2C\u00bc"+
		"\3\2\2\2E\u00bf\3\2\2\2G\u00c2\3\2\2\2I\u00cd\3\2\2\2K\u00d7\3\2\2\2M"+
		"\u00d9\3\2\2\2O\u00e0\3\2\2\2Q\u00e7\3\2\2\2S\u00f5\3\2\2\2UV\7=\2\2V"+
		"\4\3\2\2\2WX\7?\2\2X\6\3\2\2\2YZ\7]\2\2Z\b\3\2\2\2[\\\7_\2\2\\\n\3\2\2"+
		"\2]^\7*\2\2^\f\3\2\2\2_`\7+\2\2`\16\3\2\2\2ab\7.\2\2b\20\3\2\2\2cd\7}"+
		"\2\2d\22\3\2\2\2ef\7\177\2\2f\24\3\2\2\2gh\7/\2\2h\26\3\2\2\2ij\7-\2\2"+
		"j\30\3\2\2\2kl\7/\2\2lm\7/\2\2m\32\3\2\2\2no\7-\2\2op\7-\2\2p\34\3\2\2"+
		"\2qr\7,\2\2r\36\3\2\2\2st\7\61\2\2t \3\2\2\2uv\7\'\2\2v\"\3\2\2\2wx\7"+
		">\2\2x$\3\2\2\2yz\7@\2\2z&\3\2\2\2{|\7#\2\2|(\3\2\2\2}~\7x\2\2~\177\7"+
		"q\2\2\177\u0080\7k\2\2\u0080\u0081\7f\2\2\u0081*\3\2\2\2\u0082\u0083\7"+
		"k\2\2\u0083\u0084\7p\2\2\u0084\u0085\7v\2\2\u0085,\3\2\2\2\u0086\u0087"+
		"\7h\2\2\u0087\u0088\7n\2\2\u0088\u0089\7q\2\2\u0089\u008a\7c\2\2\u008a"+
		"\u008b\7v\2\2\u008b.\3\2\2\2\u008c\u008d\7k\2\2\u008d\u008e\7p\2\2\u008e"+
		"\u008f\7v\2\2\u008f\u0090\7]\2\2\u0090\u0091\7_\2\2\u0091\60\3\2\2\2\u0092"+
		"\u0093\7h\2\2\u0093\u0094\7n\2\2\u0094\u0095\7q\2\2\u0095\u0096\7c\2\2"+
		"\u0096\u0097\7v\2\2\u0097\u0098\7]\2\2\u0098\u0099\7_\2\2\u0099\62\3\2"+
		"\2\2\u009a\u009b\7y\2\2\u009b\u009c\7j\2\2\u009c\u009d\7k\2\2\u009d\u009e"+
		"\7n\2\2\u009e\u009f\7g\2\2\u009f\64\3\2\2\2\u00a0\u00a1\7k\2\2\u00a1\u00a2"+
		"\7h\2\2\u00a2\66\3\2\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6"+
		"\7u\2\2\u00a6\u00a7\7g\2\2\u00a78\3\2\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa"+
		"\7g\2\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7w\2\2\u00ac\u00ad\7t\2\2\u00ad"+
		"\u00ae\7p\2\2\u00ae:\3\2\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7t\2\2\u00b1"+
		"<\3\2\2\2\u00b2\u00b3\7c\2\2\u00b3\u00b4\7p\2\2\u00b4\u00b5\7f\2\2\u00b5"+
		">\3\2\2\2\u00b6\u00b7\7>\2\2\u00b7\u00b8\7?\2\2\u00b8@\3\2\2\2\u00b9\u00ba"+
		"\7@\2\2\u00ba\u00bb\7?\2\2\u00bbB\3\2\2\2\u00bc\u00bd\7?\2\2\u00bd\u00be"+
		"\7?\2\2\u00beD\3\2\2\2\u00bf\u00c0\7#\2\2\u00c0\u00c1\7?\2\2\u00c1F\3"+
		"\2\2\2\u00c2\u00c6\t\2\2\2\u00c3\u00c5\t\3\2\2\u00c4\u00c3\3\2\2\2\u00c5"+
		"\u00c8\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7H\3\2\2\2"+
		"\u00c8\u00c6\3\2\2\2\u00c9\u00ce\5K&\2\u00ca\u00ce\5M\'\2\u00cb\u00ce"+
		"\5O(\2\u00cc\u00ce\5Q)\2\u00cd\u00c9\3\2\2\2\u00cd\u00ca\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ceJ\3\2\2\2\u00cf\u00d8\7\62\2\2"+
		"\u00d0\u00d4\t\4\2\2\u00d1\u00d3\t\5\2\2\u00d2\u00d1\3\2\2\2\u00d3\u00d6"+
		"\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d7\u00cf\3\2\2\2\u00d7\u00d0\3\2\2\2\u00d8L\3\2\2\2"+
		"\u00d9\u00dd\7\62\2\2\u00da\u00dc\t\6\2\2\u00db\u00da\3\2\2\2\u00dc\u00df"+
		"\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00deN\3\2\2\2\u00df"+
		"\u00dd\3\2\2\2\u00e0\u00e1\7\62\2\2\u00e1\u00e3\t\7\2\2\u00e2\u00e4\t"+
		"\b\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5"+
		"\u00e6\3\2\2\2\u00e6P\3\2\2\2\u00e7\u00eb\t\4\2\2\u00e8\u00ea\t\5\2\2"+
		"\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec"+
		"\3\2\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f0\7\60\2\2"+
		"\u00ef\u00f1\t\5\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f0"+
		"\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3R\3\2\2\2\u00f4\u00f6\t\t\2\2\u00f5"+
		"\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2"+
		"\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\b*\2\2\u00faT\3\2\2\2\r\2\u00c4\u00c6"+
		"\u00cd\u00d4\u00d7\u00dd\u00e5\u00eb\u00f2\u00f7\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}