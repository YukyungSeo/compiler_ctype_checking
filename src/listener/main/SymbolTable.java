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
		INT, FLOAT, INTARRAY, VOID, ERROR
	}
	
	static public class VarINTInfo {
		Type type; 
		int id;
		int initVal;
		
		public VarINTInfo(Type type,  int id, int initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}

		public VarINTInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = 0;
		}
	}

	static public class VarFLOATInfo {
		Type type;
		int id;
		float initVal;

		public VarFLOATInfo(Type type,  int id, float initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}

		public VarFLOATInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = 0;
		}
	}
	
	static public class FInfo {
		public String sigStr;
	}
	
	private Map<String, VarINTInfo> _lintsymtable = new HashMap<>();	// local v. int
	private Map<String, VarINTInfo> _gintsymtable = new HashMap<>();	// global v. int
	private Map<String, VarFLOATInfo> _lfloatsymtable = new HashMap<>();	// local v. float
	private Map<String, VarFLOATInfo> _gfloatsymtable = new HashMap<>();	// global v. float
	private Map<String, FInfo> _fsymtable = new HashMap<>();	// function 
	
		
	private int _globalintVarID = 0;
	private int _localintVarID = 0;
	private int _globalfloatVarID = 0;
	private int _localfloatVarID = 0;
	private int _labelID = 0;
	private int _tempVarID = 0;
	
	SymbolTable(){
		initFunDecl();
		initFunTable();
	}
	
	void initFunDecl(){		// at each func decl
	    _lintsymtable.clear();
		_lfloatsymtable.clear();
		_localintVarID = 0;
		_localfloatVarID = 0;
		_labelID = 0;
		_tempVarID = 32;		
	}
	
	void putLocalVar(String varname, Type type){
		//<Fill here>
		if(type.equals(Type.INT)) {
			VarINTInfo vinfo = new VarINTInfo(type, _localintVarID++);
			_lintsymtable.put(varname, vinfo);
		} else if(type.equals(Type.FLOAT)) {
			VarFLOATInfo vinfo = new VarFLOATInfo(type, _localfloatVarID++);
			_lfloatsymtable.put(varname, vinfo);
		}
	}
	
	void putGlobalVar(String varname, Type type){
		//<Fill here>
		if(type.equals(Type.INT)) {
			VarINTInfo vinfo = new VarINTInfo(type, _globalintVarID++);
			_gintsymtable.put(varname, vinfo);
		} else if(type.equals(Type.FLOAT)) {
			VarFLOATInfo vinfo = new VarFLOATInfo(type, _globalfloatVarID++);
			_gfloatsymtable.put(varname, vinfo);
		}

	}
	
	void putLocalVarWithInitVal(String varname, Type type, String initVar){
		//<Fill here>
		if(type.equals(Type.INT)) {
			VarINTInfo vinfo = new VarINTInfo(type, _localintVarID++, Integer.parseInt(initVar));
			_lintsymtable.put(varname, vinfo);
		} else if(type.equals(Type.FLOAT)) {
			VarFLOATInfo vinfo = new VarFLOATInfo(type, _localfloatVarID++, Float.parseFloat(initVar));
			_lfloatsymtable.put(varname, vinfo);
		}
//		VarInfo vinfo = new VarInfo(type, _localVarID++, initVar);
//		_lsymtable.put(varname, vinfo);
	}
	void putGlobalVarWithInitVal(String varname, Type type, String initVar){
		//<Fill here>
		if(type.equals(Type.INT)) {
			VarINTInfo vinfo = new VarINTInfo(type, _globalintVarID++, Integer.parseInt(initVar));
			_gintsymtable.put(varname, vinfo);
		} else if(type.equals(Type.FLOAT)) {
			VarFLOATInfo vinfo = new VarFLOATInfo(type, _globalfloatVarID++, Float.parseFloat(initVar));
			_gfloatsymtable.put(varname, vinfo);
		}
//		VarInfo vinfo = new VarInfo(type, _globalVarID++, initVar);
//		_gsymtable.put(varname, vinfo);
	}
	
	void putParams(ParamsContext params) {
		for(int i = 0; i < params.param().size(); i++) { // parameter 한 개씩 확인
			//<Fill here>
			// param type 확인
			Type type = null;
			String typestr = params.param(i).getChild(0).getText();
			if (typestr.equals("int")) {
				type = Type.INT;
			} else if(typestr.equals("float")){
				type = Type.FLOAT;
			} else if (typestr.equals("void")) {
				type = Type.VOID;
			}

			// param의 정보를 VarInfo로 만들어 _lsystable에 저장
			String varname = params.param(i).getChild(1).getText();
			if(type.equals(Type.INT)) {
				VarINTInfo vinfo = new VarINTInfo(type, _localintVarID++);
				_lintsymtable.put(varname, vinfo);
			} else if(type.equals(Type.FLOAT)) {
				VarFLOATInfo vinfo = new VarFLOATInfo(type, _localfloatVarID++);
				_lfloatsymtable.put(varname, vinfo);
			}
//			if (params.param(i).getChildCount() == 2) {
//				vinfo = new VarInfo(type, _localVarID++);
//			} else {	// int x = 0... 이럴 경우는 없음
////				int initval = Integer.parseInt(params.param(i).getChild(3).getText());
//				String initval = params.param(i).getChild(3).getText();
//				vinfo = new VarInfo(type, _localVarID++, initval);
//			}
//			_lsymtable.put(varName, vinfo);
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
		VarINTInfo livar = (VarINTInfo) _lintsymtable.get(name);
		if (livar != null) {
			return livar.id +"";
		}
		VarFLOATInfo lfvar = (VarFLOATInfo) _lfloatsymtable.get(name);
		if (lfvar != null) {
			return lfvar.id +"";
		}

		// 없으면, global 변수 찾기
		VarINTInfo givar = (VarINTInfo) _gintsymtable.get(name);
		if (givar != null) {
			return givar.id +"";
		}
		VarFLOATInfo gfvar = (VarFLOATInfo) _gfloatsymtable.get(name);
		if (gfvar != null) {
			return gfvar.id +"";
		}


		return "error";
	}
	
	Type getVarType(String name){
		VarINTInfo livar = (VarINTInfo) _lintsymtable.get(name);
		if (livar != null) {
			return livar.type;
		}
		VarFLOATInfo lfvar = (VarFLOATInfo) _lfloatsymtable.get(name);
		if (lfvar != null) {
			return lfvar.type;
		}
		
		VarINTInfo givar = (VarINTInfo) _gintsymtable.get(name);
		if (givar != null) {
			return givar.type;
		}
		VarFLOATInfo gfvar = (VarFLOATInfo) _gfloatsymtable.get(name);
		if (gfvar != null) {
			return gfvar.type;
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
