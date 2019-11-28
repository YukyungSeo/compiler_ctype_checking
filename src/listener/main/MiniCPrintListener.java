package listener.main;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import gen.*;
import gen.MiniCParser.*;

public class MiniCPrintListener extends MiniCBaseListener {
	ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();
	int tapCount = 0; // 깊이 depth을 count해주는 변수, compound_stmt가 열릴 때마다 +1 증가시켜준다.

	@Override
	public void enterProgram(ProgramContext ctx) {
		// TODO Auto-generated method stub
		super.enterProgram(ctx);
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		// TODO Auto-generated method stub
		// program : decl+
		// decl이 한개 이상 있다는 의미
		// for문을 돌려 존재하는 decl을 전부 가져와 정렬한다.
		
		String str = "";
		for (int i = 0; i < ctx.decl().size(); i++) { // decl 갯수만큼 실행
			str += newTexts.get(ctx.decl(i)) + "\n"; // 한 decl 마다 줄바꿈을 해준다.
		}
		newTexts.put(ctx, str); // 정렬하여 newTexts에 넣음
		System.out.println(newTexts.get(ctx)); // 정렬이 완료된 pretty print 출력
	}

	@Override
	public void enterDecl(DeclContext ctx) {
		// TODO Auto-generated method stub
		super.enterDecl(ctx);
	}

