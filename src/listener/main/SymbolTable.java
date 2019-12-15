package listener.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import gen.*;
import gen.MiniCParser.*;

import listener.main.SymbolTable.Type;
import static listener.main.BytecodeGenListenerHelper.*;


public class SymbolTable {
	enum Type {
		INT, FLOAT, INTARRAY, FLOATARRAY, VOID, ERROR
	}
	
	static public class VarInfo {
		Type type; 
		int id;
		String initVal;
		
		public VarInfo(Type type,  int id, String initVal) {
			this.type = type;
			this.id = id;
			this.initVal = initVal;
		}

		public VarInfo(Type type,  int id) {
			this.type = type;
			this.id = id;
			this.initVal = "";
		}
	}
	
	static public class FInfo {
		public String sigStr;
		public Type[] paramsT;
		public Type returnT;
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
	
	void putLocalVarWithInitVal(String varname, Type type, String initVar){
		//<Fill here>
		VarInfo vinfo = new VarInfo(type, _localVarID++, initVar);
		_lsymtable.put(varname, vinfo);
	}
	void putGlobalVarWithInitVal(String varname, Type type, String initVar){
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
			} else if(typestr.equals("float")){
				type = Type.FLOAT;
			} else if (typestr.equals("int[]")) {
				type = Type.INTARRAY;
			} else if (typestr.equals("float[]")) {
				type = Type.FLOATARRAY;
			}else if (typestr.equals("void")) {
				type = Type.VOID;
			}

			// param의 정보를 VarInfo로 만들어 _lsystable에 저장
			String varname = params.param(i).getChild(1).getText();
			VarInfo vinfo = new VarInfo(type, _localVarID++);
			_lsymtable.put(varname, vinfo);
		}
	}
	
	private void initFunTable() {
		FInfo printlninfo = new FInfo();
		printlninfo.sigStr = "java/io/PrintStream/println";
		
		FInfo maininfo = new FInfo();
		maininfo.sigStr = "main([Ljava/lang/String;)V";
		maininfo.paramsT = new Type[1];
		maininfo.paramsT[0] = Type.VOID;
		maininfo.returnT = Type.VOID;
		_fsymtable.put("_print", printlninfo);
		_fsymtable.put("main", maininfo);
	}
	
	public String getFunSpecStr(String fname) {		
		// <Fill here>
        FInfo fInfo = _fsymtable.get(fname);
        return fInfo.sigStr;
	}

	public FInfo getFunSpec(String fname) {
		// <Fill here>
		return _fsymtable.get(fname);
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

		FInfo finfo = new FInfo();

		res +=  fname + "(" + argtype + ")" + rtype;

		int count = getCharCount(argtype);
		finfo.paramsT = new Type[argtype.length() - count];
		int index = 0;
		for(int i=0; i<argtype.length(); i++){
			Type type = getTypefromString(argtype.charAt(i));
			if(type == null){
				if(argtype.charAt(i+1) == 'I')
					finfo.paramsT[index] = Type.INTARRAY;
				else
					finfo.paramsT[index] = Type.FLOATARRAY;
				i++;
			} else {
				finfo.paramsT[index] = type;
			}
			index++;
		}
		finfo.sigStr = res;
		Type type = getTypefromString(rtype.charAt(0));
		if(type == null){
			if(argtype.charAt(1) == 'I')
				finfo.returnT = Type.INTARRAY;
			else
				finfo.returnT = Type.FLOATARRAY;
		} else {
			finfo.returnT = type;
		}
		_fsymtable.put(fname, finfo);
		
		return res;
	}

	int getCharCount(String str){
		int count = 0;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i) == '[')
				count++;
		}
		return count;
	}

	Type getTypefromString(char c){
		switch(c){
			case 'I':
				return Type.INT;
			case 'F':
				return Type.FLOAT;
			case '[':
				return null;
			case 'V':
				return Type.VOID;
		}
		return Type.ERROR;
	}
	
	String getVarId(String name){
		// <Fill here>
		// local 변수 부터 찾기
		VarInfo livar = (VarInfo) _lsymtable.get(name);
		if (livar != null) {
			return livar.id +"";
		}

		// 없으면, global 변수 찾기
		VarInfo givar = (VarInfo) _gsymtable.get(name);
		if (givar != null) {
			return givar.id +"";
		}


		return "error";
	}
	
	Type getVarType(String name){
		VarInfo livar = (VarInfo) _lsymtable.get(name);
		if (livar != null) {
			return livar.type;
		}
		
		VarInfo givar = (VarInfo) _gsymtable.get(name);
		if (givar != null) {
			return givar.type;
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
