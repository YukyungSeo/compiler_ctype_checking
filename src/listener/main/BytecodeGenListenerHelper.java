package listener.main;

import java.util.Hashtable;

import gen.*;
import gen.MiniCParser.*;

public class BytecodeGenListenerHelper {
	
	// <boolean functions>
	
	static boolean isFunDecl(MiniCParser.ProgramContext ctx, int i) {
		return ctx.getChild(i).getChild(0) instanceof MiniCParser.Fun_declContext;
	}
	
	// type_spec IDENT '[' ']'
	static boolean isArrayParamDecl(ParamContext param) {
		return param.getChildCount() == 4;
	}
	
	// global vars
	static String initVal(Var_declContext ctx) {
//		return Integer.parseInt(ctx.LITERAL().getText());
		return ctx.LITERAL().getText();
	}

	// var_decl	: type_spec IDENT '=' LITERAL ';
	static boolean isDeclWithInit(Var_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}
	// var_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static boolean isArrayDecl(Var_declContext ctx) {
		return ctx.getChildCount() == 6;
	}

	// <local vars>
	// local_decl	: type_spec IDENT '[' LITERAL ']' ';'
	static String initVal(Local_declContext ctx) {
//		return Integer.parseInt(ctx.LITERAL().getText());
		return ctx.LITERAL().getText();
	}

	static boolean isArrayDecl(Local_declContext ctx) {
		return ctx.getChildCount() == 6;
	}
	
	static boolean isDeclWithInit(Local_declContext ctx) {
		return ctx.getChildCount() == 5 ;
	}
	
	static boolean isVoidF(Fun_declContext ctx) {
			// <Fill in>
        if(ctx.getChild(0).getText().equals("void"))
            return true;
		return false;
	}
	
	static boolean isIntReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() ==3;
	}


	static boolean isVoidReturn(MiniCParser.Return_stmtContext ctx) {
		return ctx.getChildCount() == 2;
	}
	
	// <information extraction>
	static String getStackSize(Fun_declContext ctx) {
		return "32";
	}
	static String getLocalVarSize(Fun_declContext ctx) {
		return "32";
	}
	static String getTypeText(Type_specContext typespec) {
		// <Fill in>
		String str = typespec.getChild(0).getText();
		String rst = "";

		if(str.equals("int")){
			rst += "I";
		} else if (str.equals("float")){
			rst = "F";
		} else if (str.equals("void")){
			rst = "V";
		}
		return rst;
	}

	static SymbolTable.Type getType(Type_specContext ctx){
		if(ctx.getText().equals("int")){
			return SymbolTable.Type.INT;
		} else if(ctx.getText().equals("float")){
			return SymbolTable.Type.FLOAT;
		}
		return SymbolTable.Type.ERROR;
	}

//	static SymbolTable.Type getType(ExprContext ctx){
//		if(ctx.getText().equals("int")){
//			return SymbolTable.Type.INT;
//		} else if(ctx.getText().equals("float")){
//			return SymbolTable.Type.FLOAT;
//		}
//		return SymbolTable.Type.ERROR;
//	}

	// params
	static String getParamName(ParamContext param) {
		// <Fill in>
		return param.getChild(0).getText();
	}
	
	static String getParamTypesText(ParamsContext params) {
		String typeText = "";
		
		for(int i = 0; i < params.param().size(); i++) {
			MiniCParser.Type_specContext typespec = (MiniCParser.Type_specContext)  params.param(i).getChild(0);
			typeText += getTypeText(typespec); // + ";";
		}
		return typeText;
	}
	
	static String getLocalVarName(Local_declContext local_decl) {
		// <Fill in>
		String str = local_decl.getChild(1).getText();
		return str;
	}
	
	static String getFunName(Fun_declContext ctx) {
		// <Fill in>
		String str = ctx.getChild(1).getText();
		return str;
	}
	
	static String getFunName(ExprContext ctx) {
		// <Fill in>
        String str = ctx.getChild(0).getText();
		return str;
	}
	
	static boolean noElse(If_stmtContext ctx) {
		return ctx.getChildCount() <= 5;
	}
	
	static String getFunProlog() {
		// return ".class public Test .....
		// ...
		// invokenonvirtual java/lang/Object/<init>()
		// return
		// .end method"
        return ".class public Test" + "\n"
                + ".super java/lang/Object" + "\n\n"
                + "; standard initializer" + "\n\n"
                + ".method public <init>()V" + "\n"
                + "\t" + "aload_0" + "\n"
                + "\t" + "invokenonvirtual java/lang/object/<init>()V" + "\n"
                + "\t" + "return" + "\n"
                + ".end method" + "\n\n";
	}
	
	static String getCurrentClassName() {
		return "Test";
	}
}
