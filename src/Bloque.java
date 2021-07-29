import java.nio.charset.Charset;

public class Bloque {
	private Palabra32bits[] bloque = new Palabra32bits[2];
	public static Bloque ALL_ZERO = new Bloque(new Palabra32bits(0), new Palabra32bits(0));
	
	public static String toString(Bloque[] bloques) {
		String stringBloques = "";
		for(Bloque bloque : bloques) {
			stringBloques += bloque.getString();
		}
		
		return stringBloques;
	}
	
	public static String toHexString(Bloque[] bloques) {
		String stringBloques = "";
		for(Bloque bloque : bloques) {
			stringBloques += bloque.getHexString();
		}
		
		return stringBloques;
	}
	
	private static Bloque[] getBloques(byte[] bytesString) {
		int modulo = bytesString.length % 8;
		
		if(modulo != 0)
			bytesString = agregarCeros(bytesString, 8 - modulo);
		
		Bloque[] bloques = new Bloque[bytesString.length / 8];
		for(int i = 0; i < bloques.length; i++) {
			int j = 8 * i;
			Palabra32bits left = new Palabra32bits(bytesString[j], bytesString[j + 1], bytesString[j + 2], bytesString[j + 3]);
			Palabra32bits right = new Palabra32bits(bytesString[j + 4], bytesString[j + 5], bytesString[j + 6], bytesString[j + 7]);
			
			bloques[i] = new Bloque(left, right);
		}
			
		return bloques;
		
	}
	
	public static Bloque[] getBloques(String cadena) {
		byte[] bytesString = cadena.getBytes(Charset.defaultCharset());
		return getBloques(bytesString);
	}
	
	public static Bloque[] getHexBloques(String cadena) {
		
		if(cadena.length() % 2 != 0)
			cadena = "0" + cadena;
		
		byte[] bytes = new byte[cadena.length() / 2];
		
		for(int i = 0, j = 0; i < cadena.length(); i += 2, j++) {
			String byteString = cadena.substring(i, i + 2);
			bytes[j] = hexToByte(byteString);
		}
		
		return getBloques(bytes);
	}
	
	private static byte hexToByte(String hexCadena) {
		byte[] cadenaBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(hexCadena);
		return cadenaBytes[0];
	}
	
	private static byte[] agregarCeros(byte[] cadenaBytes, int numCeros) {
		byte[] nuevaCadena = new byte[cadenaBytes.length + numCeros];
		for(int i = 0; i < nuevaCadena.length; i++) {
			if(i < cadenaBytes.length)
				nuevaCadena[i] = cadenaBytes[i];
			else
				nuevaCadena[i] = 0;
		}
		
		return nuevaCadena;
	}
	
	public Bloque(Palabra32bits l, Palabra32bits r) {
		bloque[0] = l;
		bloque[1] = r;
	}
	
	public Palabra32bits getLeft() {
		return bloque[0];
	}
	
	public Palabra32bits getRight() {
		return bloque[1];
	}
	
	public String getString() {
		return bloque[0].getString() + bloque[1].getString();
	}
	
	public String getHexString() {
		return bloque[0].getHexString() + bloque[1].getHexString();
	}
}