package listener.main;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import gen.*;
import gen.MiniCParser.*;

import listener.main.SymbolTable.Type;
import static listener.main.BytecodeGenListenerHelper.*;


public class SymbolTable {
	enum Type {
		INT, INTARRAY, VOID, ERROR
	}
	
	static public class VarInfo {
		Type type; 
		int id;
		int initVal;
		
		public VarInfo(Type type,  int id, int initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}
		public VarInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = 0;
		}
	}
	
	static public class FInfo {
		public String sigStr;
	}
	
	private Map<String, VarInfo> _lsymtable = new HashMap<>();	// local v.
	private Map<String, VarInfo> _gsymtable = new HashMap<>();	// global v.
	private Map<String, FInfo> _fsymtable = new HashMap<>();	// function 
	
		
	private int _globalVarID = 0;
	private int _localVarID = 0;
	private int _labelID = 0;
	private int _tempVarID = 0;
	
	SymbolTable(){
		initFunDecl();
		initFunTable();
	}
	
	void initFunDecl(){		// at each func decl
	    _lsymtable.clear();
		_localVarID = 0;
		_labelID = 0;
		_tempVarID = 32;		
	}
	
	void putLocalVar(String varname, Type type){
		//<Fill here>
		VarInfo vinfo = new VarInfo(type, _localVarID++);
		_lsymtable.put(varname, vinfo);
	}
	
	void putGlobalVar(String varname, Type type){
		//<Fill here>
		VarInfo vinfo = new VarInfo(type, _globalVarID++);
		_gsymtable.put(varname, vinfo);
	}
	
	void putLocalVarWithInitVal(String varname, Type type, int initVar){
		//<Fill here>
		VarInfo vinfo = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, vinfo);
	}
	void putGlobalVarWithInitVal(String varname, Type type, int initVar){
		//<Fill here>
		VarInfo vinfo = new VarInfo(type, _globalVarID++, initVar);
		_gsymtable.put(varname, vinfo);
	}
	
	void putParams(ParamsContext params) {
		for(int i = 0; i < params.param().size(); i++) { // parameter 한 개씩 확인
			//<Fill here>
			// param type 확인
			Type type = null;
			String typestr = params.param(i).getChild(0).getText();
			if (typestr.equals("int")) {
				type = Type.INT;
			} else if (typestr.equals("void")) {
				type = Type.VOID;
			}

			// param의 정보를 VarInfo로 만들어 _lsystable에 저장
			VarInfo vinfo;
			String varName = params.param(i).getChild(1).getText();
			if (params.param(i).getChildCount() == 2) {
				vinfo = new VarInfo(type, _localVarID++);
			} else {	// int x = 0... 이럴 경우는 없음
				int initval = Integer.parseInt(params.param(i).getChild(3).getText());
				vinfo = new VarInfo(type, _localVarID++, initval);
			}
			_lsymtable.put(varName, vinfo);
		}
	}
	
	private void initFunTable() {
		FInfo printlninfo = new FInfo();
		printlninfo.sigStr = "java/io/PrintStream/println(I)V";
		
		FInfo maininfo = new FInfo();
		maininfo.sigStr = "main([Ljava/lang/String;)V";
		_fsymtable.put("_print", printlninfo);
		_fsymtable.put("main", maininfo);
	}
	
	public String getFunSpecStr(String fname) {		
		// <Fill here>
        FInfo fInfo = _fsymtable.get(fname);
        return fInfo.sigStr;
	}

	public String getFunSpecStr(Fun_declContext ctx) {
		// <Fill here>
        FInfo fInfo = _fsymtable.get(ctx.IDENT().getText());
        return fInfo.sigStr;
	}
	
	public String putFunSpecStr(Fun_declContext ctx) {
		String fname = getFunName(ctx);	// 함수 이름
		String argtype = getParamTypesText((ParamsContext) ctx.getChild(3));	// 매개변수들 type 정리
		String rtype = getTypeText((Type_specContext) ctx.getChild(0));	// return type 정리
		String res = "";
		
		// <Fill here>	
		
		res +=  fname + "(" + argtype + ")" + rtype;
		
		FInfo finfo = new FInfo();
		finfo.sigStr = res;
		_fsymtable.put(fname, finfo);
		
		return res;
	}
	
	String getVarId(String name){
		// <Fill here>
		// local 변수 부터 찾기
		VarInfo lvar = (VarInfo) _lsymtable.get(name);
		if (lvar != null) {
			return lvar.id +"";
		}

		// 없으면, global 변수 찾기
		VarInfo gvar = (VarInfo) _gsymtable.get(name);
		if (gvar != null) {
			return gvar.id +"";
		}

		return "error";
	}
	
	Type getVarType(String name){
		VarInfo lvar = (VarInfo) _lsymtable.get(name);
		if (lvar != null) {
			return lvar.type;
		}
		
		VarInfo gvar = (VarInfo) _gsymtable.get(name);
		if (gvar != null) {
			return gvar.type;
		}
		
		return Type.ERROR;	
	}
	String newLabel() {
		return "label" + _labelID++;
	}
	
	String newTempVar() {
		String id = "";
		return id + _tempVarID--;
	}

	// global
	public String getVarId(Var_declContext ctx) {
		// <Fill here>
        String sname = "";
        sname += getVarId(ctx.IDENT().getText());
        return sname;
	}

	// local
	public String getVarId(Local_declContext ctx) {
		String sname = "";		// 변수의 이름 찾기
		sname += getVarId(ctx.IDENT().getText());
		return sname;
	}
	
}
