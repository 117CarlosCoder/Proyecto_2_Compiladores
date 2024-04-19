
package Jflex;
import java_cup.runtime.Symbol;
import Jcup.sym;
import static ServidorP.ServidorP.sendErrorMessage;
import java.io.IOException;

%%
%class Lexico
%public
%line
%char
%cup
%unicode
%ignorecase

%init{
    yyline = 1;
    yychar = 1;
%init}
%{
StringBuffer string = new StringBuffer();
%}
%state STRING
BLANCOS = [ \r\t]+
IDS = [_|-|$][a-z|A-Z]+
LETRAS = [-|_|#|$|%|a-z|A-Z|0-9|]+


%%

<YYINITIAL>"ID" {return new Symbol(sym.ID,yyline,(int) yychar, yytext());}
<YYINITIAL>"acciones" {return new Symbol(sym.ACCIONES,yyline,(int) yychar, yytext());}
<YYINITIAL>"accion" {return new Symbol(sym.ACCION,yyline,(int) yychar, yytext());}
<YYINITIAL>"parametros" {return new Symbol(sym.PARAMETROS,yyline,(int) yychar, yytext());}
<YYINITIAL>"parametro" {return new Symbol(sym.PARAMETRO,yyline,(int) yychar, yytext());}
<YYINITIAL>"atributos" {return new Symbol(sym.ATRIBUTOS,yyline,(int) yychar, yytext());}
<YYINITIAL>"atributo" {return new Symbol(sym.ATRIBUTO,yyline,(int) yychar, yytext());}
<YYINITIAL>"etiquetas" {return new Symbol(sym.ETIQUETAS,yyline,(int) yychar, yytext());}
<YYINITIAL>"etiqueta" {return new Symbol(sym.ETIQUETA,yyline,(int) yychar, yytext());}
<YYINITIAL>"nombre" {return new Symbol(sym.NOMBREAC,yyline,(int) yychar, yytext());}

<YYINITIAL> {
"<" {return new Symbol(sym.INICIO,yyline,(int) yychar, yytext());}
"/" {return new Symbol(sym.FINAL,yyline,(int) yychar, yytext());}
"=" {return new Symbol(sym.IGUAL,yyline,(int) yychar, yytext());}
">" {return new Symbol(sym.CIERRE,yyline,(int) yychar, yytext());}
//"[" {return new Symbol(sym.CORAB,yyline,(int) yychar, yytext());}
//"]" {return new Symbol(sym.CORCE,yyline,(int) yychar, yytext());}
\"  {return new Symbol(sym.COMILLA,yyline,(int) yychar, yytext());}
\[ {string.setLength(0); yybegin(STRING);}
\n { yychar=1;}
}

<YYINITIAL>{BLANCOS} {}
<YYINITIAL>{IDS} {return new Symbol(sym.IDS,yyline,(int) yychar, yytext());}
<YYINITIAL>{LETRAS} {return new Symbol(sym.LETRAS,yyline,(int) yychar, yytext());}

    <STRING> {
              [-|a-z|A-Z|0-9|-|_| |/|#|$|@|!|%|&|*|(|)|.|,|-|=|+|:|\/\/||]+         { string.append(yytext()); }
              \\t                          { string.append('\t'); }
              \\n                          { string.append('\n'); }
              \\r                          { string.append('\r'); }
              \\\"                         { string.append('\"'); }
              \\                           { string.append('\\'); }
              \]                           { yybegin(YYINITIAL); return new Symbol(sym.STRING_LITERAL, string.toString()); }
    }
.  {
    String acumulaErrores = "Este es un error lexico: "+yytext()+
                                ", en la linea: "+yyline+", en la columna: "+ (int) yychar ;
    System.out.println("Este es un error lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+ (int) yychar);
    try {
                sendErrorMessage(acumulaErrores);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
}
