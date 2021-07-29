import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

public class Palabra32bits {
	private byte[] palabra = new byte[4];
	
	public Palabra32bits() {} //Inicializa el arreglo "palabra" en ceros
	
	public Palabra32bits(byte b1, byte b2, byte b3, byte b4) {
		 palabra[0] = b1;
		 palabra[1] = b2;
		 palabra[2] = b3;
		 palabra[3] = b4;
	}
	
	public Palabra32bits(byte[] palabraBytes) {
		for(int i = 0; i < palabra.length; i++)
			palabra[i] = palabraBytes[i];
	}
	
	public Palabra32bits(int palabra) {
		this.palabra = ByteBuffer.allocate(4).putInt(palabra).array();
	}
	
	public byte getByte(int indice) {
		return palabra[indice];
	}
	
	public Palabra32bits xor(Palabra32bits palabra) {
		byte[] resultado = new byte[4];
		for(int i = 0; i < resultado.length; i++)
			resultado[i] = (byte) (this.palabra[i] ^ palabra.palabra[i]);
		
		return new Palabra32bits(resultado[0], resultado[1], resultado[2], resultado[3]);
	}
	
	public void intercambiar(Palabra32bits palabra) {
		byte[] aux = this.palabra;
		this.palabra = palabra.palabra;
		palabra.palabra = aux;
		
	}
	
	public Palabra32bits sumar(Palabra32bits palabra) {
		int intPalabra1 = ByteBuffer.wrap(this.palabra).getInt();
		int intPalabra2 = ByteBuffer.wrap(palabra.palabra).getInt();
		int suma = intPalabra1 + intPalabra2;
		
		Palabra32bits resultado = new Palabra32bits(ByteBuffer.allocate(4).putInt(suma).array());
		return resultado;
		
	}
	
	public String getString() {
		return new String(palabra, Charset.defaultCharset());
	}
	
	public static Palabra32bits[] intToPalabra(int[] palabrasInt) {
		Palabra32bits[] palabras32bits = new Palabra32bits[palabrasInt.length];
		
		for(int i = 0; i < palabras32bits.length; i++)
			palabras32bits[i] = new Palabra32bits(palabrasInt[i]);
		
		return palabras32bits;
	}

	@Override
	public String toString() {
		String cadenaHex = javax.xml.bind.DatatypeConverter.printHexBinary(palabra);
		
		return cadenaHex;
	}
	
	public String getHexString() {
		return DatatypeConverter.printHexBinary(palabra);
	}
	
}