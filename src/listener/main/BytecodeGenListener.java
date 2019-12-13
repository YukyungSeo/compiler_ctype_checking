package listener.main;

import gen.MiniCBaseListener;
import gen.MiniCParser;
import gen.MiniCParser.*;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import static listener.main.BytecodeGenListenerHelper.*;
import static listener.main.SymbolTable.Type;

public class BytecodeGenListener extends MiniCBaseListener implements ParseTreeListener {
    ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();
    SymbolTable symbolTable = new SymbolTable();

    int tab = 0;
    int label = 0;

    // program	: decl+

    @Override
    public void enterFun_decl(Fun_declContext ctx) {
        symbolTable.initFunDecl();

        String fname = getFunName(ctx);
        ParamsContext params;

        if (fname.equals("main")) {
            symbolTable.putLocalVar("args", Type.INTARRAY);
        } else {
            symbolTable.putFunSpecStr(ctx);
            params = (MiniCParser.ParamsContext) ctx.getChild(3);
            symbolTable.putParams(params);
        }
    }


    // var_decl	: type_spec IDENT ';' | type_spec IDENT '=' LITERAL ';'|type_spec IDENT '[' LITERAL ']' ';'
    @Override
    public void enterVar_decl(Var_declContext ctx) {
        String varName = ctx.IDENT().getText();

        if (isArrayDecl(ctx)) {
            symbolTable.putGlobalVar(varName, Type.INTARRAY);
        } else if (isDeclWithInit(ctx)) {
            symbolTable.putGlobalVarWithInitVal(varName, Type.INT, initVal(ctx));
        } else { // simple decl
            symbolTable.putGlobalVar(varName, Type.INT);
        }
    }


    @Override
    public void enterLocal_decl(Local_declContext ctx) {
        if (isArrayDecl(ctx)) {
            symbolTable.putLocalVar(getLocalVarName(ctx), Type.INTARRAY);
        } else if (isDeclWithInit(ctx)) {
            symbolTable.putLocalVarWithInitVal(getLocalVarName(ctx), getType(ctx.type_spec()), initVal(ctx));
        } else { // simple decl
            symbolTable.putLocalVar(getLocalVarName(ctx), Type.INT);
        }
    }

    @Override
    public void enterCompound_stmt(Compound_stmtContext ctx) {
        this.tab++;
    }


    @Override
    public void exitProgram(ProgramContext ctx) {
        String classProlog = getFunProlog();

        String fun_decl = "", var_decl = "";

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (isFunDecl(ctx, i))
                fun_decl += newTexts.get(ctx.decl(i));
            else
                var_decl += newTexts.get(ctx.decl(i));
        }

        newTexts.put(ctx, classProlog + var_decl + fun_decl);

