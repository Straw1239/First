package main;
import java.util.*;
public class Quine {
static Deque<String> toPrint = new LinkedList<>();
static Deque<String> printA = new LinkedList<>();
public static void main(String[] args) {
print("package main;");
print("import java.util.*;");
print("public class Quine {");
print("static Deque<String> toPrint = new LinkedList<>();");
print("static Deque<String> printA = new LinkedList<>();");
print("public static void main(String[] args) {");
printA("toPrint.addAll(printA);");
printA("while(!toPrint.isEmpty())System.out.println(toPrint.remove());");
printA("}");
printA("public static void print(String arg){");
printA("System.out.println(arg);");
printA("toPrint.add(\"print(\\\"\"+arg+\"\\\");\");}");
printA("public static void printA(String arg){");
printA("toPrint.addLast(\"printA(\\\"\"+expand(arg)+\"\\\");\");");
printA("printA.addLast(expand(arg));}");
printA("public static String expand(String arg){");
printA("ArrayList<Character> result = new ArrayList<Character>();");
printA("for(int i = 0; i < arg.length();i++){");
printA("if(arg.charAt(i) == '\\'){result.add('\\');result.add('\\');}");
printA("else if(arg.charAt(i) == '\"'){result.add('\\');result.add('\"');}");
printA("else result.add(arg.charAt(i));}");
printA("char[] rr = new char[result.size()];");
printA("for(int i = 0; i < result.size();i++)rr[i] = result.get(i).charValue();");
printA("return new String(rr);}}");
toPrint.addAll(printA);
while(!toPrint.isEmpty())System.out.println(toPrint.remove());
}
public static void print(String arg){
System.out.println(arg);
toPrint.add("print(\""+arg+"\");");}
public static void printA(String arg){
toPrint.addLast("printA(\""+expand(arg)+"\");");
printA.addLast(expand(arg));}
public static String expand(String arg){
ArrayList<Character> result = new ArrayList<Character>();
for(int i = 0; i < arg.length();i++){
if(arg.charAt(i) == '\\'){result.add('\\');result.add('\\');}
else if(arg.charAt(i) == '\"'){result.add('\\');result.add('\"');}
else result.add(arg.charAt(i));}
char[] rr = new char[result.size()];
for(int i = 0; i < result.size();i++)rr[i] = result.get(i).charValue();
return new String(rr);}}