	@Override
	public void exitDecl(DeclContext ctx) {
		// TODO Auto-generated method stub
		// decl : val_decl 
		// 		| fun_decl
		// decl은 children을 1개만 가짐을 알 수 있다.
		
		if (ctx.getChild(0) == ctx.var_decl()) { // 가지고 있는 child가 var_decl일 경우
			String str = newTexts.get(ctx.var_decl()); // 정렬된 값을 newTexts에서 가져온다.
			newTexts.put(ctx, str); // 정렬하여 newTexts에 넣는다.
		} else { // 가지고 있는 child가 fun_decl일 경우
			String str = newTexts.get(ctx.fun_decl()); // 정렬된 값을 newTexts에서 가져온다.
			newTexts.put(ctx, str); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterVar_decl(Var_declContext ctx) {
		// TODO Auto-generated method stub
		super.enterVar_decl(ctx);
	}

	@Override
	public void exitVar_decl(Var_declContext ctx) {
		// TODO Auto-generated method stub
		// var_decl : type_spec IDENT ';'
		// | type_spec IDENT '=' LITERAL ';'
		// | type_spec IDENT '[' LITERAL ']' ';' ;
		// tpye_spec IDENT terminal 까지는 모두 같다.
		
		String type = newTexts.get(ctx.type_spec());
		String ident = newTexts.get(ctx.IDENT());
		String s3 = ctx.getChild(2).getText(); // type_spec IDENT terminal

		if (ctx.getChildCount() == 3) { // type_spec IDENT ';'
			newTexts.put(ctx, type + " " + ident + s3); // 정렬하여 newTexts에 넣는다.
		} else if (ctx.getChildCount() == 5) { // type_spec IDENT '=' LITERAL ';'
			String literal = newTexts.get(ctx.LITERAL()); // LITERAL
			String s5 = ctx.getChild(4).getText(); // ;
			newTexts.put(ctx, type + " " + ident + " " + s3 + " " + literal + s5); // 정렬하여 newTexts에 넣는다.
		} else {
			String literal = newTexts.get(ctx.LITERAL()); // type_spec IDENT '[' LITERAL ']' ';'
			String s5 = ctx.getChild(4).getText(); // LITERAL
			String s6 = ctx.getChild(5).getText(); // ]
			newTexts.put(ctx, type + " " + ident + s3 + literal + s5 + s6); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterType_spec(Type_specContext ctx) {
		// TODO Auto-generated method stub
		super.enterType_spec(ctx);
	}

	@Override
	public void exitType_spec(Type_specContext ctx) {
		// TODO Auto-generated method stub
		// type_spec : VOID
		// | INT
		// child가 1개이기 때문에 그것을 크게 정렬할 필요 없다.
		if (ctx.getChild(0) == ctx.VOID()) { // VOID
			String str = newTexts.get(ctx.VOID());
			newTexts.put(ctx, str); // 정렬하여 newTexts에 넣는다.
		} else { // INT
			String str = newTexts.get(ctx.INT());
			newTexts.put(ctx, str); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterFun_decl(MiniCParser.Fun_declContext ctx) {
		super.enterFun_decl(ctx);
	}

	@Override
	public void exitFun_decl(Fun_declContext ctx) {
		// TODO Auto-generated method stub
		// fun_decl : type_spec IDENT '(' params ')' compound_stmt ;
		// 정렬이 많이 필요하다.
		// 일반 괄호()는 expression 에 붙여 적는다
		String type = newTexts.get(ctx.type_spec()); // type_spec
		String ident = newTexts.get(ctx.IDENT()); 	 // IDENT
		String s3 = ctx.getChild(2).getText(); 		 // (
		String params = newTexts.get(ctx.params());  // params
		String s5 = ctx.getChild(4).getText(); 		 // )
		String compound = newTexts.get(ctx.compound_stmt()); // compound_stmt : { ... }

		newTexts.put(ctx, type + " " + ident + s3 + params + s5 + "\n" + compound); // 정렬하여 newTexts에 넣는다.
	}

	@Override
	public void enterParams(ParamsContext ctx) {
		// TODO Auto-generated method stub
		super.enterParams(ctx);
	}

	@Override
	public void exitParams(ParamsContext ctx) {
		// TODO Auto-generated method stub
		// params : param (',' param)*
		// 			| VOID
		// 			| 
		// VOID는 null(아예 빈칸)과 같다.
		// param이 0개 or 1개 이상인 경우 2개만 보면 된다.
		String str = "";

		if (ctx.param(0) != null) { // param이 존재할 때
			str += newTexts.get(ctx.param(0));
			for (int i = 1; i < ctx.param().size(); i++) { // 뒤에 ( , param)이 반복될 경우
				str += ctx.getChild(2 * i - 1).getText() + " "; // 해당 위치의 ,를 가져온다.
				str += newTexts.get(ctx.param(i)); // list에 있는 param을 순서대로 가져온다.
			}
		} // VOID & null(아예 빈칸) : str = ""
		newTexts.put(ctx, str);
	}

	@Override
	public void enterParam(ParamContext ctx) {
		// TODO Auto-generated method stub
		super.enterParam(ctx);
	}

	@Override
	public void exitParam(ParamContext ctx) {
		// TODO Auto-generated method stub
		// param : type_spec IDENT
		// | type_spec IDENT '[' ']' ;
		// type_spec IDENT 까지 동일하다.
		String type = newTexts.get(ctx.type_spec()); // type_spec
		String ident = newTexts.get(ctx.IDENT()); // IDENT

		if (ctx.getChildCount() == 2) { // type_spec IDENT
			newTexts.put(ctx, type + " " + ident); // 정렬하여 newTexts에 넣는다.
		} else {
			String s3 = ctx.getChild(2).getText(); // [
			String s4 = ctx.getChild(3).getText(); // ]
			newTexts.put(ctx, type + " " + ident + s3 + s4); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterStmt(StmtContext ctx) {
		// TODO Auto-generated method stub
		super.enterStmt(ctx);
	}

	@Override
	public void exitStmt(StmtContext ctx) {
		// TODO Auto-generated method stub
		// stmt : expr_stmt
		// | compound_stmt
		// | if_stmt
		// | while_stmt
		// | return_stmt ;
		// child가 1개이기 때문 에 특별히 정렬할 필요가 없다. 대신 각각에 어떤 stmt인지 확인이 필요하다.

		String str = null;
		if (ctx.getChild(0) == ctx.expr_stmt()) { // expr_stmt
			str = newTexts.get(ctx.expr_stmt());
		} else if (ctx.getChild(0) == ctx.compound_stmt()) { // compound_stmt
			str = newTexts.get(ctx.compound_stmt());
		} else if (ctx.getChild(0) == ctx.if_stmt()) { // if_stmt
			str = newTexts.get(ctx.if_stmt());
		} else if (ctx.getChild(0) == ctx.while_stmt()) { // while_stmt
			str = newTexts.get(ctx.while_stmt());
		} else if (ctx.getChild(0) == ctx.return_stmt()) { // return_stmt
			str = newTexts.get(ctx.return_stmt());
		}
		newTexts.put(ctx, str);
	}

	@Override
	public void enterExpr_stmt(Expr_stmtContext ctx) {
		// TODO Auto-generated method stub
		super.enterExpr_stmt(ctx);
	}

	@Override
	public void exitExpr_stmt(Expr_stmtContext ctx) {
		// TODO Auto-generated method stub
		// expr_stmt : expr ';' ;
		// expr과 ;만 붙여서 정렬하는 단순한 구조이다. 띄어쓰기도 필요없다.
		String str = newTexts.get(ctx.expr()); // expr
		str += ctx.getChild(1).getText(); // ;
		newTexts.put(ctx, str);
	}

	@Override
	public void enterWhile_stmt(While_stmtContext ctx) {
		// TODO Auto-generated method stub
		super.enterWhile_stmt(ctx);
	}

	@Override
	public void exitWhile_stmt(While_stmtContext ctx) {
		// TODO Auto-generated method stub
		// while_stmt : WHILE '(' expr ')' stmt ;

		String tap = "";
		for (int i = 0; i < this.tapCount; i++) {
			tap += "....";
		} // 필요한 갯수만큼의 tap(....)을 만들어주는 과정

		String _while = newTexts.get(ctx.WHILE()); // WHILE
		String s2 = ctx.getChild(1).getText(); // (
		String expr = newTexts.get(ctx.expr()); // expr
		String s4 = ctx.getChild(3).getText(); // )
		String stmt = newTexts.get(ctx.stmt()); // stmt

		if (!stmt.contains("{")) { // 만약 stmt가 중괄호{}에 둘러쌓여 있지 않은 한줄짜리 expr_stmt일 경우
			stmt = (tap + "{\n") + (tap + "...." + stmt) + ("\n" + tap + "}"); // 필요한 중괄호{}와 tap을 직접 입력하여준다.
		}
		newTexts.put(ctx, _while + " " + s2 + expr + s4 + "\n" + stmt); // 정렬하여 newTexts에 넣는다.
	}

	@Override
	public void enterCompound_stmt(Compound_stmtContext ctx) {
		// TODO Auto-generated method stub
		this.tapCount++; // tapCount는 중괄호{}가 열릴 때마다 생기는 depth를 count해주는 변수이다.
	}

	@Override
	public void exitCompound_stmt(Compound_stmtContext ctx) {
		// TODO Auto-generated method stub
		// compound_stmt: '{' local_decl* stmt* '}' ;
		// 함수 초반에 변수 선언을 0개 이상하고 나면,
		// 다음은 stmt가 0개 이상 선언된다.

		String tap = "";
		for (int i = 0; i < this.tapCount - 1; i++) {
			tap += "....";
		} // 필요한 갯수만큼의 tap(....)을 만들어주는 과정

		String str = "";
		str += tap + ctx.getChild(0).getText() + "\n"; // {
		for (int i = 0; i < ctx.local_decl().size(); i++) { // local_decl* : list에 존재하는 모든 locat_decl를 차례로 정렬시킨다.
			str += tap + "...." + newTexts.get(ctx.local_decl(i)) + "\n";
		}
		for (int i = 0; i < ctx.stmt().size(); i++) { // stmt* : list에 존재하는 모든 stmt를 차례로 정렬시킨다.
			str += tap + "...." + newTexts.get(ctx.stmt(i)) + "\n";// 이거 i 확인
		}
		str += tap + ctx.getChild(ctx.getChildCount() - 1).getText(); // }
		newTexts.put(ctx, str);
		this.tapCount--; // depth를 요구하는 중괄호{}가 닫혔으니, count를 -1씩 감소시킨다.
	}

	@Override
	public void enterLocal_decl(Local_declContext ctx) {
		// TODO Auto-generated method stub
		super.enterLocal_decl(ctx);
	}

	@Override
	public void exitLocal_decl(Local_declContext ctx) {
		// TODO Auto-generated method stub
		// local_decl : type_spec IDENT ';'
		// | type_spec IDENT '=' LITERAL ';'
		// | type_spec IDENT '[' LITERAL ']' ';' ;
		// type_spec IDENT token 까지는 동일하다.

		String type = newTexts.get(ctx.type_spec()); // type_spec
		String ident = newTexts.get(ctx.IDENT()); // IDENT
		String s3 = ctx.getChild(2).getText(); // ; | = | [

		if (ctx.getChildCount() == 3) { // type_spec IDENT ';'
			newTexts.put(ctx, type + " " + ident + s3); // 정렬하여 newTexts에 넣는다.
		} else if (ctx.getChildCount() == 5) { // type_spec IDENT '=' LITERAL ';'
			String s4 = newTexts.get(ctx.LITERAL()); // LITERAL
			String s5 = ctx.getChild(4).getText(); // ;
			newTexts.put(ctx, type + " " + ident + " " + s3 + " " + s4 + s5); // 정렬하여 newTexts에 넣는다.
		} else { // type_spec IDENT '[' LITERAL ']' ';'
			String s4 = newTexts.get(ctx.LITERAL()); // LITERAL
			String s5 = ctx.getChild(4).getText(); // ]
			String s6 = ctx.getChild(5).getText(); // ;
			newTexts.put(ctx, type + " " + ident + s3 + s4 + s5 + s6); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterIf_stmt(If_stmtContext ctx) {
		// TODO Auto-generated method stub
		super.enterIf_stmt(ctx);
	}

	@Override
	public void exitIf_stmt(If_stmtContext ctx) {
		// TODO Auto-generated method stub
		// if_stmt : IF '(' expr ')' stmt
		// | IF '(' expr ')' stmt ELSE stmt ;
		// IF ( expr ) stmt 까지는 동일하다.

		String tap = "";
		for (int i = 0; i < this.tapCount; i++) {
			tap += "....";
		} // 필요한 갯수만큼의 tap(....)을 만들어주는 과정

		String _if = newTexts.get(ctx.IF()); // IF
		String s2 = ctx.getChild(1).getText(); // (
		String expr = newTexts.get(ctx.expr()); // expr
		String s4 = ctx.getChild(3).getText(); // )
		String stmt = newTexts.get(ctx.stmt(0)); // stmt

		if (!stmt.contains("{")) { // 만약 stmt가 중괄호{}에 둘러쌓여 있지 않은 한줄짜리 expr_stmt일 경우
			stmt = (tap + "{\n") + (tap + "...." + stmt) + ("\n" + tap + "}"); // 필요한 중괄호{}와 tap을 직접 입력하여준다.
		}

		if (ctx.getChildCount() == 5) { // IF '(' expr ')' stmt
			newTexts.put(ctx, _if + " " + s2 + expr + s4 + "\n" + stmt);
		} else { // IF '(' expr ')' stmt ELSE stmt
			String _else = tap + newTexts.get(ctx.ELSE()); // ELSE
			String stmt_else = newTexts.get(ctx.stmt(1)); // stmt
			if (!stmt_else.contains("{")) { // 만약 stmt가 중괄호{}에 둘러쌓여 있지 않은 한줄짜리 expr_stmt일 경우
				stmt_else = (tap + "{\n") + (tap + "...." + stmt_else) + ("\n" + tap + "}"); // 필요한 중괄호{}와 tap을 직접 입력하여준다.
			}
			newTexts.put(ctx, _if + " " + s2 + expr + s4 + "\n" + stmt + "\n" + _else + "\n" + stmt_else); // 정렬하여 newTexts에 넣는다.
		}
	}

	@Override
	public void enterReturn_stmt(Return_stmtContext ctx) {
		// TODO Auto-generated method stub
		super.enterReturn_stmt(ctx);
	}

	@Override
	public void exitReturn_stmt(Return_stmtContext ctx) {
		// TODO Auto-generated method stub
		// return_stmt : RETURN ';'
		// | RETURN expr ';' ;
		// return과 ;가 동일하게 쓰인다.
		String _return = newTexts.get(ctx.RETURN()); // RETURN
		String expr = "";
		if (ctx.getChildCount() == 3) { // expr가 사이에 존재할 때 == childCount가 3개일 때
			expr = " " + newTexts.get(ctx.expr()); // expr
		}
		String s3 = ctx.getChild(ctx.getChildCount() - 1).getText(); // ;
		newTexts.put(ctx, _return + expr + s3); // 정렬하여 newTexts에 넣는다.
	}

	@Override
	public void enterExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		super.enterExpr(ctx);
	}

	private boolean isBinaryOperation(MiniCParser.ExprContext ctx) {
		// childCount가 3이고 expr의 갯수가 2개이면 Binary Operation 형태라 볼 수 있다.
		return ctx.getChildCount() == 3 && ctx.expr().size() == 2;
	}

	private boolean isOnewayOperation(MiniCParser.ExprContext ctx) {
		// TODO Auto-generated method stub
		// childCount가 2이고 expr의 갯수가 1개이면 One-way Operation 형태라 볼 수 있다.
		return ctx.getChildCount() == 2 && ctx.expr().size() == 1;
	}

	@Override
	public void exitExpr(MiniCParser.ExprContext ctx) {
		// expr : LITERAL    	
		// 		| '(' expr ')'	 
		// 		| IDENT .....
		// 		| op expr
		// 		| expr op expr
		// 위처럼 5가지 형태로 나눌 수 있다.

		if (this.isBinaryOperation(ctx)) { // expr op expr 형태
			// childCount가 3이고 expr의 갯수가 2개이면 Binary Operation 형태라 볼 수 있다.

			String expr1 = "", expr2 = "", op = "";

			expr1 = this.newTexts.get(ctx.expr(0)); // expr
			expr2 = this.newTexts.get(ctx.expr(1)); // expr

			if (ctx.getChild(1) == ctx.EQ()) { // expr EQ expr
				op = this.newTexts.get(ctx.EQ());
			} else if (ctx.getChild(1) == ctx.NE()) { // expr NE expr
				op = this.newTexts.get(ctx.NE());
			} else if (ctx.getChild(1) == ctx.LE()) { // expr LE expr
				op = this.newTexts.get(ctx.LE());
			} else if (ctx.getChild(1) == ctx.GE()) { // expr GE expr
				op = this.newTexts.get(ctx.GE());
			} else if (ctx.getChild(1) == ctx.AND()) { // expr AND expr
				op = this.newTexts.get(ctx.AND());
			} else if (ctx.getChild(1) == ctx.OR()) { // expr OR expr
				op = this.newTexts.get(ctx.OR());
			} else {
				op = ctx.getChild(1).getText(); // expr token expr
			}
			newTexts.put(ctx, expr1 + " " + op + " " + expr2); // 정렬하여 newTexts에 넣음

		} else if (this.isOnewayOperation(ctx)) { // token expr 형태
			// childCount가 2이고 expr의 갯수가 1개이면 One-way Operation 형태라 볼 수 있다.

			String op = ctx.getChild(0).getText(); // token
			String expr = this.newTexts.get(ctx.expr(0)); // expr
			newTexts.put(ctx, op + expr); // 정렬하여 newTexts에 넣음

		} else if (ctx.getChild(0) == ctx.IDENT()) { // IDENT ... 형태
			// 첫번째 child가 IDENT 형태인 경우
			// [IDENT]와 [IDENT = expr] 형태를 제외하면,
			// IDENT token (expr or args) token ... 이라는 형태를 지니고 있다.
			
			String ident = "", token1 = "", expr_args = "", token2 = "", str = "";
			ident = this.newTexts.get(ctx.IDENT()); // IDENT
			
			if (ctx.getChildCount() == 3 && ctx.getChild(2) == ctx.expr(0)) { // IDENT = expr
				token1 = " " + ctx.getChild(1).getText() + " "; // =
				expr_args = this.newTexts.get(ctx.expr(0)); 	// expr
			} else if (ctx.getChildCount() == 4 && ctx.getChild(2) == ctx.expr(0)) { // IDENT [ expr ]
				token1 = ctx.getChild(1).getText();			// [
				expr_args = this.newTexts.get(ctx.expr(0)); // expr
				token2 = ctx.getChild(3).getText();			// ]
			} else if (ctx.getChildCount() == 4 && ctx.getChild(2) == ctx.args()) { // IDENT ( args )
				token1 = ctx.getChild(1).getText();		   // (
				expr_args = this.newTexts.get(ctx.args()); // args 
				token2 = ctx.getChild(3).getText();		   // )
			} else if (ctx.getChildCount() == 6) { // IDENT [ expr ] = expr
				token1 = ctx.getChild(1).getText(); 		 // [
				expr_args = this.newTexts.get(ctx.expr(0));  // expr
				token2 = ctx.getChild(3).getText();			 // ]
				str += " " + ctx.getChild(4).getText() + " ";// =
				str += this.newTexts.get(ctx.expr(1));		 // expr
			} // 아무 것도 해당되지 않으면 IDENT 홀로 있는 경우
			newTexts.put(ctx, ident + token1 + expr_args + token2 + str); // 정렬하여 newTexts에 넣음
			
		} else if (ctx.getChild(0) == ctx.LITERAL()) { // LITERAL
			// LITERAL 홀로 존재하는 경우
			
			String literal = this.newTexts.get(ctx.LITERAL()); // LITERAL
			newTexts.put(ctx, literal); // 정렬하여 newTexts에 넣음
			
		} else if (ctx.getChildCount() == 3 && ctx.getChild(1) == ctx.expr()) { // ( expr )
			// 길이가 3이나, expr 1개가 중앙에 존재하는 경우
			
			String token1 = ctx.getChild(0).getText(); // (
			String expr = this.newTexts.get(ctx);	   // expr
			String token2 = ctx.getChild(2).getText(); // )
			newTexts.put(ctx, token1 + expr + token2); // 정렬하여 newTexts에 넣음
		}
	}

	@Override
	public void enterArgs(ArgsContext ctx) {
		// TODO Auto-generated method stub
		super.enterArgs(ctx);
	}

	@Override
	public void exitArgs(ArgsContext ctx) {
		// TODO Auto-generated method stub
		// args : expr (',' expr)*
		// | ;
		// expr가 0개 or 1개이상인 경우 2개만 보면 된다.

		String str = "";
		if (ctx.expr(0) != null) { // expr가 존재할 때
			str += newTexts.get(ctx.expr(0)); // 첫번 째 expr
			for (int i = 1; i < ctx.expr().size(); i++) { // 뒤에 (, expr)가 반복될 경우
				str += ctx.getChild(2 * i - 1).getText() + " "; // 해당 위치의 ,를 가져온다.
				str += newTexts.get(ctx.expr(i)); // list에 있는 expr를 순서대로 가져온다.
			}
		} // 아예 빈칸 : str = "";
		newTexts.put(ctx, str); // 정렬하여 newTexts에 넣음
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		super.exitEveryRule(ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub
		newTexts.put(node, node.getText()); // terminal까지 쪼개졌을 때, newTexts에 저장하여준다.
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		// TODO Auto-generated method stub
		super.visitErrorNode(node);
	}
}