        System.out.println(newTexts.get(ctx));
        FileOutput fileOutput = new FileOutput();
        fileOutput.fileWrite("./Test.j", newTexts.get(ctx));
    }


    // decl	: var_decl | fun_decl
    @Override
    public void exitDecl(DeclContext ctx) {
        String decl = "";
        if (ctx.getChildCount() == 1) {
            if (ctx.var_decl() != null)                //var_decl
                decl += newTexts.get(ctx.var_decl());
            else                                    //fun_decl
                decl += newTexts.get(ctx.fun_decl()) + "\n\n";

        }
        newTexts.put(ctx, decl);
    }

    // stmt	: expr_stmt | compound_stmt | if_stmt | while_stmt | return_stmt
    @Override
    public void exitStmt(StmtContext ctx) {
        String stmt = "";
        if (ctx.getChildCount() > 0) {
            if (ctx.expr_stmt() != null)                // expr_stmt
                stmt += newTexts.get(ctx.expr_stmt());
            else if (ctx.compound_stmt() != null)    // compound_stmt
                stmt += newTexts.get(ctx.compound_stmt());
            else if (ctx.if_stmt() != null)                // if_stmt
                stmt += newTexts.get(ctx.if_stmt());
            else if (ctx.while_stmt() != null)    // while_stmt
                stmt += newTexts.get(ctx.while_stmt());
            else                                    // return_stmt
                stmt += newTexts.get(ctx.return_stmt());
            // <(0) Fill here>
        }
        newTexts.put(ctx, stmt);
    }

    // expr_stmt	: expr ';'
    @Override
    public void exitExpr_stmt(Expr_stmtContext ctx) {
        String stmt = "";
        if (ctx.getChildCount() == 2) {
            stmt += newTexts.get(ctx.expr());    // expr
        }
        newTexts.put(ctx, stmt);
    }


    // while_stmt	: WHILE '(' expr ')' stmt
    @Override
    public void exitWhile_stmt(While_stmtContext ctx) {
        // <(1) Fill here!>
        String stmt = "";
        String condExpr = newTexts.get(ctx.expr());
        String thenStmt = newTexts.get(ctx.stmt());

        String lstart = symbolTable.newLabel();
        String lend = symbolTable.newLabel();

        stmt += lstart + ": " + "\n"
                + condExpr + "\n"
                + "ifeq " + lend + "\n"     // condExpr 결과가 0(거짓)이면 lend로
                + thenStmt + "\n"           // 결과가 1(참)이면 아래로
                + "goto " + lstart + "\n"   // stmt 수행 후 다시 condExpr를 할 수 있도록 lstart로
                + lend + ": " + "\n";

        newTexts.put(ctx, stmt);
    }

    // compound_stmt	: '{' local_decl* stmt* '}'
    @Override
    public void exitCompound_stmt(Compound_stmtContext ctx) {
        // <(3) Fill here>
        String stmt = "";
        for (int i = 0; i < ctx.local_decl().size(); i++) {
            stmt += newTexts.get(ctx.local_decl(i));
        }
        for (int i = 0; i < ctx.stmt().size(); i++) {
            stmt += newTexts.get(ctx.stmt(i));
        }
        newTexts.put(ctx, stmt);
        this.tab--;
    }

    // if_stmt	: IF '(' expr ')' stmt | IF '(' expr ')' stmt ELSE stmt;
    @Override
    public void exitIf_stmt(If_stmtContext ctx) {
        String stmt = "";
        String condExpr = newTexts.get(ctx.expr());
        String thenStmt = newTexts.get(ctx.stmt(0));

        String lend = symbolTable.newLabel();
        String lelse = symbolTable.newLabel();


        if (noElse(ctx)) {  // else가 없는 if문
            stmt += condExpr
                    + "ifeq " + lend + "\n"     // condExpr 결과가 0(거짓)이라면 lend로
                    + thenStmt + "\n"           // 결과가 참(1)이면 아래로
                    + lend + ":" + "\n";
        } else {            // lese가 있는 if문
            String elseStmt = newTexts.get(ctx.stmt(1));
            stmt += condExpr
                    + "ifeq " + lelse + "\n"    // condExpr 결과가 0(거짓)이라면 lelse로
                    + thenStmt + "\n"           // 결과가 참(1)이면 아래로
                    + "goto " + lend + "\n"     // if 아래 stmt 수행 후 lend로
                    + lelse + ": " + "\n"
                    + elseStmt + "\n"           // lelse 부분 수행
                    + lend + ":" + "\n";
        }

        newTexts.put(ctx, stmt);
    }


    // return_stmt	: RETURN ';' | RETURN expr ';'
    @Override
    public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) {
        // <(4) Fill here>
        String stmt = "";

        if (ctx.getChildCount() == 2) {
            newTexts.put(ctx, "return" + "\n");
        } else {
            stmt += newTexts.get(ctx.expr());
            stmt += "ireturn" + "\n";       //int만 고려하므로 ireturn을 넣어준다.
        }
        newTexts.put(ctx, stmt);
    }

    // fun_decl : type_sepc IDENT ‘(’ params ‘)’ compound_stmt
    @Override
    public void exitFun_decl(Fun_declContext ctx) {
        // <(2) Fill here!>
        String decl = "";
        String fname = ctx.IDENT().getText();

        decl += funcHeader(ctx, fname);
        decl += newTexts.get(ctx.compound_stmt());
        decl += ".end method";

        newTexts.put(ctx, decl);
    }


    private String funcHeader(Fun_declContext ctx, String fname) {
        return ".method public static " + symbolTable.getFunSpecStr(fname) + "\n"
                /*+ "\t"*/ + ".limit stack " + getStackSize(ctx) + "\n"
                /*+ "\t"*/ + ".limit locals " + getLocalVarSize(ctx) + "\n";

    }


    @Override
    public void exitVar_decl(Var_declContext ctx) {
        String varName = ctx.IDENT().getText();
        String varDecl = "";

        if (isDeclWithInit(ctx)) {
            varDecl += "putfield " + varName + "\n";
            // v. initialization => Later! skip now..:
        }
        newTexts.put(ctx, varDecl);
    }


    @Override
    public void exitLocal_decl(Local_declContext ctx) {
        String varDecl = "";

        if (isDeclWithInit(ctx)) {
            if(getType(ctx.type_spec()).equals(Type.INT)) {
                String vId = symbolTable.getVarId(ctx);
                varDecl += "ldc " + ctx.LITERAL().getText() + "\n"
                        + "istore_" + vId + "\n";
            } else if(getType(ctx.type_spec()).equals(Type.FLOAT)) {
                String vId = symbolTable.getVarId(ctx);
                varDecl += "ldc " + ctx.LITERAL().getText() + "\n"
                        + "fstore_" + vId + "\n";
            }
        }
        newTexts.put(ctx, varDecl);
    }


    @Override
    public void exitExpr(ExprContext ctx) {
        String expr = "";

        if (ctx.getChildCount() <= 0) {
            newTexts.put(ctx, "");
            return;
        }

        if (ctx.getChildCount() == 1) { // IDENT | LITERAL
            if (ctx.IDENT() != null) {
                String idName = ctx.IDENT().getText();
                if (symbolTable.getVarType(idName) == Type.INT) {
                    expr += "iload_" + symbolTable.getVarId(idName) + " \n";
                } else if(symbolTable.getVarType(idName) == Type.FLOAT){
                    expr += "fload_" + symbolTable.getVarId(idName) + " \n";
                }
                //else	// Type int array => Later! skip now..
                //	expr += "           lda " + symbolTable.get(ctx.IDENT().getText()).value + " \n";
            } else if (ctx.LITERAL() != null) {
                String literalStr = ctx.LITERAL().getText();
                expr += "ldc " + literalStr + " \n";
            }
        } else if (ctx.getChildCount() == 2) { // UnaryOperation
            expr = handleUnaryExpr(ctx, expr);
        } else if (ctx.getChildCount() == 3) {
            if (ctx.getChild(0).getText().equals("(")) {        // '(' expr ')'
                expr = newTexts.get(ctx.expr(0));

            } else if (ctx.getChild(1).getText().equals("=")) {    // IDENT '=' expr
                expr = newTexts.get(ctx.expr(0))
                        + "istore_" + symbolTable.getVarId(ctx.IDENT().getText()) + " \n";

            } else {                                            // binary operation
                expr = handleBinExpr(ctx, expr);
            }
        }
        // IDENT '(' args ')' |  IDENT '[' expr ']'
        else if (ctx.getChildCount() == 4) {
            if (ctx.args() != null) {        // function calls
                expr = handleFunCall(ctx, expr);
            } else { // expr
                // Arrays: TODO
            }
        }
        // IDENT '[' expr ']' '=' expr
        else { // Arrays: TODO			*/
        }
        newTexts.put(ctx, expr);
    }


    private String handleUnaryExpr(ExprContext ctx, String expr) {
        String l1 = symbolTable.newLabel();
        String l2 = symbolTable.newLabel();
        String lend = symbolTable.newLabel();

        expr += newTexts.get(ctx.expr(0));
        String idName = ctx.expr(0).getText();
        switch (ctx.getChild(0).getText()) {
            case "-":
                expr += "ineg" + "\n";
                break;
            case "--":
                expr += "ldc 1" + "\n"
                        + "isub" + "\n";

                if (symbolTable.getVarType(idName) == Type.INT) {
                    expr += "istore_" + symbolTable.getVarId(idName) + " \n";
                }
                break;
            case "++":
                expr += "ldc 1" + "\n"
                        + "iadd" + "\n";
                if (symbolTable.getVarType(idName) == Type.INT) {
                    expr += "istore_" + symbolTable.getVarId(idName) + " \n";
                }
                break;
            case "!":
                expr += "ifeq " + l2 + "\n"
                        + l1 + ": " + "ldc 0" + "\n"
                        + "goto " + lend + "\n"
                        + l2 + ": " + "ldc 1" + "\n"
                        + lend + ": " + "\n";
                break;
        }
        return expr;
    }


    private String handleBinExpr(ExprContext ctx, String expr) {
        String l2 = symbolTable.newLabel();
        String lend = symbolTable.newLabel();

        expr += newTexts.get(ctx.expr(0));
        expr += newTexts.get(ctx.expr(1));

        switch (ctx.getChild(1).getText()) {
            case "*":
                expr += "imul \n";
                break;
            case "/":
                expr += "idiv \n";
                break;
            case "%":
                expr += "irem \n";
                break;
            case "+":        // expr(0) expr(1) iadd
                expr += "iadd \n";
                break;
            case "-":
                expr += "isub \n";
                break;

            case "==":
                expr += "isub " + "\n"              // 두값을 뺀 후
                        + "ifeq " + l2 + "\n"       // 차이가 0(참)이라면 l2 label로 간다.
                        + "ldc 0" + "\n"            // 차이가 0이 아니라면 0을
                        + "goto " + lend + "\n"     // 반환한다.
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"            // 차이가 0이라면 1을
                        + lend + ": " + "\n";       // 반환한다.
                break;
            case "!=":
                expr += "isub " + "\n"
                        + "ifne " + l2 + "\n"
                        + "ldc 0" + "\n"
                        + "goto " + lend + "\n"
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"
                        + lend + ": " + "\n";
                break;
            case "<=":
                // <(5) Fill here>
                expr += "isub " + "\n"              // 두값을 뺀 후
                        + "ifle " + l2 + "\n"       // 차이가 0보다 작거나 같다면(참) l2 label로 간다.
                        + "ldc 0" + "\n"            // 차이가 0보다 크다면(거짓) 0을
                        + "goto " + lend + "\n"     // 반환한다.
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"            // 차이가 0보다 작거나 같다면(참) 1을
                        + lend + ": " + "\n";       // 반환한다.
                break;
            case "<":
                // <(6) Fill here>
                expr += "isub " + "\n"
                        + "iflt " + l2 + "\n"
                        + "ldc 0" + "\n"
                        + "goto " + lend + "\n"
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"
                        + lend + ": " + "\n";
                break;

            case ">=":
                // <(7) Fill here>
                expr += "isub " + "\n"
                        + "ifge " + l2 + "\n"
                        + "ldc 0" + "\n"
                        + "goto " + lend + "\n"
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"
                        + lend + ": " + "\n";
                break;

            case ">":
                // <(8) Fill here>
                expr += "isub " + "\n"
                        + "ifgt " + l2 + "\n"
                        + "ldc 0" + "\n"
                        + "goto " + lend + "\n"
                        + l2 + ": " + "\n"
                        + "ldc 1" + "\n"
                        + lend + ": " + "\n";
                break;

            case "and":
                expr += "ifne " + lend + "\n"
                        + "pop" + "\n"
                        + "ldc 0" + "\n"
                        + lend + ": " + "\n";
                break;
            case "or":
                // <(9) Fill here>
                expr += "ifeq " + lend + "\n"
                        + "pop" + "\n"
                        + "ldc 0" + "\n"
                        + lend + ": " + "\n";
                break;

        }
        return expr;
    }

    private String handleFunCall(ExprContext ctx, String expr) {
        String fname = getFunName(ctx);

        if (fname.equals("_print")) {        // System.out.println
            expr = "getstatic java/lang/System/out Ljava/io/PrintStream; " + "\n"
                    + newTexts.get(ctx.args())
                    + "invokevirtual " + symbolTable.getFunSpecStr("_print") + "\n";
        } else {
            expr = newTexts.get(ctx.args())
                    + "invokestatic " + getCurrentClassName() + "/" + symbolTable.getFunSpecStr(fname) + "\n";
        }

        return expr;
    }

    // args	: expr (',' expr)* | ;
    @Override
    public void exitArgs(ArgsContext ctx) {
        String argsStr = "";

        for (int i = 0; i < ctx.expr().size(); i++) {
            argsStr += newTexts.get(ctx.expr(i));
        }
        newTexts.put(ctx, argsStr);
    }
}
